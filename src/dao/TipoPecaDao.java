package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import factory.FabricaConexao;
import model.TipoPecaBean;

public class TipoPecaDao {
	
	private Connection conexao = null;
	private FabricaConexao fabricaConexao;
	
	public TipoPecaDao() {
		this.fabricaConexao = new FabricaConexao();
	}
	
	public boolean cadastrarTipoPeca(TipoPecaBean tipoPeca) {
		
		try {
			this.conexao = this.fabricaConexao.getConnection();	//possui os métodos para criar um Statement
			String sql = "INSERT INTO tipos_pecas (nome, descricao) VALUES (?,?)";
			PreparedStatement ps = this.conexao.prepareStatement(sql);	//controla e executa uma instrução SQL
			
			ps.setString(1, tipoPeca.getNome());
			ps.setString(2, tipoPeca.getDescricao());
			
			ps.executeUpdate();	//Executa a instrução sql
			return true;
		} catch (SQLException excecao) {
			excecao.printStackTrace();	//Printa a exceção
		} finally {
			this.fabricaConexao.closeConnection();	//Fecha a conexão. - Por estar no finally, esse comando é executado mesmo após o return do Try.
		}
		
		return false;
	}
	
	public ArrayList<TipoPecaBean> listarTiposPecas() {
		
		try {
			this.conexao = this.fabricaConexao.getConnection();
			String sql = "SELECT * FROM tipos_pecas";
			PreparedStatement ps = this.conexao.prepareStatement(sql);
			
			ResultSet resultados = ps.executeQuery();	//Executa e pega os resultados da consulta
			
			ArrayList<TipoPecaBean> listaTipoPecas = new ArrayList<>();
			while(resultados.next()) {	//Enquanto houver resultados
				TipoPecaBean tipoPeca = new TipoPecaBean();
				tipoPeca.setId(resultados.getInt("id_tipo_peca"));
				tipoPeca.setNome(resultados.getString("nome"));
				tipoPeca.setDescricao(resultados.getString("descricao"));
				
				listaTipoPecas.add(tipoPeca);
			}
			
			return listaTipoPecas;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.fabricaConexao.closeConnection();
		}
			return null;	//Se não tiver conseguido gerar a lista no Try.
	}
	
	public boolean editarTipoPeca(TipoPecaBean tipoPeca) {
		
		try {
			this.conexao = this.fabricaConexao.getConnection();
			String sql = "UPDATE tipos_pecas SET nome = ?, descricao = ? WHERE id_tipo_peca = ?";
			PreparedStatement ps = this.conexao.prepareStatement(sql);
			
			ps.setString(1, tipoPeca.getNome());
			ps.setString(2, tipoPeca.getDescricao());
			ps.setInt(3, tipoPeca.getId());
			
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.fabricaConexao.closeConnection();
		}
		
		return false;
	}
	
	public boolean deletarTipoPeca(TipoPecaBean tipoPeca) {
		
		try {
			this.conexao = this.fabricaConexao.getConnection();
			String sql = "DELETE FROM tipos_pecas WHERE id_tipo_peca = ?";
			PreparedStatement ps = this.conexao.prepareStatement(sql);
			
			ps.setInt(1, tipoPeca.getId());
			
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
