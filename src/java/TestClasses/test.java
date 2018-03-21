package TestClasses;

import Objects.*;

public class test {
    public static void main(String[] args){
        CompRun c = new CompRun("HelloWorld", "");
        String s = c.compile();
        System.out.println(s);
        if (s.contains("Program compiled successfully")){
            s = c.run();
            System.out.println(s);
        }
    }
}
