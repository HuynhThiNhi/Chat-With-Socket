import connect.Server;


public class MainServer {
    public static void main(String[] args) {

        Server server = new Server(8080);
        server.start();


    }
}
