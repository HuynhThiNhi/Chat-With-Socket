package services;


import com.chat.socket.commoms.enums.Action;
import view.ChattingView;


import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


public class Connection {
    String username;
    private Socket clientSocket;
    private InputStream in;
    private OutputStream out;
    private BufferedReader bufferedReader;
    private String ip;
    private int port;
    public List<ManagerView> managerViews = new ArrayList<>();


    public Connection(String ip, int port) {
        this.ip = ip;
        this.port = port;
        startConnection();

    }

    public String getUsername() {
        return username;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public void startConnection() {
        try {
            this.clientSocket = new Socket(ip, port);
            this.out = clientSocket.getOutputStream();
            this.in = clientSocket.getInputStream();
            this.bufferedReader = new BufferedReader(new InputStreamReader(in));
        } catch (IOException e) {
            e.printStackTrace();
        }
//        finally {
//            try {
//                if (in != null) {
//                    in.close();
//                }
//                if (out != null) {
//                    out.close();
//                }
//                if (clientSocket != null) {
//                    System.out.println("client disconnect");
//                    clientSocket.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }


    public void readingMessage(String[] tokens) {

        String sender = tokens[1];
        String message = "";
        for (int i = 2; i < tokens.length; i++)
            message = message + " " + tokens[i];

        List<ChattingView> chattingViewList = ManagerChattingViews.chattingViews.stream().filter(chattingView -> username.equals(chattingView.getSender())).toList();
        if (chattingViewList == null)
            return;
        ChattingView chatting = chattingViewList.stream().filter(chattingView -> sender.equals(chattingView.getReceiver())).findFirst().orElse(null);

        if(chatting != null)
            chatting.addMessageToViewChatting(sender, message);

    }

    public void sendMessage(String receiver, String message) {
        SendMessageService.sendMessage(out, message, receiver);
    }

    public boolean login(String username, String password) {
        if (LoginService.requestLogin(out, bufferedReader, username, password)) {
            this.username = username;
            return true;
        }

        return false;
    }

    public void logout(String username)
    {
        LogoutService.logoutService(out,username);
        try {
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public boolean signup(String username, String password) {
        return SignUpService.requestSignUp(out, bufferedReader, username, password);
    }

    public void getListUserOnline() {
        try {
            out.write((Action.GET_USERS_ONLINE.toString() + "\n").getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        startReadingResponse();

    }

    public void startReadingResponse() {
        Thread thread = new Thread() {
            @Override
            public void run() {

                try {
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        String[] tokens = line.split(" ");
                        if (tokens.length > 0) {

                            String cmd = tokens[0];

                            switch (cmd) {

                                case "SEND_MESSAGE_TO_USER_SPECIFIC": {

                                    readingMessage(tokens);
                                    break;
                                }
                                case "LOGOUT":
                                {
                                    System.out.println("ok");
                                    sendInfoLogoutToUsers(tokens[1]);
                                    break;
                                }
                                default: {
                                    readListOnlineUsers(line);
                                }
                            }
                        }
                    }
                } catch (Exception ex) {

                }
            }
        };
        thread.start();

    }

    void sendInfoLogoutToUsers(String username) throws IOException {
        System.out.println(1);
        for (ManagerView managerView : managerViews) {
            managerView.userLogout(username);
        }

    }
    public void updateListOnlineUsers(List<String> users) {

        for (ManagerView managerView : managerViews) {
            users.remove(username);
            if (users.size() != 0)
                managerView.showListUserOnline(users);
        }
    }

    public void readListOnlineUsers(String line) {

        List<String> users = new LinkedList<>(Arrays.asList(line.split(" ")));
        updateListOnlineUsers(users);
    }

}
