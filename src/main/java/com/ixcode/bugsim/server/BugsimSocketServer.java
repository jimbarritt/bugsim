/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.server;

import org.apache.log4j.Logger;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.ixcode.framework.experiment.model.ExperimentController;

/**
 * Description : ${CLASS_DESCRIPTION}
 * Created     : Mar 21, 2007 @ 4:26:16 PM by jim
 */
public class BugsimSocketServer implements Runnable {

    public BugsimSocketServer(ExperimentController experimentController) {
        _experimentController = experimentController;
    }


    public void run() {

        if (log.isInfoEnabled()) {
            log.info("Bugsim Socket Server Starting...");
        }
        _serverSocket = null;
        try {
            _serverSocket = new ServerSocket(4444);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 4444.");
            System.exit(1);
        }
        if (log.isInfoEnabled()) {
            log.info("Server started, waiting for a client...");
        }

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                try {
                    if (_serverSocket != null) {
                        if (log.isInfoEnabled()) {
                            log.info("Closing down socket server...");
                        }
                        _serverSocket.close();
                        if (log.isInfoEnabled()) {
                            log.info("Closed.");
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }));


        Socket clientSocket = null;
        try {
            while (!_shutdown) { // just keep going until we get shut down...
                try {
                    clientSocket = _serverSocket.accept();
                } catch (IOException e) {
                    System.err.println("Accept failed.");
                    System.exit(1);
                }

                if (log.isInfoEnabled()) {
                    log.info("Accepted Client");
                }

                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                                clientSocket.getInputStream()));
                String inputLine, outputLine;
                BugsimSocketProtocol protocol = new BugsimSocketProtocol(_experimentController);


                while ((inputLine = in.readLine()) != null) {
                    outputLine = protocol.processInput(inputLine);
                    out.println(outputLine);
                    if (outputLine.equals("Bye."))
                        break;
                }
                out.close();
                in.close();

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            clientSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void setShutdown(boolean shutdown) {
        _shutdown = shutdown;
    }


    private static final Logger log = Logger.getLogger(BugsimSocketServer.class);
    private boolean _shutdown = false;
    private ServerSocket _serverSocket;
    private ExperimentController _experimentController;
}
