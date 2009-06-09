package com.ixcode.framework.xml.sax.speedsax.example.filesystem.model;

import java.util.List;
import java.util.ArrayList;

/**
 */
public class SecurityEntry {

    public SecurityEntry() {
    }

    public List getPermissions() {
        return _permissions;
    }

    public void setPermissions(List permissions) {
        _permissions = permissions;
    }

    public String getDescription() {
        return _description;
    }

    public void setDescription(String description) {
        _description = description;
    }
    private String _description;

    private List _permissions = new ArrayList();
}
