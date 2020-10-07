package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import factory.FabricaConexao;
import model.CargoBean;
import model.EnderecoBean;
import model.UsuarioBean;

public class UsuarioDao {
	
	private FabricaConexao fabricaConexao;
    private Connection conexao = null;
	
    public UsuarioDao() {
    	this.fabricaConexao = new FabricaConexao();
    }
    
    public UsuarioBean autenticarUsuario(UsuarioBean usuario) {
    	
    	try {
        	this.conexao = this.fabricaConexao.getConnection();	//possui os métodos para criar um Statement
        	String sql = "SELECT u.id_usuario, u.nome as nome_usuario, u.cpf, u.data_nascimento, u.telefone_celular, u.telefone_fixo, u.email, u.senha, u.cargo_id, u.endereco_id, u.data_cadastro, c.id_cargo, c.nome as nome_cargo, c.descricao, c.salario, e.id_endereco, e.cep, e.estado, e.cidade, e.logradouro, e.bairro, e.complemento, e.numero_propriedade FROM usuarios as u, cargos as c, enderecos as e WHERE u.email = ? and u.senha = ? and u.cargo_id = c.id_cargo and u.endereco_id = e.id_endereco";
			PreparedStatement ps = this.conexao.prepareStatement(sql);	//controla e executa uma instrução SQL
			
			ps.setString(1, usuario.getEmail());	//Prenche o "u.email" da instrução SQL
			ps.setString(2, usuario.getSenha());
			
			ResultSet resultado = ps.executeQuery();	//Executa a instrução sql de consulta e guarda os resultados na variável do tipo ResultSet
			
			if(resultado.next()) {	//Verifica se existe resultado ou seja, se existe usuário com o email e senha informado
				usuario.setId(resultado.getInt("id_usuario"));
				usuario.setNome(resultado.getString("nome_usuario"));
				usuario.setCpf(resultado.getString("cpf"));
				usuario.setData_nascimento(resultado.getDate("data_nascimento"));
				usuario.setTelefone_celular(resultado.getString("telefone_celular"));
				usuario.setTelefone_fixo(resultado.getString("telefone_fixo"));
				usuario.setEmail(resultado.getString("email"));
				//usuario.setSenha(resultado.getString("senha"));
				usuario.setData_cadastro(resultado.getDate("data_cadastro"));
				
				CargoBean cargo = new CargoBean();
				cargo.setId(resultado.getInt("id_cargo"));
				cargo.setNome(resultado.getString("nome_cargo"));
				cargo.setDescricao(resultado.getString("descricao"));
				cargo.setSalario(resultado.getFloat("salario"));
				usuario.setCargo(cargo);
				
				EnderecoBean endereco = new EnderecoBean();
	    		endereco.setId(resultado.getInt("id_endereco"));
	    		endereco.setCep(resultado.getString("cep"));
	    		endereco.setEstado(resultado.getString("estado"));
	    		endereco.setCidade(resultado.getString("cidade"));
	    		endereco.setLogradouro(resultado.getString("logradouro"));
	    		endereco.setBairro(resultado.getString("bairro"));
	    		endereco.setComplemento(resultado.getString("complemento"));
	    		endereco.setNumero_propriedade(resultado.getInt("numero_propriedade"));
				usuario.setEndereco(endereco);
				
				return usuario;
			}
			
		} catch (SQLException excecao) {
			excecao.printStackTrace();	//Printa a exceção no console se tiver ocorrido
		} finally {
			this.fabricaConexao.closeConnection();	//Fecha a conexão. - Por estar no finally, esse comando é executado mesmo após o return do Try.
		}
    	return null;	//Retorna null se o usuário não existir ou se tiver ocorrido erro durante a consulta
    }
    
    public boolean cadastrarUsuario(UsuarioBean usuario) {
    	
    	this.conexao = this.fabricaConexao.getConnection();
    	
    	EnderecoDao enderecoDao = new EnderecoDao();
    	int idEndereco = enderecoDao.cadastrarEndereco(usuario.getEndereco());
    	
    	if(idEndereco != 0) {
	    	try {
	        	String sql = "INSERT INTO usuarios (nome, cpf, data_nascimento, telefone_celular, telefone_fixo, email, senha, cargo_id, endereco_id) VALUES (?,?,?,?,?,?,?,?,?)";
				PreparedStatement ps = this.conexao.prepareStatement(sql);
				
				ps.setString(1, usuario.getNome());
				ps.setString(2, usuario.getCpf());
				ps.setDate(3, new java.sql.Date(usuario.getData_nascimento().getTime()));
				ps.setString(4, usuario.getTelefone_celular());
				ps.setString(5, usuario.getTelefone_fixo());
				ps.setString(6, usuario.getEmail());
				ps.setString(7, usuario.getSenha());
				ps.setInt(8, usuario.getCargo().getId());
				ps.setInt(9, idEndereco);
				
				ps.executeUpdate();
				
				return true;	//Se tiver cadastrado com sucesso - (Obs.: Ainda assim executa o trecho finally{})
			} catch (SQLException erro) {
				erro.printStackTrace();
				enderecoDao.deletarEndereco(idEndereco);
			} finally {
				this.fabricaConexao.closeConnection();
			}
    	}
    	
    		return false;	//Se o cadastro não tiver sido realizado com sucesso
    }
    
    public UsuarioBean getUsuarioPorID(int id_usuario) throws SQLException {
	//A classe que chamar esse método terá que fazer um TryCatch para ele, devido ao uso de "throws SQLException".
    	
        	this.conexao = this.fabricaConexao.getConnection();
        	
        	String sql = "SELECT u.id_usuario, u.nome as nome_usuario, u.cpf, u.data_nascimento, u.telefone_celular, u.telefone_fixo, u.email, u.senha, u.cargo_id, u.endereco_id, u.data_cadastro, c.id_cargo, c.nome as nome_cargo, c.descricao, c.salario, e.id_endereco, e.cep, e.estado, e.cidade, e.logradouro, e.bairro, e.complemento, e.numero_propriedade FROM usuarios as u, cargos as c, enderecos as e WHERE id_usuario = ? and u.cargo_id = c.id_cargo and u.endereco_id = e.id_endereco;";
			PreparedStatement ps = this.conexao.prepareStatement(sql);
			
			ps.setInt(1, id_usuario);
			
			ResultSet resultado = ps.executeQuery();
			
			UsuarioBean usuario = new UsuarioBean();
			if(resultado.next()){	//Se houver resultado
				usuario.setId(resultado.getInt("id_usuario"));
				usuario.setNome(resultado.getString("nome_usuario"));
				usuario.setCpf(resultado.getString("cpf"));
				usuario.setData_nascimento(resultado.getDate("data_nascimento"));
				usuario.setTelefone_celular(resultado.getString("telefone_celular"));
				usuario.setTelefone_fixo(resultado.getString("telefone_fixo"));
				usuario.setEmail(resultado.getString("email"));
				usuario.setSenha(resultado.getString("senha"));
				usuario.setData_cadastro(resultado.getDate("data_cadastro"));
				
				CargoBean cargo = new CargoBean();
				cargo.setId(resultado.getInt("id_cargo"));
				cargo.setNome(resultado.getString("nome_cargo"));
				cargo.setDescricao(resultado.getString("descricao"));
				cargo.setSalario(resultado.getFloat("salario"));
				usuario.setCargo(cargo);
				
				EnderecoBean endereco = new EnderecoBean();
	    		endereco.setId(resultado.getInt("id_endereco"));
	    		endereco.setCep(resultado.getString("cep"));
	    		endereco.setEstado(resultado.getString("estado"));
	    		endereco.setCidade(resultado.getString("cidade"));
	    		endereco.setLogradouro(resultado.getString("logradouro"));
	    		endereco.setBairro(resultado.getString("bairro"));
	    		endereco.setComplemento(resultado.getString("complemento"));
	    		endereco.setNumero_propriedade(resultado.getInt("numero_propriedade"));
				usuario.setEndereco(endereco);
			}

			this.fabricaConexao.closeConnection();
			
			return usuario;
    }
    
    public ArrayList<UsuarioBean> listarUsuarios() {

    	try {
        	this.conexao = this.fabricaConexao.getConnection();
        	
        	String sql = "SELECT u.id_usuario, u.nome as nome_usuario, u.cpf, u.data_nascimento, u.telefone_celular, u.telefone_fixo, u.email, u.senha, u.cargo_id, u.endereco_id, u.data_cadastro, c.id_cargo, c.nome as nome_cargo, c.descricao, c.salario, e.id_endereco, e.cep, e.estado, e.cidade, e.logradouro, e.bairro, e.complemento, e.numero_propriedade FROM usuarios as u, cargos as c, enderecos as e WHERE u.cargo_id = c.id_cargo and u.endereco_id = e.id_endereco;";
			PreparedStatement ps = this.conexao.prepareStatement(sql);
			
			ResultSet resultados = ps.executeQuery();
			ArrayList<UsuarioBean> usuarios = new ArrayList<UsuarioBean>();
			while(resultados.next()){
				UsuarioBean usuario = new UsuarioBean();
				usuario.setId(resultados.getInt("id_usuario"));
				usuario.setNome(resultados.getString("nome_usuario"));
				usuario.setCpf(resultados.getString("cpf"));
				usuario.setData_nascimento(resultados.getDate("data_nascimento"));
				usuario.setTelefone_celular(resultados.getString("telefone_celular"));
				usuario.setTelefone_fixo(resultados.getString("telefone_fixo"));
				usuario.setEmail(resultados.getString("email"));
				usuario.setSenha(resultados.getString("senha"));
				usuario.setData_cadastro(resultados.getDate("data_cadastro"));
				
				CargoBean cargo = new CargoBean();
				cargo.setId(resultados.getInt("id_cargo"));
				cargo.setNome(resultados.getString("nome_cargo"));
				cargo.setDescricao(resultados.getString("descricao"));
				cargo.setSalario(resultados.getFloat("salario"));
				usuario.setCargo(cargo);
				
				EnderecoBean endereco = new EnderecoBean();
	    		endereco.setId(resultados.getInt("id_endereco"));
	    		endereco.setCep(resultados.getString("cep"));
	    		endereco.setEstado(resultados.getString("estado"));
	    		endereco.setCidade(resultados.getString("cidade"));
	    		endereco.setLogradouro(resultados.getString("logradouro"));
	    		endereco.setBairro(resultados.getString("bairro"));
	    		endereco.setComplemento(resultados.getString("complemento"));
	    		endereco.setNumero_propriedade(resultados.getInt("numero_propriedade"));
				usuario.setEndereco(endereco);
				
				usuarios.add(usuario);
			}
			
			return usuarios;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.fabricaConexao.closeConnection();
		}
    	
    	return null;
    }
    
    public boolean deletarUsuario(UsuarioBean usuario) {
    	
    	try {
        	this.conexao = this.fabricaConexao.getConnection();
        	
        	String sql = "DELETE FROM usuarios WHERE id_usuario = ?";
			PreparedStatement ps = this.conexao.prepareStatement(sql);
			ps.setInt(1, usuario.getId());
			
			ps.executeUpdate();
			
	    	EnderecoDao enderecoDao = new EnderecoDao();
	    	enderecoDao.deletarEndereco(usuario.getEndereco().getId());
			
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.fabricaConexao.closeConnection();
		}
    	
    	return false;
    }
    
    public boolean editarUsuario(UsuarioBean usuario) {
    	
    	this.conexao = this.fabricaConexao.getConnection();
    	
    	EnderecoDao enderecoDao = new EnderecoDao();
    	
    	try {
        	enderecoDao.editarEndereco(usuario.getEndereco());
    		
        	String sql = "UPDATE usuarios SET nome = ?, cpf = ?, data_nascimento = ?, telefone_celular = ?, telefone_fixo = ?, email = ?, senha = ? WHERE id_usuario = ?;";
			PreparedStatement ps = this.conexao.prepareStatement(sql);

			ps.setString(1, usuario.getNome());
			ps.setString(2, usuario.getCpf());
			ps.setDate(3, new java.sql.Date(usuario.getData_nascimento().getTime()));
			ps.setString(4, usuario.getTelefone_celular());
			ps.setString(5, usuario.getTelefone_fixo());
			ps.setString(6, usuario.getEmail());
			ps.setString(7, usuario.getSenha());
			ps.setInt(8, usuario.getId());
			
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
