/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.io;

import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: jbarr
 * Date: 13-Dec-2004
 * Time: 17:26:16
 * To change this template use File | Settings | File Templates.
 */
public class CommandExec {

    public static void execute(String command) {
        try {
            String osName = System.getProperty("os.name");
            String[] cmd = new String[3];

            if (osName.equals("Windows NT") || osName.equals("Windows XP")) {
                cmd[0] = "cmd.exe";
                cmd[1] = "/C";
                cmd[2] = command;
            } else if (osName.equals("Windows 95")) {
                cmd[0] = "command.com";
                cmd[1] = "/C";
                cmd[2] = command;
            } else {
                cmd[0] = null;
            }

            Runtime rt = Runtime.getRuntime();

//            System.out.println("Execing " + cmd[0] + " " + cmd[1]
//                    + " " + cmd[2]);
            Process proc = null;
            if (cmd[0]==null) {
                if (log.isInfoEnabled()) {
                   log.info("Executing : " + command);
                }
                proc = rt.exec(command);
            } else {
                if (log.isInfoEnabled()) {
                   log.info("Executing : " + cmd);
                }
                proc = rt.exec(cmd);
            }

            // any error message?
            StreamGobbler errorGobbler = new
                    StreamGobbler(proc.getErrorStream(), "ERROR");

            // any output?
            StreamGobbler outputGobbler = new
                    StreamGobbler(proc.getInputStream(), "OUTPUT");

            // kick them off
            errorGobbler.start();
            outputGobbler.start();

            // any error???
            int exitVal = proc.waitFor();
//            System.out.println("ExitValue: " + exitVal);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private static final Logger log = Logger.getLogger(CommandExec.class);

}
