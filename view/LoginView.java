package view;

import controller.LoginController;
import model.Usuario;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {
    private final JTextField campoMatricula;
    private final JButton botaoEntrar;
    private final JLabel mensagem;
    private JLabel lblLogin;

    public LoginView() {
    	setResizable(false);
        setTitle("Login - Sistema da Biblioteca");
        setSize(556, 276);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());
      

        // Painel de entrada
        JPanel painelCentro = new JPanel();
        painelCentro.setBounds(90,155,200,50);
        painelCentro.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        painelCentro.setLayout(null);

        getContentPane().add(painelCentro, BorderLayout.CENTER);
        
        lblLogin = new JLabel("Login");
        lblLogin.setFont(new Font("Arial", Font.PLAIN, 20));
        lblLogin.setBounds(261, 41, 55, 34);
        painelCentro.add(lblLogin);
        
        JLabel matricula = new JLabel("Digite a sua matricula:");
        matricula.setFont(new Font("Verdana", Font.PLAIN, 14));
        matricula.setBounds(64, 110, 190, 21);
        painelCentro.add(matricula);
        campoMatricula = new JTextField();
        campoMatricula.setFont(new Font("Verdana", Font.PLAIN, 14));
        campoMatricula.setBounds(279, 104, 190, 34);
        painelCentro.add(campoMatricula);
        campoMatricula.setColumns(10);
        
        botaoEntrar = new JButton("Entrar");
        botaoEntrar.setFont(new Font("Verdana", Font.PLAIN, 16));
        botaoEntrar.setBounds(239, 171, 115, 20);
        painelCentro.add(botaoEntrar);

        // Mensagem
        mensagem = new JLabel("", SwingConstants.CENTER);
        getContentPane().add(mensagem, BorderLayout.SOUTH);

        // Ação do botão
        botaoEntrar.addActionListener(e -> fazerLogin());

        setVisible(true);
    }

    private void fazerLogin() {
        String matricula = campoMatricula.getText().trim();
        if (matricula.isEmpty()) {
            mensagem.setText("⚠ Matrícula obrigatória.");
            return;
        }

        LoginController loginController = new LoginController();
        Usuario usuario = loginController.buscarPorMatricula(matricula); // ← CORRIGIDO

        if (usuario != null) {
            mensagem.setText("✅ Bem-vindo, " + usuario.getNome());
            dispose(); // fecha a tela de login

            // Redireciona com base no tipo
            SwingUtilities.invokeLater(() -> new MenuView(usuario)); // ← MenuView precisa existir
        } else {
            mensagem.setText("❌ Usuário não encontrado.");
        }
    }

    // Método main para testar
    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginView::new);
    }
}
