package view;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controller.UsuarioController;

public class AtualizarUsuarioView extends JFrame{
	private final JTextField campoNome;
    private final JTextField campoTelefone;
    private final JTextField campoEmail;
    private final JComboBox<String> comboTipoLeitor;
    private final JTextField campoMatricula;
    private final JLabel mensagem;

    public AtualizarUsuarioView() {
        setResizable(false);
        setTitle("👤 Atualização de dados dos Usuários");
        setSize(797, 532);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());

        JPanel painelLeitor = new JPanel();
        painelLeitor.setBounds(90, 155, 765, 689);
        painelLeitor.setBorder(BorderFactory.createEmptyBorder(20, 40, 10, 40));
        painelLeitor.setLayout(null);

        JLabel cl = new JLabel("Atualização de dados dos Usuários");
        cl.setFont(new Font("Arial", Font.PLAIN, 20));
        cl.setBounds(231, 50, 321, 24);
        painelLeitor.add(cl);
        
        JLabel matricula = new JLabel("Digite a sua matricula:");
        matricula.setFont(new Font("Verdana", Font.PLAIN, 14));
        matricula.setBounds(121, 110, 172, 48);
        painelLeitor.add(matricula);
        campoMatricula = new JTextField();
        campoMatricula.setFont(new Font("Verdana", Font.PLAIN, 14));
        campoMatricula.setBounds(343, 118, 349, 33);
        painelLeitor.add(campoMatricula);
        campoMatricula.setColumns(10);

        JLabel nomeNovo = new JLabel("Nome novo:");
        nomeNovo.setFont(new Font("Verdana", Font.PLAIN, 14));
        nomeNovo.setBounds(169, 168, 124, 48);
        painelLeitor.add(nomeNovo);
        campoNome = new JTextField();
        campoNome.setFont(new Font("Verdana", Font.PLAIN, 14));
        campoNome.setBounds(343, 176, 349, 33);
        painelLeitor.add(campoNome);
        campoNome.setColumns(10);

        JLabel telefoneNovo = new JLabel("Telefone novo:");
        telefoneNovo.setFont(new Font("Verdana", Font.PLAIN, 14));
        telefoneNovo.setBounds(169, 222, 124, 31);
        painelLeitor.add(telefoneNovo);
        campoTelefone = new JTextField();
        campoTelefone.setFont(new Font("Verdana", Font.PLAIN, 14));
        campoTelefone.setColumns(10);
        campoTelefone.setBounds(343, 223, 349, 28);
        painelLeitor.add(campoTelefone);

        JLabel emailNovo = new JLabel("E-mail novo:");
        emailNovo.setFont(new Font("Verdana", Font.PLAIN, 14));
        emailNovo.setBounds(169, 264, 124, 31);
        painelLeitor.add(emailNovo);
        campoEmail = new JTextField();
        campoEmail.setFont(new Font("Verdana", Font.PLAIN, 14));
        campoEmail.setColumns(10);
        campoEmail.setBounds(343, 265, 349, 28);
        painelLeitor.add(campoEmail);

        JLabel tipoNovo = new JLabel("Tipo o novo tipo de usuario:");
        tipoNovo.setFont(new Font("Verdana", Font.PLAIN, 14));
        tipoNovo.setBounds(121, 306, 222, 31);
        painelLeitor.add(tipoNovo);
        comboTipoLeitor = new JComboBox<>(new String[]{" ", "ADMINISTRADOR", "BIBLIOTECARIO", "ESTAGIARIO"});
        comboTipoLeitor.setBounds(343, 303, 212, 31);
        painelLeitor.add(comboTipoLeitor);

        mensagem = new JLabel("", SwingConstants.CENTER);
        getContentPane().add(mensagem, BorderLayout.NORTH);
        getContentPane().add(painelLeitor, BorderLayout.CENTER);

        JButton botaoAtualizar = new JButton("Atualizar Usuario");
        botaoAtualizar.setFont(new Font("Verdana", Font.PLAIN, 16));
        botaoAtualizar.setBounds(275, 386, 263, 21);
        painelLeitor.add(botaoAtualizar);
        botaoAtualizar.addActionListener(e -> atualizarUsuario());

        setVisible(true);
    }

    private void atualizarUsuario() {
        String nomeNovo = campoNome.getText().trim();
        String matricula = campoMatricula.getText().trim();
        String telefoneNovo = campoTelefone.getText().trim();
        String emailNovo = campoEmail.getText().trim();
        String tipoStr = (String) comboTipoLeitor.getSelectedItem();

        // Validações básicas
        if (nomeNovo.isEmpty() || matricula.isEmpty() ||telefoneNovo.isEmpty() || emailNovo.isEmpty() || tipoStr == null || tipoStr.isBlank()) {
            mensagem.setText("⚠ Preencha todos os campos.");
            return;
		}

        if (!telefoneNovo.matches("\\d+")) {
            mensagem.setText("⚠ O telefone deve conter apenas números.");
            return;
        }

        if (!emailNovo.matches("^[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,}$")) {
            mensagem.setText("⚠ E-mail inválido. Ex: exemplo@dominio.com");
            return;
        }

        if (!nomeNovo.matches("^[A-Za-zÀ-ÿ ]+$")) {
            mensagem.setText("⚠ O nome deve conter apenas letras.");
            return;
        }

        try {
            UsuarioController controller = new UsuarioController();
            boolean editarUsuario = controller.editarUsuario(matricula, nomeNovo, telefoneNovo, emailNovo); // matrícula automática
            if(editarUsuario) {
                mensagem.setText("✅ Usuario atualizado com sucesso!");
                limparCampos();
            }else {
            	System.out.println("❌ Usuário não encotrado.");
            }
        } catch (Exception e) {
            mensagem.setText("❌ Erro inesperado ao cadastrar.");
            e.printStackTrace();
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
