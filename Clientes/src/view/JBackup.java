package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.Backup;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class JBackup extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Backup backup;
	ArrayList<String> arquivosBackup;
	String[] nomesbackup;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JBackup frame = new JBackup();
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
	public JBackup() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 718, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		JButton btnNewButton_1 = new JButton("Recuperar Backup");
		btnNewButton_1.setBounds(133, 233, 141, 20);
		contentPane.add(btnNewButton_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 684, 213);
		contentPane.add(scrollPane);
		
		backup = new Backup();
        arquivosBackup = new ArrayList<>();
        arquivosBackup = backup.listarArquivos();
        nomesbackup = arquivosBackup.toArray(new String[arquivosBackup.size()]);

        JList list = new JList(nomesbackup);
        list.setListData(nomesbackup);

        scrollPane.setViewportView(list);
        
        JButton btnNewButton = new JButton("Gerar Backup");
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if(JOptionPane.showConfirmDialog(btnNewButton,"Deseja gerar o backup?") ==JOptionPane.YES_NO_OPTION) {
        			backup.gerarBackup();
        			arquivosBackup = backup.listarArquivos();
        			nomesbackup = arquivosBackup.toArray(new String[arquivosBackup.size()]);
        			list.setListData(nomesbackup);
        			revalidate();
        			repaint();
        			
        		}
        	}
        });
        btnNewButton.setBounds(8, 233, 115, 20);
        contentPane.add(btnNewButton);
    }
}
