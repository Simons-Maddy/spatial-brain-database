package com.spatial.spatialbrain.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
            SimpleDateFormat dateFormat= new SimpleDateFormat("yyyyMMdd-hhmmss");
            String tempPath = dateFormat.format(date);
            String filePath = "/Users/chunfu/Desktop/"+ tempPath;
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
            annotationResult(filePath);
            waitPage(m,tempPath);
            return "waitpage";
        }catch (Exception e){
            throw e;
        }
    }

    public void waitPage(Model m, String RequestID) throws Exception {
        m.addAttribute("RequestID",RequestID);
    }

    /**
     * 根据此方法跳转到result.html界面
     *
     */
    @RequestMapping("/result")
    public String toResult() throws Exception { return "result";}

    /**
     * 返回分析结果
     *
     */
    public String annotationResult(String dir) throws Exception {
        String anotate_shell = "python cal_qc_metrics.py --dirpath $"+ dir;
        return "result";
    }
}

