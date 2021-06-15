package handler;

import java.io.*;
import java.net.Socket;
import java.util.List;


import com.chat.socket.commoms.enums.Action;
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
                        case "SEND_MESSAGE_TO_USER_SPECIFIC":
                        {
                            sendMessageToUserSpecific(tokens);
                            break;
                        }
                        case  "DISCONNECT":
                        {
                           responseLogout(tokens[1]);

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

    public boolean  sendMessageToUserSpecific(String[] tokens) {

        String receiver = tokens[1];
        String message = "";
        for (int i = 2; i < tokens.length; i++)
            message = message + " " + tokens[i];
        ClientHandler receiverHandler = server.getClientHandlers().stream().filter(clientHandler -> receiver.equals(clientHandler.getUserName()))
                .findFirst().orElse(null);
        if(receiverHandler != null)
        {
            receiverHandler.sendMessage(Action.SEND_MESSAGE_TO_USER_SPECIFIC.toString() + " " + onlineUser.getUserName() + " " + message + "\n");
        }
        return true;
    }
    public String getUserName() {
        if(onlineUser != null)
            return onlineUser.getUserName();
        return null;
    }

    public void responseUsersOnline() {

        List<ClientHandler> onlineUsers = server.getUserOnline();
        String response = "";

        for (ClientHandler client : onlineUsers) {
            if (client.getUserName() != null)
                response = response + client.getUserName() + " ";
        }
        response = response.trim() + '\n';
        for (ClientHandler client : onlineUsers) {
            if(client.getUserName() != null)
                client.sendMessage(response);
        }

    }

    public void responseLogIn(String[] tokens) {
        if (tokens.length == 3) {

            List<User> userList = server.getUserList();
            for (User user : userList) {
                if (user.getUserName().equals(tokens[1]) && user.getPassWord().equals(tokens[2])) {
                    try {
                       server.addUserToList(user.getUserName());
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
    public void responseLogout(String username) throws IOException {
        server.removeUserToList(username);

        ClientHandler logoutClient = server.getClientHandlers().stream().filter(clientHandler -> username.equals(clientHandler.getUserName())).findFirst().orElse(null);
        server.getClientHandlers().remove(logoutClient);

        List<ClientHandler> onlineUsers = server.getUserOnline();
        for (ClientHandler client : onlineUsers) {
            client. sendMessage("LOGOUT "+ username + "\n");
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
