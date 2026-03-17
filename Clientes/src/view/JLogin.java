package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controller.Criptografia;

public class JLogin extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldusuario;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JLogin frame = new JLogin();
					frame.setLocationRelativeTo(null);
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
	public JLogin() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 556, 334);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(49, 62, 64));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(135, 36, 285, 224);
		panel.setBackground(new Color(204,207,208));
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Password:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblNewLabel.setBounds(21, 100, 69, 13);
		panel.add(lblNewLabel);
		
		textFieldusuario = new JTextField();
		textFieldusuario.setBounds(21, 71, 176, 19);
		panel.add(textFieldusuario);
		textFieldusuario.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("User:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblNewLabel_1.setBounds(21, 57, 69, 12);
		panel.add(lblNewLabel_1);
		
		JButton btnNewButton = new JButton("Entrar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Criptografia criptografia = new Criptografia(passwordField.getText(), Criptografia.MD5);
				System.out.println(criptografia.criptografar());
				if (textFieldusuario.getText()!=null && 
						!textFieldusuario.getText().isEmpty() && 
						passwordField.getText()!=null &&
						!passwordField.getText().isEmpty()){
					if(criptografia.criptografar().equals("E10ADC3949BA59ABBE56E057F20F883E")) {
						JOptionPane.showMessageDialog(btnNewButton, "Informações válidas");
					dispose();
					JPrincipal jPrincipal = new JPrincipal();
					jPrincipal.setLocationRelativeTo(jPrincipal);
					jPrincipal.setVisible(true);		
					}
				}else {
					JOptionPane.showMessageDialog(btnNewButton, "Verifique as informações","Aviso", JOptionPane.WARNING_MESSAGE);
					
				}
			}
		});
		btnNewButton.setBackground(new Color(42,62,64));	
		btnNewButton.setForeground(Color .WHITE);
		btnNewButton.setBounds(92, 165, 104, 20);
		panel.add(btnNewButton);
		
		JLabel lblNewLabel_2 = new JLabel("Welcome");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_2.setBounds(113, 10, 63, 12);
		panel.add(lblNewLabel_2);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(21, 113, 176, 18);
		panel.add(passwordField);

	}
}
