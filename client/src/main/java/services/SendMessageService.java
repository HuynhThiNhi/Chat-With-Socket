package services;

import com.chat.socket.commoms.enums.Action;

import java.io.IOException;
import java.io.OutputStream;

public class SendMessageService {
    public static boolean sendMessage(OutputStream out, String message, String receiver)
    {
        try {
            out.write((Action.SEND_MESSAGE_TO_USER_SPECIFIC.toString() + " " + receiver + " " + message + "\n").getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
