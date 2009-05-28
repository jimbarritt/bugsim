package com.ixcode.framework.xml.sax.speedsax.example.filesystem.model;

import java.util.List;
import java.util.ArrayList;

/**
 */
public class DirectoryEntry {

    public DirectoryEntry() {
    }

    public void addFile(FileEntry fileEntry) {
        _fileEntries.add(fileEntry);
    }

    public List getFileEntries() {
        return _fileEntries;
    }

    


    public void setSecurityEntry(SecurityEntry securityEntry) {
        _securityEntry = securityEntry;
    }

    public SecurityEntry getSecurityEntry() {
        return _securityEntry;
    }

    public void setFileEntries(List list) {
        _fileEntries = list;
    }

    public void setDirectories(List list) {
        _directories = list;
    }

    public List getDirectories() {
        return _directories;
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        _name = name;
    }

    public String toString() {
        return "DirectoryEntry{" +
                "_name='" + _name + "'" +
                "}";
    }

    public void addDirectory(DirectoryEntry directoryEntry) {
        _directories.add(directoryEntry);
    }


    public String getTitle() {
        return _title;
    }

    public void setTitle(String title) {
        _title = title;
    }

    public String getTitleStyle() {
        return _titleStyle;
    }

    public void setTitleStyle(String titleStyle) {
        _titleStyle = titleStyle;
    }

    public Message getMessage() {
        return _message;
    }

    public void setMessage(Message message) {
        _message = message;
    }

    private List _fileEntries = new ArrayList();

    private SecurityEntry _securityEntry;
    private List _directories = new ArrayList();
    private String _name;
    private String _title;
    private String _titleStyle;
    private Message _message;
}
