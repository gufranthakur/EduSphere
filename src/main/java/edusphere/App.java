package edusphere;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import edusphere.controllers.AttendenceView;
import edusphere.controllers.DatasheetView;
import edusphere.controllers.HomeView;
import edusphere.controllers.LoginView;
import edusphere.models.Class;

import javax.swing.*;
import java.awt.*;

public class App extends JFrame {

    private LoginView loginView;
    public HomeView homeView;
    public DatasheetView datasheetView;
    public AttendenceView attendenceView;

    //DataManager dataManager = new DataManager(this, null, null);

    public App() {
        this.setSize(1000, 700);
        this.setTitle("EduSphere");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);
    }

    public void init() {
        loginView = new LoginView(this);
        loginView.init();
        loginView.addActionListeners();

        homeView = new HomeView(this);
        homeView.init();
        homeView.addActionListeners();

        datasheetView = new DatasheetView(this);
        datasheetView.init();

        attendenceView = new AttendenceView(this);
        attendenceView.init();
    }

    public void addComponent() {
        this.setContentPane(loginView.getRootPanel());
        this.setVisible(true);
    }

    public void changeState(String state) {
        switch (state) {
            case "HomeView" : this.setContentPane(homeView.getRootPanel());
            break;
            case "DatasheetView" : this.setContentPane(datasheetView.getRootPanel());
            break;
            case "LoginView" : this.setContentPane(loginView.getRootPanel());
            case "AttendanceView" : this.setContentPane(attendenceView.getRootPanel());
            break;
        }
        repaint();
        revalidate();
    }

    public void throwError(String message) {
        JOptionPane.showInternalMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        FlatMacDarkLaf.setup();

        App app = new App();
        app.init();
        app.addComponent();
    }

}