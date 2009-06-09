/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.io;

import java.io.IOException;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class IOExceptionWithCause extends IOException {

    public IOExceptionWithCause(Throwable cause) {
        this( cause, cause.getMessage());
    }

    public IOExceptionWithCause(Throwable cause, String message) {
        super(message);
        _cause = cause;
    }


    public Throwable getCause() {
        return _cause;
    }

    private Throwable _cause;
}
