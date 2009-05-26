/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.security;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Set;

/**
 * THe application implements this an passes it
 * 
 * @author Jim Barritt
 * @version $Revision: 1.2 $
 *          $Id: ISecurityService.java,v 1.2 2004/08/10 11:17:36 rdjbarri Exp $
 */
public interface ISecurityService {

    /**
     * Has this session been initialised with a valid user.
     */
    boolean isUserAuthenticated(HttpSession session);

    /**
     * Used so we can ask if the user is in a set of roles.
     * Roles are just strings so you can secure certain sections of a page so that only
     * certain roles may see them.
     */
    boolean isUserInApplicationRoles(HttpSession session, Set roles);

    Principal getUserPrincipal(HttpSession session);

    String getUserName(HttpSession session);

    Set getUserRoles(HttpSession session);

    /**
     * It is up to the application to decide where to send requests for a login.
     * Whatever path you return here (e.g. "./login.jsp") will be used as the login page.
     */
    String getLoginForwardPath();


}
