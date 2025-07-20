package view;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class RegistroMultaView extends JFrame{
	private final JTextField campoObra;
	private final JTextField campoNome;
	private final JTextField campoValorMulta;
	private final JComboBox<String> comboTipoPagamento;
	private final JLabel mensagem;
	
	public RegistroMultaView() {
		setTitle("Cadastro de Leitores");
		setSize(1400, 900);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());
        
        JPanel painelRMV = new JPanel();
        painelRMV.setBounds(90,155,1400,641);
        painelRMV.setBorder(BorderFactory.createEmptyBorder(20, 40, 10, 40));
        painelRMV.setLayout(null);
        
        JLabel titulo = new JLabel("Registro de Multas");
        titulo.setFont(new Font("Arial", Font.PLAIN, 20));
        titulo.setBounds(604, 92, 191, 61);
        painelRMV.add(titulo);
        
        JLabel obras = new JLabel();
        campoObra = new JTextField();
        painelRMV.add(campoObra);
        
        JLabel nome = new JLabel("Nome");
        nome.setFont(new Font("Verdana", Font.PLAIN, 14));
        nome.setBounds(554, 192, 73, 48);
        painelRMV.add(nome);
        campoNome = new JTextField();
        campoNome.setBounds(637, 199, 271, 40);
        painelRMV.add(campoNome);
        
        JLabel valorMulta = new JLabel("Valor");
        valorMulta.setFont(new Font("Verdana", Font.PLAIN, 14));
        valorMulta.setBounds(554, 241, 73, 48);
        painelRMV.add(valorMulta);
        campoValorMulta = new JTextField();
        campoValorMulta.setBounds(637, 251, 271, 33);
        painelRMV.add(campoValorMulta);
        
        JLabel tipoPagamento = new JLabel("Metodo de pagamento");
        tipoPagamento.setFont(new Font("Verdana", Font.PLAIN, 14));
        tipoPagamento.setBounds(444, 306, 183, 23);
        painelRMV.add(tipoPagamento);
        comboTipoPagamento = new JComboBox<>(new String[] {"", "Pix", "Cartão", "Dinheiro"});
        comboTipoPagamento.setBounds(637, 303, 271, 33);
        painelRMV.add(comboTipoPagamento);
        
        JButton btnRegistrar = new JButton("Registrar Multa");
        btnRegistrar.setFont(new Font("Verdana", Font.PLAIN, 16));
        btnRegistrar.setBounds(604, 403, 174, 20);
        painelRMV.add(btnRegistrar);
        
        mensagem = new JLabel("", SwingConstants.CENTER);
        
        setVisible(true);
        
	}
	
	private void RegistroMultaView() {
		String obras = campoObra.getText().trim();
		String nome = campoNome.getText().trim();
		String valorMulta = campoValorMulta.getText().trim();
		String tipo = (String) comboTipoPagamento.getSelectedItem();
		
		if(nome.isEmpty() || valorMulta.isEmpty()){
			mensagem.setText("⚠ Preencha todos os campos.");
			return;
		}
		
		if(tipo == " ") {
			mensagem.setText(" ⚠ Escolha a forma de pagamento");
		}
		
	}
}

