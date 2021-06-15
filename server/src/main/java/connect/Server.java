package connect;
import data.ManageUser;
import handler.ClientHandler;
import lombok.Getter;
import model.User;
import view.ServerView;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Getter
public class Server extends Thread{
    ServerSocket serverSocket;
    private List<User> userList;
    int port;
    private List<ClientHandler> clientHandlers;
    private final AtomicBoolean running = new AtomicBoolean(false);
    private ServerView serverView;
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
            running.set(true);
            serverSocket = new ServerSocket(port);
            while(running.get())
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
           // e.printStackTrace();
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

    public ServerView getServerView()
    {
        return serverView;
    }
    public void setServerView(ServerView serverView)
    {
        this.serverView = serverView;
    }
    public void addUserToList(String username)
    {
        serverView.addOnlineUser(username);
    }
    public void removeUserToList(String username)
    {
        serverView.removeUserToList(username);
    }
    public List<ClientHandler> getUserOnline()
    {
        return clientHandlers;
    }
    public List<User> getListUser()
    {
        return userList;
    }
    public void closeServer()
    {
        try {

            running.set(false);
            serverSocket.close();
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
