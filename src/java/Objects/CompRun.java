package Objects;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CompRun {
    String filePath, fileName, code, sampleIn, errStream, output;
    final int EXECUTION_TIME_ALLOWED = 1000, EXECUTION_TIME_INTERVAL = 100;
    boolean isInfiniteLoop = false;
    
    //Constructors
    public CompRun(){
        filePath = "ags10e/";
        fileName = code = sampleIn = errStream = output = "";
    }
    
    public CompRun(String file, String code){
        filePath = "ags10e/";
        this.fileName = file;
        this.code = code;
        sampleIn = errStream = output = "";
    }
    
    public CompRun(String file, String code, String sampleIn){
        filePath = "ags10e/";
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
        FileWriter fw = null;
        BufferedWriter bw = null;
        String pathToWrite = "C:\\" + filePath + fileName + ".java";
        
        try{
            fw = new FileWriter(pathToWrite);
            bw = new BufferedWriter(fw);
            
            
            //Package name added to code
            code = "" + code;
            bw.write(code);
        } catch(IOException e){
            try{
                if (bw != null){
                    bw.close();
                }
                if (fw != null){
                    fw.close();
                }
            } catch(IOException x){
                //
            }
            return "Error writing file:<br />" + format(e.toString());
        } finally{
            try{
                if (bw != null){
                    bw.close();
                }
                if (fw != null){
                    fw.close();
                }
            } catch(IOException x){
                //
            }
        }
        
        return "File was written successfully";
    }
    
    public String compile(String fileName, String code, String sampleIn){
        this.fileName = fileName;
        this.code = code;
        this.sampleIn = sampleIn;
        return compile();
    }
    
    public String compile(){
        String command = "javac -cp src C:/" + filePath + fileName + ".java";
        try{
            Process pro = Runtime.getRuntime().exec(command);
            if (hasError(pro.getErrorStream())){
                //setMessage(pro.getErrorStream(), "output");
                //setMessage(pro.getErrorStream(), "errStream");
                pro.waitFor();
                return "The program could not be compiled<br />" + format(errStream);
            }
            
            else{
                pro.waitFor();
                return "Program compiled successfully";
            }
        } catch (Exception e){
                return "Error compiling file";
        }
    }
    
    public String run(String fileName){
        this.fileName = fileName;
        return run();
    }
    
    public String run(){
        String command = "java " + fileName;
        File dir = new File("C:\\ags10e");
        
        
        try{
            Process pro = Runtime.getRuntime().exec(command, null, dir);
            
            //Destroy thread if taking too long
            final Process pro1 = pro;
            new Thread(){
                @Override
                public void run(){
                    int sleepTime = 0;
                    boolean isFinished = false;
                    while(sleepTime <= EXECUTION_TIME_ALLOWED && !isFinished){
                    
                        try {
                        
                            try {
                                Thread.sleep(EXECUTION_TIME_INTERVAL);
                            } catch (Exception e){
                                errStream = e.getMessage();
                            }
                        
                            sleepTime += EXECUTION_TIME_INTERVAL;
                            pro.exitValue();
                            isFinished = true;
                        
                        } catch (IllegalThreadStateException e){
                            errStream = e.getMessage();
                        }
                    }
                
                    if (!isFinished){
                        pro1.destroy();
                        isInfiniteLoop = true;
                        output = "Error: Infinite loop detected<br />";
                    }
                }
            }.start();
            
            //If sample input is needed, load it into the OutputStream
            if (!sampleIn.isEmpty()){
                pro.getOutputStream().write(sampleIn.getBytes("UTF-8"));
                pro.getOutputStream().close();
            }
            
            //If an error exists, return the error
            if (hasError(pro.getErrorStream())){
                pro.waitFor();
                return "RunTime Error:<br />" + format(errStream);
            }
            
            //Return the InputStream (stdout) of the program
            else{
                setMessage(pro.getInputStream(), "output");
                pro.waitFor();
                return output;
            }
        } catch (Exception e){
            errStream = e.getMessage();
            return "Error running file";
        }
    }
    
    public String delete(String fileName){
        this.fileName = fileName;
        return delete();
    }
    
    public String delete(){
        String fileToDelete = "C:\\" + filePath + fileName;
        File file = new File(fileToDelete);
        return (file.delete()) ? "File deleted successfully" : "File could not be deleted";
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
    
    public String format(String s) {
        return s.replace("\n", "<br />");
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
    
    public String getErrStream(){
        return errStream;
    }
    
    public void setErrStream(String s){
        errStream = s;
    }
    
    public String getOutput(){
        return output;
    }
    
    public void setOutput(String s){
        output = s;
    }
}
