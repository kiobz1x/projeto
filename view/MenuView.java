package view;

import model.Usuario;
import model.TipoUsuario;

import javax.swing.*;
import java.awt.*;

public class MenuView extends JFrame {
    private final Usuario usuario;

    public MenuView(Usuario usuario) {
        setResizable(false);
        this.usuario = usuario;

        setTitle("Menu - " + usuario.getTipo());
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel boasVindas = new JLabel("Olá, " + usuario.getNome() + " (" + usuario.getTipo() + ")", SwingConstants.CENTER);
        boasVindas.setFont(new Font("Arial", Font.BOLD, 16));
        add(boasVindas, BorderLayout.NORTH);

        JPanel painelBotoes = new JPanel(new GridLayout(0, 1, 10, 10));
        painelBotoes.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        switch (usuario.getTipo()) {
            case ADMINISTRADOR -> {
                JButton botaoCadastrarObra = new JButton("Cadastrar Obras");
                botaoCadastrarObra.addActionListener(e -> new CadastroObraView());
                painelBotoes.add(botaoCadastrarObra);

                JButton botaoCadastrarUsuario = new JButton("Cadastrar Usuários");
                botaoCadastrarUsuario.addActionListener(e -> new CadastroUsuarioView());
                painelBotoes.add(botaoCadastrarUsuario);

                JButton botaoCadastrarLeitor = new JButton("Cadastrar Leitores");
                botaoCadastrarLeitor.addActionListener(e -> new CadastroLeitoresView());
                painelBotoes.add(botaoCadastrarLeitor);

            }

            case BIBLIOTECARIO -> {
                JButton botaoEmprestimo = new JButton("Registrar Empréstimo");
                botaoEmprestimo.addActionListener(e -> new RegistrarEmprestimoView());
                painelBotoes.add(botaoEmprestimo);

                JButton botaoDevolucaoBiblio = new JButton("Registrar Devolução");
                botaoDevolucaoBiblio.addActionListener(e -> new RegistrarDevolucaoView());
                painelBotoes.add(botaoDevolucaoBiblio);

                JButton botaoPagamento = new JButton("Registrar Pagamento de Multa");
                botaoPagamento.addActionListener(e -> new RegistrarPagamentoView());
                painelBotoes.add(botaoPagamento);

                JButton botaoRelatorios = new JButton("📄 Gerar Relatórios");
                botaoRelatorios.addActionListener(e -> new RelatorioView());
                painelBotoes.add(botaoRelatorios);
            }

            case ESTAGIARIO -> {
                JButton botaoDevolucaoEstagiario = new JButton("Registrar Devolução");
                botaoDevolucaoEstagiario.addActionListener(e -> new RegistrarDevolucaoView());
                painelBotoes.add(botaoDevolucaoEstagiario);
            }
        }

        JButton sair = new JButton("Sair");
        sair.addActionListener(e -> System.exit(0));
        painelBotoes.add(sair);

        add(painelBotoes, BorderLayout.CENTER);
        setVisible(true);
    }
}
