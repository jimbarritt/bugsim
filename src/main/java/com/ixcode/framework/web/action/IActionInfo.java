/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.web.action;

/**
 * Again, we put an interface here because we expect different subclasses
 * e.g. we have {@link com.ixcode.framework.web.struts.StrutsActionInfo StrutsActionInfo}
 *
 * @author Jim Barritt
 * @version $Revision: 1.1 $
 *          $Id: IActionInfo.java,v 1.1 2004/08/30 11:29:41 rdjbarri Exp $
 */
public interface IActionInfo {

    public ActionEvent getActionEvent();
}
