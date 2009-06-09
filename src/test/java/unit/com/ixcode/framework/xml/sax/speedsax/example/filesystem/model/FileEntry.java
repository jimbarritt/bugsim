package com.ixcode.framework.xml.sax.speedsax.example.filesystem.model;

/**
 */
public class FileEntry {

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        _name = name;
    }


    public String getFileContent() {
        return _fileContent;
    }

    public void setFileContent(String fileContent) {
        _fileContent = fileContent;
    }

    private String _name;

    private String _fileContent;


}
