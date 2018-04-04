package Beans;

import javax.inject.Named;
import javax.faces.bean.ManagedBean;
import Objects.CompRun;
import javax.faces.bean.RequestScoped;

@Named(value = "checkExerciseBean")
@ManagedBean
@RequestScoped
public class CheckExerciseBean {
    String exercise, code, sampleInput, output;
    
    public CheckExerciseBean() {
        exercise = code = sampleInput = "";
        exercise = "Exercise01_01";
    }
    
    public void compileAndRun(){
        CompRun c = new CompRun(exercise, code, sampleInput);
        String s;
        
        //Write file. If successful, continue
        if ((s = c.write()).equals("File was written successfully")){
            output = s; 
            //Compile file. If successful, continue
            if ((s = c.compile()).equals("Program compiled successfully")){
                
                //Run file
                output = c.run();
                
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
    
    public String getExercise(){
        return exercise;
    }
    
    public void setExercise(String s){
        exercise = s;
    }
    
    public String getCode(){
        return code;
    }
    
    public void setCode(String s){
        code = s;
    }
    
    public String getSampleInput(){
        return sampleInput;
    }
    
    public void setSampleInput(String s){
        sampleInput = s;
    }
    
    public String getOutput(){
        return output;
    }
    
    public void setOutput(String s){
        output = s;
    }
    
}
