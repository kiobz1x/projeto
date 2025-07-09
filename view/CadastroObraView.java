package view;

import javax.swing.*;
import java.awt.*;
import controller.ObraController;


public class CadastroObraView extends JFrame {
    private final JTextField campoCodigo;
    private final JTextField campoTitulo;
    private final JTextField campoAutor;
    private final JTextField campoAno;
    private final JComboBox<String> comboTipo;
    private final JLabel mensagem;

    public CadastroObraView() {
        setTitle("📘 Cadastro de Obra");
        setSize(400, 350);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // Fecha só esta janela
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel painelCampos = new JPanel(new GridLayout(6, 2, 10, 10));
        painelCampos.setBorder(BorderFactory.createEmptyBorder(20, 40, 10, 40));

        painelCampos.add(new JLabel("Código:"));
        campoCodigo = new JTextField();
        painelCampos.add(campoCodigo);

        painelCampos.add(new JLabel("Título:"));
        campoTitulo = new JTextField();
        painelCampos.add(campoTitulo);

        painelCampos.add(new JLabel("Autor:"));
        campoAutor = new JTextField();
        painelCampos.add(campoAutor);

        painelCampos.add(new JLabel("Ano de publicação:"));
        campoAno = new JTextField();
        painelCampos.add(campoAno);

        painelCampos.add(new JLabel("Tipo:"));
        comboTipo = new JComboBox<>(new String[]{"Livro", "Revista", "Artigo"});
        painelCampos.add(comboTipo);

        JButton botaoCadastrar = new JButton("Cadastrar");
        botaoCadastrar.addActionListener(e -> cadastrarObra());

        mensagem = new JLabel("", SwingConstants.CENTER);

        add(painelCampos, BorderLayout.CENTER);
        add(botaoCadastrar, BorderLayout.SOUTH);
        add(mensagem, BorderLayout.NORTH);

        setVisible(true);
    }

    private void cadastrarObra() {
        String codigo = campoCodigo.getText().trim();
        String titulo = campoTitulo.getText().trim();
        String autor = campoAutor.getText().trim();
        String anoTexto = campoAno.getText().trim();
        String tipo = (String) comboTipo.getSelectedItem();

        if (codigo.isEmpty() || titulo.isEmpty() || autor.isEmpty() || anoTexto.isEmpty()) {
            mensagem.setText("⚠ Preencha todos os campos.");
            return;
        }

        try {
            int ano = Integer.parseInt(anoTexto);
            ObraController controller = new ObraController();
            boolean sucesso = controller.adicionarObra(codigo, titulo, autor, ano, tipo);

            if (sucesso) {
                mensagem.setText("✅ Obra cadastrada com sucesso!");
                limparCampos();
            } else {
                mensagem.setText("❌ Erro: código já existe ou tipo inválido.");
            }

        } catch (NumberFormatException e) {
            mensagem.setText("❌ Ano inválido. Use apenas números.");
        }
    }

    private void limparCampos() {
        campoCodigo.setText("");
        campoTitulo.setText("");
        campoAutor.setText("");
        campoAno.setText("");
        comboTipo.setSelectedIndex(0);
    }
}
