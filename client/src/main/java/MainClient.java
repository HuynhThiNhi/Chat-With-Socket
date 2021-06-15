
import controller.ClientController;
import services.Connection;
import controller.LoginController;


public class MainClient {

    public static void main(String[] args) {
//        Connection client = new Connection("localhost", 8080);
//
//        if(client.getClientSocket() != null)
//        {
//            LoginController loginController = new LoginController(client);
//            client.setLoginController(loginController);
//            System.out.printf("connect successfully\n");
//        }
//        else
//        {
//            System.out.printf("connect failed\n");
//        }

        ClientController clientController = new ClientController();

    }

}
