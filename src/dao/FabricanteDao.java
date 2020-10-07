package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import factory.FabricaConexao;
import model.FabricanteBean;

public class FabricanteDao {
	
	private FabricaConexao fabricaConexao;
	private Connection conexao = null;
	
	public FabricanteDao() {
		this.fabricaConexao = new FabricaConexao();
	}
	
	public boolean cadastrarFabricante(FabricanteBean fabricante) {
		
		try {
			this.conexao = this.fabricaConexao.getConnection();
			String sql = "INSERT INTO fabricantes (nome, email, telefone, site) VALUES (?,?,?,?)";
			PreparedStatement ps = this.conexao.prepareStatement(sql);
			
			ps.setString(1, fabricante.getNome());
			ps.setString(2, fabricante.getEmail());
			ps.setString(3, fabricante.getTelefone());
			ps.setString(4, fabricante.getSite());
			
			ps.executeUpdate();	//Executa a instrução sql
			return true;
		} catch (SQLException excecao) {
			excecao.printStackTrace();	//Printa a exceção caso haja.
		} finally {
			this.fabricaConexao.closeConnection();	//Fecha a conexão quando acabar o Try Catch. - Isso é executado mesmo após o return do Try.
		}
		
		return false;
	}
	
	public ArrayList<FabricanteBean> listarFabricantes(){
		
		try {
			this.conexao = this.fabricaConexao.getConnection();
			String sql = "SELECT * FROM fabricantes";
			PreparedStatement ps = this.conexao.prepareStatement(sql);
			
			ResultSet resultados = ps.executeQuery();	//Executa a consulta sql
			
			ArrayList<FabricanteBean> listaFabricantes = new ArrayList<>();
			while(resultados.next()) {	//Enquanto houver resultados
				FabricanteBean fabricante = new FabricanteBean();
				fabricante.setId(resultados.getInt("id_fabricante"));
				fabricante.setNome(resultados.getString("nome"));
				fabricante.setEmail(resultados.getString("email"));
				fabricante.setTelefone(resultados.getString("telefone"));
				fabricante.setSite(resultados.getString("site"));
				
				listaFabricantes.add(fabricante);
			}
			
			return listaFabricantes;
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.fabricaConexao.closeConnection();
		}		
		
		return null; //Se tiver ocorrido erro no try será retornado null.
	}
	
	public boolean deletarFabricante(FabricanteBean fabricante) {
		
		try {
			this.conexao = this.fabricaConexao.getConnection();
			String sql = "DELETE FROM fabricantes WHERE id_fabricante = ?";
			PreparedStatement ps = this.conexao.prepareStatement(sql);
			
			ps.setInt(1, fabricante.getId());
			
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.fabricaConexao.closeConnection();
		}
		
		return false;
	}
	
	public boolean editarFabricante(FabricanteBean fabricante) {
		
		try {
			this.conexao = this.fabricaConexao.getConnection();
			String sql = "UPDATE fabricantes SET nome = ?, email = ?, telefone = ?, site = ? WHERE id_fabricante = ?";
			PreparedStatement ps = this.conexao.prepareStatement(sql);
			
			ps.setString(1, fabricante.getNome());
			ps.setString(2, fabricante.getEmail());
			ps.setString(3, fabricante.getTelefone());
			ps.setString(4, fabricante.getSite());
			ps.setInt(5, fabricante.getId());
			
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
