package com.spatial.spatialbrain.service;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FileUpload {

    /**
     * 文件上传
     * @param multipartFile
     * @return
     * @throws IllegalStateException
     * @throws IOException
     */
    public String[] Upload(Model m, MultipartFile[] multipartFile)
            throws Exception {
        try {
            Map<String, Object> map = new HashMap<String, Object>();
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
            String[] dir = new String[] {tempDir,filePath};
            return dir;
        }catch (Exception e){
            throw e;
        }
    }
}
