/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.security;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

/**
 * Default implementation if you dont specify one which means
 * all users are allowed in.
 * 
 * @author Jim Barritt
 * @version $Revision: 1.2 $
 *          $Id: NullSecurityService.java,v 1.2 2004/08/10 11:17:36 rdjbarri Exp $
 */
public class NullSecurityService implements ISecurityService {

    public static final ISecurityService INSTANCE = new NullSecurityService();

    public boolean isUserAuthenticated(HttpSession session) {
        return true;
    }

    public boolean isUserInApplicationRoles(HttpSession session, Set roles) {
        return false;
    }

    public String getLoginForwardPath() {
        throw new IllegalStateException("This method should never be called");
    }

    public String getUserName(HttpSession session) {
        return "No security applied";
    }

    public Principal getUserPrincipal(HttpSession session) {
        return NULL_USER_PRINCIPAL;
    }

    public Set getUserRoles(HttpSession session) {
        return new HashSet();
    }

    private static Principal NULL_USER_PRINCIPAL =  new Principal() {
        public String getName() {
            return "No User";
        }
    };


}
