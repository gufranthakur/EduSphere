package edusphere;

import com.formdev.flatlaf.fonts.inter.FlatInterFont;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import edusphere.controllers.*;

import javax.swing.*;
import java.awt.*;

public class App extends JFrame {

    private LoginView loginView;
    public HomeView homeView;
    public DatasheetView datasheetView;
    public AttendenceView attendenceView;
    public MarksView marksView;

    public Font fontSmall = new Font(FlatInterFont.FAMILY, Font.PLAIN, 20);
    public Font fontLarge = new Font(FlatInterFont.FAMILY, Font.PLAIN, 30);

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
        attendenceView.initActionListeners();

        marksView = new MarksView(this);
        marksView.init();
        marksView.initActionListeners();

        homeView.runTest();
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
            break;
            case "AttendanceView" : this.setContentPane(attendenceView.getRootPanel());
            break;
            case "MarksView" : this.setContentPane(marksView.getRootPanel());
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
