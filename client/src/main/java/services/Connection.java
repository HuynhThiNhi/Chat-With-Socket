package services;

import com.chat.socket.commoms.enums.Action;
import view.ChattingView;
import view.ReceiveFileView;

import java.io.*;
import java.net.Socket;
import java.util.*;


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

        if (chatting != null)
            chatting.addMessageToViewChatting(sender, message);
    }

    public void readingGroupMessage(String[] tokens) {

        String receiver = tokens[1];
        String sender = tokens[2];
        String message = "";
        for (int i = 3; i < tokens.length; i++)
            message = message + " " + tokens[i];
        List<ChattingView> chattingViewList = ManagerChattingViews.chattingViews.stream().filter(chattingView -> receiver.equals(chattingView.getReceiver()) && !(sender.equals(chattingView.getSender()))).toList();
        System.out.println(chattingViewList.size());
        for (ChattingView chatting : chattingViewList) {
            chatting.addMessageToViewChatting(sender, message);
        }
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

    public void logout(String username) {
        LogoutService.logoutService(out, username);

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

    public void receiveFile(String filename, String sender, String body)
    {

        ReceiveFileView receiveFileView = ManagerReceiveFile.receiveFileViews.get(0);
        if(receiveFileView != null)
        {
            receiveFileView.setSenderTxt(sender);
            receiveFileView.setBody(body);
            receiveFileView.setFilename(filename);
            receiveFileView.setFileNameTxt(filename);
            receiveFileView.setTitle(username + " receive file");
            receiveFileView.setVisible(true);
        }
    }
    public void startReadingResponse() {  // lăng nghe tất cả response
        Thread thread = new Thread() {
            @Override
            public void run() {

                try {
                    String line;
                    String cmd = "";
                    while ((line = bufferedReader.readLine()) != null) {
                        String[] tokens;
                        if (line.charAt(0) == ' ') {
                            tokens = line.split("&");
                            cmd = "FILE";
                        } else {
                            tokens = line.split(" ");
                            if (tokens.length > 0)
                                cmd = tokens[0];
                        }

                        if (tokens.length > 0) {

                            switch (cmd) {

                                case "SEND_MESSAGE_TO_USER_SPECIFIC": {
                                    readingMessage(tokens);
                                    break;
                                }
                                case "LOGOUT": {
                                    sendInfoLogoutToUsers(tokens[1]);
                                    break;
                                }
                                case "GROUP": {
                                    readingGroupMessage(tokens);
                                    break;
                                }
                                case "FILE": {

                                    String header = tokens[0].trim();
                                    String filename = header.split(" ")[1];
                                    String sender = header.split(" ")[0];
                                    String body = tokens[1];
                                    receiveFile(filename, sender, body);
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

    public void sendFile(String path, String receiver) {
        File file = new File(path);

        String[] parts = file.getName().split("\\\\");
        String header = receiver + " " + parts[parts.length - 1];
        System.out.println("sending " + file.getName() + "(" + file.length() + " byte)");
        writeFile(file, header);
    }
    public void writeFile(File file, String header) {

        BufferedReader buff = null;
        try {

            buff = new BufferedReader(new FileReader(file));
            String line;
            String content = "";
            while ((line = buff.readLine()) != null) {
                content = content + line + "#";
            }
            String body = " " + header + "&" + content + "\n";
            //String body = " " + header + "&" + "nhi" + "\n";
            out.write(body.getBytes());

        } catch (IndexOutOfBoundsException e) {
            System.err.println("Error while reading file");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Error while writing " + file.toString() + " to output stream");
            e.printStackTrace();
        } finally {
            if (buff != null) {
                try {
                    buff.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    void sendInfoLogoutToUsers(String username) throws IOException {
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
