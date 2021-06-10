package services;


import com.chat.socket.commoms.enums.Action;
import view.OnlineUserListView;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


public class Connection{
    String username;
    private Socket clientSocket;
    private InputStream in;
    private OutputStream out;
    private BufferedReader bufferedReader;
    private String ip;
    private int port;
    public List<ManagerView> managerViews = new ArrayList<>();


    public Connection(String ip, int port)
    {
        this.ip = ip;
        this.port = port;
        startConnection();

    }

    public Socket getClientSocket()
    {
        return clientSocket;
    }

    public  void startConnection()
    {
        try {
            this.clientSocket = new Socket(ip,port);
            this.out =clientSocket.getOutputStream();
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

    public boolean login(String username, String password)
    {
        if(LoginService.requestLogin(out, bufferedReader, username, password))
        {
            this.username = username;
            return true;
        }

        return false;
    }
    public boolean signup(String username, String password)
    {
        return SignUpService.requestSignUp(out,bufferedReader,username,password);
    }
    public void getListUserOnline()
    {

        try {
            out.write((Action.GET_USERS_ONLINE.toString() + "\n").getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        startReadingResponse();


    }
    public void startReadingResponse()
    {
        Thread thread = new Thread()
        {
            @Override
            public void run() {
                readListOnlineUsers();
            }
        };
        thread.start();

    }
    public void updateListOnlineUsers(List<String> users)
    {

        for (ManagerView managerView: managerViews) {
           users.remove(username);
            managerView.showListUserOnline(users);
        }
    }
    public void readListOnlineUsers() {

        try {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                List<String> users = new LinkedList<>(Arrays.asList(line.split(" ")));
                updateListOnlineUsers(users);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }





}
