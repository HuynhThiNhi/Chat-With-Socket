package data;



import model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class ManageUser {
    public static List<User> userList;

    public ManageUser()
    {


    }
    public static List<User> read() {

        userList = new ArrayList<>();

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("ListUser.txt"));
            String line;
            while ((line = bufferedReader.readLine()) != null)
            {
                String[] tokens = line.split(" ");
                String username = tokens[0];
                String password = tokens[1];

                if(tokens.length == 2)
                {
                    User user = new User(username,password);
                    userList.add(user);
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return userList;
    }
    public static void writeListUser(List<User> userList)
    {
        try {
          BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("ListUser.txt"));
            for (User user: userList) {

               bufferedWriter.write(user.getUserName() + " "+ user.getPassWord());
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
