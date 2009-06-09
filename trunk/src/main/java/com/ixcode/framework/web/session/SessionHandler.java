/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.web.session;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Wrapper that deals with the session
 * 
 * @author Jim Barritt
 * @version $Revision: 1.4 $
 *          $Id: SessionHandler.java,v 1.4 2004/09/17 10:58:11 rdjbarri Exp $
 */
public class SessionHandler {

    public static ISessionContext getSessionContext(HttpSession session, Class contextClass) {
        return (ISessionContext) getAttribute(session, contextClass.getName());
    }


    public static void setSessionContext(HttpSession session, ISessionContext sessionContext) {
        session.setAttribute(sessionContext.getClass().getName(), sessionContext);
    }

    public static void removeSessionContext(HttpSession session, Class contextClass) {
        session.removeAttribute(contextClass.getName());
    }


    public static Object getAttribute(HttpSession session, String key) {
        if (!hasAttribute(session, key)) {
            throw new IllegalStateException("Could not locate attribute '" + key + "' in the session");
        }
        return session.getAttribute(key);
    }

    private static boolean hasAttribute(HttpSession session, String key) {
        return session.getAttribute(key) != null;
    }


    public static final String PREFIX = "com.ixcode.framework.web.session";
    public static final String ATTR_FORM_SUBMISSION_TOKENS = PREFIX + "FORM_SUBMISSION_IDS";

    public static int getNextFormSumbissionToken(HttpSession session, String formId) {
        Map formSubmissionTokens = getFormSubmissionTokens(session);
        Integer currentToken = getCurrentFormSubmissionToken(formSubmissionTokens, formId);

        Integer nextToken = new Integer(currentToken.intValue() + 1);
        formSubmissionTokens.put(formId, nextToken);
        return nextToken.intValue();
    }

    public static int getCurrentFormSubmissionToken(HttpSession session, String formId) {
        Map formSubmissionIds = getFormSubmissionTokens(session);
        Integer currentId = getCurrentFormSubmissionToken(formSubmissionIds, formId);
        return currentId.intValue();
    }


    private static Integer getCurrentFormSubmissionToken(Map formSubmissionIds, String formId) {
        if (!formSubmissionIds.containsKey(formId)) {
            formSubmissionIds.put(formId, new Integer(0));
        }
        Integer currentId = (Integer)formSubmissionIds.get(formId);
        return currentId;
    }

    private static Map getFormSubmissionTokens(HttpSession session) {
        Map formSubmissionIds = (Map)session.getAttribute(ATTR_FORM_SUBMISSION_TOKENS);
        if (formSubmissionIds == null) {
            formSubmissionIds = new HashMap();
            session.setAttribute(ATTR_FORM_SUBMISSION_TOKENS, formSubmissionIds);
        }
        return formSubmissionIds;
    }



}
