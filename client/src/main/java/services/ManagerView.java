package services;
import java.util.List;
public interface ManagerView {
    public void showListUserOnline(List<String> users);
    public  void userLogout(String username);
}

