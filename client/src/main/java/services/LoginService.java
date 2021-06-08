package services;

import com.chat.socket.commoms.enums.Action;
import com.chat.socket.commoms.enums.StatusCode;
import org.apache.commons.lang3.ObjectUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;

public class LoginService {
    public static boolean requestLogin(OutputStream out, BufferedReader bufferedReader, String username, String password) {
        String cmd = Action.SIGN_IN + " " + username + " " + password + "\n";
        try {
            out.write(cmd.getBytes());
            String status = bufferedReader.readLine();
            if(!ObjectUtils.isEmpty(status) && StatusCode.OK.toString().equals(status))
                return true;


        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
