package view;

import model.Obra;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import controller.ObraController;


public class PesquisarObrasView extends JFrame {
    private final JTextField campoTitulo;
    private final JTextField campoAutor;
    private final JComboBox<String> comboTipo;
    private final JComboBox<String> comboStatus;
    private final JTextArea resultadoArea;
    private final ObraController controller;

    public PesquisarObrasView() {
        setTitle("🔎 Pesquisar Obras");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        controller = new ObraController();

        JPanel painelFiltros = new JPanel(new GridLayout(5, 2, 5, 5));
        painelFiltros.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        painelFiltros.add(new JLabel("Título:"));
        campoTitulo = new JTextField();
        painelFiltros.add(campoTitulo);

        painelFiltros.add(new JLabel("Autor:"));
        campoAutor = new JTextField();
        painelFiltros.add(campoAutor);

        painelFiltros.add(new JLabel("Tipo:"));
        comboTipo = new JComboBox<>(new String[]{"Todos", "Livro", "Revista", "Artigo"});
        painelFiltros.add(comboTipo);

        painelFiltros.add(new JLabel("Status:"));
        comboStatus = new JComboBox<>(new String[]{"Todos", "Disponível", "Emprestado"});
        painelFiltros.add(comboStatus);

        JButton botaoBuscar = new JButton("Buscar");
        botaoBuscar.addActionListener(e -> realizarBusca());
        painelFiltros.add(botaoBuscar);

        add(painelFiltros, BorderLayout.NORTH);

        resultadoArea = new JTextArea();
        resultadoArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(resultadoArea);
        add(scroll, BorderLayout.CENTER);

        setVisible(true);
    }

    private void realizarBusca() {
        String titulo = campoTitulo.getText().trim();
        String autor = campoAutor.getText().trim();
        String tipoSelecionado = (String) comboTipo.getSelectedItem();
        String statusSelecionado = (String) comboStatus.getSelectedItem();

        // Começa com todas as obras
        List<Obra> lista = controller.getObras();

        // Aplica filtros
        if (!titulo.isEmpty()) {
            lista.retainAll(controller.buscarPorTitulo(titulo));
        }

        if (!autor.isEmpty()) {
            lista.retainAll(controller.buscarPorAutor(autor));
        }

        if (!"Todos".equalsIgnoreCase(tipoSelecionado)) {
            lista.retainAll(controller.filtrarPorTipo(tipoSelecionado));
        }

        if (!"Todos".equalsIgnoreCase(statusSelecionado)) {
            boolean emprestado = "Emprestado".equalsIgnoreCase(statusSelecionado);
            lista.retainAll(controller.filtrarPorStatus(emprestado));
        }

        exibirResultados(lista);
    }

    private void exibirResultados(List<Obra> lista) {
        resultadoArea.setText("");
        if (lista == null || lista.isEmpty()) {
            resultadoArea.setText("📭 Nenhuma obra encontrada.");
        } else {
            for (Obra obra : lista) {
                resultadoArea.append(obra.toString() + "\n");
            }
        }
    }
}
