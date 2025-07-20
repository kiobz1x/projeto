package view;

import controller.LeitorController;
import model.Leitor;
import model.TipoLeitor;
import dao.LeitorDAO;

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
		setTitle("Cadastro de Leitores");
		setSize(1400, 900);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());
        
        JPanel painelLeitor = new JPanel();
        painelLeitor.setBounds(90,155,765,689);
        painelLeitor.setBorder(BorderFactory.createEmptyBorder(20, 40, 10, 40));
        painelLeitor.setLayout(null);
        
        JLabel nome = new JLabel("Nome: ");
        nome.setBounds(508, 151, 73, 48);
        painelLeitor.add(nome);
        campoNome = new JTextField();
        campoNome.setBounds(584, 201, 413, 33);
        painelLeitor.add(campoNome);
        
        JLabel matricula = new JLabel("Matricula");
        matricula.setBounds(508, 201, 62, 48);
        painelLeitor.add(matricula);
        campoMatricula = new JTextField();
        campoMatricula.setBounds(584, 151, 413, 33);
        painelLeitor.add(campoMatricula);
        
        JLabel telefone = new JLabel("Telefone");
        telefone.setBounds(508, 248, 73, 48);
        painelLeitor.add(telefone);
        campoTelefone = new JTextField();
        campoTelefone.setBounds(584, 248, 165, 33);
        painelLeitor.add(campoTelefone);
        
        JLabel email = new JLabel("E-mail");
        email.setBounds(508, 291, 73, 48);
        painelLeitor.add(email);
        campoEmail = new JTextField();
        campoEmail.setBounds(584, 299, 413, 33);
        painelLeitor.add(campoEmail);
        
        JLabel cadastroLeitor = new JLabel("Cadastro de Leitores");
        cadastroLeitor.setBounds(570, 67, 245, 48);
        cadastroLeitor.setFont(new Font("Arial", Font.BOLD, 25));
        painelLeitor.add(cadastroLeitor);
        
        JButton cadastrarLeitor = new JButton("Cadastrar");
        cadastrarLeitor.setBounds(561, 385, 271, 33);
        cadastrarLeitor.setFont(new Font("Arial", Font.BOLD, 25));
        painelLeitor.add(cadastrarLeitor);
        
        JLabel tipo = new JLabel("Tipo leitor");
        tipo.setBounds(759, 255, 73, 19);
        painelLeitor.add(tipo);
        comboTipoLeitor = new JComboBox<>(new String[] {" ","Aluno", "Professor", "Servidor"});
        comboTipoLeitor.setBounds(838, 248, 158, 33);
        painelLeitor.add(comboTipoLeitor);
        
        mensagem = new JLabel("", SwingConstants.CENTER);
        
        getContentPane().add(mensagem, BorderLayout.NORTH);
        getContentPane().add(painelLeitor, BorderLayout.CENTER);
        getContentPane().add(mensagem, BorderLayout.NORTH);

        setVisible(true);
        
	}
	
	private void cadastrarLeitor() {
		String nome = campoNome.getText().trim();
		String matricula = campoMatricula.getText().trim();
		String telefone = campoTelefone.getText().trim();
		String email = campoEmail.getText().trim();
		String tipoLeitor = (String) comboTipoLeitor.getSelectedItem();
		
		if (nome.isEmpty() || matricula.isEmpty() || telefone.isEmpty() || email.isEmpty()) {
            mensagem.setText("⚠ Preencha todos os campos.");
            return;
        }
		
		try {
            TipoLeitor tipo = TipoLeitor.valueOf(tipoLeitor);
            Leitor leitor = new Leitor(nome, matricula, tipo, telefone, email);
            LeitorController controller = new LeitorController();

            boolean sucesso = controller.cadastrarLeitor(leitor);

            if (sucesso) {
                mensagem.setText("✅ Leitor cadastrado com sucesso!");
                limparCampos();
            } else {
                mensagem.setText("❌ Leitor já cadastrado.");
            }
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
