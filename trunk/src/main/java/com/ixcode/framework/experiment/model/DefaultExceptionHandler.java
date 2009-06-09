/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.experiment.model;

import org.apache.log4j.Logger;

/**
 *  Description : Simply throws a runtime exception
 */
public class DefaultExceptionHandler implements IExceptionHandler {
    public void handle(Throwable t) {
        log.error("Handled exception: " + t);
        throw new RuntimeException(t);
    }

    private static final Logger log = Logger.getLogger(DefaultExceptionHandler.class);
}
