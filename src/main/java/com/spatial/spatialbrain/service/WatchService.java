package com.spatial.spatialbrain.service;

import java.nio.file.*;
import java.util.concurrent.TimeUnit;

public class WatchService {
    public void WatchService(String dirPath) throws Exception {
        //WatchService是一个接口,利用Filesystems类获取FileSystem，然后根据这个类，new一个WatchService。
        //监听文件
        String target_file = "t.txt";
        //构造监听服务
        java.nio.file.WatchService watchService= FileSystems.getDefault().newWatchService();
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
