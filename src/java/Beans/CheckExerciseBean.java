package Beans;

import javax.inject.Named;
import Objects.*;
import java.io.Serializable;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@Named(value = "checkExerciseBean")
@ManagedBean
@RequestScoped
public class CheckExerciseBean implements Serializable {

    String chapter, exercise, code, sampleInput, output;
    Boolean visible;
    private String inputStyle;
    private String outputStyle;

    public CheckExerciseBean() {
        exercise = code = sampleInput = "";
        exercise = "Exercise01_01";
        chapter = "Chapter 1";
        
        //Hide the input and output box originally
        inputStyle="display:none;";
        outputStyle="display:none;";
        //changeExercise();
    }

    public void compileAndRun() {
        CompRun c = new CompRun(exercise, code, sampleInput);
        String s;
        
        //show the output
        setOutputStyle("");
        
        //Write file. If successful, continue
        if ((s = c.write()).equals("File was written successfully")) {
            output = s;
            //Compile file. If successful, continue
            if ((s = c.compile()).equals("Program compiled successfully")) {

                //Run file
                output = c.run();
                output = format(output);

                //Delete the .java and .class files created
                c.delete(exercise + ".java");
                c.delete(exercise + ".class");

            } else {
                //Compile was unsuccessful
                output = s;
                c.delete(exercise + ".java");
            }
        } else {
            //Write was unsuccessful
            output = s;
        }
        
        if (!sampleInput.isEmpty())inputStyle = "";
        else inputStyle = "display:none;";
    }

    public void automaticCheck() {
        String temp = sampleInput;
        ExerciseParser e = new ExerciseParser(exercise);
        e.setValues();
        ArrayList outputs = e.getOutput();

        //show the output
        setOutputStyle("");
        
        
        //No input required. Only one run needed
        if (e.getInput().isEmpty() || e.getInput() == null) {
            CompRun c = new CompRun(exercise, code, sampleInput);
            String s;

            //Write file. If successful, continue
            if ((s = c.write()).equals("File was written successfully")) {

                //Write file. If successful, continue
                if ((s = c.write()).equals("File was written successfully")) {
                    output = s;
                    
                    //Compile file. If successful, continue
                    if ((s = c.compile()).equals("Program compiled successfully")) {

                        //Run file
                        output = c.run();

                        //If an error occur, do not diff check. Return the output
                        if (output.contains("Error: Infinite loop detected")
                                || output.contains("RunTime Error:") || output.contains("Error running file")) {
                            output = format(output);
                        }

                        //If no errors, but cannot be graded, notify user
                        if (e.getDescription().contains("This exercise can be compiled and submitted, but cannot be run and automatically graded")) {
                            output = "This program cannot be automatically graded, but has compiled and run successfully";
                            return;
                        } 

                        //Program ran successfully. Diff check
                        else {
                            DiffChecker d = new DiffChecker(exercise, output);
                            String expected = outputs.get(0).toString();
                            outputs.remove(0);

                            //If there is a difference, notify the user
                            if (d.hasDiff(output, expected)) {
                                
                                //Test
                                c.setCode(e.getCorrectAnswer(exercise));
                                c.setOutput("");
                                
                                c.write(); c.compile();
                                output = format("Your program is incorrect\r\n\r\nYour Output\r\n" + output
                                        + "\r\n\r\nExpected Output\r\n" + c.run());
                            } 

                            //Program is correct
                            else {
                                output = "Your program is correct";
                            }
                        }
                        
                        //Delete the .java and .class files created
                        c.delete(exercise + ".java");
                        c.delete(exercise + ".class");
                    } else {
                        //Compile was unsuccessful
                        output = s;
                        c.delete(exercise + ".java");
                    }
                } else {
                    //Write was unsuccessful
                    output = s;
                }
            }
        } 

        //Input required. Multiple runs needed 
        else {
            String sampleIn = sampleInput;
            for (String in : e.getInput()) {
                sampleInput = in;
                CompRun c = new CompRun(exercise, code, sampleInput);
                String s;

                //Write file. If successful, continue
                if ((s = c.write()).equals("File was written successfully")) {
                    output = s;
                    //Compile file. If successful, continue
                    if ((s = c.compile()).equals("Program compiled successfully")) {

                        //Run file
                        output = c.run();

                        //If an error occur, do not diff check. Return the output
                        if (output.contains("Error: Infinite loop detected")
                                || output.contains("RunTime Error:") || output.contains("Error running file")) {
                            output = format(output);
                        }

                        //If no errors, but cannot be graded, notify user
                        if (e.getDescription().contains("This exercise can be compiled and submitted, but cannot be run and automatically graded")) {
                            output = "This program cannot be automatically graded, but has compiled and run successfully";
                            return;
                        } //Program ran successfully. Diff check
                        else {
                            DiffChecker d = new DiffChecker(exercise, output);
                            String expected = outputs.get(0).toString();
                            outputs.remove(0);
                            


                            //If there is a difference, notify the user
                            if (d.hasDiff(output, expected)) {
                                
                                //Test
                                c.setCode(e.getCorrectAnswer(exercise));
                                c.setOutput("");
                                
                                c.write(); c.compile();
                                output = format("Your program is incorrect\r\n\r\nYour Output\r\n" + output
                                        + "\r\n\r\nExpected Output\r\n" + c.run());
                                break;
                            } //Program is correct
                            else {
                                output = "Your program is correct";
                            }
                        }
                        
                        //Delete the .java and .class files created
                        c.delete(exercise + ".java");
                        c.delete(exercise + ".class");
                    } else {
                        //Compile was unsuccessful
                        output = s;
                        c.delete(exercise + ".java");
                    }
                } else {
                    //Write was unsuccessful
                    output = s;
                }
            }
            
            sampleInput = sampleIn;

        }
        
        sampleInput = temp;
        if (!sampleInput.isEmpty())inputStyle = "";
        else inputStyle = "display:none;";
    }

    public void changeExercise() {
        //Hide output
        setOutputStyle("display:none;");
        
        
        ExerciseParser e = new ExerciseParser(exercise);
        e.setValues();

        //Exercise cannot be auto graded
        if (e.getDescription().contains("This exercise can be compiled and submitted, but cannot be run and automatically graded")) {
            code = "/* This exercise cannot be automatically graded, because it may use random\r\n"
                    + "numbers, file I/O, or graphics */";
            visible = false;
        } //Exercise can be auto graded. Display description
        else {
            code = "/* Paste your " + exercise + " here, and click Automatic Check\r\n"
                    + "For all programming projects, the numbers should be double\r\n"
                    + "unless it is explicitly stated as integer.\r\n"
                    + "If you get a java.util.InputMismatchException error, check if \r\n"
                    + "your code used input.readInt(), but it should be input.readDouble().\r\n"
                    + "For integers, use int unless it is explicitly stated as long. */";
            visible = true;
            sampleInput = "";
            ArrayList<String> s = e.getInput();
            if (!s.isEmpty()){
                sampleInput = s.get(0);
            }
//            for (String s : e.getInput()) {
//                //Avoid putting space on first input
//                if (sampleInput.isEmpty()) {
//                    sampleInput = s;
//                } else {
//                    sampleInput += " " + s; 
//                }
//            }
            
            if (sampleInput.isEmpty()) setInputStyle("display:none;");
            else setInputStyle("");
        }
    }

    public String format(String s) {
        return s.replace("#", "\r\n");
    }

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String s) {
        chapter = s;
    }

    public String getExercise() {
        return exercise;
    }

    public void setExercise(String s) {
        exercise = s;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String s) {
        code = s;
    }

    public String getSampleInput() {
        return sampleInput;
    }

    public void setSampleInput(String s) {
        sampleInput = s;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String s) {
        output = s;
    }

    public boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean b) {
        visible = b;
    }

    /**
     * @return the inputStyle
     */
    public String getInputStyle() {
        return inputStyle;
    }

    /**
     * @param inputStyle the inputStyle to set
     */
    public void setInputStyle(String inputStyle) {
        this.inputStyle = inputStyle;
    }

    /**
     * @return the outputStyle
     */
    public String getOutputStyle() {
        return outputStyle;
    }

    /**
     * @param outputStyle the outputStyle to set
     */
    public void setOutputStyle(String outputStyle) {
        this.outputStyle = outputStyle;
    }

}
