package edusphere.controllers;

import com.formdev.flatlaf.FlatClientProperties;
import edusphere.App;

import javax.swing.*;
import java.awt.*;

public class LoginView {
    private JPanel rootPanel;
    private JPanel loginPanel;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton loginButton;
    private JButton signUpButton;

    private App app;

    public LoginView(App app) {
        this.app = app;
    }

    public void init() {
        loginPanel.setBackground(app.getBackground().darker());
        loginPanel.putClientProperty(FlatClientProperties.STYLE, "arc : 30");

        loginButton.setBackground(new Color(19, 101, 181));
        signUpButton.setBackground(new Color(45, 165, 21));

    }

    public void addActionListeners() {
        loginButton.addActionListener(e -> {
            app.changeState("HomeView");
        });
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }

}
