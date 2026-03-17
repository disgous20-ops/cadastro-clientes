package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.border.LineBorder;

import dao.DAO;
import model.Cliente;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class JCadastro extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldNome;
	private JTextField textFieldCpfCnpj;
	private JTextField textFieldEmail;
	private JTextField textFieldTelefone;
	private JTextArea textAreaEndereco;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JCadastro frame = new JCadastro(null, null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public JCadastro(Cliente clienteSelecionado, JPrincipal jPrincipal) {
		DAO dao = new DAO();
		setForeground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setForeground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Nome");
		lblNewLabel.setBounds(20, 20, 45, 13);
		contentPane.add(lblNewLabel);
		
		textFieldNome = new JTextField();
		textFieldNome.setBounds(20, 33, 390, 18);
		contentPane.add(textFieldNome);
		textFieldNome.setColumns(10);
		
		textFieldCpfCnpj = new JTextField();
		textFieldCpfCnpj.setColumns(10);
		textFieldCpfCnpj.setBounds(20, 74, 173, 18);
		contentPane.add(textFieldCpfCnpj);
		
		JLabel lblCpfCnpj = new JLabel("CPF CNPJ");
		lblCpfCnpj.setBounds(20, 58, 69, 13);
		contentPane.add(lblCpfCnpj);
		
		textFieldEmail = new JTextField();
		textFieldEmail.setColumns(10);
		textFieldEmail.setBounds(20, 119, 390, 18);
		contentPane.add(textFieldEmail);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(20, 102, 45, 13);
		contentPane.add(lblEmail);
		
		JLabel lblEndereo = new JLabel("Endereço");
		lblEndereo.setBounds(20, 147, 69, 13);
		contentPane.add(lblEndereo);
		
		textFieldTelefone = new JTextField();
		textFieldTelefone.setColumns(10);
		textFieldTelefone.setBounds(195, 74, 215, 18);
		contentPane.add(textFieldTelefone);
		
		JLabel lblTelefone = new JLabel("Telefone");
		lblTelefone.setBounds(197, 58, 69, 13);
		contentPane.add(lblTelefone);
		
		textAreaEndereco = new JTextArea();
		textAreaEndereco.setBorder(new LineBorder(new Color(0, 0, 0)));
		textAreaEndereco.setBounds(20, 165, 390, 37);
		contentPane.add(textAreaEndereco);
		
		JButton btnNewButton = new JButton(clienteSelecionado==null?"Incluir":"Alterar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//String id, String nome, String cpfCnpj, String email, String telefone, String endereco
				Cliente cliente = new Cliente(null, textFieldNome.getText(), textFieldCpfCnpj.getText(),
						textFieldEmail.getText(),textFieldTelefone.getText(), textAreaEndereco.getText());
				if(clienteSelecionado == null) {
					if (!"".equalsIgnoreCase(textFieldNome.getText()) && !"".equalsIgnoreCase(textFieldCpfCnpj.getText())) {
						dao.cadastrarClient(cliente);
						abrirTelaPrincipal (jPrincipal);
					}else {
						JOptionPane.showMessageDialog(null, "Confira os Campos NOME e CPF/CNPJ");
					}
				}else {
					if (!"".equalsIgnoreCase(textFieldNome.getText()) && !"".equalsIgnoreCase(textFieldCpfCnpj.getText())) {
						dao.alterarCliente(clienteSelecionado.getId(),cliente);
						abrirTelaPrincipal (jPrincipal);
					}else {
						JOptionPane.showMessageDialog(null, "Confira os Campos NOME e CPF/CNPJ");
					}
					
				}	
			}
				
		});
		btnNewButton.setBounds(326, 213, 84, 20);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Excluir");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dao.excluirCliente(clienteSelecionado.getId());
				abrirTelaPrincipal (jPrincipal);
			}
		});
		btnNewButton_1.setBackground(new Color(255, 0, 0));
		btnNewButton_1.setForeground(new Color(255, 255, 255));
		btnNewButton_1.setBounds(20, 212, 84, 20);
		btnNewButton_1.setVisible(false);
		contentPane.add(btnNewButton_1);

		if(clienteSelecionado!=null) {
		preencherCampos(clienteSelecionado);
		btnNewButton_1.setVisible(true);
		}
	}
	
	private void preencherCampos(Cliente clienteSelecionado) {
		textFieldNome.setText(clienteSelecionado.getNome());
		textFieldCpfCnpj.setText(clienteSelecionado.getCpfCnpj());
		textFieldEmail.setText(clienteSelecionado.getEmail());
		textFieldTelefone.setText(clienteSelecionado.getTelefone());
		textAreaEndereco.setText(clienteSelecionado.getEndereco());
	}
	
	private void abrirTelaPrincipal (JPrincipal jPrincipal) {
		jPrincipal.dispose();
		dispose();
		jPrincipal = new JPrincipal();
		jPrincipal.setLocationRelativeTo(jPrincipal);
		jPrincipal.setVisible(true);
	}
}
