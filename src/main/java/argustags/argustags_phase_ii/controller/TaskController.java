package argustags.argustags_phase_ii.controller;

import argustags.argustags_phase_ii.service.TaskService;
import argustags.argustags_phase_ii.serviceImpl.TaskImpl;
import argustags.argustags_phase_ii.service.AdminService;
import argustags.argustags_phase_ii.serviceImpl.AdminImpl;
import argustags.argustags_phase_ii.util.ResultMessage;
import argustags.argustags_phase_ii.vo.TaskVO;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

import static argustags.argustags_phase_ii.util.UnZip.unZipFiles;

@Controller
public class TaskController{
    TaskService Taskservice=new TaskImpl();
    AdminService Adminservice=new AdminImpl();
    @PostMapping(value = "/release", produces = "application/text; charset=utf-8")
    public @ResponseBody
    String releasetask(@RequestParam("username") String username,
                       @RequestParam("method") String method,
                       @RequestParam("reward") int reward,
                       @RequestParam("cut") double cut,
                       @RequestParam("dscb") String dscb,
                       @RequestParam("markedPersonNum") int markedPersonNum,
                       @RequestParam("obj") String obj,
                       @RequestParam("ddl") String ddl,
                       @RequestParam("file") MultipartFile file
    ) {System.out.println("0");
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String projectId = df.format(new Date());
        ArrayList<String> imgList = new ArrayList<>();
        System.out.println("1");
        try {
            //创建临时文件夹放置zip文件
            File tmpfile = new File("tempath");
            if (!tmpfile.exists()) {
                tmpfile.mkdir();
            }
            System.out.println("2");
            //文件为空
            if (file.isEmpty())
                return ResultMessage.FAILED.toString() + "请选择要上传的文件";
            System.out.println("3");
            //将zip放在tmp文件夹中
            String filename = file.getOriginalFilename();
            System.out.println(filename);
            if (!filename.endsWith(".zip")) {
                return ResultMessage.FAILED.toString() + "请上传zip格式的压缩文件";
            }
            File zipFile = new File("tempath" + File.separator + projectId + ".zip");
            if (!tmpfile.exists()) {
                zipFile.mkdir();
            }
            System.out.println(zipFile.getAbsolutePath());
            //file.transferTo(zipFile);
            byte[] bytes = file.getBytes();
            Path path = Paths.get("tempath" + File.separator + projectId + ".zip");
            FileOutputStream fos = new FileOutputStream(zipFile);
            fos.write(bytes);
            fos.close();

            //解压zip文件
            unZipFiles(zipFile.getAbsolutePath(),"d:"+File.separator+projectId+File.separator+"pics");
            System.out.println("4");
            //置filelist
            File picDest = new File("d:"+File.separator+projectId+File.separator+"pics"+projectId);
            String[] names = picDest.list();
            for(String name:names) {
                InputStream inputStream = null;
                byte[] data = null;
                try {
                    inputStream = new FileInputStream("d:" + File.separator + projectId + File.separator + "pics"+projectId+File.separator + name );
                    data = new byte[inputStream.available()];
                    inputStream.read(data);
                    inputStream.close();
                }catch(IOException e){
                    e.printStackTrace();
                }
                BASE64Encoder encoder = new BASE64Encoder();
                String base64_image =  "data:image/png;base64,"+encoder.encode(data);
                JsonObject object=new JsonObject();
                object.addProperty("origin_image", base64_image);
                object.addProperty("current_image",base64_image);
                object.addProperty("total_tag","default");
                object.addProperty("tags","default");
                object.addProperty("marked",0);
                imgList.add(object.toString());
            }
            System.out.println("5");
        } catch (IOException e) {
            e.printStackTrace();
        }

        int id=(Adminservice.getTaskNum()+1);
        TaskVO vo=new TaskVO(id,"标注",username,imgList,method,0+"",df.toString(),ddl,dscb,obj,cut,reward,markedPersonNum);
        ArrayList<String> worker = new ArrayList<>();
        vo.setWorkers(worker);
        ResultMessage rm=Taskservice.addTask(vo);
        if(rm == ResultMessage.SUCCESS)
            return "success";
        else
            return  "wrong";
    }



}
