/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.security;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * If you just want to set up a simple security test with a fixed set of
 * hardcoded users and roles this is your baby.
 *
 * @author Jim Barritt
 * @version $Revision: 1.2 $
 *          $Id: SecurityServiceStub.java,v 1.2 2004/08/10 11:17:36 rdjbarri Exp $
 */
public abstract class SecurityServiceStub implements ISecurityService {

    protected SecurityServiceStub(String loginForwardPath) {
        _loginForwardPath = loginForwardPath;
    }

    public final String getLoginForwardPath() {
        return _loginForwardPath;
    }

    protected void registerUser(HttpSession session, String userId) {
        session.setAttribute(ATTR_USER, _users.get(userId));
    }

    public final boolean isUserAuthenticated(HttpSession session) {
        return (session.getAttribute(ATTR_USER) != null);
    }

    public Principal getUserPrincipal(HttpSession session) {
        return ((UserStub)session.getAttribute(ATTR_USER)).getPrincipal();
    }


    public final boolean isUserInApplicationRoles(HttpSession session, Set roles) {
        Set userRoles = getCurrentUser(session).getRoles();
        boolean isInRole = false;
        for (Iterator itr = roles.iterator(); itr.hasNext();) {
            String role = (String)itr.next();
            if (userRoles.contains(role)) {
                isInRole = true;
                break;
            }
        }
        return isInRole;
    }

    private UserStub getCurrentUser(HttpSession session) {
        return (UserStub)session.getAttribute(ATTR_USER);
    }

    public String getUserName(HttpSession session) {
        return getCurrentUser(session).getUserName();
    }

    public Set getUserRoles(HttpSession session) {
        return getCurrentUser(session).getRoles();
    }

    protected final void addUser(String userId, String userName, String password, Set roles) {
        UserStub user = new UserStub(userId, userName, password, roles);
        _users.put(userId, user);
    }

    protected boolean hasUser(String userId) {
        return _users.containsKey(userId);
    }

    protected boolean authenticate(String userId, String password) {
        if (!hasUser(userId)) {
            throw new IllegalStateException("No user registered with id'" + userId + "'");
        }
        UserStub user = (UserStub)_users.get(userId);
        return user.isValidPassword(password);
    }

    public void invalidateUser(HttpSession session) {
        session.removeAttribute(ATTR_USER);
    }


    private  static final String ATTR_USER = "com.ixcode.framework.security.USER";

    private Map _users = new HashMap();
    private String _loginForwardPath;

}
