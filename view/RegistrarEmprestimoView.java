package view;

import controller.EmprestimoController;
import controller.LeitorController;
import controller.ObraController;
import model.Leitor;
import model.Obra;

import javax.swing.*;
import java.awt.*;

public class RegistrarEmprestimoView extends JFrame {
    private final JTextField campoMatricula;
    private final JTextField campoCodigoObra;
    private final JLabel mensagem;

    public RegistrarEmprestimoView() {
        setTitle("📥 Registrar Empréstimo");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel painel = new JPanel(new GridLayout(3, 2, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 40, 10, 40));

        painel.add(new JLabel("Matrícula do leitor:"));
        campoMatricula = new JTextField();
        painel.add(campoMatricula);

        painel.add(new JLabel("Código da obra:"));
        campoCodigoObra = new JTextField();
        painel.add(campoCodigoObra);

        JButton botaoRegistrar = new JButton("Registrar");
        botaoRegistrar.addActionListener(e -> registrarEmprestimo());
        painel.add(botaoRegistrar);

        mensagem = new JLabel("", SwingConstants.CENTER);
        add(mensagem, BorderLayout.NORTH);
        add(painel, BorderLayout.CENTER);

        setVisible(true);
    }

    private void registrarEmprestimo() {
        String matricula = campoMatricula.getText().trim();
        String codigoObra = campoCodigoObra.getText().trim();

        if (matricula.isEmpty() || codigoObra.isEmpty()) {
            mensagem.setText("⚠ Preencha todos os campos.");
            return;
        }

        LeitorController leitorController = new LeitorController();
        ObraController obraController = new ObraController();
        EmprestimoController emprestimoController = new EmprestimoController();

        Leitor leitor = leitorController.buscarLeitorPorMatricula(matricula);
        if (leitor == null) {
            mensagem.setText("❌ Leitor não encontrado.");
            return;
        }

        Obra obra = obraController.buscarPorCodigo(codigoObra);
        if (obra == null) {
            mensagem.setText("❌ Obra não encontrada.");
            return;
        }

        if (!obra.isDisponivel()) {
            mensagem.setText("❌ Obra já está emprestada.");
            return;
        }

        boolean sucesso = emprestimoController.realizarEmprestimo(leitor.getMatricula(), obra.getCodigo());

        if (sucesso) {
            mensagem.setText("✅ Empréstimo registrado com sucesso!");
            limparCampos();
        } else {
            mensagem.setText("❌ Falha ao registrar o empréstimo.");
        }
    }

    private void limparCampos() {
        campoMatricula.setText("");
        campoCodigoObra.setText("");
    }
}
