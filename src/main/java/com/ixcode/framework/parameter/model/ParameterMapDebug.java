/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.parameter.model;

import com.ixcode.framework.javabean.format.IJavaBeanValueFormat;
import com.ixcode.framework.javabean.format.JavaBeanFormatter;

import java.util.List;
import java.util.Iterator;
import java.util.Locale;

import org.apache.log4j.Logger;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class ParameterMapDebug {
    public static void debugParams(ParameterMap params) {
        if (log.isDebugEnabled()) {
            log.debug("-----------------------------------------------");
            log.debug("Parameters:");

            debugCategories(0, params.getCategories());
            log.debug("-----------------------------------------------");
        }
    }

    private static void debugCategories(int indent, List categories) {
        String padding = createIndent(indent);
        for (Iterator itr = categories.iterator(); itr.hasNext();) {
            Category category = (Category)itr.next();
            log.info(padding + "Category: " + category.getName());

            debugParameters(indent + 1, category.getParameters());

            debugCategories(indent + 1, category.getSubCategories());

        }

    }

    private static void debugParameters(int indent, List parameters) {
        String padding = createIndent(indent);
        for (Iterator itr = parameters.iterator(); itr.hasNext();) {
            Parameter parameter = (Parameter)itr.next();
            if (parameter.containsStrategy()) {
                StrategyDefinitionParameter alg = (StrategyDefinitionParameter)parameter.getValue();
                log.info(padding + "Parameter: " + parameter.getName() + " = (Contains Strategy)");
                debugStrategyParameter(indent + 1, alg);
            } else if (parameter instanceof StrategyDefinitionParameter) {
                debugStrategyParameter(indent, (StrategyDefinitionParameter)parameter);
            } else if (parameter instanceof DerivedParameter) {
                log.info(padding + "Derived: " + parameter.getName() + " = " + debugValue(indent, parameter.getValue()) + " derived from  - " + ((DerivedParameter)parameter).getSourceParameters());
            } else if (parameter.getValue() instanceof List) {
                debugParameter(padding, parameter, indent);
                List valueList = (List)parameter.getValue();
                if (valueList.size() > 0 && Parameter.class.isAssignableFrom(valueList.get(0).getClass())) {
                    debugParameters(indent+1, valueList);
                }
            } else {
                debugParameter(padding, parameter, indent);
            }


        }
    }

    private static void debugParameter(String padding, Parameter parameter, int indent) {
        log.info(padding + "Parameter: " + parameter.getName() + " = " + debugValue(indent, parameter.getValue()));
    }

    public static String debugValue(int indent, Object value) {
        IJavaBeanValueFormat format = FORMATTER.getFormat(Locale.UK, value.getClass());
        return format.format(value);

    }

    private static void debugStrategyParameter(int indent, StrategyDefinitionParameter strategy) {
        String padding = createIndent(indent);
        log.info(padding + "Strategy: " + strategy.getName() + " implemented by: '" + debugValue(indent, strategy.getValue()) + "'");
        debugParameters(indent + 1, strategy.getParameters());
    }

    private static String createIndent(int indent) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < indent; ++i) {
            sb.append("    ");
        }
        return sb.toString();
    }

     private static final JavaBeanFormatter FORMATTER = new JavaBeanFormatter();
    private static final Logger log = Logger.getLogger(ParameterMapDebug.class);
}
