package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import factory.FabricaConexao;
import model.EnderecoBean;

public class EnderecoDao {
	
	private FabricaConexao fabricaConexao;
    private Connection conexao = null;
	
    public EnderecoDao() {
    	this.fabricaConexao = new FabricaConexao();
    }
    
    public EnderecoBean getEnderecoPorID(int idEndereco) {
    	
		try {
	    	this.conexao = this.fabricaConexao.getConnection();
	    	
	    	String sql = "SELECT * FROM enderecos WHERE id_endereco = ?";
	    	PreparedStatement ps = this.conexao.prepareStatement(sql);
	    	ps.setInt(0, idEndereco);
	    	
	    	ResultSet resultado = ps.executeQuery();
	    	if(resultado.next()) {
	    		EnderecoBean endereco = new EnderecoBean();
	    		endereco.setId(resultado.getInt("id_endereco"));
	    		endereco.setCep(resultado.getString("cep"));
	    		endereco.setEstado(resultado.getString("estado"));
	    		endereco.setCidade(resultado.getString("cidade"));
	    		endereco.setLogradouro(resultado.getString("logradouro"));
	    		endereco.setBairro(resultado.getString("bairro"));
	    		endereco.setComplemento(resultado.getString("complemento"));
	    		endereco.setNumero_propriedade(resultado.getInt("numero_propriedade"));
	    		return endereco;
	    	}
	    	
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.fabricaConexao.closeConnection();
		}
		
		return null;
    	
    }
    
	public int cadastrarEndereco(EnderecoBean endereco) {
		int idEndereco = 0;
		
		try {
			conexao = this.fabricaConexao.getConnection();	//possui os métodos para criar um Statement
			
			String sql = "INSERT INTO enderecos (cep, estado, cidade, logradouro, bairro, complemento, numero_propriedade) VALUES (?,?,?,?,?,?,?)";
			PreparedStatement ps = this.conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);	//controla e executa uma instrução SQL. Retornará a ID gerada.
			
			ps.setString(1, endereco.getCep());
			ps.setString(2, endereco.getEstado());
			ps.setString(3, endereco.getCidade());
			ps.setString(4, endereco.getLogradouro());
			ps.setString(5, endereco.getBairro());
			ps.setString(6, endereco.getComplemento());
			ps.setInt(7, endereco.getNumero_propriedade());
			
			ps.executeUpdate();	//Executa o comando de insert
			final ResultSet rs = ps.getGeneratedKeys();	//contém as chaves geradas
			if(rs.next()) {	//Se tiver sido gerada uma nova chave
				idEndereco = (int) rs.getLong(1);	//Pega o id da linha gerada
			}
			
		} catch (SQLException erro) {
			erro.printStackTrace();	//Printa o erro no console
		} finally {
			this.fabricaConexao.closeConnection();
		}
		
		return idEndereco;
	}
	
	public boolean deletarEndereco(int idEndereco) {
		boolean instrucaoExecutada = false;
		
		try {
			conexao = this.fabricaConexao.getConnection();
			
			String sql = "DELETE FROM enderecos WHERE id_endereco = ?";
			PreparedStatement ps = this.conexao.prepareStatement(sql);
			ps.setInt(1, idEndereco);
			
			ps.executeUpdate();
			
			instrucaoExecutada = true;
		} catch (SQLException erro) {
			erro.printStackTrace();
		} finally {
			this.fabricaConexao.closeConnection();
		}
		
		return instrucaoExecutada;
	}
	
	public void editarEndereco(EnderecoBean endereco) throws SQLException {
		//A classe que chamar esse método terá que fazer um TryCatch para ele, devido ao uso de "throws SQLException".

			this.conexao = this.fabricaConexao.getConnection();
			
			String sql = "UPDATE enderecos SET cep = ?, estado = ?, cidade = ?, logradouro = ?, bairro = ?, complemento = ?, numero_propriedade = ? WHERE id_endereco = ?";
			PreparedStatement ps = this.conexao.prepareStatement(sql);
			
			ps.setString(1, endereco.getCep());
			ps.setString(2, endereco.getEstado());
			ps.setString(3, endereco.getCidade());
			ps.setString(4, endereco.getLogradouro());
			ps.setString(5, endereco.getBairro());
			ps.setString(6, endereco.getComplemento());
			ps.setInt(7, endereco.getNumero_propriedade());
			ps.setInt(8, endereco.getId());
			
			ps.executeUpdate();
			
			this.fabricaConexao.closeConnection();
	}
	
}
