package Objects;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CompRun {
    String filePath, fileName, code, sampleIn, errStream, output;
    
    //Constructors
    public CompRun(){
        filePath = "temp/";
        fileName = code = sampleIn = errStream = output = "";
    }
    
    public CompRun(String file, String code){
        filePath = "temp/";
        this.fileName = file;
        this.code = code;
        sampleIn = errStream = output = "";
    }
    
    public CompRun(String file, String code, String sampleIn){
        filePath = "temp/";
        this.fileName = file;
        this.code = code;
        this.sampleIn = sampleIn;
        errStream = output = "";
    }
    
    public String write(String fileName, String code){
        this.fileName = fileName;
        this.code = code;
        return write();
    }
    
    public String write(){
        
    }
    
    public String compile(String fileName, String code, String sampleIn){
        this.fileName = fileName;
        this.code = code;
        this.sampleIn = sampleIn;
        return compile();
    }
    
    public String compile(){
        String command = "javac -cp src src/" + filePath + fileName + ".java";
        try{
            Process pro = Runtime.getRuntime().exec(command);
            if (hasError(pro.getErrorStream())){
                setMessage(pro.getErrorStream(), "output");
                //setMessage(pro.getErrorStream(), "errStream");
                pro.waitFor();
                return "The program could not be compiled\n" + errStream;
            }
            
            else{
                pro.waitFor();
                return "Program compiled successfully";
            }
        } catch (Exception e){
                return "Errot compiling file";
        }
    }
    
    public String run(){
        String command = "java -cp src " + filePath + fileName;
        try{
            Process pro = Runtime.getRuntime().exec(command);
            if (hasError(pro.getErrorStream())){
                //setMessage(pro.getErrorStream(), "errStream");
                //setMessage(pro.getErrorStream(), "errStream");
                pro.waitFor();
                return "RunTime Error:\n" + errStream;
            }
            
            else{
                setMessage(pro.getInputStream(), "output");
                pro.waitFor();
                return output;
            }
        } catch (Exception e){
                return "Error compiling file";
        }
    }
    
    private void setMessage(InputStream ins, String type) 
            throws Exception {
        String line = null;
        BufferedReader in = new BufferedReader(new InputStreamReader(ins));
        while ((line = in.readLine()) != null){
            if (type == "output"){
                output += line + "#";
            }
          
            else if (type == "errStream"){
                errStream += line;
            }
        }
        
        //Remove last # from string
        if (output.charAt(output.length() - 1) == '#'){
            output = output.substring(0, output.length() - 1);
        }
    }
    
    
    private boolean hasError(InputStream ins) throws Exception{
        String line = null;
        BufferedReader in = new BufferedReader(new InputStreamReader(ins));
        if ((line = in.readLine()) != null){
            errStream += line + "\n";
            while ((line = in.readLine()) != null){
                errStream += line + "\n";   
            }
            return true;
        }
        return false;
    }
    
    //Getters and Setters
    public String getFilePath(){
        return filePath;
    }
    
    public void setFilePath(String s){
        filePath = s;
    }
    
    public String getFileName(){
        return fileName;
    }
    
    public void setFileName(String s){
        fileName = s;
    }
    
    public String getCode(){
        return code;
    }
    
    public void setCode(String s){
        code = s;
    }
    
    public String getSampleIn(){
        return sampleIn;
    }
    
    public void setSampleIn(String s){
        sampleIn = s;
    }
}
