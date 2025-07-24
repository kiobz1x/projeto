package view;

import controller.PagamentoController;
import controller.UsuarioController;
import controller.LeitorController;
import controller.MultaController;
import model.Multa;
import model.Leitor;
import model.MetodoPagamento;
import model.Usuario;

import javax.swing.*;
import java.awt.*;

public class RegistrarPagamentoView extends JFrame {
    private final JTextField campoMatricula;
    private final JTextField campoValor;
    private final JComboBox<String> comboMetodo;
    private final JLabel mensagem;

    public RegistrarPagamentoView() {
    	setResizable(false);
        setTitle("💳 Registrar Pagamento de Multa");
        setSize(601, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());

        JPanel painel = new JPanel(new GridLayout(4, 2, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 40, 10, 40));

        JLabel label_2 = new JLabel("Matrícula do usuário:");
        label_2.setFont(new Font("Verdana", Font.PLAIN, 14));
        painel.add(label_2);
        campoMatricula = new JTextField();
        campoMatricula.setFont(new Font("Verdana", Font.PLAIN, 14));
        painel.add(campoMatricula);

        JLabel label_1 = new JLabel("Valor da multa (R$):");
        label_1.setFont(new Font("Verdana", Font.PLAIN, 14));
        painel.add(label_1);
        campoValor = new JTextField();
        campoValor.setFont(new Font("Verdana", Font.PLAIN, 14));
        painel.add(campoValor);

        JLabel label = new JLabel("Método de pagamento:");
        label.setFont(new Font("Verdana", Font.PLAIN, 14));
        painel.add(label);
        comboMetodo = new JComboBox<>(new String[]{" ", "DINHEIRO", "PIX", "CARTAO"});
        comboMetodo.setFont(new Font("Verdana", Font.PLAIN, 14));
        painel.add(comboMetodo);

        JButton botaoRegistrar = new JButton("Registrar Pagamento");
        botaoRegistrar.setFont(new Font("Verdana", Font.PLAIN, 16));
        botaoRegistrar.addActionListener(e -> registrarPagamento());
        painel.add(botaoRegistrar);

        mensagem = new JLabel("", SwingConstants.CENTER);
        getContentPane().add(mensagem, BorderLayout.NORTH);
        getContentPane().add(painel, BorderLayout.CENTER);

        setVisible(true);
    }

    private void registrarPagamento() {
        String matriculaLeitor = campoMatricula.getText().trim();
        String valorTexto = campoValor.getText().trim();
        String metodoStr = (String) comboMetodo.getSelectedItem();

        if (matriculaLeitor.isEmpty() || valorTexto.isEmpty()) {
            mensagem.setText("⚠ Preencha todos os campos.");
            return;
        }

//        UsuarioController usuarioController = new UsuarioController();
//        Usuario usuario = usuarioController.buscarPorMatricula(matricula);
        
        LeitorController leitorController = new LeitorController();
        Leitor leitor = leitorController.buscarLeitorPorMatricula(matriculaLeitor);
        if (leitor == null) {
            mensagem.setText("❌ Usuário não encontrado.");
            return;
        }
        try {
            double valor = Double.parseDouble(campoValor.getText().trim());
            if (valor < 0) {
                mensagem.setText("⚠ Valor negativo não é permitido.");
                return;
            }
        } catch (NumberFormatException e) {
            mensagem.setText("⚠ Valor inválido. Use apenas números.");
            return;
        }


        try {
            double valor = Double.parseDouble(valorTexto);
            MetodoPagamento metodo = MetodoPagamento.valueOf(metodoStr);

            PagamentoController pagamentoController = new PagamentoController();
            pagamentoController.registrarPagamento(leitor, valor, metodo);
            mensagem.setText("✅ Pagamento registrado com sucesso!");
            limparCampos();
        } catch (NumberFormatException e) {
            mensagem.setText("❌ Valor inválido.");
        } catch (IllegalArgumentException e) {
            mensagem.setText("❌ Método de pagamento inválido.");
        }
    }

    private void limparCampos() {
        campoMatricula.setText("");
        campoValor.setText("");
        comboMetodo.setSelectedIndex(0);
    }
}
