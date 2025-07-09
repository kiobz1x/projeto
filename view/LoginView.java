package view;

import controller.LoginController;
import model.Usuario;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {
    private final JTextField campoMatricula;
    private final JButton botaoEntrar;
    private final JLabel mensagem;

    public LoginView() {
        setTitle("Login - Sistema da Biblioteca");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Painel de entrada
        JPanel painelCentro = new JPanel(new GridLayout(3, 1));
        painelCentro.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        painelCentro.add(new JLabel("Digite sua matrícula:"));

        campoMatricula = new JTextField();
        painelCentro.add(campoMatricula);

        botaoEntrar = new JButton("Entrar");
        painelCentro.add(botaoEntrar);

        add(painelCentro, BorderLayout.CENTER);

        // Mensagem
        mensagem = new JLabel("", SwingConstants.CENTER);
        add(mensagem, BorderLayout.SOUTH);

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
