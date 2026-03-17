package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import controller.Conexao;
import model.Cliente;
import model.Usuario;

public class DAO {
	private static PreparedStatement preparedStatement = null;
	private static ResultSet resultSet = null;

		
	private static String CADASTRAR_CLIENTE = " INSERT INTO CLIENTE "
			+ "(ID, NOME, CPFCNPJ, EMAIL, TELEFONE, ENDERECO) "
			+ "VALUES (NULL, ?, ?, ?, ?, ?) ";
	
	private static String CONSULTAR_CLIENTE = " SELECT * FROM CLIENTE "
			+ "WHERE ID = ?";
	
	private static String ALTERAR_CLIENTE = " UPDATE CLIENTE SET "
			+ "NOME = ?, CPFCNPJ = ?, EMAIL = ?, TELEFONE = ?, ENDERECO = ?"
			+ "WHERE ID = ?";
	
	private static String EXCLUIR_CLIENTE = " DELETE FROM CLIENTE "
			+ "WHERE ID = ?";
	
	private static String LISTAR_CLIENTE = " SELECT * FROM CLIENTE "
			+ "WHERE 1=1";
	
	private static String CONSULTAR_USUARIO = " SELECT USUARIO, SENHA "
			+ "FROM USUARIO" 
	        + "WHERE USUARIO = ?"
	        + "AND SENHA = ?";
	
	public DAO() {
		
	}
	
	public void cadastrarClient(Cliente cliente) {
		Connection connection = Conexao.getInstacia().abrirConexao();		
		
		String query = CADASTRAR_CLIENTE;
		try {
			preparedStatement = connection.prepareStatement(query);
			
			int i = 1;
			
			//NOME, CPFCNPJ, EMAIL, TELEFONE, ENDERECO
		preparedStatement.setString(i++, cliente.getNome());
		preparedStatement.setString(i++, cliente.getCpfCnpj());
		preparedStatement.setString(i++, cliente.getEmail());
		preparedStatement.setString(i++, cliente.getTelefone());
		preparedStatement.setString(i++, cliente.getEndereco());
		
		preparedStatement.execute();
		
		connection.commit();
		
		JOptionPane.showMessageDialog(null, "Cliente incluído com sucesso!");
		
		} catch (SQLException e) {
		 e.printStackTrace();
		
		} finally {
			fecharConexao();
		}
		
	}
	
	public Cliente consultarCliente(String id) throws Exception {
		Connection connection = Conexao.getInstacia().abrirConexao();
		Cliente cliente = null;
		String query = CONSULTAR_CLIENTE;
		try {
			preparedStatement = connection.prepareStatement(query);
			
			int i = 1;
			
			//NOME, CPFCNPJ, EMAIL, TELEFONE, ENDERECO
		preparedStatement.setString(i++, id);
		
		resultSet = preparedStatement.executeQuery();
		
		while (resultSet.next()) {
			//String id, String nome, String cpfCnpj, String email, String telefone, String endereco
			cliente = new Cliente(resultSet.getString("ID"),
					resultSet.getString("nome"),
					resultSet.getString("cpfCnpj"),
					resultSet.getString("Email"),
					resultSet.getString("Telefone"),
					resultSet.getString("Endereco"));
			
		}	
		
		
		} catch (SQLException e) {
		 e.printStackTrace();
		
		} finally {
			fecharConexao();
		}
		if (cliente == null) {
			JOptionPane.showMessageDialog(null, "Cliente não encontrado","", JOptionPane.WARNING_MESSAGE);
			throw new Exception("Cliente não encontrado");
			
	}
		return cliente;
		
	}

	public void alterarCliente(String id, Cliente cliente) {
		Connection connection = Conexao.getInstacia().abrirConexao();
		
		String query = ALTERAR_CLIENTE;
		try {
			preparedStatement = connection.prepareStatement(query);
			
			int i = 1;
			
		preparedStatement.setString(i++, cliente.getNome());
		preparedStatement.setString(i++, cliente.getCpfCnpj());
		preparedStatement.setString(i++, cliente.getEmail());
		preparedStatement.setString(i++, cliente.getTelefone());
		preparedStatement.setString(i++, cliente.getEndereco());
		preparedStatement.setString(i++, id);
		
		preparedStatement.execute();		
		connection.commit();
		
		JOptionPane.showMessageDialog(null, "Cliente alterado com sucesso");
		
		} catch (SQLException e) {
		 e.printStackTrace();
		
		} finally {
			fecharConexao();
		}
		
	}
	
	public void excluirCliente(String id) {
		Connection connection = Conexao.getInstacia().abrirConexao();
		
		String query = EXCLUIR_CLIENTE;
		try {
			preparedStatement = connection.prepareStatement(query);
			
			int i = 1;
		
		preparedStatement.setString(i++, id);
		
		preparedStatement.execute();		
		connection.commit();
		
		JOptionPane.showMessageDialog(null, "Cliente excluido com sucesso");
		
		} catch (SQLException e) {
		 e.printStackTrace();
		
		} finally {
			fecharConexao();
		}
		
	}
	
	public ArrayList<Cliente> listarClientes() throws Exception {
		Connection connection = Conexao.getInstacia().abrirConexao();
		ArrayList<Cliente> clientes = new ArrayList<Cliente>();
		String query = LISTAR_CLIENTE;
		try {
			preparedStatement = connection.prepareStatement(query);
								
		resultSet = preparedStatement.executeQuery();
		
		while (resultSet.next()) {
			//String id, String nome, String cpfCnpj, String email, String telefone, String endereco
			clientes.add(new Cliente(resultSet.getString("ID"),
					resultSet.getString("nome"),
					resultSet.getString("cpfCnpj"),
					resultSet.getString("Email"),
					resultSet.getString("Telefone"),
					resultSet.getString("Endereco")));
			
		}	
		
		
		} catch (SQLException e) {
		 e.printStackTrace();
		
		} finally {
			fecharConexao();
		}
		if (clientes.size() < 0) {
			JOptionPane.showMessageDialog(null, "Não há clientes cadastrados","", JOptionPane.WARNING_MESSAGE);
			throw new Exception("Não há clientes cadastrados");
			
	}
		return clientes;
		
	}
	
	public Usuario consultarUsuario(String nomeUsuario, String senhaCriptografada) throws Exception {
		Connection connection = Conexao.getInstacia().abrirConexao();
		Usuario usuario = null;
		String query = CONSULTAR_USUARIO;
		try {
			preparedStatement = connection.prepareStatement(query);
			
			int i = 1;

		preparedStatement.setString(i++, nomeUsuario);
		preparedStatement.setString(i++, senhaCriptografada);
		
		resultSet = preparedStatement.executeQuery();
		
		while (resultSet.next()) {
			
			//String id, String nome, String cpfCnpj, String email, String telefone, String endereco
			usuario = new Usuario( resultSet.getInt("id"),
									resultSet.getString("USUARIO"),
									resultSet.getString("SENHA"));
					
					
		}	
		
		
		} catch (SQLException e) {
		 e.printStackTrace();
		
		} finally {
			fecharConexao();
		}
		if (usuario == null) {
			JOptionPane.showMessageDialog(null, "Usuario não encontrado","", JOptionPane.WARNING_MESSAGE);
			throw new Exception("Usuario não encontrado");
			
	}
		return usuario;
		
	}
	
	
	private void fecharConexao() {
		
			try {
				if (resultSet!=null) {
				resultSet.close();
				}
				if (preparedStatement!=null) {
					preparedStatement.close();
				}
			    Conexao.getInstacia().fecharConexao();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	        

