
package application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class App {

    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeLater(App::showLogin);
    }

    private static void showLogin() {
        JFrame loginFrame = new JFrame("Login");
        loginFrame.setSize(300, 150);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setLayout(new GridLayout(3, 2));

        JLabel userLabel = new JLabel("Usuário:");
        JTextField userField = new JTextField();
        JLabel passwordLabel = new JLabel("Senha:");
        JPasswordField passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");

        loginFrame.add(userLabel);
        loginFrame.add(userField);
        loginFrame.add(passwordLabel);
        loginFrame.add(passwordField);
        loginFrame.add(loginButton);

        loginFrame.setVisible(true);

        loginButton.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passwordField.getPassword());

            if (validateLogin(username, password)) {
                loginFrame.dispose(); // fecha a tela de login
                startGame(); // inicia o jogo
            } else {
                JOptionPane.showMessageDialog(loginFrame, "Usuário ou senha inválidos", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private static boolean validateLogin(String username, String password) {
        // Aqui você pode implementar a lógica de validação de usuário e senha
        return "admin".equals(username) && "senha123".equals(password); // exemplo simples
    }

    private static void startGame() {
        int titleSize = 32;
        int rows = 16;
        int columns = 16;
        int boardWidth = titleSize * columns;
        int boardHeight = titleSize * rows;

        JFrame frame = new JFrame("Space Invaders");
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        SpaceInvaders spaceInvaders = new SpaceInvaders();
        frame.add(spaceInvaders);
        frame.pack();
        spaceInvaders.requestFocus();
        frame.setVisible(true);
    }
}



//package application;
//
//import javax.swing.*;
//
//
//public class App {
//
//	public static void main(String[] args)throws Exception {
//		// TODO Auto-generated method stub
//		
//		int titleSize = 32;
//		int rows = 16;
//		int columns = 16;
//		int boardWidth = titleSize * columns;
//		int boardHeight = titleSize *rows;
//		
//		JFrame frame = new JFrame("Space Invaders ");
//		//frame.setVisible(true);
//		frame.setSize(boardWidth,boardHeight);
//		frame.setLocationRelativeTo(null);
//		
//		frame.setResizable(false);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		
//		SpaceInvaders spaceInvaders = new SpaceInvaders();
//		frame.add(spaceInvaders);
//		frame.pack();
//		spaceInvaders.requestFocus();
//		frame.setVisible(true);
//		
//		
//		
//		
//
//	}
//
//}
