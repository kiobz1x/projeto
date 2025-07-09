package view;

import controller.PagamentoController;
import controller.UsuarioController;
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
        setTitle("💳 Registrar Pagamento de Multa");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel painel = new JPanel(new GridLayout(4, 2, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 40, 10, 40));

        painel.add(new JLabel("Matrícula do usuário:"));
        campoMatricula = new JTextField();
        painel.add(campoMatricula);

        painel.add(new JLabel("Valor da multa (R$):"));
        campoValor = new JTextField();
        painel.add(campoValor);

        painel.add(new JLabel("Método de pagamento:"));
        comboMetodo = new JComboBox<>(new String[]{"DINHEIRO", "PIX", "CARTAO"});
        painel.add(comboMetodo);

        JButton botaoRegistrar = new JButton("Registrar Pagamento");
        botaoRegistrar.addActionListener(e -> registrarPagamento());
        painel.add(botaoRegistrar);

        mensagem = new JLabel("", SwingConstants.CENTER);
        add(mensagem, BorderLayout.NORTH);
        add(painel, BorderLayout.CENTER);

        setVisible(true);
    }

    private void registrarPagamento() {
        String matricula = campoMatricula.getText().trim();
        String valorTexto = campoValor.getText().trim();
        String metodoStr = (String) comboMetodo.getSelectedItem();

        if (matricula.isEmpty() || valorTexto.isEmpty()) {
            mensagem.setText("⚠ Preencha todos os campos.");
            return;
        }

        UsuarioController usuarioController = new UsuarioController();
        Usuario usuario = usuarioController.buscarPorMatricula(matricula);
        if (usuario == null) {
            mensagem.setText("❌ Usuário não encontrado.");
            return;
        }

        try {
            double valor = Double.parseDouble(valorTexto);
            MetodoPagamento metodo = MetodoPagamento.valueOf(metodoStr);

            PagamentoController pagamentoController = new PagamentoController();
            pagamentoController.registrarPagamento(usuario, valor, metodo);
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
