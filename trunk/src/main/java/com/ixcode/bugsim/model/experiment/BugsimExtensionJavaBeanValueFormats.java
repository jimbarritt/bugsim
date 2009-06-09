/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.model.experiment;

import com.ixcode.bugsim.model.experiment.parameter.resource.layout.calculated.*;
import com.ixcode.bugsim.model.experiment.parameter.resource.layout.predefined.*;
import com.ixcode.framework.javabean.format.*;

import java.util.*;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class BugsimExtensionJavaBeanValueFormats {


    private BugsimExtensionJavaBeanValueFormats() {
    }

    public static void registerBugsimExtensionFormats() {
        if (!JavaBeanFormatter.isExtensionFormatRegistered(Locale.UK, CabbageInitialisationParameters.class)) {
            JavaBeanFormatter.registerExtensionFormat(Locale.UK, CabbageInitialisationParameters.class,  new CabbageInitialisationParametersFormat());
        }
        if (!JavaBeanFormatter.isExtensionFormatRegistered(Locale.UK, CalculatedResourceLayoutType.class)) {
            JavaBeanFormatter.registerExtensionFormat(Locale.UK, CalculatedResourceLayoutType.class,  new TypeSafeEnumFormat(CalculatedResourceLayoutType.class));
        }

    }
}
