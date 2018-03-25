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
                + "int x = 1024;\r\n"
                + "System.out.println(x);\r\n"
                + "}\r\n"
                + "}";
        CompRun c = new CompRun(fileName, code);
        System.out.println(c.write() + "\n" + c.compile() + "\n" + c.run() + "\n" +
                c.delete(fileName + ".class") + "\n" + c.delete(fileName + ".java"));
    }
}
