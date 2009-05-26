/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.model.experiment;

import com.ixcode.bugsim.model.experiment.parameter.resource.layout.predefined.CabbageInitialisationParameters;
import com.ixcode.bugsim.model.experiment.parameter.resource.layout.predefined.CabbageInitialisationParametersFormat;
import com.ixcode.bugsim.model.experiment.parameter.resource.layout.calculated.CalculatedResourceLayoutType;
import com.ixcode.framework.javabean.format.JavaBeanFormatter;
import com.ixcode.framework.javabean.format.TypeSafeEnumFormat;
import com.ixcode.framework.math.geometry.ShapeLocationType;

import java.util.Locale;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class BugsimExtensionJavaBeanValueFormats {


    private BugsimExtensionJavaBeanValueFormats() {
    }

    public static void registerBugsimExtensionFormats() {
        JavaBeanFormatter.registerExtensionFormat(Locale.UK, CabbageInitialisationParameters.class,  new CabbageInitialisationParametersFormat());
        JavaBeanFormatter.registerExtensionFormat(Locale.UK, CalculatedResourceLayoutType.class,  new TypeSafeEnumFormat(CalculatedResourceLayoutType.class));

    }
}
