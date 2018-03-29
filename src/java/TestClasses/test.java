package TestClasses;

import Objects.*;

public class test {
    public static void main(String[] args){
        String fileName = "PrintX";
        
        //Run a program that requires sample input
        String code = "import java.util.Scanner;\r\n"
                + "public class PrintX{\r\n"
                + "public static void main(String[] args){\r\n"
                + "Scanner s = new Scanner(System.in);\r\n"
                + "String x = s.nextLine();\r\n"
                + "System.out.println(x);\r\n"
                + "System.out.println(s.nextLine());\r\n"
                + "}\r\n"
                + "}";
        CompRun c = new CompRun(fileName, code);
        c.setSampleIn("This string is printed using sample input!\nThis will also print");
        
        System.out.println(c.write());
        System.out.println(c.compile());
        System.out.println(c.run());
        c.delete(fileName + ".java");
        c.delete(fileName + ".class");
        
        //Run a program that does not require sample input
        code = "public class PrintX{\r\n"
                + "public static void main(String[] args){\r\n"
                + "System.out.println(\"This string is printed without sample input\");}}";
        
        c.setCode(code);
        c.setFileName("PrintX");
        c.setOutput("");
        
        c.write();
        c.compile();
        System.out.println(c.run());
        c.delete(fileName + ".java");
        c.delete(fileName + ".class");
        
        //Run a program with an infinite loop
        code = "public class PrintX{\r\n"
                + "public static void main(String[] args){\r\n"
                + "int i = 0;\r\n"
                + "while (i < 10) System.out.println(\"LOOP\");\r\n}\r\n}";
        
        c.setCode(code);
        c.setFileName("PrintX");
        c.setOutput("");
        
        c.write();
        c.compile();
        System.out.println(c.run());
        c.delete(fileName + ".java");
        c.delete(fileName + ".class");
    }
}
