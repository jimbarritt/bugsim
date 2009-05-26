/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.web.action;

/**
 * Something that knows how to create an action model.
 *
 * @author Jim Barritt
 * @version $Revision: 1.1 $
 *          $Id: IActionModelFactory.java,v 1.1 2004/08/30 11:29:41 rdjbarri Exp $
 */
public interface IActionModelFactory {

    ActionModel createActionModel();
}
