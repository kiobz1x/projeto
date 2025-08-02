package view;

import controller.LeitorController;

import javax.swing.*;
import java.awt.*;

public class ExcluirLeitoresView extends JFrame {
    private final JTextField campoMatricula;
    private final JLabel mensagem;

    public ExcluirLeitoresView() {
        setResizable(false);
        setTitle("Excluir Leitores");
        setSize(797, 383);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());

        JPanel painelLeitor = new JPanel();
        painelLeitor.setBounds(90, 155, 765, 689);
        painelLeitor.setBorder(BorderFactory.createEmptyBorder(20, 40, 10, 40));
        painelLeitor.setLayout(null);

        JLabel r = new JLabel("Exlcuir Leitores");
        r.setFont(new Font("Arial", Font.PLAIN, 20));
        r.setBounds(287, 51, 212, 24);
        painelLeitor.add(r);

        JLabel excluirLeitor = new JLabel("Digite a matricula do leitor");
        excluirLeitor.setFont(new Font("Verdana", Font.PLAIN, 14));
        excluirLeitor.setBounds(178, 151, 203, 32);
        painelLeitor.add(excluirLeitor);
        campoMatricula = new JTextField();
        campoMatricula.setFont(new Font("Verdana", Font.PLAIN, 14));
        campoMatricula.setBounds(416, 154, 178, 29);
        painelLeitor.add(campoMatricula);
        campoMatricula.setColumns(10);

        mensagem = new JLabel("", SwingConstants.CENTER);
        getContentPane().add(mensagem, BorderLayout.NORTH);
        getContentPane().add(painelLeitor, BorderLayout.CENTER);

        JButton botaoExcluir = new JButton("Excluir");
        botaoExcluir.setFont(new Font("Verdana", Font.PLAIN, 16));
        botaoExcluir.setBounds(248, 253, 263, 21);
        painelLeitor.add(botaoExcluir);
        botaoExcluir.addActionListener(e -> excluirLeitor());

        setVisible(true);
    }

    private void excluirLeitor() {
    	String excluirLeitor = campoMatricula.getText().trim();
    	
    	if (excluirLeitor.isEmpty()) {
            mensagem.setText("Preencha todos os campos.");
            return;
        }
    	
    	LeitorController controller = new LeitorController();
    	boolean removerLeitor = controller.removerLeitor(excluirLeitor);
    	
    	if(removerLeitor) {
    		mensagem.setText("Leitor excluido com sucesso");
        	limparCampos();
    	}else {
    		mensagem.setText("Leitor não encontrado.");
    	}
    	
    }

    private void limparCampos() {
        campoMatricula.setText("");
    }
}
