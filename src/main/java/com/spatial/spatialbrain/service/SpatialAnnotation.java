package com.spatial.spatialbrain.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SpatialAnnotation {
    public static String execShell(String shell, boolean log, Object...args) throws Exception{
        InputStream inputStream=null;
        BufferedReader br=null;
        InputStreamReader inputStreamReader=null;
        for(int i=0;i<args.length;i++){
            shell = shell +" "+ args[i];
        }
        if(log){
            System.out.println("cmd is : { "+ shell + " }");//输出shell操作
        }
        try{
            Process ps=Runtime.getRuntime().exec(new String[]{"sh","-c",shell});//执行
            inputStream=ps.getInputStream();
            inputStreamReader=new InputStreamReader(inputStream);
            br=new BufferedReader(inputStreamReader);
            return readStream(br,log);
        }catch(Exception e){
            throw new Exception(e);
        }finally{
            inputStream.close();
            inputStreamReader.close();
            br.close();
        }
    }

    private static String readStream(BufferedReader bufferedReader, boolean log) throws IOException {
        StringBuffer sb = new StringBuffer();
        String line;
        //返回结果
        sb.append("done");
        while ((line = bufferedReader.readLine()) != null) {
            sb.append(line).append("\n");
            if (log) {
                System.out.println(line);
            }
        }
        return sb.toString().trim();
    }
}
