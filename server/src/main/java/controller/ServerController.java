package controller;

import connect.Server;
import view.ServerView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ServerController {
    ServerView serverView;
    Server server;
    int port;
    public ServerController()
    {
        serverView = new ServerView();

        serverView.addCloseListener(new CloseListener());
        serverView.addOpenListener(new OpenListener());

    }

    class CloseListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(server != null)
            {
                server.closeServer();
            }
           // serverView.setVisible(false);
            serverView.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        }
    }
    class OpenListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e) {
            port = serverView.getPort();
            if(port == -1)
                return;

            server = new Server(port);
            server.start();
          //  serverView.setPort(server.getPort());
            server.setServerView(serverView);
            serverView.showMessage("Open successfully!");
        }
    }

}
