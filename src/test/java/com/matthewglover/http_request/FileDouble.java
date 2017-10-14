package com.matthewglover.http_request;

import java.io.File;

public class FileDouble extends File {
    private File[] fileList;
    private String fileName;
    private boolean isFile;

    public FileDouble(String pathname) {
        super(pathname);
    }

    public void setFileList(String[] fileNameList) {
        fileList = new File[fileNameList.length];
        for (int i = 0; i < fileNameList.length; i++) {
            FileDouble fileDouble = new FileDouble("/path/to/file");
            fileDouble.setFileName(fileNameList[i]);
            fileList[i] = fileDouble;
        }
    }

    @Override
    public File[] listFiles() {
        return fileList;
    }

    @Override
    public String getName() {
        return fileName;
    }

    @Override
    public boolean isDirectory() {
        return false;
    }

    @Override
    public boolean isFile() {
        return isFile;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setIsFile(boolean isFile) {
        this.isFile = isFile;
    }
}
