package view;

import controller.LeitorController;
import exception.leitor.LeitorJaExisteException;
import model.Leitor;
import model.TipoLeitor;

import javax.swing.*;
import java.awt.*;

public class CadastroLeitoresView extends JFrame {
    private final JTextField campoNome;
    private final JTextField campoMatricula;
    private final JTextField campoTelefone;
    private final JTextField campoEmail;
    private final JComboBox<String> comboTipoLeitor;
    private final JLabel mensagem;

    public CadastroLeitoresView() {
        setResizable(false);
        setTitle("👤 Cadastro de Leitores");
        setSize(797, 532);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());

        JPanel painelLeitor = new JPanel();
        painelLeitor.setBounds(90, 155, 765, 689);
        painelLeitor.setBorder(BorderFactory.createEmptyBorder(20, 40, 10, 40));
        painelLeitor.setLayout(null);

        JLabel r = new JLabel("Cadastro de Leitores");
        r.setFont(new Font("Arial", Font.PLAIN, 20));
        r.setBounds(285, 50, 212, 24);
        painelLeitor.add(r);

        JLabel nome = new JLabel("Nome:");
        nome.setFont(new Font("Verdana", Font.PLAIN, 14));
        nome.setBounds(154, 122, 73, 48);
        painelLeitor.add(nome);
        campoNome = new JTextField();
        campoNome.setFont(new Font("Verdana", Font.PLAIN, 14));
        campoNome.setBounds(264, 130, 413, 33);
        painelLeitor.add(campoNome);
        campoNome.setColumns(10);

        JLabel matricula = new JLabel("Matrícula:");
        matricula.setFont(new Font("Verdana", Font.PLAIN, 14));
        matricula.setBounds(154, 176, 76, 31);
        painelLeitor.add(matricula);
        campoMatricula = new JTextField();
        campoMatricula.setFont(new Font("Verdana", Font.PLAIN, 14));
        campoMatricula.setColumns(10);
        campoMatricula.setBounds(264, 173, 212, 33);
        painelLeitor.add(campoMatricula);

        JLabel telefone = new JLabel("Telefone:");
        telefone.setFont(new Font("Verdana", Font.PLAIN, 14));
        telefone.setBounds(154, 227, 76, 31);
        painelLeitor.add(telefone);
        campoTelefone = new JTextField();
        campoTelefone.setFont(new Font("Verdana", Font.PLAIN, 14));
        campoTelefone.setColumns(10);
        campoTelefone.setBounds(264, 228, 413, 28);
        painelLeitor.add(campoTelefone);

        JLabel email = new JLabel("E-mail:");
        email.setFont(new Font("Verdana", Font.PLAIN, 14));
        email.setBounds(154, 268, 76, 31);
        painelLeitor.add(email);
        campoEmail = new JTextField();
        campoEmail.setFont(new Font("Verdana", Font.PLAIN, 14));
        campoEmail.setColumns(10);
        campoEmail.setBounds(264, 269, 413, 28);
        painelLeitor.add(campoEmail);

        JLabel tipo = new JLabel("Tipo de usuário:");
        tipo.setFont(new Font("Verdana", Font.PLAIN, 14));
        tipo.setBounds(106, 312, 124, 31);
        painelLeitor.add(tipo);
        comboTipoLeitor = new JComboBox<>(new String[]{" ", "ALUNO", "PROFESSOR", "SERVIDOR"});
        comboTipoLeitor.setBounds(264, 314, 212, 31);
        painelLeitor.add(comboTipoLeitor);

        mensagem = new JLabel("", SwingConstants.CENTER);
        getContentPane().add(mensagem, BorderLayout.NORTH);
        getContentPane().add(painelLeitor, BorderLayout.CENTER);

        JButton botaoCadastrar = new JButton("Cadastrar");
        botaoCadastrar.setFont(new Font("Verdana", Font.PLAIN, 16));
        botaoCadastrar.setBounds(260, 390, 263, 21);
        painelLeitor.add(botaoCadastrar);
        botaoCadastrar.addActionListener(e -> cadastrarLeitor());

        setVisible(true);
    }

    private void cadastrarLeitor() {
        String nome = campoNome.getText().trim();
        String matricula = campoMatricula.getText().trim();
        String telefone = campoTelefone.getText().trim();
        String email = campoEmail.getText().trim();
        String tipoStr = (String) comboTipoLeitor.getSelectedItem();

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
            TipoLeitor tipo = TipoLeitor.valueOf(tipoStr);
            Leitor leitor = new Leitor(nome, matricula, tipo, telefone, email);
            LeitorController controller = new LeitorController();

            controller.cadastrarLeitor(leitor); // agora lança exceção
            mensagem.setText("✅ Leitor cadastrado com sucesso!");
            limparCampos();

        } catch (LeitorJaExisteException e) {
            mensagem.setText(e.getMessage());
        } catch (IllegalArgumentException e) {
            mensagem.setText("❌ Tipo de leitor inválido.");
        }
    }

    private void limparCampos() {
        campoNome.setText("");
        campoMatricula.setText("");
        campoTelefone.setText("");
        campoEmail.setText("");
        comboTipoLeitor.setSelectedIndex(0);
    }
}
