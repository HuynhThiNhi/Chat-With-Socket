package controller;

import services.Connection;
import view.LoginView;
import view.OnlineUserListView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController {
    private LoginView loginView;
    Connection client;


    public LoginController(Connection client) {
        loginView = new LoginView();
        this.client = client;
        loginView.addLoginListener(new LoginListener());
        loginView.addRegistryListener(new RegisterListener());

    }
    public void setVisibleLoginView(boolean chosen){
        loginView.setVisible(chosen);
    }

    public LoginView getLoginView() {
        return loginView;
    }

    public class RegisterListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            setVisibleLoginView(false);
            SignUpController signUpController = new SignUpController(client);

        }
    }
    public class LoginListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            String userName = loginView.getUserName();
            String password = loginView.getPassword();
            OnlineUserListView onlineUserListView = new OnlineUserListView(userName);

            if(userName.trim().length() != 0 && password.trim().length() != 0)
            {
                if(client.login(userName,password))
                {
                    setVisibleLoginView(false);
                    System.out.printf("login successfully.\n");
                    client.managerViews.add(onlineUserListView);
                    OnlineUserListController onlineUserListController = new OnlineUserListController(client, onlineUserListView);

                }
                else
                {
                    loginView.showMessage("Login failed!");
                    System.out.println("login failed.\n");
                }
            }
            else{
                loginView.showMessage("username or password can not empty.");
            }

        }
    }
}
