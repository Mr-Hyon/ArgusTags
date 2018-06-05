package argustags.argustags_phase_ii.util;

import java.io.*;
import java.util.ArrayList;

public class RegisterLogin {

    public ResultMessage register(String identity,String username,String password){
        int sign = 0;
        String valueString = null;
        ArrayList<String> List = new ArrayList<String>();
        File f = new File(identity);
        try {
            FileWriter fw = new FileWriter(f,true);
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            while ((valueString=br.readLine())!=null){
                if(sign%2==0){
                    List.add(valueString);
                    sign++;
                }
                else{
                    sign++;
                }
            }
            br.close();
            for(int i=0;i<List.size();i++){
                if(List.get(i).equals(username)){
                    return ResultMessage.REPEATEDNAME;
                }
            }

            fw.write(username+"\n"+password+"\n");
            fw.flush();
            fw.close();
            return ResultMessage.SUCCESS;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return ResultMessage.FAILED;
        } catch (IOException e) {
            e.printStackTrace();
            return ResultMessage.FAILED;
        }
    }

    public ResultMessage login(String identity,String username,String password){
        int sign = 0;
        String valueString = null;
        ArrayList<String> List1 = new ArrayList<String>();
        ArrayList<String> List2 = new ArrayList<String>();
        try {
            FileReader fr = new FileReader(identity);
            BufferedReader br = new BufferedReader(fr);
            while ((valueString=br.readLine())!=null){
                if(sign%2==0){
                    List1.add(valueString);
                    sign++;
                }
                else{
                    List2.add(valueString);
                    sign++;
                }
            }
            br.close();
            for(int i=0;i<List1.size();i++){
                if(List1.get(i).equals(username)){
                    if(List2.get(i).equals(password)){
                        return ResultMessage.SUCCESS;
                    }
                }
            }
            return ResultMessage.FAILED;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return ResultMessage.FAILED;
        } catch (IOException e) {
            e.printStackTrace();
            return ResultMessage.FAILED;
        }
    }
}
