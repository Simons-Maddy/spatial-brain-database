package com.spatial.spatialbrain.controller;

import com.spatial.spatialbrain.service.FileUpload;
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
     * 根据此方法跳转到annotation.html界面
     * @return
     */
    @RequestMapping("/annotation")
    public String toannotation() {
        return "annotation";
    }

    /**
     * 根据此方法跳转到waitpage.html界面
     * @return
     */
    @RequestMapping("/upload")
    public String toWaitPage(Model m,@RequestParam(value = "multipartFile") MultipartFile[] multipartFile
        ) throws Exception {
        FileUpload fileUpload = new FileUpload();
        String[] dirs = fileUpload.Upload(m,multipartFile);
        m.addAttribute("RequestID",dirs[0]);
        //开始注释文件
        String results = annotationResult(m,dirs[1]);
        //监控文件变化
        //FileMonite(filePath);
        return "waitpage";
    }

    /**
     * 根据此方法跳转到result.html界面
     * @return
     */
    @RequestMapping(value = "/result", method = RequestMethod.POST)
    public String toResult(Model m, String results) throws Exception {
        //监控文件变化
        //FileMonite(filePath);
        m.addAttribute("csv",results);

        return "result";
    }


    /**
     * 根据此方法跳转到annoError.html界面
     * @return
     */
    public String toAnnoError() throws Exception { return "annoError"; }


    /**
     * 进行注释分析，并判断结果
     *
     */
    public String annotationResult(Model m,String dir) throws Exception {
        File file = new File(dir +"logs");
        file.mkdirs();
        String pypath = "src/main/java/com/spatial/spatialbrain/service/cal_qc_metrics.py";
        String annotateShell = "python " + pypath +" --dirpath "+ dir  + " 1>" + dir + "logs/cal_qc_metrics.log " +
                "2>" + dir + "logs/error.log";
        String result = SpatialAnnotation.execShell(annotateShell, true);
        if(result.matches("done")){
            ReadFile readfile = new ReadFile();
            String fileContext = readfile.ReadFile(dir+"adata_a1p1.obs.csv");
            return fileContext;
        }else {
            return toAnnoError();
        }
    }
}

