package controller;

import services.Connection;
import view.ClientView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientController {
    ClientView clientView;

    public ClientController() {
        clientView = new ClientView();
        clientView.addAddListener(new AddListener());
        clientView.addDeleteListener(new DeleteListener());
        clientView.addConnectListener(new ConnectListener());
    }

    class AddListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (clientView.isValidData()) {
                clientView.addServer();
            }
        }
    }

    class DeleteListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            clientView.removeServer(clientView.getSelectedServer());
            clientView.showMessage("Delete successfully!");
        }
    }

    class ConnectListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String server = clientView.getSelectedServer();
            if (server == null) {
                clientView.showMessage("Connect failed!");
                return;
            }
            String[] tokens = server.split(":");
            if (tokens.length == 2) {
                String ip = tokens[0];

                try {
                    int port = Integer.parseInt(tokens[1]);
                    Connection client = new Connection(ip, port);
                    if (client.getClientSocket() != null) {
                        LoginController loginController = new LoginController(client);
                        System.out.printf("connect successfully\n");
                        return;
                    }
                } catch (Exception exception) {

                    //  exception.printStackTrace();
                }

            }
            clientView.showMessage("connect failed!");
        }

    }
}
