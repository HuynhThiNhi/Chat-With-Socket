package controller;

import lombok.Getter;
import services.Connection;
import view.SignUpView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


@Getter
public class SignUpController {
    SignUpView signUpView ;
    Connection client;

    public SignUpController(Connection client)
    {
        this.client = client;
        signUpView = new SignUpView();
        signUpView.addLogInListener(new LogInListener());
        signUpView.addSignUpListener(new SignUpListener());

    }
    public void hideSignUpView(boolean chosen)
    {
        signUpView.setVisible(chosen);
    }
    public class LogInListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e) {
            signUpView.setVisible(false);
            LoginController loginController = new LoginController(client);
        }
    }
    public class SignUpListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e) {
            String userName = signUpView.getUserName();
            String passWord = signUpView.getPassword();
            String confirmPass = signUpView.getConfirmPass();

            if(!(userName.trim().length() != 0 && passWord.trim().length()!=0))
            {
                signUpView.showMessage("username or password can't empty!");
                return;
            }
            if(!passWord.equals(confirmPass))
            {
                signUpView.showMessage("password don't match!");
                return;
            }

            if(client.signup(userName, passWord))
            {
                signUpView.showMessage("registry successfully.");
                System.out.println("Sign up successfully.");
            }
            else {
                System.out.println("Sign up failed.");
                signUpView.showMessage("username already exits.");
            }

        }
    }
}
