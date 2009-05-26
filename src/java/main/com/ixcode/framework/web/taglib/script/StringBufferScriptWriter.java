/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.web.taglib.script;

/**
 * Class or Interface description.
 *
 * @author Jim Barritt
 * @version $Revision: $
 *          $Id: $
 */
public class StringBufferScriptWriter implements IScriptWriter {

    public IScriptWriter write(String content) {
        _sb.append(content);
        return this;
    }

    public IScriptWriter  write(StringBuffer sb) {
        _sb.append(sb);
        return this;
    }

    public IScriptWriter  writeLn(String line) {
        _sb.append(line).append("\n");
        return this;
    }

    public StringBuffer getContent() {
        return _sb;
    }

    private StringBuffer _sb = new StringBuffer();
}
