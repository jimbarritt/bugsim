/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.web.taglib;

import com.ixcode.framework.model.lookup.Lookup;
import com.ixcode.framework.model.lookup.LookupContext;
import com.ixcode.framework.model.lookup.LookupHandler;
import com.ixcode.framework.model.lookup.LookupValueMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Basically spits out some javascript which deals with handling a lookup popup.
 *
 * @author Jim Barritt
 * @version $Revision: 1.4 $
 *          $Id: LookupHandlerTag.java,v 1.4 2004/09/13 11:10:34 rdjbarri Exp $
 */
public class LookupHandlerTag extends ListHandlerTag {

    public LookupHandlerTag() {
        super(LookupHandler.PARAM_SELECTED_ITEM);
        setSubmitForm(true);
    }

    public int doStartTag() throws JspException {
        int retVal  = super.doStartTag();

        StringBuffer sb = new StringBuffer();
        if (LookupHandler.isLookupCompleted(pageContext.getRequest())) {
            retVal = handleLookupComplete(sb);
        } else if (LookupHandler.isLookupCancelled(pageContext.getRequest())) {
            retVal = handleLookupCancelled(sb);
        }

        PageContextHandler.printOut(pageContext, sb);
        return retVal;
    }

    protected List createSubmissionParameters() {
        List params = new ArrayList();
        params.add(PARAM_LOOKUP_COMMAND);

        int lookupId = LookupHandler.getLookupIdParameter((HttpServletRequest)pageContext.getRequest());
        params.add(new SubmissionParameter(LookupHandler.PARAM_LOOKUP_ID, "" + lookupId, SubmissionParameter.SubmissionType.FORM));

        return params;
    }

    private int handleLookupCancelled(StringBuffer sb) {
        PageContextHandler.appendln(sb, "<script language=\"javascript\">");
        PageContextHandler.appendln(sb, 1, "function closePopup() {");
        PageContextHandler.appendln(sb, 2, "if (opener != null) {");
        PageContextHandler.appendln(sb, 3, "top.close();");
        PageContextHandler.appendln(sb, 2, "}");
        PageContextHandler.appendln(sb, 1, "}");
        PageContextHandler.appendln(sb, 1, "closePopup();");

        PageContextHandler.appendln(sb,  "</script>");

        return SKIP_PAGE;
    }

    /**
     * @todo We could add extra javascript to populate some descriptive fields with descriptive information (like name aswell as code).
     */ 
    private int handleLookupComplete(StringBuffer sb) throws JspException {

        sb.append("<script language=\"javascript\">\n");

        LookupContext ctx = LookupHandler.getLookupContext(pageContext.getSession());
        int id = LookupHandler.getLookupIdParameter((HttpServletRequest)pageContext.getRequest());
        Lookup lookup = ctx.getLookup(id);
        List lookupValues = LookupHandler.getLookupValueMappings(pageContext.getRequest());

        sb.append("    function closePopup() {\n");
        sb.append("        if (opener != null) {\n");


        for (Iterator itr = lookupValues.iterator(); itr.hasNext();) {
            LookupValueMapping mapping = (LookupValueMapping)itr.next();
            String bindingUrl = mapping.getXpath();
            sb.append("            opener.document.forms['").append(lookup.getSourceFormId()).append("']");
            sb.append("['").append(bindingUrl).append("'].value='").append(mapping.getValue()).append("';\n");
        }

        // USe this if you really need to reload the parent.
//            sb.append("            opener.location.reload(false);\n");
//        sb.append(" alert(\"Close Popup is called !!\");");
        sb.append("            top.close();\n");
        sb.append("        }\n");
        sb.append("     }\n");


        sb.append("    closePopup();\n");

        sb.append("</script>");

        return SKIP_BODY;

    }

    private static final SubmissionParameter PARAM_LOOKUP_COMMAND = new SubmissionParameter(LookupHandler.PARAM_LOOKUP_COMMAND, LookupHandler.COMMAND_SUBMIT);


}
