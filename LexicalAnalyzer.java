/*

Lexical Analyzer to identify a C program's:
keywords
functions
special symbols
operators

*/

import java.util.*;
import java.io.*;

class LexicalAnalyzer {
    static List<String> keywords;
    static List<String> functions;
    static List<String> special_symbols;
    static List<String> operators;
    static List<String> identifiers;
    static List<Integer> literals;
    static PrintWriter pw;
    static String[][] faTable =  { 
                {"States", "a-z","A-Z","_","0-9"},
                {"q0", "q1","q1","q1","q0"},
                {"q1", "q1","q1","q1","q0"}
         }; 
    static String state = faTable[1][0]; 
         
    public static void main(String args[]) throws Exception {
        initializeTables();
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("input.txt"))));
        pw = new PrintWriter(new FileOutputStream(new File("output.txt")), true);
        String s = new String();
        String state = "q0"; 
        while((s = br.readLine()) != null) {
            StringTokenizer st = new StringTokenizer(s, " ,+=-*%/#<>;", true);
            while(st.hasMoreTokens()) {
                printToken(st.nextToken());
            }
            pw.println();
        }
        
    
        // while((s = br.readLine()) != null) {
            // StringTokenizer st1 = new StringTokenizer(s, " ,+=-*%/#<>;", true);
            // while(st1.hasMoreTokens()) {
                // // printToken(st.nextToken());
                // if(s.substring(0, 1).matches("[0-9]")){ state = "q0";}
                // else if(s.substring(0, 1).matches("[a-z]")){
                    // state = "q1";
                // }
                // else if(s.substring(0, 1).matches("[A-Z]")){
                    // state = "q1";
                // }
                // else if(s.substring(0, 1).matches("_")){
                    // state = "q1";
                // }
            // }
            // pw.println();
            
        // }
        // System.out.println(state);
    }
    

    static void printToken(String s) {
        if(!s.equals(" ")) {
            String x = getTokenType(s);
            pw.print(x+" ");
            if(s.substring(0, 1).matches("[0-9]")){ state = faTable[1][4];}
             else if(s.substring(0, 1).matches("[a-z]")){
                    state = faTable[1][1]; 
                }
                else if(s.substring(0, 1).matches("[A-Z]")){
                    state = faTable[1][2]; ;
                }
                else if(s.substring(0, 1).matches("_")){
                    state = faTable[1][3]; ;
                }
        }
        System.out.print(" state: " + state + ",");
        if(state.equals("q1")){
            System.out.print("The machine has reached final state therefore is a token ");
        }else{
            
            System.out.print("The machine has remained in initial state q0 therefore is a not a token ");
        }
    }

    static String getTokenType(String s) {
        int index = 0;
        if((index = Collections.binarySearch(keywords, s)) >= 0) {
            return ("KEYWORD - " + s);
        }
        if((index = Collections.binarySearch(functions, s)) >= 0) {
            return ("FUNCTION - " + s);
        }
        if((index = Collections.binarySearch(operators, s)) >= 0) {
            return ("OPRERATOR - " + s);
        }
        if((index = Collections.binarySearch(special_symbols, s)) >= 0) {
            return ("SPECIAL CHARACTER - " + s);
        }
        int ctr = 0;
        for(String i : identifiers) {
                    if(i.equalsIgnoreCase(s)) {
                         return ("IDENTIFIER - " + ctr);
            }
            ctr++;
        }
        
        try {
            int lit = Integer.parseInt(s);
            literals.add(lit);
            return ("LITERAL"+(literals.size()-1));
        } catch(NumberFormatException e) {
            identifiers.add(s);
        }
        
        return ("IDENTIFIER - "+(identifiers.size() - 1));
    }
    
    
    static void initializeTables() throws Exception {
        keywords = new LinkedList<>();
        functions = new LinkedList<>();
        operators = new LinkedList<>();
        special_symbols = new LinkedList<>();
        identifiers = new LinkedList<>();
        literals = new LinkedList<>();
        
        BufferedReader br;
        br = new BufferedReader(new InputStreamReader(new FileInputStream("keywords.txt")));
        System.out.println("Keywords:");
        readIntoList(keywords, br);
        br = new BufferedReader(new InputStreamReader(new FileInputStream("functions.txt")));
        System.out.println("\nFunctions:");
        readIntoList(functions, br);
        br = new BufferedReader(new InputStreamReader(new FileInputStream("operators.txt")));
        System.out.println("\nOperators:");
        readIntoList(operators, br);
        br = new BufferedReader(new InputStreamReader(new FileInputStream("special_symbols.txt")));
        System.out.println("\nSpecial Symbols:");
        readIntoList(special_symbols, br);
    }
    
    static void readIntoList(List<String> l, BufferedReader br) throws Exception {
        String s;
        while((s = br.readLine()) != null) {
            l.add(s);
        }
        Collections.sort(l);
        int ctr = 0;
        for(String i : l) {
            System.out.println((ctr++) + ".) " + i);
        }
    }
}
