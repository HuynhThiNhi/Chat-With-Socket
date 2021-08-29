package controller;

import services.Connection;
import view.CreateGroupView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CreateGroupController {
    CreateGroupView createGroupView;
    Connection client;
    public static List<String> groupNames;

    public CreateGroupController(Connection client) {
        this.client = client;
        createGroupView = new CreateGroupView();
        createGroupView.addCreateListener(new CreateListener());
        createGroupView.setCreator(client.getUsername());
        readGroupsFromFile();
    }

    class CreateListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (!createGroupView.isValidData()) {
                createGroupView.showMessage("Group Name cannot be empty!");
                return;
            }
            createGroupView.setVisible(false);
            String groupName = createGroupView.getNameGroup();
            groupNames.add(groupName);
            saveGroupsToFile();
            ChattingController chattingController = new ChattingController(client, "$" + groupName);


        }
    }

    public void saveGroupsToFile() {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("Group.txt"));
            for (String str : groupNames) {

                //bufferedWriter.write(client.getUsername() + " " + str);
                bufferedWriter.write(str);
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static List<String> readGroupsFromFile() {
        groupNames = new ArrayList<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("Group.txt"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                groupNames.add(line);
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return groupNames;
    }

}
