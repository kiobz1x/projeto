package view;

import controller.RelatorioController;

import javax.swing.*;
import java.awt.*;

public class RelatorioView extends JFrame {
    private final RelatorioController controller;

    public RelatorioView() {
        setTitle("📄 Gerar Relatórios");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        controller = new RelatorioController();

        JLabel titulo = new JLabel("Relatórios Gerenciais", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        add(titulo, BorderLayout.NORTH);

        JPanel painel = new JPanel(new GridLayout(4, 1, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JButton botaoEmprestimosMes = new JButton("📆 Empréstimos do Mês");
        botaoEmprestimosMes.addActionListener(e -> controller.gerarRelatorioEmprestimosDoMes());
        painel.add(botaoEmprestimosMes);

        JButton botaoMaisEmprestadas = new JButton("📚 Obras Mais Emprestadas");
        botaoMaisEmprestadas.addActionListener(e -> controller.gerarRelatorioObrasMaisEmprestadas());
        painel.add(botaoMaisEmprestadas);

        JButton botaoAtrasos = new JButton("⏰ Usuários com Mais Atrasos");
        botaoAtrasos.addActionListener(e -> controller.gerarRelatorioUsuariosComMaisAtrasos());
        painel.add(botaoAtrasos);

        JButton botaoFechar = new JButton("Fechar");
        botaoFechar.addActionListener(e -> dispose());
        painel.add(botaoFechar);

        add(painel, BorderLayout.CENTER);

        setVisible(true);
    }
}
