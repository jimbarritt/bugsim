/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.web.taglib;

/**
 * Class or Interface description.
 *
 * @author Jim Barritt
 * @version $Revision: 1.2 $
 *          $Id: SubmissionParameter.java,v 1.2 2004/09/08 10:11:55 rdjbarri Exp $
 */
class SubmissionParameter {


    public SubmissionParameter(String name, String value) {
        _name = name;
        _value = value;
    }

    public SubmissionParameter(String name, String value, SubmissionType submissionType) {
        _name = name;
        _value = value;
        _submissionType = submissionType;
    }

    public String getName() {
        return _name;
    }

    public String getValue() {
        return _value;
    }

    public SubmissionType getSubmissionType() {
        return _submissionType;
    }




    public static class SubmissionType {
        public static final SubmissionType JAVASCRIPT = new SubmissionType("javascript");
        public static final SubmissionType FORM = new SubmissionType("form");

        public SubmissionType(String name) {
            _name = name;
        }

        public String toString() {
            return _name;
        }

        public String getName() {
            return _name;
        }

        private String _name;
    }

    private String _name;
    private String _value;
    private SubmissionType _submissionType = SubmissionType.JAVASCRIPT;



}
