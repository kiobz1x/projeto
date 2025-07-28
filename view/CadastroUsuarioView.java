package view;

import controller.UsuarioController;
import exception.UsuarioJaExisteException;
import model.TipoUsuario;
import model.Usuario;

import javax.swing.*;
import java.awt.*;

public class CadastroUsuarioView extends JFrame {
    private final JTextField campoNome;
    private final JTextField campoMatricula;
    private final JTextField campoTelefone;
    private final JTextField campoEmail;
    private final JComboBox<String> comboTipo;
    private final JLabel mensagem;

    public CadastroUsuarioView() {
        setResizable(false);
        setTitle("👤 Cadastro de Usuário");
        setSize(615, 485);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());

        JPanel painelCampos = new JPanel();
        painelCampos.setBounds(90, 155, 200, 50);
        painelCampos.setBorder(BorderFactory.createEmptyBorder(20, 40, 10, 40));
        painelCampos.setLayout(null);

        JLabel r = new JLabel("Cadastro de Usuarios");
        r.setFont(new Font("Arial", Font.PLAIN, 20));
        r.setBounds(212, 50, 212, 24);
        painelCampos.add(r);

        JLabel nome = new JLabel("Nome:");
        nome.setFont(new Font("Verdana", Font.PLAIN, 14));
        nome.setBounds(154, 128, 76, 31);
        painelCampos.add(nome);
        campoNome = new JTextField();
        campoNome.setFont(new Font("Verdana", Font.PLAIN, 14));
        campoNome.setBounds(264, 129, 192, 28);
        painelCampos.add(campoNome);
        campoNome.setColumns(10);

        JLabel matricula = new JLabel("Matrícula:");
        matricula.setFont(new Font("Verdana", Font.PLAIN, 14));
        matricula.setBounds(154, 176, 76, 31);
        painelCampos.add(matricula);
        campoMatricula = new JTextField();
        campoMatricula.setFont(new Font("Verdana", Font.PLAIN, 14));
        campoMatricula.setColumns(10);
        campoMatricula.setBounds(264, 177, 192, 28);
        painelCampos.add(campoMatricula);

        JLabel telefone = new JLabel("Telefone:");
        telefone.setFont(new Font("Verdana", Font.PLAIN, 14));
        telefone.setBounds(154, 227, 76, 31);
        painelCampos.add(telefone);
        campoTelefone = new JTextField();
        campoTelefone.setFont(new Font("Verdana", Font.PLAIN, 14));
        campoTelefone.setColumns(10);
        campoTelefone.setBounds(264, 228, 192, 28);
        painelCampos.add(campoTelefone);

        JLabel email = new JLabel("E-mail:");
        email.setFont(new Font("Verdana", Font.PLAIN, 14));
        email.setBounds(154, 268, 76, 31);
        painelCampos.add(email);
        campoEmail = new JTextField();
        campoEmail.setFont(new Font("Verdana", Font.PLAIN, 14));
        campoEmail.setColumns(10);
        campoEmail.setBounds(264, 269, 192, 28);
        painelCampos.add(campoEmail);

        JLabel tipo = new JLabel("Tipo de usuário:");
        tipo.setFont(new Font("Verdana", Font.PLAIN, 14));
        tipo.setBounds(106, 312, 124, 31);
        painelCampos.add(tipo);
        comboTipo = new JComboBox<>(new String[]{" ", "ADMINISTRADOR", "BIBLIOTECARIO", "ESTAGIARIO"});
        comboTipo.setBounds(264, 314, 192, 31);
        painelCampos.add(comboTipo);

        mensagem = new JLabel("", SwingConstants.CENTER);

        getContentPane().add(painelCampos, BorderLayout.CENTER);

        JButton botaoCadastrar = new JButton("Cadastrar");
        botaoCadastrar.setFont(new Font("Verdana", Font.PLAIN, 16));
        botaoCadastrar.setBounds(147, 389, 353, 21);
        painelCampos.add(botaoCadastrar);
        botaoCadastrar.addActionListener(e -> cadastrarUsuario());
        getContentPane().add(mensagem, BorderLayout.NORTH);

        setVisible(true);
    }

    private void cadastrarUsuario() {
        String nome = campoNome.getText().trim();
        String matricula = campoMatricula.getText().trim();
        String telefone = campoTelefone.getText().trim();
        String email = campoEmail.getText().trim();
        String tipoStr = (String) comboTipo.getSelectedItem();

        if (nome.isEmpty() || matricula.isEmpty() || telefone.isEmpty() || email.isEmpty()) {
            mensagem.setText("⚠ Preencha todos os campos.");
            return;
        }
        if (!telefone.matches("\\d+")) {
            mensagem.setText("⚠ O telefone deve conter apenas números.");
            return;
        }
        if (!email.matches("^[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,}$")) {
            mensagem.setText("⚠ E-mail inválido. Ex: exemplo@dominio.com");
            return;
        }
        if (!nome.matches("^[A-Za-zÀ-ÿ ]+$")) {
            mensagem.setText("⚠ O nome deve conter apenas letras.");
            return;
        }

        try {
            TipoUsuario tipo = TipoUsuario.valueOf(tipoStr);
            Usuario usuario = new Usuario(nome, matricula, tipo, telefone, email);
            UsuarioController controller = new UsuarioController();

            controller.adicionarUsuario(usuario);  // agora sem boolean
            mensagem.setText("✅ Usuário cadastrado com sucesso!");
            limparCampos();

        } catch (UsuarioJaExisteException e) {
            mensagem.setText(e.getMessage());
        } catch (IllegalArgumentException e) {
            mensagem.setText("❌ Tipo de usuário inválido.");
        }
    }

    private void limparCampos() {
        campoNome.setText("");
        campoMatricula.setText("");
        campoTelefone.setText("");
        campoEmail.setText("");
        comboTipo.setSelectedIndex(0);
    }
}
