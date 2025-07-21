package view;

import controller.UsuarioController;
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
        setTitle("👤 Cadastro de Usuário");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel painelCampos = new JPanel(new GridLayout(6, 2, 10, 10));
        painelCampos.setBorder(BorderFactory.createEmptyBorder(20, 40, 10, 40));

        painelCampos.add(new JLabel("Nome:"));
        campoNome = new JTextField();
        painelCampos.add(campoNome);

        painelCampos.add(new JLabel("Matrícula:"));
        campoMatricula = new JTextField();
        painelCampos.add(campoMatricula);

        painelCampos.add(new JLabel("Telefone:"));
        campoTelefone = new JTextField();
        painelCampos.add(campoTelefone);

        painelCampos.add(new JLabel("E-mail:"));
        campoEmail = new JTextField();
        painelCampos.add(campoEmail);

        painelCampos.add(new JLabel("Tipo de usuário:"));
        comboTipo = new JComboBox<>(new String[]{"ADMINISTRADOR", "BIBLIOTECARIO", "ESTAGIARIO"});
        painelCampos.add(comboTipo);

        JButton botaoCadastrar = new JButton("Cadastrar");
        botaoCadastrar.addActionListener(e -> cadastrarUsuario());

        mensagem = new JLabel("", SwingConstants.CENTER);

        add(painelCampos, BorderLayout.CENTER);
        add(botaoCadastrar, BorderLayout.SOUTH);
        add(mensagem, BorderLayout.NORTH);

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

            boolean sucesso = controller.adicionarUsuario(usuario);

            if (sucesso) {
                mensagem.setText("✅ Usuário cadastrado com sucesso!");
                limparCampos();
            } else {
                mensagem.setText("❌ Matrícula já cadastrada.");
            }
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
