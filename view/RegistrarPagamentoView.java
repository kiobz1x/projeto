package view;

import controller.LeitorController;
import controller.MultaController;
import model.Multa;
import model.Leitor;
import model.MetodoPagamento;

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

        JLabel matricula = new JLabel("Matrícula do usuário:");
        matricula.setFont(new Font("Verdana", Font.PLAIN, 14));
        painel.add(matricula);
        campoMatricula = new JTextField();
        campoMatricula.setFont(new Font("Verdana", Font.PLAIN, 14));
        painel.add(campoMatricula);

        JLabel valorMulta = new JLabel("Valor da multa (R$):");
        valorMulta.setFont(new Font("Verdana", Font.PLAIN, 14));
        painel.add(valorMulta);
        campoValor = new JTextField();
        campoValor.setFont(new Font("Verdana", Font.PLAIN, 14));
        painel.add(campoValor);

        JLabel metodoPagamento = new JLabel("Método de pagamento:");
        metodoPagamento.setFont(new Font("Verdana", Font.PLAIN, 14));
        painel.add(metodoPagamento);
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
        String metodoStr = (String) comboMetodo.getSelectedItem();

        if (matriculaLeitor.isEmpty() || metodoStr.equals(" ")) {
            mensagem.setText("⚠ Preencha todos os campos.");
            return;
        }

        LeitorController leitorController = new LeitorController();
        Leitor leitor = leitorController.buscarLeitorPorMatricula(matriculaLeitor);
        if (leitor == null) {
            mensagem.setText("❌ Leitor não encontrado.");
            return;
        }

        MetodoPagamento metodo;
        try {
            metodo = MetodoPagamento.valueOf(metodoStr);
        } catch (IllegalArgumentException e) {
            mensagem.setText("❌ Método de pagamento inválido.");
            return;
        }

        MultaController multaController = new MultaController();
        java.util.List<Multa> multasPendentes = multaController.listarMultasPendentes();

        boolean pagouAlguma = false;
        for (Multa multa : multasPendentes) {
            if (multa.getNomeLeitor().equalsIgnoreCase(leitor.getNome())) {
                boolean sucesso = multaController.pagarMultaComRegistro(multa.getIdEmprestimo(), metodo, leitor);
                if (sucesso) {
                    pagouAlguma = true;
                }
            }
        }

        if (pagouAlguma) {
            mensagem.setText("✅ Multa(s) paga(s) com sucesso!");
            limparCampos();
        } else {
            mensagem.setText("⚠ Nenhuma multa pendente encontrada para este leitor.");
        }
    }

    private void limparCampos() {
        campoMatricula.setText("");
        campoValor.setText("");
        comboMetodo.setSelectedIndex(0);
    }
}
