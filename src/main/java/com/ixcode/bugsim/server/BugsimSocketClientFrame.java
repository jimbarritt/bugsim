/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.server;

import com.ixcode.bugsim.BugsimMain;
import com.ixcode.framework.swing.JFrameExtension;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Mar 21, 2007 @ 4:56:58 PM by jim
 */
public class BugsimSocketClientFrame extends JFrameExtension {

    public static void main(String[] args) {
        if (log.isInfoEnabled()) {
            log.info("Weclome to bugsim socket client version " + BugsimMain.getVersion());
        }


        BugsimSocketClientFrame f = new BugsimSocketClientFrame();
        f.show();

    }

    public BugsimSocketClientFrame() throws HeadlessException {
        super("Bugsim Socket Client Version " + BugsimMain.getVersion());
        super.setSize(1000, 400);
        JFrameExtension.centreWindowOnScreen(this);

        _client = new BugsimSocketClient();

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable(){
            public void run() {
                _client.disconnect();
            }
        }));
        initUI();
    }

    private void initUI() {
        JPanel container = new JPanel(new BorderLayout());

        JPanel buttons = createButtonPanel();
        container.add(buttons, BorderLayout.NORTH);

        JPanel output = createOutputPanel();
        container.add(output, BorderLayout.CENTER);

        super.getContentPane().add(container);
    }

    private JPanel createOutputPanel() {
        JPanel container = new JPanel (new BorderLayout());

        _listModel = new DefaultListModel();
        _list = new JList(_listModel);
        JScrollPane sp = new JScrollPane(_list);

        container.add(sp, BorderLayout.CENTER);
        return container;
    }

    private JPanel createButtonPanel() {
        _serverNames = new JComboBox();
        _serverNames.addItem("localhost");
        _serverNames.addItem("192.168.10.24");
        _serverNames.addItem("barretts.mcs.vuw.ac.nz");
        _serverNames.addItem("debretts.mcs.vuw.ac.nz");
        _serverNames.addItem("greta-pt.mcs.vuw.ac.nz");

        _serverNames.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                _hostName = (String)_serverNames.getSelectedItem();
            }
        });
        _serverNames.setSelectedIndex(0);

        JButton btnConnect = new JButton("Connect");
        btnConnect.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                String response = _client.connect(_hostName);
                addResponse(response);
                if (response.startsWith("Connected")) {
                    addResponse("Experiment Id: " + _client.getExperimentId());
                    addResponse("Description  : " + _client.getExperimentDescription());
                }
            }
        });

        JButton btnDisconnect = new JButton("Disconnect");
        btnDisconnect.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                _client.disconnect();
                addResponse("Disconnected from :" + _client.getHost());
            }
        });

        JButton btnStatus = new JButton("Get Status");
        btnStatus.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                String status = _client.getStatus();
                if (log.isInfoEnabled()) {
                    log.info("Recieved response: " + status);
                }
                addResponse(status);

            }
        });

        JButton btnSummary = new JButton("Get Summary");
        btnSummary.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                String status = _client.getParameterSummary();
                if (log.isDebugEnabled()) {
                    log.debug("Recieved response: " + status);
                }
                addResponse(status);

            }
        });

        final JFrame t = this;
        JButton btnExit = new JButton("Exit Server");
        btnExit.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                int response = JOptionPane.showConfirmDialog(t, "Are you SURE you wish to shut down server '" + _client.getHost() +  " : "+ _client.getExperimentId());
                if (response == JOptionPane.YES_OPTION) {
                    _client.exit();
                    addResponse("Requested Shutdown");
                }

            }
        });

        JButton btnDownload = new JButton("Download Results");
        btnDownload.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {

                addResponse("Downloading Results...");
                SwingUtilities.invokeLater(new Runnable(){
                    public void run() {
                        _client.downloadResults();
                        addResponse("Download complete.");
                    }
                });

            }
        });

        JButton btnClear = new JButton("Clear");
        btnClear.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                _listModel.clear();

            }
        });

        JPanel container = new JPanel(new FlowLayout(FlowLayout.LEADING));
        container.add(_serverNames);
        container.add(btnConnect);
        container.add(btnStatus);
        container.add(btnSummary);
        container.add(btnDisconnect);
        container.add(btnExit);
        container.add(btnDownload);
        container.add(btnClear);
        return container;
    }

    public void addResponse(String response) {
        _listModel.addElement(response);
        _list.setSelectedIndex(_listModel.size()-1);
        _list.ensureIndexIsVisible(_listModel.size()-1);
    }

    private static final Logger log = Logger.getLogger(BugsimSocketClientFrame.class);
    private BugsimSocketClient _client;
    private JList _list;
    private DefaultListModel _listModel;
    private String _hostName;
    private JComboBox _serverNames;

}
