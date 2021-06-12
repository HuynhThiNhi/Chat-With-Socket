package services;

import com.chat.socket.commoms.enums.Action;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class LogoutService {
    public static void logoutService(OutputStream out, String username)
    {
        try {
            out.write((Action.DISCONNECT.toString() + " " + username + "\n").getBytes());



        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
