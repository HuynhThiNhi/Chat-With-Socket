package view;

import services.ManagerView;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.List;

public class OnlineUserListView extends javax.swing.JFrame implements ManagerView {

    // Variables declaration - do not modify
    private javax.swing.JButton chatBtn;
    private javax.swing.JButton joinGroupBtn;
    private javax.swing.JLabel onlineUsersLabel;
    private JButton logoutBtn;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private JList<String> userList;
    private DefaultListModel<String> userListModel;
    // End of variables declaration
    public OnlineUserListView(String username) {
        initComponents();
        this.setTitle("Welcome " + username);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();

        onlineUsersLabel = new javax.swing.JLabel();
        chatBtn = new javax.swing.JButton();
        joinGroupBtn = new javax.swing.JButton();
        onlineUsersLabel.setText("Online Users");
        chatBtn.setText("Chat");
        joinGroupBtn.setText("Join Group");
        logoutBtn = new JButton("Logout");
        userListModel = new DefaultListModel<>();
        userList = new JList<>(userListModel);
        jScrollPane1 = new javax.swing.JScrollPane(userList);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(chatBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(joinGroupBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(logoutBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addGap(0, 2, Short.MAX_VALUE)))
                                .addContainerGap())
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(86, 86, 86)
                                .addComponent(onlineUsersLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(onlineUsersLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(50, 50, 50)
                                                .addComponent(chatBtn)
                                                .addGap(27, 27, 27)
                                                .addComponent(joinGroupBtn)
                                                .addGap(29, 29, 29)
                                                .addComponent(logoutBtn)))
                                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }

    @Override
    public void showListUserOnline(List<String> users)
    {
        userListModel.clear();
       for (int i = 0; i < users.size(); i++)
       {
           userListModel.add(i, users.get(i));
       }

    }
    @Override
    public void userLogout(String username)
    {
        userListModel.removeElement(username);
    }
    public String getUserSelectedValue()
    {
        return userList.getSelectedValue();
    }
    public void addMouseClickListener(MouseAdapter mouseClickListener) {
        userList.addMouseListener(mouseClickListener);
    }
    public void addChatListener(ActionListener actionListener)
    {
        chatBtn.addActionListener(actionListener);
    }
    public void addJoinGroup(ActionListener actionListener)
    {
        joinGroupBtn.addActionListener(actionListener);
    }

    public void addLogoutListener(ActionListener actionListener)
    {
        logoutBtn.addActionListener(actionListener);
    }
}

