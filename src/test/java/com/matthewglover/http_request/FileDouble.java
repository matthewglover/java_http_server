package com.matthewglover.http_request;

import java.io.File;

public class FileDouble extends File {
    private File[] fileList;
    private String fileName;
    private boolean isFile = false;
    private boolean isDirectory = false;
    private long length;

    public FileDouble(String pathname) {
        super(pathname);
    }

    @Override
    public String getName() {
        return fileName;
    }

    @Override
    public boolean isDirectory() {
        return isDirectory;
    }

    @Override
    public boolean isFile() {
        return isFile;
    }

    @Override
    public File[] listFiles() {
        return fileList;
    }

    public void setFileList(String[] fileNameList) {
        fileList = new File[fileNameList.length];
        for (int i = 0; i < fileNameList.length; i++) {
            fileList[i] = buildFileDouble(fileNameList[i]);
        }
    }

    private FileDouble buildFileDouble(String fileName) {
        FileDouble fileDouble = new FileDouble("/path/to/file");
        fileDouble.setFileName(fileName);
        fileDouble.setIsFile(true);
        return fileDouble;
    }

    private void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setIsFile(boolean isFile) {
        this.isFile = isFile;
    }

    public void setIsDirectory(boolean isDirectory) {
        this.isDirectory = isDirectory;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public long length() {
        return length;
    }
}
