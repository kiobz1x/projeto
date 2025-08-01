package view;

import controller.UsuarioController;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class ExcluirUsuarioView extends JFrame{
	private final JTextField campoMatricula;
    private final JLabel mensagem;

    public ExcluirUsuarioView() {
        setResizable(false);
        setTitle("👤 Excluir Usuário");
        setSize(797, 383);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());

        JPanel painelLeitor = new JPanel();
        painelLeitor.setBounds(90, 155, 765, 689);
        painelLeitor.setBorder(BorderFactory.createEmptyBorder(20, 40, 10, 40));
        painelLeitor.setLayout(null);

        JLabel r = new JLabel("Exlcuir Usuário");
        r.setFont(new Font("Arial", Font.PLAIN, 20));
        r.setBounds(287, 51, 212, 24);
        painelLeitor.add(r);

        JLabel excluirUsuario = new JLabel("Digite a matricula do Usuário:");
        excluirUsuario.setFont(new Font("Verdana", Font.PLAIN, 14));
        excluirUsuario.setBounds(110, 148, 222, 32);
        painelLeitor.add(excluirUsuario);
        campoMatricula = new JTextField();
        campoMatricula.setBounds(373, 151, 203, 32);
        painelLeitor.add(campoMatricula);

        mensagem = new JLabel("", SwingConstants.CENTER);
        getContentPane().add(mensagem, BorderLayout.NORTH);
        getContentPane().add(painelLeitor, BorderLayout.CENTER);

        JButton botaoExcluir = new JButton("Excluir");
        botaoExcluir.setFont(new Font("Verdana", Font.PLAIN, 16));
        botaoExcluir.setBounds(248, 253, 263, 21);
        painelLeitor.add(botaoExcluir);
        botaoExcluir.addActionListener(e -> excluirUsuario());
        

        setVisible(true);
    }

    private void excluirUsuario() {
    	String excluirUsuario = campoMatricula.getText().trim();
    	
    	if (excluirUsuario.isEmpty()) {
            mensagem.setText("⚠ Preencha todos os campos.");
            return;
        }
    	
    	UsuarioController controller = new UsuarioController();
    	boolean removerUsuario = controller.removerUsuario(excluirUsuario);
    	
    	if(removerUsuario) {
        	mensagem.setText("✅ Usuário excluido com sucesso");
        	limparCampos();
    	}else {
    		mensagem.setText("❌ Usuário não encontrado.");
    	}
    }

    private void limparCampos() {
        campoMatricula.setText("");
    }

}
