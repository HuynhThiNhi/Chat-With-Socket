package controller;

import services.Connection;
import view.OnlineUserListView;

import java.awt.event.*;

public class OnlineUserListController {
    OnlineUserListView onlineUserListView;
    Connection client;


    public OnlineUserListController(Connection client, OnlineUserListView onlineUserListView) {

        this.onlineUserListView = onlineUserListView;
        this.client = client;
        showListUsersOnline();
        onlineUserListView.setVisible(true);
        onlineUserListView.addChatListener(new ChatListener());
        onlineUserListView.addJoinGroup(new JoinGroupListener());
        onlineUserListView.addMouseClickListener(new MouseClickListener());
        onlineUserListView.addLogoutListener(new LogoutListener());
    }
    void showListUsersOnline() {
        client.getListUserOnline();

    }
    class LogoutListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e) {
            client.logout(client.getUsername());
        }
    }
    class MouseClickListener extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() > 1) {
                String receiver = onlineUserListView.getUserSelectedValue();
                ChattingController chattingController = new ChattingController(client, receiver);

            }
        }
    }

    class ChatListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    class JoinGroupListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }
}
