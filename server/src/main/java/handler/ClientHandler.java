package handler;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.chat.socket.commoms.enums.StatusCode;
import connect.Server;
import data.ManageUser;
import lombok.SneakyThrows;
import model.User;


public class ClientHandler extends Thread {
    private final Socket client;
    private final Server server;
    User onlineUser;
    private InputStream in;
    private OutputStream out;
    private BufferedReader bufferedReader;

    public ClientHandler(Server server, Socket client) throws IOException {
        this.client = client;
        this.server = server;
        in = client.getInputStream();
        out = client.getOutputStream();
        bufferedReader = new BufferedReader(new InputStreamReader(in));

    }

    public void disconnectClient() {
        server.getClientHandlers().remove(this);
    }

    @SneakyThrows
    @Override
    public void run() {
        try {
            String line;

            while ((line = bufferedReader.readLine()) != null) {

                String[] tokens = line.split(" ");
                if (tokens.length != 0) {

                    String request = tokens[0];
                    switch (request) {

                        case "SIGN_UP": {
                            responseSignUp(tokens);
                            break;
                        }
                        case "SIGN_IN": {
                            responseLogIn(tokens);
                            break;
                        }
                        case "GET_USERS_ONLINE": {
                            responseUsersOnline();
                            break;
                        }
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("exception at run client handle\n");
        } finally {
            ManageUser.writeListUser(server.getUserList());
            if (in != null)
                in.close();
            if (out != null)
                out.close();
            bufferedReader.close();
        }
    }

    public String getUserName() {
        return onlineUser.getUserName();
    }

    public void responseUsersOnline() {

        List<ClientHandler> onlineUsers = server.getUserOnline();
        String response = "";

        for (ClientHandler client : onlineUsers) {
            response = response + client.getUserName() + " ";
        }
        response = response.trim() + '\n';
        for (ClientHandler client : onlineUsers) {
            client.sendMessage(response);
        }

    }

    public void responseLogIn(String[] tokens) {
        if (tokens.length == 3) {

            List<User> userList = server.getUserList();
            for (User user : userList) {
                if (user.getUserName().equals(tokens[1]) && user.getPassWord().equals(tokens[2])) {
                    try {
                        this.onlineUser = new User(user.getUserName(), user.getPassWord());
                        out.write((StatusCode.OK.toString() + "\n").getBytes());

                        return;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            try {
                out.write((StatusCode.FAILED.toString() + "\n").getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void responseSignUp(String[] tokens) {

        if (tokens.length == 3) {
            String userName = tokens[1];
            String password = tokens[2];
            List<User> userList = server.getUserList();
            for (User user : userList) {
                if (user.getUserName().equals(userName)) {
                    try {
                        out.write((StatusCode.ALREADY_EXIT.toString() + "\n").getBytes());
                        return;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            User newUser = new User(userName, password);
            server.getListUser().add(newUser);
            try {
                out.write(StatusCode.OK.toString().getBytes());
                out.write("\n".getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    void sendMessage(String message)
    {
        try {
            out.write(message.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
