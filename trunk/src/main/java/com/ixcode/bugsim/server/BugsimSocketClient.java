/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.server;

import org.apache.log4j.Logger;

import java.net.Socket;
import java.net.UnknownHostException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import com.ixcode.framework.io.CommandExec;

/**
 * Description : ${CLASS_DESCRIPTION}
 * Created     : Mar 21, 2007 @ 4:53:34 PM by jim
 */
public class BugsimSocketClient {

    public BugsimSocketClient() {

    }

    public String connect(String hostName) {
        String response = "Nothing happened";
        if (_hostname != null) {
            disconnect();
        }
        _hostname = hostName;
        if (log.isInfoEnabled()) {
            log.info("Connecting to server " + _hostname + "@" + _port);
        }

        try {
            _bsSocket = new Socket(_hostname, _port);
            _out = new PrintWriter(_bsSocket.getOutputStream(), true);
            _in = new BufferedReader(new InputStreamReader(_bsSocket.getInputStream()));
            if (log.isDebugEnabled()) {
                log.debug("Connected, got i/o stream. READY!");
            }
            response = "Connected to server " + _hostname + "@" + _port + ".";
            _experimentId = getServerExperimentId();
            _experimentDescription = getServerExperimentDescription();
        } catch (UnknownHostException e) {
            response = "Could not find host : " + _hostname + ":" + _port + "!";
        } catch (IOException e) {
//            e.printStackTrace();

            response = "No Server Running at : " + _hostname + "@" + _port + " (" + e.getMessage() + ")";

        }
        return response;

    }

    private String getServerExperimentDescription() {
        try {
            _out.println(BugsimSocketProtocol.CMD_EXPERIMENT_DESCRIPTION);
            return _in.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getStatus() {
        try {
            _out.println(BugsimSocketProtocol.CMD_REPORT_STATUS);
            return _in.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getParameterSummary() {
        try {
            _out.println(BugsimSocketProtocol.CMD_PARAMETER_SUMMARY);
            return _in.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getServerExperimentId() {
        try {
            _out.println(BugsimSocketProtocol.CMD_EXPERIMENT_ID);
            return _in.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void exit() {

            _out.println(BugsimSocketProtocol.CMD_EXIT);

    }

    public void disconnect() {
        if (_out != null) {
            _out.close();
            _out = null;
        }
        try {
            if (_in != null) {
                _in.close();
                _in = null;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            if (_bsSocket != null) {
                _bsSocket.close();
                _bsSocket = null;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (log.isInfoEnabled()) {
            log.info("Disconnected, closed everything.");
        }
    }

    public String getHost() {
        return _hostname;
    }


    /**
     * barritjame@barretts.mcs.vuw.ac.nz
     */
    public void downloadResults() {
        String userId="barritjame";
        if (_hostname.equals("192.168.10.24")) {
            userId="jim";
        }
        CommandExec.execute(DOWNLOAD_COMMAND + " " + userId + " " + _hostname + " " + _experimentId);
    }

    public String getExperimentId() {
        return _experimentId;
    }

    public String getExperimentDescription() {
        return _experimentDescription;
    }


    public static final String DOWNLOAD_COMMAND = "/Users/jim/code/bugsim/src/script/autoresults.sh";
    private static final Logger log = Logger.getLogger(BugsimSocketClient.class);
    private String _hostname;
    private int _port = 4444;
    private Socket _bsSocket;
    private PrintWriter _out;
    private BufferedReader _in;
    private String _experimentId;
    private String _experimentDescription;
}
