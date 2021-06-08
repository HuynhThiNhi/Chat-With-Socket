package connect;

import data.ManageUser;
import handler.ClientHandler;
import lombok.Getter;
import model.User;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Server extends Thread{
    ServerSocket serverSocket;
    private List<User> userList;
    int port;
    private List<ClientHandler> clientHandlers;
    public Server(int port)
    {
        clientHandlers = new ArrayList<>();
        userList = ManageUser.read();
        this.port = port;
    }
    @Override
    public void run()
    {
        System.out.printf("Server is starting\n");
        try {
            serverSocket = new ServerSocket(port);
            while(true)
            {
                Socket client = serverSocket.accept();
                if(userList.size() != 0)
                {
                    ManageUser.writeListUser(userList);

                }
                ClientHandler clientHandler = new ClientHandler(this,client);
                clientHandlers.add(clientHandler);
                clientHandler.start();

            }
        } catch (IOException e) {
            e.printStackTrace();
            ManageUser.writeListUser(userList);
        }
        finally {
            ManageUser.writeListUser(userList);
            try {
                if (serverSocket != null) {

                    serverSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public List<ClientHandler> getUserOnline()
    {
        return clientHandlers;
    }
    public List<User> getListUser()
    {
        return userList;
    }
}
