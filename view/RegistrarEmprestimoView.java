package view;

import controller.EmprestimoController;
import controller.UsuarioController;
import model.Obra;
import model.Usuario;
import controller.ObraController;


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

        painel.add(new JLabel("Matrícula do usuário:"));
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

        UsuarioController usuarioController = new UsuarioController();
        ObraController obraController = new ObraController();
        EmprestimoController emprestimoController = new EmprestimoController();

        Usuario usuario = usuarioController.buscarPorMatricula(matricula);
        if (usuario == null) {
            mensagem.setText("❌ Usuário não encontrado.");
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

        boolean sucesso = emprestimoController.realizarEmprestimo(usuario, obra);
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
