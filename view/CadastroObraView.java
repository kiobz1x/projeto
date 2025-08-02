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
    	setResizable(false);
        setTitle("Cadastro de Obra");
        setSize(615, 485);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // Fecha só esta janela
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());

        JPanel painelCampos = new JPanel();
        painelCampos.setBounds(90,155,200,50);
        painelCampos.setBorder(BorderFactory.createEmptyBorder(20, 40, 10, 40));
        painelCampos.setLayout(null);
        
        JLabel c = new JLabel("Cadastro de Obras");
        c.setFont(new Font("Arial", Font.PLAIN, 20));
        c.setBounds(209, 45, 183, 24);
        painelCampos.add(c);
        
        JLabel codigo = new JLabel("Código:");
        codigo.setFont(new Font("Verdana", Font.PLAIN, 14));
        codigo.setBounds(134, 135, 81, 24);
        painelCampos.add(codigo);
        campoCodigo = new JTextField();
        campoCodigo.setFont(new Font("Verdana", Font.PLAIN, 14));
        campoCodigo.setBounds(316, 130, 241, 34);
        painelCampos.add(campoCodigo);
        campoCodigo.setColumns(10);
        
        JLabel titulo = new JLabel("Título:");
        titulo.setFont(new Font("Verdana", Font.PLAIN, 14));
        titulo.setBounds(134, 179, 81, 24);
        painelCampos.add(titulo);
        campoTitulo = new JTextField();
        campoTitulo.setFont(new Font("Verdana", Font.PLAIN, 14));
        campoTitulo.setColumns(10);
        campoTitulo.setBounds(315, 174, 242, 34);
        painelCampos.add(campoTitulo);
        
        JLabel autor = new JLabel("Autor:");
        autor.setFont(new Font("Verdana", Font.PLAIN, 14));
        autor.setBounds(134, 226, 81, 24);
        painelCampos.add(autor);
        campoAutor = new JTextField();
        campoAutor.setFont(new Font("Verdana", Font.PLAIN, 14));
        campoAutor.setColumns(10);
        campoAutor.setBounds(315, 221, 242, 34);
        painelCampos.add(campoAutor);
        
        JLabel ano = new JLabel("Ano de Publicação:");
        ano.setFont(new Font("Verdana", Font.PLAIN, 14));
        ano.setBounds(134, 271, 160, 24);
        painelCampos.add(ano);
        campoAno = new JTextField();
        campoAno.setFont(new Font("Verdana", Font.PLAIN, 14));
        campoAno.setColumns(10);
        campoAno.setBounds(316, 266, 241, 34);
        painelCampos.add(campoAno);
        
        JLabel tipo = new JLabel("Tipo de Obra:");
        tipo.setFont(new Font("Verdana", Font.PLAIN, 14));
        tipo.setBounds(134, 316, 136, 24);
        painelCampos.add(tipo);
        comboTipo = new JComboBox<>(new String[] {" ", "Livro", "Revista", "Artigo"});
        comboTipo.setFont(new Font("Verdana", Font.PLAIN, 14));
        comboTipo.setBounds(316, 310, 241, 34);
        painelCampos.add(comboTipo);
        
        mensagem = new JLabel("", SwingConstants.CENTER);

        getContentPane().add(painelCampos, BorderLayout.CENTER);
        
        JButton botaoCadastrar = new JButton("Cadastrar");
        botaoCadastrar.setFont(new Font("Verdana", Font.PLAIN, 16));
        botaoCadastrar.setBounds(134, 387, 337, 34);
        painelCampos.add(botaoCadastrar);
        botaoCadastrar.addActionListener(e -> cadastrarObra());
        getContentPane().add(mensagem, BorderLayout.NORTH);

        setVisible(true);
    }

    private void cadastrarObra() {
        String codigo = campoCodigo.getText().trim();
        String titulo = campoTitulo.getText().trim();
        String autor = campoAutor.getText().trim();
        String anoTexto = campoAno.getText().trim();
        String tipo = (String) comboTipo.getSelectedItem();

        if (codigo.isEmpty() || titulo.isEmpty() || autor.isEmpty() || anoTexto.isEmpty()) {
            mensagem.setText("Preencha todos os campos.");
            return;
        }

        try {
            int ano = Integer.parseInt(anoTexto);
            int anoAtual = java.time.Year.now().getValue();

            if (ano > anoAtual) {
                mensagem.setText("O ano de publicação não pode ser maior que o ano atual.");
                return;
            }

            ObraController controller = new ObraController();
            boolean sucesso = controller.adicionarObra(codigo, titulo, autor, ano, tipo);

            if (sucesso) {
                mensagem.setText("Obra cadastrada com sucesso!");
                limparCampos();
            } else {
                mensagem.setText("Erro: código já existe ou tipo inválido.");
            }

        } catch (NumberFormatException e) {
            mensagem.setText("Ano inválido. Use apenas números.");
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
