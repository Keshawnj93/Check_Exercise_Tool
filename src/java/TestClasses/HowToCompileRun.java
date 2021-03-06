
import java.util.*;

import java.lang.ProcessBuilder.Redirect;

import java.io.*;

public class HowToCompileRun {

    final static int EXECUTION_TIME_ALLOWED = 1000;

    final static int EXECUTION_TIME_INTERVAL = 100;

    public static class Output {

        public String output = "";

        public String error = "";

        public boolean isInfiniteLoop = false;

        public int timeUsed = 100;

    }

    public static void main(String[] args) {

        // Compile
//    Output output = compileProgram("javac",
//      "c:\\temp\\temp1", "Test.java");
//    System.out.println(output.error);
//    System.out.println("Result: " + output.output);
        // Run without input
        Output output = executeProgram("java", "Test",
                "c:\\temp\\temp1", null, "c:\\temp\\temp1\\Test.output");

        System.out.println(output.error);

        System.out.println("Result: " + output.output);

        // Compile Exercise02_01.java
        output = compileProgram("javac",
                "c:\\temp\\temp1", "Exercise02_01.java");

        System.out.println(output.error);

        System.out.println("Result: " + output.output);

        // Run with input
        output = executeProgram("java", "Exercise02_01",
                "c:\\temp\\temp1", "c:\\temp\\temp1\\Exercise02_01a.input",
                "c:\\temp\\temp1\\Exercise02_01a.output");

        System.out.println(output.error);

        System.out.println("Result: " + output.output);

    }

    public static Output executeProgram(String command, String program,
            String programDirectory, String inputFile, String outputFile) {

        final Output result = new Output();

        ProcessBuilder pb;

        // For Java security, added c:/etext.policy in c:\program files\jre\bin\security\java.security
        pb = new ProcessBuilder(command, "-Djava.security.manager", program);

        pb.directory(new File(programDirectory));

        pb.redirectErrorStream(true);

        if (inputFile != null) {

            pb.redirectInput(Redirect.from(new File(inputFile)));

        }

        pb.redirectOutput(Redirect.to(new File(outputFile)));

        long startTime = System.currentTimeMillis();

        Process proc = null;

        try {

            proc = pb.start();

        } catch (Exception ex) {

            ex.printStackTrace();

        }

        // This separate thread destroy the process if it takes too long time
        final Process proc1 = proc;

        new Thread() {

            public void run() {

                int sleepTime = 0;

                boolean isFinished = false;

                while (sleepTime <= EXECUTION_TIME_ALLOWED && !isFinished) {

                    try {

                        try {

                            Thread.sleep(EXECUTION_TIME_INTERVAL);

                        } catch (Exception ex) {

                            ex.printStackTrace();

                        }

//                System.out.println("sleepTime " + sleepTime);
                        sleepTime += EXECUTION_TIME_INTERVAL;

                        int exitValue = proc1.exitValue();

                        isFinished = true;

//                System.out.println("exitValue " + exitValue);
                    } catch (IllegalThreadStateException ex) {

                    }

                }

                if (!isFinished) {

                    proc1.destroy();

                    result.isInfiniteLoop = true;

//            System.out.println("Infinite loop");
                }

            }

        }.start();

        try {

            int exitCode = proc.waitFor();

        } catch (Exception ex) {

            ex.printStackTrace();

        }

        result.timeUsed = (int) (System.currentTimeMillis() - startTime);

        return result;

    }

    public static Output compileProgram(String command,
            String sourceDirectory, String program) {

        final Output result = new Output();

        ProcessBuilder pb;

        pb = new ProcessBuilder(command, "-classpath", ".;c:\\book",
                "-Xlint:unchecked", "-nowarn", "-XDignore.symbol.file", program);

        pb.directory(new File(sourceDirectory));

        long startTime = System.currentTimeMillis();

        Process proc = null;

        try {

            proc = pb.start();

        } catch (Exception ex) {

            ex.printStackTrace();

        }

        // This separate thread destroy the process if it takes too long time
        final Process proc1 = proc;

        new Thread() {

            public void run() {

                Scanner scanner1 = new Scanner(proc1.getInputStream());

                while (scanner1.hasNext()) {

                    result.output += scanner1.nextLine().replaceAll(" ", "&nbsp;") + "\n";

                    //  scanner1.close(); // You could have closed it too soon
                }

            }

        }.start();

        new Thread() {

            public void run() {

                // Process output from proc
                Scanner scanner2 = new Scanner(proc1.getErrorStream());

                while (scanner2.hasNext()) {

                    result.error += scanner2.nextLine() + "\n";

                }

                // scanner2.close(); // You could have closed it too soon
            }

        }.start();

        try {

            //Wait for the external process to finish
            int exitCode = proc.waitFor();

        } catch (Exception ex) {

            ex.printStackTrace();

        }

        result.output.replaceAll(" ", "&nbsp;");

        result.error.replaceAll(" ", "&nbsp;");

        // Ignore warnings
        if (result.error.indexOf("error") < 0) {

            result.error = "";

        }

//        if (result.error.indexOf("error") >= 0 || result.error.indexOf("Error") >= 0)
//          result.error = "";
        result.timeUsed = (int) (System.currentTimeMillis() - startTime);

        return result;

    }

}

