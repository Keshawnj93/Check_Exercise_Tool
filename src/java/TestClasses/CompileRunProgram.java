package TestClasses;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CompileRunProgram {
    public static void main(String[] args){
        try {
            //runProcess("pwd");
            //System.out.println("**********");
            runProcess("javac -cp src src/temp/HelloWorld.java");
            //System.out.println("**********");
            runProcess("java -cp src temp/HelloWorld");
            //System.out.println("**********");
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private static void printLines(String cmd, InputStream ins) 
            throws Exception {
        String line = null;
        BufferedReader in = new BufferedReader(new InputStreamReader(ins));
        while ((line = in.readLine()) != null){
            System.out.println(cmd + " " + line);
        }
    }
    
    private static void printLines(InputStream ins) 
            throws Exception {
        String line = null;
        BufferedReader in = new BufferedReader(new InputStreamReader(ins));
        while ((line = in.readLine()) != null){
            System.out.println(line);
        }
    }
    
    private static void runProcess(String command) throws Exception {
        Process pro = Runtime.getRuntime().exec(command);
        printLines(pro.getInputStream());
        printLines(pro.getErrorStream());
        pro.waitFor();
        //System.out.println(command + " exitValue() " + pro.exitValue());
    }
}
