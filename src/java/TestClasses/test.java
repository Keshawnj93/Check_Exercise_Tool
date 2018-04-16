package TestClasses;

import Beans.CheckExerciseBean;
import Objects.*;

public class test {

    public static void main(String[] args) {
//        String fileName = "PrintX";
//        
//        //Run a program that requires sample input
//        String code = "import java.util.Scanner;\r\n"
//                + "public class PrintX{\r\n"
//                + "public static void main(String[] args){\r\n"
//                + "Scanner s = new Scanner(System.in);\r\n"
//                + "String x = s.nextLine();\r\n"
//                + "System.out.println(x);\r\n"
//                + "System.out.println(s.nextLine());\r\n"
//                + "}\r\n"
//                + "}";
//        CompRun c = new CompRun(fileName, code);
//        c.setSampleIn("This string is printed using sample input!\nThis will also print");
//        
//        System.out.println(c.write());
//        System.out.println(c.compile());
//        System.out.println(c.run());
//        c.delete(fileName + ".java");
//        c.delete(fileName + ".class");
//        
//        //Run a program that does not require sample input
//        code = "public class PrintX{\r\n"
//                + "public static void main(String[] args){\r\n"
//                + "System.out.println(\"This string is printed without sample input\");}}";
//        
//        c.setCode(code);
//        c.setFileName("PrintX");
//        c.setOutput("");
//        
//        c.write();
//        c.compile();
//        System.out.println(c.run());
//        c.delete(fileName + ".java");
//        c.delete(fileName + ".class");
//        
//        //Run a program with an infinite loop
//        code = "public class PrintX{\r\n"
//                + "public static void main(String[] args){\r\n"
//                + "int i = 0;\r\n"
//                + "while (i < 10) System.out.println(\"LOOP\");\r\n}\r\n}";
//        
//        c.setCode(code);
//        c.setFileName("PrintX");
//        c.setOutput("");
//        
//        c.write();
//        c.compile();
//        System.out.println(c.run());
//        c.delete(fileName + ".java");
//        c.delete(fileName + ".class");

//        ExerciseParser e = new ExerciseParser("Exercise42_01");
//        e.setValues();
//        
//        System.out.println(e.getExercise());
//        System.out.println(e.getDescription());
//        
//        for (String s : e.getInput()){
//            System.out.println("in " + s);
//        }
//        
//        for (String s : e.getOutput()){
//            System.out.println("out " + s);
//        }
        //DiffChecker d = new DiffChecker("Exercise02_16");
        CheckExerciseBean b = new CheckExerciseBean();
        b.setExercise("Exercise02_02");
        b.setCode("import java.util.Scanner;\r\n" +
"\r\n" +
"public class Exercise02_02 {\r\n" +
"  public static void main(String[] args) {\r\n" +
"    Scanner input = new Scanner(System.in);\r\n" +
"\r\n" +
"    // Enter radius of the cylinder\r\n" +
"    System.out.print(\"Enter the radius and length of a cylinder: \");\r\n" +
"    double radius = input.nextDouble();\r\n" +
"    double length = input.nextDouble();\r\n" +
"\r\n" +
"    double area = radius * radius * 30.14159;\r\n" +
"    double volume = area * length;\r\n" +
"\r\n" +
"    System.out.println(\"The area is \" + area);\r\n" +
"    System.out.println(\"The volume of the cylinder is \" + volume);\r\n" +
"  }\r\n" +
"}");
        b.automaticCheck();
        System.out.println(b.getOutput());
    }
}
