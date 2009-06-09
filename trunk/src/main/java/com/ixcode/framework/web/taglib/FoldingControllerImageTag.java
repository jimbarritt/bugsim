/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.web.taglib;

import com.ixcode.framework.model.IModelAdapter;
import com.ixcode.framework.model.binding.BindingUrlFormatter;
import com.ixcode.framework.model.binding.ModelBinding;
import com.ixcode.framework.model.folding.FoldingState;

import javax.servlet.jsp.JspException;

/**
 * Class or Interface description.
 * 
 * @author Jim Barritt
 * @version $Revision: 1.4 $
 *          $Id: FoldingControllerImageTag.java,v 1.4 2004/09/17 10:58:05 rdjbarri Exp $
 */
public class FoldingControllerImageTag extends BoundTagBase {
    public String getStyle() {
        return _style;
    }

    public void setStyle(String style) {
        _style = style;
    }

    public void setStyleClass(String styleClass) {
        _styleClass = styleClass;
    }

    protected  void appendDefaultAttributes(StringBuffer sb) {
        if (_style != null) {
            sb.append(" style='").append(_style).append("'");
        }

        if (_styleClass != null) {
            sb.append(" class='").append(_styleClass).append("'");
        }

    }

    /**
     * Override this if you want to do something spexial @see TableCellInputTag.
     *
     * @return
     */
    public String calculateAlignment() {
        return _alignment;
    }

    public String getAlignment() {
        return _alignment;
    }

    public void setAlignment(String alignment) {
        _alignment = alignment;
    }

    static String FOLDING_CONTROLLER_PREFIX = "foldingController_";

    public FoldingControllerImageTag() {
    }

    protected int writeTagContent(StringBuffer sb, ModelBinding binding, IModelAdapter modelAdapter) throws JspException {

        String url = BindingUrlFormatter.createFoldingBindingUrl(binding);

        appendJavaScript(sb);

        String controllerId = FOLDING_CONTROLLER_PREFIX + url;
        String sectionId = FoldingSectionTag.FOLDING_SECTION_PREFIX + url;

        FoldingState foldingState = PageContextHandler.getFoldingState(pageContext, binding);
        String imgSrc = (foldingState == FoldingState.FOLDED) ? _foldedImgSrc : _unfoldedImgSrc;


        sb.append("<img");
        sb.append(" id='").append(controllerId).append("'");
        sb.append(" src='").append(PageContextHandler.createContextUrl(pageContext, imgSrc)).append("'");
        sb.append(" onclick=\"toggleFolding('").append(controllerId).append("'");
        sb.append(", '").append(sectionId).append("'");
        sb.append(", '").append(url).append("'");
        sb.append(")\"");

        appendDefaultAttributes(sb);

        sb.append(" />");
        return SKIP_BODY;
    }

    /**
     * Not a particulalry nice way of remembering if we already printed it but if / when we introduce the concept
     * of a javascript handler we can probably use that to do the same thing.
     */
    private void appendJavaScript(StringBuffer sb) throws JspException {

        if (pageContext.getAttribute(FOLDING_JSCRIPT_PRINTED) != null) {
            return; // its already been printed.
        }


        PageContextHandler.appendln(sb, "<script language='javascript'>");

        PageContextHandler.appendln(sb, 1, "function toggleFolding(controllerId, sectionId, foldedStateId) {");

        PageContextHandler.appendln(sb, 2, "var foldingController = document.getElementById(controllerId);");
        PageContextHandler.appendln(sb, 2, "var foldingSection = document.getElementById(sectionId);");
        PageContextHandler.appendln(sb, 2, "var foldedStateInput = document.getElementById(foldedStateId);");

        PageContextHandler.appendln(sb, 2, "if (foldingSection.style.display == \"block\") {");
        PageContextHandler.appendln(sb, 3, "foldingSection.style.display = \"none\";");
        PageContextHandler.appendln(sb, 3, "foldingController.src = \"" + PageContextHandler.createContextUrl(pageContext, _foldedImgSrc) +  "\";");
        PageContextHandler.appendln(sb, 3, "foldedStateInput.value = \""+ FoldingState.FOLDED + "\";");

        PageContextHandler.appendln(sb, 2, "} else {");
        PageContextHandler.appendln(sb, 3, "foldingSection.style.display = \"block\";");
        PageContextHandler.appendln(sb, 3, "foldingController.src = \"" + PageContextHandler.createContextUrl(pageContext, _unfoldedImgSrc) +  "\";");
        PageContextHandler.appendln(sb, 3, "foldedStateInput.value = \""+ FoldingState.UNFOLDED + "\";");

        PageContextHandler.appendln(sb, 2, "}");

//        PageContextHandler.appendln(sb, 2, "alert('Just set the value of input ' + foldedStateId + ' to ' + foldedStateInput.value);");
        if (_fireOnResize) {
            PageContextHandler.appendln(sb, 2, "onResize();"); // incase you have any resize code
        }

        PageContextHandler.appendln(sb, 1, "}");

        PageContextHandler.appendln(sb, "</script>");

        pageContext.setAttribute(FOLDING_JSCRIPT_PRINTED, Boolean.TRUE);

    }


    public void setAnchorModel(String anchorModel) {
        super.setModel(anchorModel);
    }

    public void setFoldedImgSrc(String foldedImgSrc) {
        _foldedImgSrc = foldedImgSrc;
    }

    public void setUnfoldedImgSrc(String unfoldedImgSrc) {
        _unfoldedImgSrc = unfoldedImgSrc;
    }


    public void setFireOnResize(boolean fireOnResize) {
        _fireOnResize = fireOnResize;
    }

    private String _foldedImgSrc;
    private String _unfoldedImgSrc;
    private boolean _fireOnResize;


    private static final String FOLDING_JSCRIPT_PRINTED = "com.ixcode.framework.web.taglib.FOLDING_JSCRIPT_PRINTED";
    private String _style;
    private String _styleClass;
    private String _alignment;
}
