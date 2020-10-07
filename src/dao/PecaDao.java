package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import factory.FabricaConexao;
import model.FabricanteBean;
import model.PecaBean;
import model.TipoPecaBean;

public class PecaDao {
	
	private Connection conexao = null;
	private FabricaConexao fabricaConexao;
	
	public PecaDao() {
		this.fabricaConexao = new FabricaConexao();
	}
	
	public boolean cadastrarPeca(PecaBean peca) {
		
		try {
			this.conexao = this.fabricaConexao.getConnection();	//possui os métodos para criar um Statement
			String sql = "INSERT INTO pecas (nome, preco, descricao, quantidade, tipo_peca_id, fabricante_id) VALUES (?, ?, ?, ?, ?, ?)";
			PreparedStatement ps = this.conexao.prepareStatement(sql);	//controla e executa uma instrução SQL
			
			ps.setString(1, peca.getNome());	//Preenche o valor nome do SQL
			ps.setFloat(2, peca.getPreco());
			ps.setString(3, peca.getDescricao());
			ps.setInt(4, peca.getQuantidade());
			ps.setInt(5, peca.getTipoPeca().getId());
			ps.setInt(6, peca.getFabricante().getId());
			
			ps.executeUpdate();	//Executa a instrução sql
			return true;
		} catch (SQLException excecao) {
			excecao.printStackTrace();	//Printa a exceção caso tenha ocorrido
		} finally {
			this.fabricaConexao.closeConnection();	//Fecha a conexão. - Por estar no finally, esse comando é executado mesmo após o return do Try.
		}
		
		return false;	//Se não tiver conseguido cadastrar a peça
	}
	
	public ArrayList<PecaBean> listarPecas(){
		
		try {
			this.conexao = this.fabricaConexao.getConnection();
			String sql = "SELECT p.id_peca, p.nome as nome_peca, p.preco as preco_peca, p.descricao as descricao_peca, p.quantidade as quantidade_peca, tp.id_tipo_peca, tp.nome as nome_tipo_peca, tp.descricao as descricao_tipo_peca,  f.id_fabricante, f.nome as nome_fabricante, f.email as email_fabricante, f.telefone as telefone_fabricante, f.site as site_fabricante FROM pecas as p, tipos_pecas as tp, fabricantes as f WHERE p.tipo_peca_id = tp.id_tipo_peca and p.fabricante_id = f.id_fabricante;";
			PreparedStatement ps = this.conexao.prepareStatement(sql);
			
			ResultSet resultados = ps.executeQuery();	//Executa a consulta e pega seus resultados.
			
			ArrayList<PecaBean> listaPecas = new ArrayList<>();
			while(resultados.next()) {	//Enquanto houver resultado
				PecaBean peca = new PecaBean();
				peca.setId(resultados.getInt("id_peca"));
				peca.setNome(resultados.getString("nome_peca"));
				peca.setPreco(resultados.getFloat("preco_peca"));
				peca.setDescricao(resultados.getString("descricao_peca"));
				peca.setQuantidade(resultados.getInt("quantidade_peca"));
				
				TipoPecaBean tipoPeca = new TipoPecaBean();
				tipoPeca.setId(resultados.getInt("id_tipo_peca"));
				tipoPeca.setNome(resultados.getString("nome_tipo_peca"));
				tipoPeca.setDescricao(resultados.getString("descricao_tipo_peca"));
				
				peca.setTipoPeca(tipoPeca);
				
				FabricanteBean fabricante = new FabricanteBean();
				fabricante.setId(resultados.getInt("id_fabricante"));
				fabricante.setNome(resultados.getString("nome_fabricante"));
				fabricante.setEmail(resultados.getString("email_fabricante"));
				fabricante.setTelefone(resultados.getString("telefone_fabricante"));
				fabricante.setSite(resultados.getString("site_fabricante"));
				
				peca.setFabricante(fabricante);
				
				listaPecas.add(peca);
			}
			
			return listaPecas;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.fabricaConexao.closeConnection();
		}
			return null;	//Se não tiver conseguido listar as pecas
	}
	
	public boolean editarPeca(PecaBean peca) {
		
		try {
			this.conexao = this.fabricaConexao.getConnection();
			String sql = "UPDATE pecas SET nome = ?, preco = ?, descricao = ?, quantidade = ?, tipo_peca_id = ?, fabricante_id = ? WHERE id_peca = ?";
			PreparedStatement ps = this.conexao.prepareStatement(sql);
			
			ps.setString(1, peca.getNome());
			ps.setFloat(2, peca.getPreco());
			ps.setString(3, peca.getDescricao());
			ps.setInt(4, peca.getQuantidade());
			ps.setInt(5, peca.getTipoPeca().getId());
			ps.setInt(6, peca.getFabricante().getId());
			ps.setInt(7, peca.getId());
			
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.fabricaConexao.closeConnection();
		}
		
		return false;
	}
	
	public boolean deletarPeca(PecaBean peca) {
		
		try {
			this.conexao = this.fabricaConexao.getConnection();
			String sql = "DELETE FROM pecas WHERE id_peca = ?";
			PreparedStatement ps = this.conexao.prepareStatement(sql);
			
			ps.setInt(1, peca.getId());
			
			ps.executeUpdate();	//Executa a instrução
			return true;	//Retorna true se tiver conseguido
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.fabricaConexao.closeConnection();	//Fecha a conexão que foi aberta. - Esse código por estar dentro do finally é executado mesmo após o return do try.
		}
			return false;	//Retorna false se não tiver conseguido deletar a peça
	}
	
}
