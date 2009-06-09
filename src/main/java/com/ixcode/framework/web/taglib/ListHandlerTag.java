/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.web.taglib;


import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @todo not too sure about this class - i think it is maybe the same thing as LookupHandler - perhaps ther is only one - i thought it might be that there was a generic ListHandler aswell as a specific lookuplist handler
 * 
 * @author Jim Barritt
 * @version $Revision: 1.3 $
 *          $Id: ListHandlerTag.java,v 1.3 2004/09/08 10:11:55 rdjbarri Exp $
 */
public class ListHandlerTag extends TagSupport {

    public ListHandlerTag(String selectedItemParameterName) {
        _selectedItemParameterName = selectedItemParameterName;
    }


    public int doStartTag() throws JspException {
        FormTag parent = (FormTag)TagSupport.findAncestorWithClass(this, FormTag.class);
        if (parent == null && _formId == null) {
            throw new JspException("ListHandlerTag is not inside a from tag or you have not specified a form id");
        }
        if (parent != null) {
            _formId = parent.getId();
        }

        StringBuffer sb = new StringBuffer();
        List submissionParameters = createSubmissionParameters();
        appendHiddenInputs(sb, submissionParameters);

        sb.append("<script language=\"javascript\">\n");

        sb.append("    function submitItem(selectedItemId) {\n");
//        sb.append("          alert('trying to submit item' + selectedItemId);\n");
        sb.append("        document.forms['").append(_formId).append("']['").append(_selectedItemParameterName).append("'].value = selectedItemId;\n");


        appendSubmissionParameters(sb, submissionParameters);

        if (_submitForm) {
            sb.append("        document.forms['").append(_formId).append("'].submit();\n");
        }
        sb.append("     }\n");



        sb.append("</script>\n");

        PageContextHandler.printOut(pageContext, sb);

        return SKIP_BODY;
    }

    /**
     * @return a list of {@link SubmissionParameter }  objects which tell us how to set up hidden inputs and javascript for the submission.
     */
    protected List createSubmissionParameters() {
        return new ArrayList();
    }

    private void appendHiddenInputs(StringBuffer sb, List submissionParameters) {
        sb.append("<input type='hidden' name='").append(_selectedItemParameterName).append("' value='' />\n");
        for (Iterator itr = submissionParameters.iterator(); itr.hasNext();) {
            SubmissionParameter parameter = (SubmissionParameter)itr.next();
            String value = (parameter.getSubmissionType() == SubmissionParameter.SubmissionType.FORM) ? parameter.getValue() : "" ;
            sb.append("<input type='hidden' name='").append(parameter.getName()).append("' value='").append(value).append("' />\n");
        }
    }

    private void appendSubmissionParameters(StringBuffer sb, List parameters) {
        for (Iterator itr = parameters.iterator(); itr.hasNext();) {
            SubmissionParameter parameter = (SubmissionParameter)itr.next();
            if (parameter.getSubmissionType() == SubmissionParameter.SubmissionType.JAVASCRIPT) {
                sb.append("        document.forms['").append(_formId).append("']['").append(parameter.getName());
                sb.append("'].value = '").append(parameter.getValue()).append("';\n");
            }
        }
    }

    public void setFormId(String formId) {
        _formId = formId;
    }


    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }

    public void setSubmitForm(String submitForm) {
        _submitForm = Boolean.valueOf(submitForm).booleanValue();
    }

    protected void setSubmitForm(boolean submitForm) {
        _submitForm = submitForm;
    }

    private String _formId;

    private boolean _submitForm;



    private String _selectedItemParameterName;
}
