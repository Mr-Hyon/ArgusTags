package argustags.argustags_phase_ii.controller;

import argustags.argustags_phase_ii.service.InitiatorService;
import argustags.argustags_phase_ii.service.TaskService;
import argustags.argustags_phase_ii.serviceImpl.TaskImpl;
import argustags.argustags_phase_ii.service.AdminService;
import argustags.argustags_phase_ii.serviceImpl.AdminImpl;
import argustags.argustags_phase_ii.util.ResultMessage;
import argustags.argustags_phase_ii.vo.Image;
import argustags.argustags_phase_ii.vo.TaskVO;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    InitiatorService initiatorService;
    @Autowired
    TaskService taskService;
    AdminService Adminservice=new AdminImpl();
    @PostMapping(value = "/release", produces = "application/text; charset=utf-8")
    public @ResponseBody
    String releasetask(@RequestParam("username") String username,
                       @RequestParam("method") String method,
                       @RequestParam("dscb") String dscb,
                       @RequestParam("obj") String obj,
                       @RequestParam("ddl") String ddl,
                       @RequestParam("file") MultipartFile file
    ) {System.out.println("0");
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String projectId = df.format(new Date());
//        ArrayList<String> imgList = new ArrayList<>();
        ArrayList<Integer> imgList = new ArrayList<>();
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
                Image img =new Image();
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
                ArrayList<Integer> tags=new ArrayList<>();

                img.setBase64(base64_image);
                img.setTags(tags);
                taskService.addimage(img);
                int id=img.getId();
                imgList.add(id);

            }

        }catch(IOException e){
            e.printStackTrace();
        }



        TaskVO vo=new TaskVO("标注",username,imgList,method,0,df.toString(),ddl,dscb,obj);
        ArrayList<String> worker = new ArrayList<>();
        vo.setWorkers(worker);
        ResultMessage rm=taskService.addTask(vo,username);
        int credit=initiatorService.getCredit(username)-imgList.size()*15;
        initiatorService.updateCredit(credit,username);
        if(rm == ResultMessage.SUCCESS)
            return "success";
        else
            return  "wrong";
    }



}
