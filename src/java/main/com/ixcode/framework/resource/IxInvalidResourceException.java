/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.resource;

import java.net.URL;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class IxInvalidResourceException extends RuntimeException {

    public IxInvalidResourceException(String resourcePath, URL url) {
        super ("could not locate the environment '" + resourcePath + "' url: '" + url + "'");
        _url = url;
        _resourcePath = resourcePath;
    }

    private URL _url;
    private String _resourcePath;
}
