package controller;


import services.Connection;
import view.JoinGroupView;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class JoinGroupController {
    JoinGroupView joinGroupView;
    Connection client;

    public JoinGroupController(Connection client) {

        this.client = client;
        joinGroupView = new JoinGroupView();
        joinGroupView.addJoinListener(new JoinListener());
    }

    class JoinListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            List<String> groupNames = CreateGroupController.readGroupsFromFile();
            System.out.println(groupNames);
            String nameGroup = joinGroupView.getNameGroup();
            if(!groupNames.contains(nameGroup))
            {
                joinGroupView.showMessage("Group doesn't exit!");
                return;
            }

            ChattingController chattingController = new ChattingController(client, "$" + nameGroup);
            joinGroupView.setVisible(false);

        }
    }

}
