package com.spatial.spatialbrain.service;

import java.io.File;
import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationObserver;

/**
 * @time 2022-1-14
 * @author Chunfu Simons
 * @version 1.0
 */
public class FileListener implements FileAlterationListener {

    @Override
    public void onStart(FileAlterationObserver observer) {
        System.out.println("onStart");
    }

    @Override
    public void onDirectoryCreate(File directory) {
        System.out.println("onDirectoryCreate:" + directory.getName());
    }

    @Override
    public void onDirectoryChange(File directory) {
        System.out.println("onDirectoryChange:" + directory.getName());
    }

    @Override
    public void onDirectoryDelete(File directory) {
        System.out.println("onDirectoryDelete:" + directory.getName());
    }

    @Override
    public void onFileCreate(File file) {
        System.out.println("onFileCreate:" + file.getName());
    }

    @Override
    public void onFileChange(File file) {
        System.out.println("onFileCreate : " + file.getName());
    }

    @Override
    public void onFileDelete(File file) {
        System.out.println("onFileDelete :" + file.getName());
    }

    @Override
    public void onStop(FileAlterationObserver observer) {
        System.out.println("onStop");
    }

}
