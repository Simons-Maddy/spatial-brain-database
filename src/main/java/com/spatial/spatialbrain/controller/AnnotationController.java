package com.spatial.spatialbrain.controller;

import com.spatial.spatialbrain.service.ReadFile;
import com.spatial.spatialbrain.service.SpatialAnnotation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Controller
public class AnnotationController {
    
    /**
     * 首先根据此方法跳转到upload.html界面
     *
     * @return
     */
    @RequestMapping("/annotation")
    public String toannotation() {
        return "annotation";
    }


    /**
     * 文件上传
     * @param multipartFile
     * @return
     * @throws IllegalStateException
     * @throws IOException
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String upload(Model m, @RequestParam(value = "multipartFile") MultipartFile[] multipartFile)
            throws Exception {
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            for (int i = 0; i < multipartFile.length; i++) {
                if (Objects.equals(multipartFile[i].getOriginalFilename(), "")) {
                    return "annotation";
                }
            }
            Date date = new Date();
            String ramStr = "";
            for (int i = 0; i < 3; i++) {
                ramStr = ramStr + (char)(Math.random()*26+'A');
            }
            SimpleDateFormat dateFormat= new SimpleDateFormat("yyyyMMddhhmmss");
            String tempDir = dateFormat.format(date) + ramStr;
            String filePath = "/Users/chunfu/Desktop/"+ tempDir + "/";
            for (int i = 0; i < multipartFile.length; i++) {
                // 设置文件名称
                map.put("nameParam", i);
                // 设置文件名称
                map.put("fileame", multipartFile[i].getName());
                // 设置文件类型
                map.put("contentType", multipartFile[i].getContentType());
                // 设置文件大小
                map.put("fileSize", multipartFile[i].getSize());
                // 创建文件名称
                /*
                String fileName = UUID.randomUUID() + "." + multipartFile[i].getOriginalFilename()
                        .substring(multipartFile[i].getOriginalFilename().indexOf(".")+1);
                if (multipartFile[i].getOriginalFilename()
                        .substring(multipartFile[i].getOriginalFilename().indexOf(".")+1) == ".mtx.gz")
                    matrixname = fileName;
                 */

                // 获取到文件的路径信息
                /* RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
                ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
                String filePath = servletRequestAttributes.getRequest().getServletContext().getRealPath("/") + fileName;*/
                // 打印保存路径
                System.out.println(multipartFile[i].getContentType());
                System.out.println(filePath);
                // 保存文件的路径信息
                map.put("filePath", filePath);
                // 创建文件和文件路径
                File saveFile = new File(filePath,multipartFile[i].getOriginalFilename());
                saveFile.getParentFile().mkdirs();
                // 文件保存
                multipartFile[i].transferTo(saveFile);
                System.out.println(map);
                // 返回信息
            }
            //显示等待页面
            waitPage(m,tempDir,filePath);
            //开始注释文件
            annotationResult(filePath);
            return "waitpage";
        }catch (Exception e){
            throw e;
        }
    }

    public void waitPage(Model m, String RequestID,String filePath) throws Exception {
        m.addAttribute("RequestID",RequestID);
        //监控文件变化
        //FileMonite(filePath);
    }

    /**
     * 根据此方法跳转到result.html界面
     *
     */
    @RequestMapping("/result")
    public String toResult() throws Exception {
        return "result";
    }

    /**
     * 进行注释分析
     *
     */
    public String annotationResult(String dir) throws Exception {
        File file = new File(dir +"logs");
        file.mkdirs();
        String pypath = "src/main/java/com/spatial/spatialbrain/service/cal_qc_metrics.py";
        String annotateShell = "python " + pypath +" --dirpath "+ dir  + " 1>" + dir + "logs/cal_qc_metrics.log " +
                "2>" + dir + "logs/error.log";
        String result = SpatialAnnotation.execShell(annotateShell, true);
        if(result.matches("done")){
            ReadFile readfile = new ReadFile();
            String fileContext = readfile.ReadFile(dir+"adata_a1p1.obs.csv");
            System.out.println(fileContext);
            return "result";
        }else {
            return "annoError";
        }
    }

    public void FileMonite(String dirPath) throws Exception {
        //WatchService是一个接口,利用Filesystems类获取FileSystem，然后根据这个类，new一个WatchService。
        //监听文件
        String target_file = "t.txt";
        //构造监听服务
        WatchService watchService= FileSystems.getDefault().newWatchService();
        //监听注册，监听实体的创建、修改、删除事件，并以高频率(每隔2秒一次，默认是10秒)监听
        Paths.get(dirPath).register(watchService,
                new WatchEvent.Kind[]{
                StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_DELETE,
                StandardWatchEventKinds.ENTRY_MODIFY});
        while (true){
            //每隔2秒拉取监听key
            WatchKey key = watchService.poll(2, TimeUnit.SECONDS);
            //监听key为null,则跳过
            if (key == null) {
                continue;
            }
            //获取监听事件
            for(WatchEvent<?> event : key.pollEvents()){
                System.out.println(event.context()+"文件发生了"+event.kind()+"事件");
            }
            boolean vaild = key.reset();
            if(!vaild){
                break;
            }
        }

    }
}

