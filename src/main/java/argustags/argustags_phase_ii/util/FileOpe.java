package argustags.argustags_phase_ii.util;

import argustags.argustags_phase_ii.service.TaskService;
import argustags.argustags_phase_ii.serviceImpl.TaskImpl;

import java.io.*;
import java.util.ArrayList;

public class FileOpe {

    //返回文件的行数
    public int getLineNum(String fileName){
        int num = 0;
        try {
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            while (br.readLine()!=null){
                num++;
            }
            br.close();
            return num;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return 0;
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    //返回查询对应状态task的总数
    public int getStatusNum(String fileName,String status){
        String valueString = null;
        TaskService ts = new TaskImpl();
        int result = 0;
        try {
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            while((valueString = br.readLine())!=null){
                if(ts.getByID(valueString).getStatus().equals(status)){
                    result++;
                }
            }
            return result;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return 0;
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    //在制定文件中写入指定内容
    public ResultMessage write(String path,String content){
        File f = new File(path);
        try{
            FileWriter fw = new FileWriter(f,true);
            fw.write(content);
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

    //删除含关键字的一行记录
    public ResultMessage deleteLine(String keyword,String fileName){
        String valueString = null;
        File f1 = new File(fileName);
        File f2 = new File("temp");
        try {
            FileWriter fw = new FileWriter(f2,true);
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            while ((valueString=br.readLine())!=null){
                if(valueString.indexOf(keyword)!=(-1)){
                    continue;
                }
                fw.write(valueString+"\n");
            }
            br.close();
            fw.flush();
            fw.close();
            f1.delete();
            f2.renameTo(f1);
            return ResultMessage.SUCCESS;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return ResultMessage.FAILED;
        } catch (IOException e) {
            e.printStackTrace();
            return ResultMessage.FAILED;
        }
    }

    //读取文件，将内容按行存入一个ArrayList中
    public ArrayList<String> getLine(String path){
        String valueString = null;
        try {
            FileReader fr = new FileReader(path);
            BufferedReader br = new BufferedReader(fr);
            ArrayList<String> content = new ArrayList<>();
            while((valueString = br.readLine())!=null){
                content.add(valueString);
            }
            return content;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    //读取一个文件第三行的内容
    public String getThirdLine(String path){
        try {
            FileReader fr = new FileReader(path);
            BufferedReader br = new BufferedReader(fr);
            br.readLine();
            br.readLine();
            String result = br.readLine();
            br.close();
            return result;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
