import java.io.*;
import java.util.*;
import java.util.stream.IntStream;

public class Impl {
    Impl() {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        BufferedReader reader = null;
        String CommentStore = "";
        String LibraryStore = "";
        String CharId = "";
        String IntId = "";
        String FloatId = "";
        ArrayList<String> intid=new ArrayList<String>();
        int Total = 0, LibName = 0;
        int Comment = 0;
        int /*Constant = 0,*/ Pointer = 0/*, PrintedToken = 0*/,Address=0;
        int Btotal = 0, Puntotal = 0, Optotal = 0, Comptotal = 0, Retotal = 0, Fmtotal = 0;
        int[] Bracket = new int[6];
        int[] Punctuation = new int[6];
        int[] Operator = new int[11];
        int[] Comparator = new int[6];
        int[] Identifier = new int[3];// 0 char 1 int 2 float
        int[] ResWord = new int[16];
        int[] FmSpecifier = new int[15];
        int i, j,k;
        int x = 0;// for comment /* */ 0 for false 1 for true
        int y = 0;// for "
        int z = 0; // for identifier char=1 int=2 float=3
        int temp=0;
        for (i = 0; i < 3; i++)
            Identifier[i] = 0;
        for (i = 0; i < 16; i++)
            ResWord[i] = 0;
        for (i = 0; i < 6; i++) {
            Bracket[i] = 0;
            Punctuation[i] = 0;
            Comparator[i] = 0;
        }
        for (i = 0; i < 11; i++)
            Operator[i] = 0;
        for (i = 0; i < 15; i++)
            FmSpecifier[i] = 0;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(input), "UTF-8"));
            String str = null;
            while ((str = reader.readLine()) != null) {
                for (i = 0; i < str.length(); i++) {
                    if (x == 1) {
                        if (str.indexOf("*/") < 0) {
                            CommentStore = CommentStore + str.substring(i);
                            break;
                        } else {
                            x = 0;
                            CommentStore = CommentStore + str.substring(i, str.indexOf("*/") + 2);
                            i = str.indexOf("*/") + 2;
                        }
                    }
                    /*
                     * if(y==1){ if(str.substring(i,str.length()-1).indexOf('\"')!=-1){
                     * i=str.substring(i,str.length()-1).indexOf('\"')+1; y=0; } else break; }
                     */
                    if (z == 1) {
                        if ((str.charAt(i) >= 'A' && str.charAt(i) <= 'Z')
                                || (str.charAt(i) >= 'a' && str.charAt(i) <= 'z')) {
                            for (j = i; j < str.length(); j++) {
                                if (str.charAt(j) != ',' && str.charAt(j) != ';'&&str.charAt(j)!=' ')
                                    CharId += str.charAt(j);
                                else if (str.charAt(j) == ',')
                                    CharId += ",";
                                else if (str.charAt(j) == ';') {
                                    z = 0;
                                }
                            }
                        } else {
                            z = 0;
                            break;
                        }
                    } else if (z == 2) {
                        if ((str.charAt(i) >= 'A' && str.charAt(i) <= 'Z')
                                || (str.charAt(i) >= 'a' && str.charAt(i) <= 'z')) {
                            System.out.println("11111111");
                            for (j = i; j < str.length(); j++) {
                                if (str.charAt(j) != ',' && str.charAt(j) != ';'&&str.charAt(j)!=' ')
                                    IntId += str.charAt(j);
                                else if (str.charAt(j) == ',')
                                    IntId += ",";
                                else if (str.charAt(j) == ';') {
                                    z = 0;
                                    /*for(k=0;k<IntId.length();k++){
                                        if(IntId.charAt(k)==','){
                                            intid.add(IntId.substring(temp,k));
                                            temp=k+1;
                                            System.out.println(intid.size());
                                        }
                                    }*/
                                }
                            }
                        } else {
                            z = 0;
                            break;
                        }
                    } else if (z == 3) {
                        if ((str.charAt(i) >= 'A' && str.charAt(i) <= 'Z')
                                || (str.charAt(i) >= 'a' && str.charAt(i) <= 'z')) {
                            for (j = i; j < str.length(); j++) {
                                if (str.charAt(j) != ',' && str.charAt(j) != ';')
                                    FloatId += str.charAt(j);
                                else if (str.charAt(j) == ',')
                                    FloatId += ",";
                                else if (str.charAt(j) == ';') {
                                    z = 0;
                                }
                            }
                        } else {
                            z = 0;
                            break;
                        }
                    }
                    try {
                        if (str.charAt(i) == '/' && str.charAt(i + 1) == '*') {
                            Comment++;
                            if (str.indexOf("*/") < 0) {
                                x = 1;
                                CommentStore = CommentStore + str.substring(i);
                                CommentStore += "\n";
                                break;
                            } else {
                                CommentStore = CommentStore + str.substring(i, str.indexOf("*/") + 2);
                                i = str.indexOf("*/") + 2;
                            }
                        } else if (str.charAt(i) == '/' && str.charAt(i + 1) == '/') {
                            Comment++;
                            CommentStore = CommentStore + str.substring(i);
                            i+=1;
                            continue;
                        } else if (str.charAt(i) == '\"') {
                            // if(y==0)
                            // y=1;
                            Punctuation[4]++;
                        } else if (str.charAt(i) == '<') {
                            if (str.indexOf(".h>") != -1) {
                                LibraryStore = LibraryStore + str.substring(i, str.indexOf(".h>") + 3);
                                LibraryStore += " ";
                                LibName++;
                                i = str.indexOf(".h>") + 3;
                            } else if (str.charAt(i + 1) == '=') {
                                Comparator[3]++;
                                i++;
                            } else
                                Comparator[1]++;
                        } else if (str.charAt(i) == 'i' || str.charAt(i) == 'I') {
                            if ((str.substring(i + 1, i + 7)).equalsIgnoreCase("nclude")) {
                                ResWord[0]++;
                                i += 6;
                            } else if (str.charAt(i + 1) == 'f' || str.charAt(i + 1) == 'F') {
                                ResWord[5]++;
                                i += 1;
                            } else if (str.substring(i + 1, i + 4).equalsIgnoreCase("nt ")) {
                                ResWord[3]++;
                                i += 3;
                                z = 2;
                            }
                        } else if (str.charAt(i) == 'c' || str.charAt(i) == 'C') {
                            if (str.substring(i + 1, i + 5).equalsIgnoreCase("har ")) {
                                ResWord[2]++;
                                i += 4;
                                z = 1;
                            } else if (str.substring(i + 1, i + 4).equalsIgnoreCase("ase")) {
                                ResWord[13]++;
                                i += 3;
                            }
                        } else if (str.charAt(i) == 'f' || str.charAt(i) == 'F') {
                            if (str.substring(i + 1, i + 6).equalsIgnoreCase("loat ")) {
                                ResWord[4]++;
                                i += 5;
                                z = 3;
                            } else if (str.substring(i + 1, i + 3).equalsIgnoreCase("or")) {
                                if(str.charAt(i+3)==' '||str.charAt(i+3)=='('){
                                    ResWord[8]++;
                                    i += 3;
                                }
                                
                            }
                        } else if (str.charAt(i) == 'e' || str.charAt(i) == 'E') {
                            if (str.substring(i + 1, i + 4).equalsIgnoreCase("lse")) {
                                if (str.substring(i + 4, i + 6).equalsIgnoreCase("if")) {
                                    ResWord[7]++;
                                    i += 5;
                                } else {
                                    ResWord[6]++;
                                    i += 3;
                                }
                            }
                        } else if (str.charAt(i) == 'm' || str.charAt(i) == 'M') {
                            if (str.substring(i + 1, i + 4).equalsIgnoreCase("ain")) {
                                ResWord[1]++;
                                i += 3;
                            }
                        } else if (str.charAt(i) == 'w' || str.charAt(i) == 'W') {
                            if (str.substring(i + 1, i + 5).equalsIgnoreCase("hile")) {
                                if(str.charAt(i+5)==' '||str.charAt(i+5)=='('){
                                ResWord[9]++;
                                i += 4;
                                }
                            }
                        } else if (str.charAt(i) == 'd' || str.charAt(i) == 'D') {
                            if (str.charAt(i + 1) == 'o' || str.charAt(i + 1) == 'O') {
                                ResWord[10]++;
                                i += 1;
                            }
                        } else if (str.charAt(i) == 'r' || str.charAt(i) == 'R') {
                            if (str.substring(i + 1, i + 6).equalsIgnoreCase("eturn")) {
                                ResWord[11]++;
                                i += 5;
                            }
                        } else if (str.charAt(i) == 's' || str.charAt(i) == 'S') {
                            if (str.substring(i + 1, i + 6).equalsIgnoreCase("witch")) {
                                ResWord[12]++;
                                i += 5;
                            } else if (str.substring(i, i + 5).equalsIgnoreCase("canf")) {
                                ResWord[15]++;
                                i += 4;
                            }
                        } else if (str.charAt(i) == 'p' || str.charAt(i) == 'P') {
                            if (str.substring(i + 1, i + 6).equalsIgnoreCase("rintf")) {
                                ResWord[14]++;
                                i += 5;
                            }
                        } else if (str.charAt(i) == '+') {
                            if (str.charAt(i + 1) == '+') {
                                Operator[6]++;
                                i++;
                            } else
                                Operator[0]++;
                        } else if (str.charAt(i) == '-') {
                            if (str.charAt(i + 1) == '-') {
                                Operator[7]++;
                                i++;
                            } else
                                Operator[1]++;
                        } else if (str.charAt(i) == '/') {
                            Operator[3]++;
                        } else if (str.charAt(i) == '%') {
                            if (str.charAt(i + 1) == 'd') {
                                FmSpecifier[0]++;
                                i++;
                            } else if (str.charAt(i + 1) == 'f') {
                                FmSpecifier[1]++;
                                i++;
                            } else if (str.charAt(i + 1) == 'c') {
                                FmSpecifier[2]++;
                                i++;
                            } else
                                Operator[4]++;
                        } else if (str.charAt(i) == '\\') {
                            if (str.charAt(i + 1) == 'n')
                                FmSpecifier[3]++;
                            else if (str.charAt(i + 1) == 'r')
                                FmSpecifier[4]++;
                            else if (str.charAt(i + 1) == 't')
                                FmSpecifier[5]++;
                        } else if (str.charAt(i) == '^') {
                            Operator[5]++;
                        } else if (str.charAt(i) == '>') {
                            if (str.charAt(i + 1) == '=') {
                                Comparator[4]++;
                                i++;
                            } else
                                Comparator[2]++;
                        } else if (str.charAt(i) == '=') {
                            if (str.charAt(i + 1) == '=') {
                                Comparator[0]++;
                                i += 1;
                            } else
                                Operator[10]++;
                        } else if (str.charAt(i) == '!' && str.charAt(i + 1) == '=') {
                            Comparator[5]++;
                            i += 1;
                        } else if (str.charAt(i) == ',') {
                            Punctuation[0]++;
                        } else if (str.charAt(i) == ';') {
                            Punctuation[1]++;
                        } else if (str.charAt(i) == ':') {
                            Punctuation[2]++;
                        } else if (str.charAt(i) == '#') {
                            Punctuation[3]++;
                        } else if (str.charAt(i) == '\'') {
                            Punctuation[5]++;
                        } else if (str.charAt(i) == '(') {
                            Bracket[0]++;
                        } else if (str.charAt(i) == ')') {
                            Bracket[1]++;
                        } else if (str.charAt(i) == '[') {
                            Bracket[2]++;
                        } else if (str.charAt(i) == ']') {
                            Bracket[3]++;
                        } else if (str.charAt(i) == '{') {
                            Bracket[4]++;
                        } else if (str.charAt(i) == '}') {
                            Bracket[5]++;
                        } else if (str.charAt(i) == '*') { 
                            if((str.charAt(i+1) >= 'A' && str.charAt(i+1) <= 'Z')
                            || (str.charAt(i+1) >= 'a' && str.charAt(i+1) <= 'z'))
                            Pointer++; 
                            else
                            Operator[2]++;
                        } else if(str.charAt(i)=='&'){
                            if((str.charAt(i+1) >= 'A' && str.charAt(i+1) <= 'Z')
                            || (str.charAt(i+1) >= 'a' && str.charAt(i+1) <= 'z'))
                            Address++;
                            else
                            Operator[8]++;
                        }
                        
                           
                    } catch (Exception e) {
                    }
                }
            }
            System.out.println(IntId);
            Retotal = IntStream.of(ResWord).sum();
            Comptotal = IntStream.of(Comparator).sum();
            Optotal = IntStream.of(Operator).sum();
            Btotal = IntStream.of(Bracket).sum();
            Puntotal = IntStream.of(Punctuation).sum();
            Fmtotal = IntStream.of(FmSpecifier).sum();
            Total = Comptotal + Optotal + Btotal + Puntotal + Retotal + Fmtotal;

            File file = new File("./" + "scanner_" + input);
            file.createNewFile();
            if (!file.exists())
                file.mkdirs();
            FileWriter fw = new FileWriter(file);
            // fw.write(System.getProperty("line.separator"));//another \n
            fw.write("Total: " + Total + " tokens\n\n");
            fw.write("Reserved word: " + Retotal + "\n");
            if(ResWord[0]!=0)
            fw.write("include (x"+ResWord[0]+")\n");
            if(ResWord[1]!=0)
            fw.write("main (x"+ResWord[1]+")\n");
            if(ResWord[2]!=0)
            fw.write("char (x"+ResWord[2]+")\n");
            if(ResWord[3]!=0)
            fw.write("int (x"+ResWord[3]+")\n");
            if(ResWord[4]!=0)
            fw.write("float (x"+ResWord[4]+")\n");
            if(ResWord[5]!=0)
            fw.write("if (x"+ResWord[5]+")\n");
            if(ResWord[6]!=0)
            fw.write("else (x"+ResWord[6]+")\n");
            if(ResWord[7]!=0)
            fw.write("elseif (x"+ResWord[7]+")\n");
            if(ResWord[8]!=0)
            fw.write("for (x"+ResWord[8]+")\n");
            if(ResWord[9]!=0)
            fw.write("while (x"+ResWord[9]+")\n");
            if(ResWord[10]!=0)
            fw.write("do (x"+ResWord[10]+")\n");
            if(ResWord[11]!=0)
            fw.write("return (x"+ResWord[11]+")\n");
            if(ResWord[12]!=0)
            fw.write("switch (x"+ResWord[12]+")\n");
            if(ResWord[13]!=0)
            fw.write("case (x"+ResWord[13]+")\n");
            if(ResWord[14]!=0)
            fw.write("printf (x"+ResWord[14]+")\n");
            if(ResWord[15]!=0)
            fw.write("scanf (x"+ResWord[15]+")\n");
            fw.write("Library name: " + LibName + "\n");
            for (i = 0; i < LibraryStore.length(); i++) {
                if (LibraryStore.charAt(i) != ' ')
                    fw.write(LibraryStore.charAt(i));
                else
                    fw.write("\n");
            }
            fw.write("Comment: " + Comment + "\n");
            for (i = 0; i < CommentStore.length(); i++) {
                if (CommentStore.charAt(i) == '/' && CommentStore.charAt(i + 1) == '/') {
                    fw.write("//");
                    for (j = i + 2; CommentStore.charAt(j) != '/'; j++)
                        fw.write(CommentStore.charAt(j));
                    i = j;
                    fw.write("\n");
                }
                if (CommentStore.charAt(i) == '/' && CommentStore.charAt(i + 1) == '*') {
                    for (j = i;; j++) {
                        if (CommentStore.charAt(j) == '*' && CommentStore.charAt(j + 1) == '/') {
                            fw.write("*/\n");
                            break;
                        } else
                            fw.write(CommentStore.charAt(j));
                    }
                    i = j + 2;
                }
            }
            fw.write("Identifier: " +intid.size()+ "\n");
            fw.write("Constant: " + "\n");
            fw.write("Operator: " + Optotal + "\n");
            if (Operator[0] != 0)
                fw.write("+ (x" + Operator[0] + ")\n");
            if (Operator[1] != 0)
                fw.write("- (x" + Operator[1] + ")\n");
            if (Operator[2] != 0)
                fw.write("* (x" + Operator[2] + ")\n");
            if (Operator[3] != 0)
                fw.write("/ (x" + Operator[3] + ")\n");
            if (Operator[4] != 0)
                fw.write("% (x" + Operator[4] + ")\n");
            if (Operator[5] != 0)
                fw.write("^ (x" + Operator[5] + ")\n");
            if (Operator[6] != 0)
                fw.write("++ (x" + Operator[6] + ")\n");
            if (Operator[7] != 0)
                fw.write("-- (x" + Operator[7] + ")\n");
            if (Operator[8] != 0)
                fw.write("& (x" + Operator[8] + ")\n");
            if (Operator[9] != 0)
                fw.write("| (x" + Operator[9] + ")\n");
            if (Operator[10] != 0)
                fw.write("= (x" + Operator[10] + ")\n");
            fw.write("Comparator: " + Comptotal + "\n");
            if(Comparator[0]!=0)
            fw.write("== (x" + Comparator[0] + ")\n");
            if(Comparator[1]!=0)
            fw.write("< (x" + Comparator[1] + ")\n");
            if(Comparator[2]!=0)
            fw.write("> (x" + Comparator[2] + ")\n");
            if(Comparator[3]!=0)
            fw.write("<= (x" + Comparator[3] + ")\n");
            if(Comparator[4]!=0)
            fw.write(">= (x" + Comparator[4] + ")\n");
            if(Comparator[5]!=0)
            fw.write("!= (x" + Comparator[5] + ")\n");
            fw.write("Bracket: " + Btotal + "\n");
            if (Bracket[0] != 0)
                fw.write("( (x" + Bracket[0] + ")\n");
            if (Bracket[1] != 0)
                fw.write(") (x" + Bracket[1] + ")\n");
            if (Bracket[2] != 0)
                fw.write("[ (x" + Bracket[2] + ")\n");
            if (Bracket[3] != 0)
                fw.write("] (x" + Bracket[3] + ")\n");
            if (Bracket[4] != 0)
                fw.write("{ (x" + Bracket[4] + ")\n");
            if (Bracket[5] != 0)
                fw.write("} (x" + Bracket[5] + ")\n");
            fw.write("Format specifier: " + Fmtotal + "\n");
            if(FmSpecifier[0]!=0)
            fw.write("%d (x"+FmSpecifier[0]+")\n");
            if(FmSpecifier[1]!=0)
            fw.write("%f (x"+FmSpecifier[1]+")\n");
            if(FmSpecifier[2]!=0)
            fw.write("%c (x"+FmSpecifier[2]+")\n");
            if(FmSpecifier[3]!=0)
            fw.write("\\n (x"+FmSpecifier[3]+")\n");
            if(FmSpecifier[4]!=0)
            fw.write("\\r (x"+FmSpecifier[4]+")\n");
            if(FmSpecifier[5]!=0)
            fw.write("\\t (x"+FmSpecifier[5]+")\n");
            fw.write("Pointer: " +Pointer+ "\n");
            fw.write("Address: "+Address+"\n");
            fw.write("Punctuation: " + Puntotal + "\n");
            if (Punctuation[0] != 0)
                fw.write(", (x" + Punctuation[0] + ")\n");
            if (Punctuation[1] != 0)
                fw.write("; (x" + Punctuation[1] + ")\n");
            if (Punctuation[2] != 0)
                fw.write(": (x" + Punctuation[2] + ")\n");
            if (Punctuation[3] != 0)
                fw.write("# (x" + Punctuation[3] + ")\n");
            if (Punctuation[4] != 0)
                fw.write("\" (x" + Punctuation[4] + ")\n");
            if (Punctuation[5] != 0)
                fw.write("' (x" + Punctuation[5] + ")\n");
            fw.write("Printed token: " + "\n");
            fw.close();

           /* System.out.println("Total " + Total + " tokens.\n");
            System.out.println("Reserved word: " + Retotal);
            System.out.println("Library name: " + LibName);
            for (i = 0; i < LibraryStore.length(); i++) {
                if (LibraryStore.charAt(i) != ' ')
                    System.out.print(LibraryStore.charAt(i));
                else
                    System.out.println();
            }
            System.out.println("Comment: " + Comment);
            for (i = 0; i < CommentStore.length(); i++) {
                if (CommentStore.charAt(i) == '/' && CommentStore.charAt(i + 1) == '/') {
                    System.out.print("//");
                    for (j = i + 2; CommentStore.charAt(j) != '/'; j++)
                        System.out.print(CommentStore.charAt(j));
                    i = j;
                    System.out.println();
                }
                if (CommentStore.charAt(i) == '/' && CommentStore.charAt(i + 1) == '*') {
                    for (j = i;; j++) {
                        if (CommentStore.charAt(j) == '*' && CommentStore.charAt(j + 1) == '/') {*/
//                            System.out.println("*/");
 /*                           break;
                        } else
                            System.out.print(CommentStore.charAt(j));
                    }
                    i = j + 2;
                }
            }
            System.out.println("Identifier: ");
            System.out.println("Constant: ");
            System.out.println("Operator: " + Optotal);
            System.out.println("Comparator: " + Comptotal);
            System.out.println("Bracket: " + Btotal);
            if (Bracket[0] != 0)
                System.out.println("( (x" + Bracket[0] + ")");
            if (Bracket[1] != 0)
                System.out.println(") (x" + Bracket[1] + ")");
            if (Bracket[2] != 0)
                System.out.println("[ (x" + Bracket[2] + ")");
            if (Bracket[3] != 0)
                System.out.println("] (x" + Bracket[3] + ")");
            if (Bracket[4] != 0)
                System.out.println("{ (x" + Bracket[4] + ")");
            if (Bracket[5] != 0)
                System.out.println("} (x" + Bracket[5] + ")");
            System.out.println("Format specifier: " + Fmtotal);
            System.out.println("Pointer: ");
            System.out.println("Punctuation: " + Puntotal);
            if (Punctuation[0] != 0)
                System.out.println(", (x" + Punctuation[0] + ")");
            if (Punctuation[1] != 0)
                System.out.println("; (x" + Punctuation[1] + ")");
            if (Punctuation[2] != 0)
                System.out.println(": (x" + Punctuation[2] + ")");
            if (Punctuation[3] != 0)
                System.out.println("# (x" + Punctuation[3] + ")");
            if (Punctuation[4] != 0)
                System.out.println("\" (x" + Punctuation[4] + ")");
            if (Punctuation[5] != 0)
                System.out.println("' (x" + Punctuation[5] + ")");
            System.out.println("Printed token: ");*/
        } catch (final Exception e) {
        }
        sc.close();
    }
}
