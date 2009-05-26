/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.security;

import java.security.Principal;
import java.util.Set;

/**
 * Class or Interface description.
 * 
 * @author Jim Barritt
 * @version $Revision: 1.2 $
 *          $Id: UserStub.java,v 1.2 2004/08/10 11:17:36 rdjbarri Exp $
 */
class UserStub {

    public UserStub(String userId, String userName, String password, Set roles) {
        _password = password;
        _roles = roles;
        _userId = userId;
        _userName = userName;
        _principal = new SimplePrincipal(_userId);
    }

    public boolean isValidPassword(String test) {
        return _password.equals(test);
    }

    public Set getRoles() {
        return _roles;
    }

    public String getUserId() {
        return _userId;
    }


    public Principal getPrincipal() {
        return _principal;
    }

    public String getUserName() {
        return _userName;
    }

    private static class SimplePrincipal implements Principal {
        public SimplePrincipal(String name) {
            _name = name;
        }

        public String getName() {
            return _name;
        }

        private String _name;
    }

    private String _userId;
    private String _userName;
    private String _password;
    private Set _roles;
    private SimplePrincipal _principal;


}
