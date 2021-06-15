package controller;

import lombok.SneakyThrows;
import services.Connection;
import view.OnlineUserListView;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

public class OnlineUserListController {
    OnlineUserListView onlineUserListView;
    Connection client;


    public OnlineUserListController(Connection client, OnlineUserListView onlineUserListView) {

        this.onlineUserListView = onlineUserListView;
        this.client = client;
        showListUsersOnline();
        onlineUserListView.setVisible(true);
        onlineUserListView.addCreateGroupListener(new CreateGroupListener());
        onlineUserListView.addJoinGroup(new JoinGroupListener());
        onlineUserListView.addMouseClickListener(new MouseClickListener());
        onlineUserListView.addLogoutListener(new LogoutListener());
    }

    void showListUsersOnline() {
        client.getListUserOnline();

    }

    class LogoutListener implements ActionListener {

        @SneakyThrows
        @Override
        public void actionPerformed(ActionEvent e) {
            client.logout(client.getUsername());
            onlineUserListView.setVisible(false);

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

    class CreateGroupListener implements ActionListener {
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
