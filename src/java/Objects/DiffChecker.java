package Objects;


public class DiffChecker {
    private String exercise, user, expected;
    ExerciseParser e;
    public DiffChecker(){
        exercise = user = expected = "";
        e = new ExerciseParser();
    }
    
    public DiffChecker(String exercise){
        this.exercise = exercise;
        user = expected = "";
        e = new ExerciseParser(exercise);
        e.setValues();
        for (String s : e.getOutput()){
            expected += s;
        }
    }
    
    public DiffChecker(String exercise, String user){
        this.exercise = exercise;
        this.user = user;
        expected = "";
        e = new ExerciseParser(exercise);
        e.setValues();
        for (String s : e.getOutput()){
            expected += s;
        }
    }
    
    public boolean hasDiff(){
        String us = user, ex = expected;
        boolean diff = false;
        //Multiple tokens need to be checked
        if (ex.contains("#")){
            while (ex.contains("#")){
                if (diff) break;
                String tokenEx = ex.substring(0, ex.indexOf("#"));
                String tokenUs;
                try{
                    tokenUs = us.substring(0, us.indexOf("#"));
                } catch (Exception e){
                    tokenUs = us;
                }
                
                diff = (!tokenUs.contains(tokenEx));
                if (diff){
                    while (!tokenUs.isEmpty() && diff){
                        try{
                            tokenUs = us.substring(0, us.indexOf("#"));
                        } catch (Exception e){
                            tokenUs = "";
                        }
                        
                        diff = (!tokenUs.contains(tokenEx));
                    }
                    
                }
                
                ex = ex.substring(ex.indexOf("#") + 1);
                try{
                    us = us.substring(us.indexOf("#"));
                    us = us.substring(1);
                } catch (Exception e){
                    us = "";
                }
            }
        }
        
        //Only a single token to check
        if (!diff){
            diff = (!us.contains(ex));
        }
        
        return diff;
    }
    
    public boolean hasDiff(String user, String expected, int dummy){
        String us = user, ex = expected;
        boolean diff = false;
        //Multiple tokens need to be checked
        if (ex.contains("#")){
            while (ex.contains("#")){
                if (diff) break;
                String tokenEx = ex.substring(0, ex.indexOf("#"));
                String tokenUs;
                try{
                    tokenUs = us.substring(0, us.indexOf("#"));
                } catch (Exception e){
                    tokenUs = us;
                }
                
                diff = (!tokenUs.contains(tokenEx));
                if (diff){
                    while (!tokenUs.isEmpty() && diff){
                        try{
                            us = us.substring(us.indexOf("#"));
                            us = us.substring(1);
                            tokenUs = us;
                        } catch (Exception e){
                            tokenUs = "";
                        }
                        
                        diff = (!tokenUs.contains(tokenEx));
                    }
                    
                }
                
                ex = ex.substring(ex.indexOf("#") + 1);
                try{
                    us = us.substring(us.indexOf("#"));
                    us = us.substring(1);
                } catch (Exception e){
                    us = "";
                }
            }
        }
        
        //Only a single token to check
        if (!diff){
            diff = (!us.contains(ex));
        }
        
        return diff;
    }
    
    public boolean hasDiff(String user, String expected){
        String us = user, ex = expected;
        boolean diff = false;
        //Multiple tokens need to be checked
        if (ex.contains("#")){
            while (ex.contains("#")){
                String tokenEx = ex.substring(0, ex.indexOf("#"));
                
                diff = (!us.contains(tokenEx));
                if (diff){
                    break;                    
                }
                
                try{
                    us = us.substring(us.indexOf(tokenEx) + tokenEx.length());
                } catch (Exception e){
                    us = "";
                }
                ex = ex.substring(ex.indexOf("#") + 1);  
            }
        }
        
        //Only a single token to check
        if (!diff){
            diff = (!us.contains(ex));
        }
        
        return diff;
    }
    
    public String getExercise(){
        return exercise;
    }
    
    public void setExercise(String s){
        exercise = s;
    }
    
    public String getUser(){
        return user;
    }
    
    public void setUser(String s){
        user = s;
    }
    
    public String getExpected(){
        return expected;
    }
    
    public void setExpected(String s){
        expected = s;
    }
}
