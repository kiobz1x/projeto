package view;

import controller.EmprestimoController;
import controller.LeitorController;
import controller.ObraController;
import exception.emprestimo.EmprestimoJaRealizadoException;
import model.Leitor;
import model.Obra;

import javax.swing.*;
import java.awt.*;

public class RegistrarEmprestimoView extends JFrame {
    private final JTextField campoMatricula;
    private final JTextField campoCodigoObra;
    private final JLabel mensagem;

    public RegistrarEmprestimoView() {
        setResizable(false);
        setTitle("Registrar Empréstimo");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());

        JPanel painel = new JPanel(new GridLayout(3, 2, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 40, 10, 40));

        JLabel matricula = new JLabel("Matrícula do leitor:");
        matricula.setFont(new Font("Verdana", Font.PLAIN, 14));
        painel.add(matricula);
        campoMatricula = new JTextField();
        campoMatricula.setFont(new Font("Verdana", Font.PLAIN, 14));
        painel.add(campoMatricula);

        JLabel codigo = new JLabel("Código da obra:");
        codigo.setFont(new Font("Verdana", Font.PLAIN, 14));
        painel.add(codigo);
        campoCodigoObra = new JTextField();
        campoCodigoObra.setFont(new Font("Verdana", Font.PLAIN, 14));
        painel.add(campoCodigoObra);

        JButton botaoRegistrar = new JButton("Registrar");
        botaoRegistrar.setFont(new Font("Verdana", Font.PLAIN, 16));
        botaoRegistrar.addActionListener(e -> {
			try {
				registrarEmprestimo();
			} catch (EmprestimoJaRealizadoException e1) {
				e1.printStackTrace();
			}
		});
        painel.add(botaoRegistrar);

        mensagem = new JLabel("", SwingConstants.CENTER);
        getContentPane().add(mensagem, BorderLayout.NORTH);
        getContentPane().add(painel, BorderLayout.CENTER);

        setVisible(true);
    }

    private void registrarEmprestimo() throws EmprestimoJaRealizadoException { //adicionei throws EmprestimoJaRealizadoException
        String matricula = campoMatricula.getText().trim();
        String codigoObra = campoCodigoObra.getText().trim();

        if (matricula.isEmpty() || codigoObra.isEmpty()) {
            mensagem.setText("Preencha todos os campos.");
            return;
        }

        LeitorController leitorController = new LeitorController();
        ObraController obraController = new ObraController();
        EmprestimoController emprestimoController = new EmprestimoController();

        Leitor leitor = leitorController.buscarLeitorPorMatricula(matricula);
        if (leitor == null) {
            mensagem.setText("Leitor não encontrado.");
            return;
        }

        Obra obra = obraController.buscarPorCodigo(codigoObra);
        if (obra == null) {
            mensagem.setText("Obra não encontrada.");
            return;
        }

        try {
            boolean sucesso = emprestimoController.realizarEmprestimo(leitor.getMatricula(), obra.getCodigo());

            if (sucesso) {
                mensagem.setText("Empréstimo registrado com sucesso!");
                limparCampos();
            } else {
                mensagem.setText("Falha ao registrar o empréstimo.");
            }
        } catch (EmprestimoJaRealizadoException e) { //antes tava ObraNaoEncontradaException | ObraNaoDisponivelException e, mudei para EmprestimoJaRealizadoException e
            mensagem.setText("X " + e.getMessage());
        }
    }

    private void limparCampos() {
        campoMatricula.setText("");
        campoCodigoObra.setText("");
    }
}
