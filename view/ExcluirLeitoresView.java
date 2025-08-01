package view;

import controller.LeitorController;
import model.Leitor;
import model.TipoLeitor;

import javax.swing.*;
import java.awt.*;

public class ExcluirLeitoresView extends JFrame {
    private final JTextField campoMatricula;
    private final JLabel mensagem;
    private JTextField textField;

    public ExcluirLeitoresView() {
        setResizable(false);
        setTitle("👤 Cadastro de Leitores");
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

        JLabel excluirLeitores = new JLabel("Digite a matricula do leitor:");
        campoMatricula = new JTextField();
        painelLeitor.add(campoMatricula);
        

        mensagem = new JLabel("", SwingConstants.CENTER);
        getContentPane().add(mensagem, BorderLayout.NORTH);
        getContentPane().add(painelLeitor, BorderLayout.CENTER);

        JButton botaoExcluir = new JButton("Excluir");
        botaoExcluir.setFont(new Font("Verdana", Font.PLAIN, 16));
        botaoExcluir.setBounds(248, 253, 263, 21);
        painelLeitor.add(botaoExcluir);
        
        textField = new JTextField();
        textField.setFont(new Font("Verdana", Font.PLAIN, 14));
        textField.setBounds(416, 154, 178, 29);
        painelLeitor.add(textField);
        textField.setColumns(10);
        
        JLabel lblNewLabel = new JLabel("Digite a matricula do leitor");
        lblNewLabel.setFont(new Font("Verdana", Font.PLAIN, 14));
        lblNewLabel.setBounds(178, 151, 203, 32);
        painelLeitor.add(lblNewLabel);
        botaoExcluir.addActionListener(e -> excluirLeitor());

        setVisible(true);
    }

    private void excluirLeitor() {
    	String matricula = campoMatricula.getText().trim();
    	limparCampos();
    }

    private void limparCampos() {
        campoMatricula.setText("");
    }
}
