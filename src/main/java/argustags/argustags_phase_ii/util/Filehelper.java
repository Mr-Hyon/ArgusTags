package argustags.argustags_phase_ii.util;

import java.io.*;
import java.util.Scanner;

public class Filehelper {
    public static String getFilePath() {

        String currentPath = new File("").getAbsolutePath();
        if(currentPath.contains("pictures")) {
            currentPath = currentPath + "/src/main/resources/static/data/json/";
        }
        else{
            currentPath+= "/pictures/src/main/resources/static/data/json/";
        }
        return currentPath;

    }

    /**
     * 文件的写入操作
     * @param content
     * @param filename
     * @return
     */
    public static boolean saveFile(String content,String filename){

        String filePath=getFilePath()+filename+".json";
        try {


            File file = new File(filePath);
            if(!file.exists()){
                file.createNewFile();
            }
            FileWriter writer = new FileWriter(file, true);
            PrintWriter printWriter = new PrintWriter(writer);
            printWriter.write(content);
            printWriter.flush();
            writer.close();
            printWriter.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }



    public static String readFile(String fileName){

        File file=new File(getFilePath()+fileName+".json");


        try {

            Scanner scanner=new Scanner(file);
            StringBuilder stringBuilder=new StringBuilder();

            while(scanner.hasNext()){

                stringBuilder.append(scanner.nextLine());
            }

            String res=stringBuilder.toString();
            return res;
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;

    }

    public static boolean override(String content,String fileName){
        try {

            File file = new File(getFilePath()+fileName+".json");
            FileWriter writer = new FileWriter(file);
            PrintWriter printWriter = new PrintWriter(writer);
            printWriter.write(content);
            writer.close();
            printWriter.close();

            return true;
        }catch (IOException ex){
            ex.printStackTrace();
        }

        return false;
    }

    public static boolean createDir(String dirName){
        File f=new File(getFilePath()+dirName);

        if(f.mkdirs()){
            return true;
        }

        return false;
    }

    public static boolean createFile(String fileName){

        File f=new File(getFilePath()+fileName+".json");
        if(f.exists()){
            return true;
        }
        else{
            try {
                f.createNewFile();
            } catch (IOException e) {

                e.printStackTrace();
            }
        }
        return false;
    }
}
