/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.web.taglib;

import com.ixcode.framework.model.IModelAdapter;
import com.ixcode.framework.model.binding.BindingUrlFormatter;
import com.ixcode.framework.model.binding.ModelBinding;
import com.ixcode.framework.model.folding.FoldingState;

/**
 * Class or Interface description.
 * 
 * @author Jim Barritt
 * @version $Revision: 1.2 $
 *          $Id: FoldingSectionTag.java,v 1.2 2004/08/18 12:39:55 rdjbarri Exp $
 */
public class FoldingSectionTag extends BoundBodyTagBase {

    static String FOLDING_SECTION_PREFIX = "foldingSection_";

    public FoldingSectionTag() {
    }





    protected int appendStartTagContent(StringBuffer sb, ModelBinding binding, IModelAdapter modelAdapter) {
        String url = BindingUrlFormatter.createFoldingBindingUrl(binding);
        FoldingState foldingState = PageContextHandler.getFoldingState(pageContext, binding);

        String foldingStyle = (foldingState == FoldingState.FOLDED) ? "none" : "block";


        sb.append("<").append(_htmlTag);
        sb.append(" id='").append(FOLDING_SECTION_PREFIX).append(url).append("'");

        sb.append(" style=\"");
        if (_style != null) {
            sb.append(_style).append(";");
        }
        sb.append("display:").append(foldingStyle).append(";");
        sb.append("\"");
        PageContextHandler.appendln(sb, ">");





        sb.append("<input type='hidden'");
        sb.append(" name='").append(url).append("'");
        sb.append(" value='").append(foldingState).append("'");
        sb.append(" id='").append(url).append("'");
        PageContextHandler.appendln(sb, " />");

        return EVAL_BODY_INCLUDE;
    }




    protected int appendAfterBodyContent(StringBuffer sb, ModelBinding binding, IModelAdapter modelAdapter) {
        sb.append("</" + _htmlTag + ">\n");
        return EVAL_PAGE;
    }


    public void setAnchorModel(String anchorModel) {
        super.setModel(anchorModel);
    }


    public void setStyle(String style) {
        _style = style;
    }

    private String _style;

    private String _htmlTag = "div";
}

