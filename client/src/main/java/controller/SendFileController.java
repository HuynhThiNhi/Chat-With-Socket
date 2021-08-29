package controller;

import services.Connection;
import view.SendFileView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SendFileController {
    SendFileView sendFileView;
    Connection client;
    String receiver;

    public SendFileController(Connection client, String receiver) {
        this.client = client;
        this.receiver = receiver;
        sendFileView = new SendFileView(receiver);
        sendFileView.addSendListener(new SendListener());

    }
    class SendListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            sendFileView.setVisible(false);
            client.sendFile(sendFileView.getFilePath(), receiver);

        }

    }

}
