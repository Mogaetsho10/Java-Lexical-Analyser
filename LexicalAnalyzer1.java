/*

Lexical Analyzer to identify a C program's:
keywords
functions
special symbols
operators

*/

import java.util.*;
import java.io.*;
import java.nio.file.*;

class LexicalAnalyzer {
    static List<String> adverbs;
    static List<String> english;
    static List<String> lediri;
    static List<String> matlhalosi;
    static List<String> nounClass;
    static List<String> sets_possessive;
    static List<String> eng_possessive;
    static List<String> sets_relative;
    static List<String> eng_relative;
    static List<String> setswana;
    static List<String> verb;
    static PrintWriter pw;
    static String[][] faTable =  { 
                {"States", "a-z","A-Z","_","0-9"},
                {"q0", "q1","q1","q1","q0"},
                {"q1", "q1","q1","q1","q0"}
         }; 
    static String state = faTable[1][0]; 
         
    public static void main(String args[]) throws Exception {
        initializeTables();
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("input1.txt"))));
        pw = new PrintWriter(new FileOutputStream(new File("output1.txt")), true);
        String s = new String();
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
    

    static void printToken(String s) throws IOException {
       
        
        try{
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
        }catch (Exception e){
              System.out.println (e.getMessage());
        }
    }

    public static int countLinesOld(String filename) throws IOException {
    InputStream is = new BufferedInputStream(new FileInputStream(filename));
    try {
        byte[] c = new byte[1024];
        int count = 0;
        int readChars = 0;
        boolean empty = true;
        while ((readChars = is.read(c)) != -1) {
            empty = false;
            for (int i = 0; i < readChars; ++i) {
                if (c[i] == '\n') {
                    ++count;
                }
            }
        }
        return (count == 0 && !empty) ? 1 : count;
    } finally {
        is.close();
    }
   }
    
   static int grepLineNumber(String file, String word) throws Exception {
    BufferedReader buf = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(file))));

    String line;
    int lineNumber = 0;
    while ((line = buf.readLine()) != null)   {
        lineNumber++;
        if (word.equals(line)) {
            return lineNumber;
        }
    }
    return -1;
    }
    
   
    
    static String getTokenType(String s) throws Exception {
        int index = 0;
        String lastString = "";
     
        if((index = Collections.binarySearch(english, s)) >= 0) {
            int a = grepLineNumber("english.txt", s);
            String extractedLine = Files.readAllLines(Paths.get("C:\\Users\\mogae\\Desktop\\CSI428 A3\\setswana.txt")).get(a);
            lastString = lastString+extractedLine;
            return (extractedLine);
            
        }
        if((index = Collections.binarySearch(eng_relative, s)) >= 0) {
            int b = grepLineNumber("eng_relative.txt", s);
            String extractedLine1 = Files.readAllLines(Paths.get("C:\\Users\\mogae\\Desktop\\CSI428 A3\\sets_relative.txt")).get(b);
            lastString = lastString+extractedLine1;
            return (extractedLine1);
        }
        if((index = Collections.binarySearch(verb, s)) >= 0) {
            int c = grepLineNumber("verb.txt", s);
            String extractedLine2 = Files.readAllLines(Paths.get("C:\\Users\\mogae\\Desktop\\CSI428 A3\\lediri.txt")).get(c);
            lastString = lastString+extractedLine2;
            return (extractedLine2);
        }
        if((index = Collections.binarySearch(adverbs, s)) >= 0) {
            int d = grepLineNumber("adverb.txt", s);
            String extractedLine3 = Files.readAllLines(Paths.get("C:\\Users\\mogae\\Desktop\\CSI428 A3\\matlhalosi.txt")).get(d);
            lastString = lastString+extractedLine3;
            return (extractedLine3);
        }
        // int ctr = 0;
        // for(String i : identifiers) {
                    // if(i.equalsIgnoreCase(s)) {
                         // return ("IDENTIFIER - " + ctr);
            // }
            // ctr++;
        // }
        
        // try {
            // int lit = Integer.parseInt(s);
            // literals.add(lit);
            // return ("LITERAL"+(literals.size()-1));
        // } catch(NumberFormatException e) {
            // identifiers.add(s);
        // }
        
         return (lastString);
    }
    
    
    static void initializeTables() throws Exception {
        english = new LinkedList<>();
        setswana = new LinkedList<>();
        eng_relative = new LinkedList<>();
        sets_relative = new LinkedList<>();
        verb = new LinkedList<>();
        lediri = new LinkedList<>();
        adverbs = new LinkedList<>();
        matlhalosi = new LinkedList<>();
        eng_possessive = new LinkedList<>();
        sets_possessive = new LinkedList<>();
        
        BufferedReader br;
        br = new BufferedReader(new InputStreamReader(new FileInputStream("english.txt")));
        System.out.println("English nouns:");
        readIntoList(english, br);
        
        br = new BufferedReader(new InputStreamReader(new FileInputStream("setswana.txt")));
        System.out.println("\nSetswana nouns:");
        readIntoList(setswana, br);
        
        br = new BufferedReader(new InputStreamReader(new FileInputStream("eng_relative.txt")));
        System.out.println("\nEnglish Relative:");
        readIntoList(eng_relative, br);
        
        br = new BufferedReader(new InputStreamReader(new FileInputStream("sets_relative.txt")));
        System.out.println("\nSetswana Relative:");
        readIntoList(sets_relative, br);
        
        br = new BufferedReader(new InputStreamReader(new FileInputStream("verb.txt")));
        System.out.println("\nVerbs:");
        readIntoList(verb, br);
        
        br = new BufferedReader(new InputStreamReader(new FileInputStream("lediri.txt")));
        System.out.println("\nLediri:");
        readIntoList(lediri, br);
        
        br = new BufferedReader(new InputStreamReader(new FileInputStream("adverbs.txt")));
        System.out.println("\nAdverb:");
        readIntoList(adverbs, br);
        
        br = new BufferedReader(new InputStreamReader(new FileInputStream("matlhalosi.txt")));
        System.out.println("\nMatlhalosi:");
        readIntoList(matlhalosi, br);
        
        br = new BufferedReader(new InputStreamReader(new FileInputStream("eng_possessive.txt")));
        System.out.println("\nEngish Possessives:");
        readIntoList(eng_possessive, br);
        
        br = new BufferedReader(new InputStreamReader(new FileInputStream("sets_possessive.txt")));
        System.out.println("\nSetswana Possessive:");
        readIntoList(sets_possessive, br);
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
