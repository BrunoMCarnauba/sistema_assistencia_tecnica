package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import factory.FabricaConexao;
import model.ClienteBean;
import model.EnderecoBean;

public class ClienteDao {
	private FabricaConexao fabricaConexao;
	private Connection conexao = null;
	
	public ClienteDao() {
		this.fabricaConexao = new FabricaConexao();
	}
	
	public boolean cadastrarCliente(ClienteBean cliente) {
		
		EnderecoDao enderecoDao = new EnderecoDao();
		int idEndereco = enderecoDao.cadastrarEndereco(cliente.getEndereco());
		
		if(idEndereco != 0) {
			try {
				this.conexao = this.fabricaConexao.getConnection();	//possui os métodos para criar um Statement
				
				String sql = "INSERT INTO clientes (nome, cpf, email, telefone_celular, telefone_fixo, endereco_id) VALUES (?,?,?,?,?,?)";
				PreparedStatement ps = this.conexao.prepareStatement(sql);	//controla e executa uma instrução SQL
				
				ps.setString(1, cliente.getNome());
				ps.setString(2, cliente.getCpf());
				ps.setString(3, cliente.getEmail());
				ps.setString(4, cliente.getTelefone_celular());
				ps.setString(5, cliente.getTelefone_fixo());
				ps.setInt(6, idEndereco);
				
				ps.executeUpdate();	//Executa a instrução sql
				
				return true;
			} catch (SQLException e) {
				e.printStackTrace();	//Printa a exceção
				enderecoDao.deletarEndereco(idEndereco);
			} finally {
				this.fabricaConexao.closeConnection();	//Fecha a conexão. - Por estar no finally, esse comando é executado mesmo após o return do Try.
			}
		}
		
			return false;
	}
	
	public ClienteBean getClientePorID(int id_cliente) throws SQLException {
		//A classe que chamar esse método terá que fazer um TryCatch para ele devido ao uso de "throws SQLException".		
		
		this.conexao = this.fabricaConexao.getConnection();
		String sql = "SELECT * FROM clientes, enderecos WHERE id_cliente = ? and endereco_id = id_endereco;";
		PreparedStatement ps = this.conexao.prepareStatement(sql);
		
		ps.setInt(1, id_cliente);
		
		ResultSet resultado = ps.executeQuery();
		
		ClienteBean cliente = new ClienteBean();
		if(resultado.next()) {	//Se houver resultado
			cliente.setId(resultado.getInt("id_cliente"));
			cliente.setNome(resultado.getString("nome"));
			cliente.setCpf(resultado.getString("cpf"));
			cliente.setEmail(resultado.getString("email"));
			cliente.setTelefone_celular(resultado.getString("telefone_celular"));
			cliente.setTelefone_fixo(resultado.getString("telefone_fixo"));
			cliente.setData_cadastro(resultado.getDate("data_cadastro"));
			
			EnderecoBean endereco = new EnderecoBean();
			endereco.setId(resultado.getInt("id_endereco"));
			endereco.setCep(resultado.getString("cep"));
			endereco.setEstado(resultado.getString("estado"));
			endereco.setCidade(resultado.getString("cidade"));
			endereco.setLogradouro(resultado.getString("logradouro"));
			endereco.setBairro(resultado.getString("bairro"));
			endereco.setComplemento(resultado.getString("complemento"));
			endereco.setNumero_propriedade(resultado.getInt("numero_propriedade"));
			
			cliente.setEndereco(endereco);
		}
		
		this.fabricaConexao.closeConnection();
		
		return cliente;
	}
	
	public ArrayList<ClienteBean> listarClientes(){

		try {
			this.conexao = this.fabricaConexao.getConnection();
			String sql = "SELECT * FROM clientes as c,enderecos as e WHERE c.endereco_id = e.id_endereco;";
			
			PreparedStatement ps = this.conexao.prepareStatement(sql);
			ResultSet resultados = ps.executeQuery();
			
			ArrayList<ClienteBean> clientes = new ArrayList<>();
			while(resultados.next()) {	//Enquanto houver resultado
				ClienteBean cliente = new ClienteBean();
				cliente.setId(resultados.getInt("id_cliente"));
				cliente.setNome(resultados.getString("nome"));
				cliente.setCpf(resultados.getString("cpf"));
				cliente.setEmail(resultados.getString("email"));
				cliente.setTelefone_celular(resultados.getString("telefone_celular"));
				cliente.setTelefone_fixo(resultados.getString("telefone_fixo"));
				cliente.setData_cadastro(resultados.getDate("data_cadastro"));
				
				EnderecoBean endereco = new EnderecoBean();
				endereco.setId(resultados.getInt("id_endereco"));
				endereco.setCep(resultados.getString("cep"));
				endereco.setEstado(resultados.getString("estado"));
				endereco.setCidade(resultados.getString("cidade"));
				endereco.setLogradouro(resultados.getString("logradouro"));
				endereco.setBairro(resultados.getString("bairro"));
				endereco.setComplemento(resultados.getString("complemento"));
				endereco.setNumero_propriedade(resultados.getInt("numero_propriedade"));
				
				cliente.setEndereco(endereco);
				
				clientes.add(cliente);
			}
			
			return clientes;
		} catch (SQLException excecao) {
			excecao.printStackTrace();
		} finally {
			this.fabricaConexao.closeConnection();	//Fecha a conexão. (Isso é rodado mesmo após o return do try).
		}
		
		return null;
	}
	
	public boolean deletarCliente(ClienteBean cliente) {
		
		try {
			this.conexao = this.fabricaConexao.getConnection();
			
			String sql = "DELETE FROM clientes WHERE id_cliente = ?";
			PreparedStatement ps = this.conexao.prepareStatement(sql);
			
			ps.setInt(1, cliente.getId());
			
			ps.executeUpdate();
			return true;	//Se conseguiu deletar
		} catch (SQLException excecao) {
			excecao.printStackTrace();	//Printa a exceção no console
		} finally {
			this.fabricaConexao.closeConnection();
		}
		
		return false;	//Se não conseguiu deletar
	}
	
	public boolean editarCliente(ClienteBean cliente) {
		
		this.conexao = this.fabricaConexao.getConnection();
		EnderecoDao enderecoDao = new EnderecoDao();
		
		try {
			enderecoDao.editarEndereco(cliente.getEndereco()); //Como o método editarEndereco possui throws SQLException, se houver um erro lá, ele cai no catch desse método editarCliente daqui.
			
			String sql = "UPDATE clientes SET nome = ?, cpf = ?, email = ?, telefone_celular = ?, telefone_fixo = ? WHERE id_cliente = ?";
			
			PreparedStatement ps = this.conexao.prepareStatement(sql);
			
			ps.setString(1, cliente.getNome());
			ps.setString(2, cliente.getCpf());
			ps.setString(3, cliente.getEmail());
			ps.setString(4, cliente.getTelefone_celular());
			ps.setString(5, cliente.getTelefone_fixo());
			ps.setInt(6, cliente.getId());
			
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.fabricaConexao.closeConnection();
		}
		
		return false;
	}
	
	
}
