package controller;

import services.Connection;
import services.ManagerChattingViews;
import view.ChattingView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChattingController {

    Connection client;
    ChattingView chattingView;
    String receiver;

    ChattingController(Connection connection, String receiver) {

        if (!ChattingView.receiverList.contains(receiver) || receiver.charAt(0) == '$') {

            this.client = connection;
            this.receiver = receiver;
            if (chattingView == null)
                chattingView = new ChattingView(client.getUsername(), receiver);
            else
                chattingView.setVisible(true);
            ChattingView.receiverList.add(receiver);
            chattingView.setTitle(client.getUsername(), receiver);
            chattingView.addEmojiListener(new EmojiListener());
            chattingView.addExitListener(new ExitListener());
            chattingView.addSendFileListener(new SendFileListener());
            chattingView.addMessageListener(new MessageListener());

            ManagerChattingViews.chattingViews.add(chattingView);

        }

    }

    class MessageListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String message = chattingView.getMessage();
            if (message.length() == 0)
                return;
            chattingView.deleteMessageFromText();
            chattingView.addMessageToViewChatting("You", message);
            client.sendMessage(receiver, message);

        }
    }

    class EmojiListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            chattingView.showEmoji();
        }
    }

    class ExitListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            chattingView.setVisible(false);
            ChattingView.receiverList.remove(receiver);
        }
    }

    class SendFileListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
        }
    }

}
