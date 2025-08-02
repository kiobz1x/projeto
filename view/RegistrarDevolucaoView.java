package view;

import controller.EmprestimoController;
import exception.emprestimo.DevolucaoInvalidaException;
import model.Emprestimo;

import javax.swing.*;
import java.awt.*;

public class RegistrarDevolucaoView extends JFrame {
    private final JTextField campoCodigo;
    private final JLabel mensagem;

    public RegistrarDevolucaoView() {
        setTitle("Registrar Devolução");
        setSize(350, 200);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel painel = new JPanel(new GridLayout(3, 1, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        painel.add(new JLabel("Código da obra:"));
        campoCodigo = new JTextField();
        painel.add(campoCodigo);

        JButton botaoRegistrar = new JButton("Registrar");
        botaoRegistrar.addActionListener(e -> {
			try {
				registrarDevolucao();
			} catch (DevolucaoInvalidaException e1) {
				e1.printStackTrace();
			}
		});
        painel.add(botaoRegistrar);

        mensagem = new JLabel("", SwingConstants.CENTER);
        add(mensagem, BorderLayout.NORTH);
        add(painel, BorderLayout.CENTER);

        setVisible(true);
    }

    private void registrarDevolucao() throws DevolucaoInvalidaException {
        String codigo = campoCodigo.getText().trim();

        if (codigo.isEmpty()) {
            mensagem.setText("Informe o código da obra.");
            return;
        }

        EmprestimoController emprestimoController = new EmprestimoController();
        Emprestimo emprestimo = emprestimoController.encontrarEmprestimoAtivoPorObra(codigo);

        if (emprestimo == null) {
            mensagem.setText("Nenhum empréstimo ativo encontrado para esta obra.");
            return;
        }

        String codigoObra = emprestimo.getObra().getCodigo();
        boolean sucesso = emprestimoController.realizarDevolucao(codigoObra);

        if (sucesso) {
            mensagem.setText("Devolução registrada com sucesso.");
        } else {
            mensagem.setText("Não foi possível registrar a devolução.");
        }
    }
}
