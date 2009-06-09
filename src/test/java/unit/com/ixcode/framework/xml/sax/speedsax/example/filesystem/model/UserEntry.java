package com.ixcode.framework.xml.sax.speedsax.example.filesystem.model;

/**
 */
public class UserEntry {

    public String getId() {
        return _id;
    }

    public void setId(String id) {
        _id = id;
    }

    public boolean isRead() {
        return _read;
    }

    public void setRead(boolean read) {
        _read = read;
    }

    public boolean isWrite() {
        return _write;
    }

    public void setWrite(boolean write) {
        _write = write;
    }

    public boolean isCreate() {
        return _create;
    }

    public void setCreate(boolean create) {
        _create = create;
    }

    private String _id;
    private boolean _read = true;
    private boolean _write = true;
    private boolean _create;
}
