/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.web.taglib;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Simply prints out the appropriate URL including all the nescessary stuff
 * to make sure it allways works.
 *
 *
 * You pass it the relative path to whatever environment you want and it does the rest.
 *
 * You pass the path relative to the context root, NOT your file.
 *
 * e.g.
 *
 * i have a file which is served by url :
 *
 * http://localhost/someServlet/myfile.jim
 *
 * on the disk it is :
 *
 * webapp/
 *     jimsfiles/
 *         subfolder/
 *              myFile.jim
 *
 * But i want to reference the file :
 *
 * webapp/
 *     styles/
 *        page.css
 *
 *
 * If i put :
 *
 * <code>
 *     <link rel="stylesheet" type="text/css" href="../../style/taglib.css">
 * </code>
 *
 * which is relative to my file, it wont work.
 * 
 * @author Jim Barritt
 * @version $Revision: 1.1 $
 *          $Id: UrlTag.java,v 1.1 2004/08/11 12:08:22 rdjbarri Exp $
 */
public class UrlTag extends TagSupport {

    public UrlTag() {
    }

    /**
     * The encode part makes sure that the session id gets added if the browser has cookies disabled.4
     * @return
     * @throws JspException
     */
    public int doEndTag() throws JspException {
        String contextPath = PageContextHandler.createContextUrl(pageContext, _relativePath);
        HttpServletResponse response = (HttpServletResponse)pageContext.getResponse();
        String encoded = (_isRedirect) ? response.encodeRedirectURL(contextPath) : response.encodeURL(contextPath);
        PageContextHandler.printOut(pageContext, encoded);

        return EVAL_PAGE;
    }

    public void setRelativePath(String relativePath) {
        _relativePath = relativePath;
    }

    public void setRedirect(boolean redirect) {
        _isRedirect = redirect;
    }

    private String _relativePath;
    private boolean _isRedirect;
}
