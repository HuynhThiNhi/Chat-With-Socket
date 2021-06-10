package controller;

import services.Connection;
import view.OnlineUserListView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OnlineUserListController {
    OnlineUserListView onlineUserListView;
    Connection client;


    public OnlineUserListController(Connection client, OnlineUserListView onlineUserListView)
    {

        this.onlineUserListView = onlineUserListView;
        this.client = client;
        showListUsersOnline();
        onlineUserListView.setVisible(true);
        onlineUserListView.addChatListener(new ChatListener());
        onlineUserListView.addJoinGroup(new JoinGroupListener());
        onlineUserListView.addChatListener(new ChatListener());
        onlineUserListView.addJoinGroup(new JoinGroupListener());


    }
    void showListUsersOnline()
    {
        client.getListUserOnline();

    }
    class ChatListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {


        }
    }
    class JoinGroupListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }
}
