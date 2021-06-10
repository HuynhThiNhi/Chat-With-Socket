package services;

import com.chat.socket.commoms.enums.Action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

public class GetListUserOnlineService {

    public static List<String> getListUserOnline(OutputStream out, BufferedReader bufferedReader)
    {
        try {
            out.write((Action.GET_USERS_ONLINE.toString() + "\n").getBytes());
            String response = bufferedReader.readLine();
            if(response != null)
            {
                List<String> onlineUsers = Arrays.stream(response.split(" ")).toList();
                System.out.println(onlineUsers.size());
                return onlineUsers;
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
