/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Description : @todo make it generic by passing names of properties ina map ?
 */
public abstract class ArgsBase {

    public ArgsBase(String[] args, Map defaults) {
        initArgs(args, defaults);
    }

    private void initArgs(String[] args, Map defaults) {
        Map argMap = new HashMap();
        for (int i = 0; i < args.length; ++i) {
            StringTokenizer st = new StringTokenizer(args[i], "=");
            String argName = st.nextToken();
            if (st.hasMoreTokens()) {
                String argValue = st.nextToken();
                argMap.put(argName, argValue);
            }
        }

        for (Iterator itr = defaults.keySet().iterator(); itr.hasNext();) {
            String key = (String)itr.next();
            if (!argMap.containsKey(key)) {
                argMap.put(key, defaults.get(key));
            }
        }

        mapArgsToProperties(argMap);
    }

    protected abstract void mapArgsToProperties(Map argMap);

}
