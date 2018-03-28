package TestClasses;

import Objects.*;

public class test {
    public static void main(String[] args){
//        CompRun c = new CompRun("HelloWorld", "");
//        String s = c.compile();
//        System.out.println(s);
//        if (s.contains("Program compiled successfully")){
//            s = c.run();
//            System.out.println(s);
//        }
        String fileName = "PrintX";
        String code = "public class PrintX{\r\n"
                + "public static void main(String[] args){\r\n"
                + "int x = 10;\r\n"
                + "System.out.println(x);\r\n"
                + "}\r\n"
                + "}";
        CompRun c = new CompRun(fileName, code);
        System.out.println(c.write());
        System.out.println(c.compile());
        //System.out.println(c.run(1));
        c.delete(fileName + ".java");
        c.delete(fileName + ".class");
        c.delete(fileName + "Input.txt");
        c.delete(fileName + "Output.txt");
    }
}
