/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objects;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 *
 * @author Keshawn
 */
public class ExerciseParser {
    private String exercise, description;
    private ArrayList<String> input, output;
    
    public ExerciseParser(){
        exercise = description = "";
        input = new ArrayList<>();
        output = new ArrayList<>();
    }
    
    public ExerciseParser(String exercise){
        this.exercise = exercise;
        description = "";
        input = new ArrayList<>();
        output = new ArrayList<>();
    }
    
    public void setValues(String exercise){
        this.exercise = exercise;
        setValues();
    }
    
    public void setValues(){
        BufferedReader br;
        String s;
        //Set Exersise Description
        String dir = "C:\\ags10e\\exercisedescription\\";
        String path = dir + exercise;
        try{
            br = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
            while((s = br.readLine()) != null){
                description += s;
            }
            
            br.close();
        } catch (Exception e){
            //e.printStackTrace();
        }
        
        //Set inputs
        dir = "C:\\ags10e\\gradeexercise\\";
        char c = 'a';
        path = dir + exercise + c + ".input";
        boolean cont = true;
        
        while (cont){
            try{
               br = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
               String in = "";
               
               while((s = br.readLine()) != null){
                    in += s + " ";
                }
               
               //Remove last trailing space, if applicable
               if (!in.isEmpty()){
                   in = in.substring(0, in.length() - 1);
               }
               
               input.add(in);
               c++;
               path = dir + exercise + c + ".input"; 
            } catch(Exception e){
                //e.printStackTrace();
                cont = false;
            }
        }
        
        //Set Output
        path = dir + exercise + ".output";
        
        //Try single output first
        try{
            br = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
            String out = "";
            while((s = br.readLine()) != null){
                out += s;
            }
            
            output.add(out);
        }catch (Exception e){
            //Try multiple output files
            c = 'a';
            path = dir + exercise + c + ".output";
            cont = true;
            
            while (cont){
                try{
                    br = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
                    String out = "";
               
                    while((s = br.readLine()) != null){
                        out += s;
                    }
               
                    output.add(out);
                    c++;
                    path = dir + exercise + c + ".output"; 
                } catch(Exception x){
                    //x.printStackTrace();
                    cont = false;
                }
            }
        }
    }
    
    public String getExercise(){
        return exercise;
    }
    
    public void setExercise(String s){
        exercise = s;
    }
    
    public String getDescription(){
        return description;
    }
    
    public void setDescription(String s){
        description = s;
    }
    
    public ArrayList<String> getInput(){
        return input;
    }
    
    public void setInput(ArrayList<String> s){
        input = s;
    }
    
    public ArrayList<String> getOutput(){
        return output;
    }
    
    public void setOutput(ArrayList<String> s){
        output = s;
    }
}
