package com.spatial.spatialbrain.controller;

import org.apache.tomcat.util.file.ConfigurationSource;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.spatial.spatialbrain.service.SpatialAnnotation;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

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

    public String  fileError(Model m){
        String status = "error";
        m.addAttribute("status", status);
        return "annotation";
    }

    /**
     * 文件上传
     *
     * @param name
     * @param multipartFile
     * @return
     * @throws IllegalStateException
     * @throws IOException
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String upload(String name, @RequestParam(value = "multipartFile") MultipartFile[] multipartFile, HttpServletResponse response)
            throws IllegalStateException, IOException, FileException {
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            for (int i = 0; i < multipartFile.length; i++) {
                if (Objects.equals(multipartFile[i].getOriginalFilename(), "")) {
                    return "annotation";
                }
            }
            Date date = new Date();
            SimpleDateFormat dateFormat= new SimpleDateFormat("yyyyMMdd-hhmmss");
            String tempPath = dateFormat.format(date);
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
                String fileName = UUID.randomUUID() + "." + multipartFile[i].getOriginalFilename()
                        .substring(multipartFile[i].getOriginalFilename().indexOf(".")+1);

                // 获取到文件的路径信息
                /*RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
                ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
                String filePath = servletRequestAttributes.getRequest().getServletContext().getRealPath("/") + fileName;*/
                String filePath = "/Users/chunfu/Desktop/"+ tempPath;
                // 打印保存路径
                System.out.println(multipartFile[i].getContentType());
                System.out.println(filePath);
                // 保存文件的路径信息
                map.put("filePath", filePath);
                // 创建文件和文件路径
                File saveFile = new File(filePath,fileName);
                saveFile.getParentFile().mkdirs();
                // 文件保存
                multipartFile[i].transferTo(saveFile);
                System.out.println(map);
                // 返回信息
            }
        return "result";
        }catch (Exception e){
            throw e;
        }
    }

    /**
     * 根据此方法跳转到result.html界面
     *
     */

    public String annotationResult(String argument1,String argument2) throws Exception {

        return SpatialAnnotation.execShell("ls", true, argument1, argument2);

    }
}

