package services;


import java.io.*;
import java.net.Socket;


public class Connection{
    private Socket clientSocket;
    private InputStream in;
    private OutputStream out;
    private BufferedReader bufferedReader;
    private String ip;
    private int port;

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
        return LoginService.requestLogin(out, bufferedReader, username, password);
    }
    public boolean signup(String username, String password)
    {
        return SignUpService.requestSignUp(out,bufferedReader,username,password);
    }


}
