package services;

import com.chat.socket.commoms.enums.Action;
import com.chat.socket.commoms.enums.StatusCode;
import org.apache.commons.lang3.ObjectUtils;

import java.io.*;

public class SignUpService {
    public static boolean requestSignUp(OutputStream out, BufferedReader bufferedReader, String username, String password)
    {
        String action = Action.SIGN_UP.toString();
        String cmd = action + " " + username + " " + password + '\n';

        try {
            out.write(cmd.getBytes());
            String status =  bufferedReader.readLine();
            System.out.println(status);

            if(!ObjectUtils.isEmpty(status) && status.equals(StatusCode.OK.toString()))
            {

                return  true;
            }

        }
        catch (IOException  e)
        {
            System.out.println("error read at signup service\n");
            e.printStackTrace();
        }

        return  false;

    }
}
