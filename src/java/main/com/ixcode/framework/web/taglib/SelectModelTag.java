/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.web.taglib;

import com.ixcode.framework.model.IModelAdapter;
import com.ixcode.framework.model.binding.BindingUrlFormatter;
import com.ixcode.framework.model.binding.ModelBinding;
import com.ixcode.framework.model.selection.SelectionModel;
import com.ixcode.framework.web.request.RequestHandler;

import javax.servlet.jsp.JspException;

/**
 * Prints out a check box which binds into the selection processing.
 * 
 * @author Jim Barritt
 * @version $Revision: 1.4 $
 *          $Id: SelectModelTag.java,v 1.4 2004/09/17 10:58:05 rdjbarri Exp $
 */
public class SelectModelTag extends BoundTagBase {

    public SelectModelTag() {
    }

    protected int writeTagContent(StringBuffer sb, ModelBinding binding, IModelAdapter modelAdapter) throws JspException {
        String selectionUrl = BindingUrlFormatter.createSelectionBindingUrl(binding);
        sb.append("<input type=\"checkbox\" name='");
        sb.append(selectionUrl);
        sb.append("'");

        SelectionModel selectionModel = RequestHandler.getSelectionModel(pageContext.getRequest());
        String modelId = modelAdapter.getModelId(binding.getModel());

        boolean boolValue = selectionModel.isModelSelected(modelId);
        if (boolValue) {
            sb.append(" checked");
        }
        sb.append(" value='true'");
        appendOnClick(sb);
        sb.append("' />" );

        // @todo make this a method on BindingUrlFormatter so it creates the url for you.
        sb.append("<input type='hidden' name='").append(BindingUrlFormatter.getSelectionCheckBoxMarkerId());
        sb.append("_").append(binding.getXpath()).append("'");
        sb.append(" value='").append(selectionUrl).append("'");



        sb.append("/>");

        return SKIP_BODY;
        
    }

    private void appendOnClick(StringBuffer sb) {
        if (_onClick != null) {
            sb.append(" onClick='").append(_onClick).append("'");
        }

    }

    public void setOnClick(String onClick) {
        _onClick = onClick;
    }

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

    private String _onClick;
    private String _style;
    private String _styleClass;
    private String _alignment;
}
