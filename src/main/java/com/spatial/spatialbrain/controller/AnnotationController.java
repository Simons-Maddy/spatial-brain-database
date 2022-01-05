package com.spatial.spatialbrain.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

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
    @ResponseBody
    public Object upload(String name, @RequestParam(value = "multipartFile") MultipartFile[] multipartFile)
            throws IllegalStateException, IOException {
        Map<String, Object> map = new HashMap<String, Object>();
            for (int i = 0; i < multipartFile.length; i++) {
                if(Objects.equals(multipartFile[i].getContentType(), "application/octet-stream")){
                    return "no 3 files!!!";
                } else {
                    // 设置文件名称
                    map.put("nameParam", name);
                    // 设置文件名称
                    map.put("fileame", multipartFile[i].getName());
                    // 设置文件类型
                    map.put("contentType", multipartFile[i].getContentType());
                    // 设置文件大小
                    map.put("fileSize", multipartFile[i].getSize());
                    // 创建文件名称
                    String fileName = UUID.randomUUID() + "." + multipartFile[i].getContentType()
                            .substring(multipartFile[i].getContentType().lastIndexOf("/") + 1);
                    // 获取到文件的路径信息
                    /*RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
                    ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
                    String filePath = servletRequestAttributes.getRequest().getServletContext().getRealPath("/") + fileName;*/
                    String filePath = "/Users/chunfu/Desktop/"+ fileName;
                    // 打印保存路径
                    System.out.println(multipartFile[i].getContentType());
                    System.out.println(filePath);
                    // 保存文件的路径信息
                    map.put("filePath", filePath);
                    // 创建文件
                    File saveFile = new File(filePath);
                    // 文件保存
                    multipartFile[i].transferTo(saveFile);
                    // 返回信息
            }
        }
        return map;
    }
}

