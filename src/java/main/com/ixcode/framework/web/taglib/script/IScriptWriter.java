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
public interface IScriptWriter {

    IScriptWriter writeLn(String line);

    IScriptWriter write(String content);

    IScriptWriter write(StringBuffer sb);



}
