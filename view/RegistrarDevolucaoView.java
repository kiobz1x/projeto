package view;

import controller.EmprestimoController;
import model.Emprestimo;
import model.Obra;

import javax.swing.*;
import java.awt.*;

public class RegistrarDevolucaoView extends JFrame {
    private final JTextField campoCodigo;
    private final JLabel mensagem;

    public RegistrarDevolucaoView() {
        setTitle("📤 Registrar Devolução");
        setSize(350, 200);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // Fecha só essa janela
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Painel principal
        JPanel painel = new JPanel(new GridLayout(3, 1, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        painel.add(new JLabel("Código da obra:"));
        campoCodigo = new JTextField();
        painel.add(campoCodigo);

        JButton botaoRegistrar = new JButton("Registrar");
        botaoRegistrar.addActionListener(e -> registrarDevolucao());
        painel.add(botaoRegistrar);

        // Mensagem
        mensagem = new JLabel("", SwingConstants.CENTER);
        add(mensagem, BorderLayout.NORTH);
        add(painel, BorderLayout.CENTER);

        setVisible(true);
    }

    private void registrarDevolucao() {
        String codigo = campoCodigo.getText().trim();

        if (codigo.isEmpty()) {
            mensagem.setText("⚠ Informe o código da obra.");
            return;
        }

        EmprestimoController emprestimoController = new EmprestimoController();
        Emprestimo emprestimo = emprestimoController.encontrarEmprestimoPorCodigoObra(codigo);

        if (emprestimo == null) {
            mensagem.setText("❌ Nenhum empréstimo ativo encontrado para esta obra.");
            return;
        }

        Obra obra = emprestimo.getObra();
        boolean sucesso = emprestimoController.realizarDevolucao(obra);

        if (sucesso) {
            mensagem.setText("✅ Devolução registrada com sucesso.");
        } else {
            mensagem.setText("❌ Não foi possível registrar a devolução.");
        }
    }
}
