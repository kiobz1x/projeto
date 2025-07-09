package view;

import controller.EmprestimoController;
import model.Obra;

import javax.swing.*;
import java.awt.*;
import controller.ObraController;


public class RegistrarDevolucaoView extends JFrame {
    private final JTextField campoCodigoObra;
    private final JLabel mensagem;

    public RegistrarDevolucaoView() {
        setTitle("📤 Registrar Devolução");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel painel = new JPanel(new GridLayout(2, 2, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 40, 10, 40));

        painel.add(new JLabel("Código da obra:"));
        campoCodigoObra = new JTextField();
        painel.add(campoCodigoObra);

        JButton botaoRegistrar = new JButton("Registrar");
        botaoRegistrar.addActionListener(e -> registrarDevolucao());
        painel.add(botaoRegistrar);

        mensagem = new JLabel("", SwingConstants.CENTER);
        add(mensagem, BorderLayout.NORTH);
        add(painel, BorderLayout.CENTER);

        setVisible(true);
    }

    private void registrarDevolucao() {
        String codigo = campoCodigoObra.getText().trim();

        if (codigo.isEmpty()) {
            mensagem.setText("⚠ Informe o código da obra.");
            return;
        }

        ObraController obraController = new ObraController();
        EmprestimoController emprestimoController = new EmprestimoController();

        Obra obra = obraController.buscarPorCodigo(codigo);
        if (obra == null) {
            mensagem.setText("❌ Obra não encontrada.");
            return;
        }

        if (obra.isDisponivel()) {
            mensagem.setText("❌ Essa obra já está disponível.");
            return;
        }

        boolean sucesso = emprestimoController.realizarDevolucao(obra);
        if (sucesso) {
            mensagem.setText("✅ Devolução registrada com sucesso!");
        } else {
            mensagem.setText("❌ Empréstimo não encontrado.");
        }
    }
}
