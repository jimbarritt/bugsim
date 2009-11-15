package com.ixcode.bugsim;

import static com.ixcode.framework.logging.Log4JConfiguration.*;
import static com.ixcode.bugsim.BugsimVersion.getVersion;
import org.apache.log4j.*;

/**
 * Re-work of BugsimMain to simplify.
 */
public class BugsimApplication {

    private static final Logger log = Logger.getLogger(BugsimApplication.class);

    public static void main(String[] args) {
        loadLog4JConfig();

        log.info("Welcome to BugSim : Version [" + getVersion() + "]");
    }
}
