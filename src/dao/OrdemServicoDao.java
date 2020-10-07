package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import factory.FabricaConexao;
import model.FabricanteBean;
import model.OrdemServicoBean;
import model.PecaBean;
import model.TipoPecaBean;

public class OrdemServicoDao {
	
	private Connection conexao = null;
	private FabricaConexao fabricaConexao;
	
	public OrdemServicoDao() {
		this.fabricaConexao = new FabricaConexao();
	}
	
	public boolean cadastrarOrdemServico(OrdemServicoBean ordemServico) {
			
		try {
			this.conexao = this.fabricaConexao.getConnection();	//possui os métodos para criar um Statement
			String sql = "INSERT INTO ordens_servicos (status, cliente_id, descricao_problema, usuario_id) VALUES (?, ?, ?, ?)";
			PreparedStatement ps = this.conexao.prepareStatement(sql);	//controla e executa uma instrução SQL
			
			ps.setString(1, ordemServico.getStatus());	//Preenche o valor status do SQL
			ps.setInt(2, ordemServico.getCliente().getId());
			ps.setString(3, ordemServico.getDescricaoProblema());
			ps.setInt(4, ordemServico.getUsuario().getId());
			
			ps.executeUpdate();	//Executa a instrução sql
			return true;
		} catch (SQLException excecao) {
			excecao.printStackTrace();	//Printa a exceção caso tenha ocorrido
		} finally {
			this.fabricaConexao.closeConnection();	//Fecha a conexão. - Por estar no finally, esse comando é executado mesmo após o return do Try.
		}
		return false;	//Se não tiver conseguido cadastrar a ordem de serviço
	}
	
	public boolean editarCadastro(OrdemServicoBean ordemServico) { 
		
		try {
			this.conexao = this.fabricaConexao.getConnection();
			String sql = "UPDATE ordens_servicos SET status = ?, cliente_id = ?, descricao_problema = ?, usuario_id = ? WHERE id_ordem_servico = ?";
			PreparedStatement ps = this.conexao.prepareStatement(sql);
			
			ps.setString(1, ordemServico.getStatus());
			ps.setInt(2, ordemServico.getCliente().getId());
			ps.setString(3, ordemServico.getDescricaoProblema());
			ps.setInt(4, ordemServico.getUsuario().getId());
			ps.setInt(5, ordemServico.getId());
			
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.fabricaConexao.closeConnection();
		}
		return false;
	}
	
	public boolean atualizarOrdemServico(OrdemServicoBean ordemServico) {
		
		try {
			this.conexao = this.fabricaConexao.getConnection();
			String sql = "UPDATE ordens_servicos SET status = ?, descricao_problema = ?, descricao_solucao = ?, pecas_usadas = ? WHERE id_ordem_servico = ?";
			PreparedStatement ps = this.conexao.prepareStatement(sql);
			
			ps.setString(1, ordemServico.getStatus());
			ps.setString(2, ordemServico.getDescricaoProblema());
			ps.setString(3, ordemServico.getDescricaoSolucao());
			
			ps.setString(4, ordemServico.pecas_usadas_toString());
			
			ps.setInt(5, ordemServico.getId());
			
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.fabricaConexao.closeConnection();
		}
		return false;
	}
	
	public ArrayList<OrdemServicoBean> listarOrdensServicos() {
		
		try {
			this.conexao = this.fabricaConexao.getConnection();
			String sql = "SELECT * FROM ordens_servicos";
			PreparedStatement ps = this.conexao.prepareStatement(sql);
			
			ResultSet resultados = ps.executeQuery();
			
			ArrayList<OrdemServicoBean> listaOrdensServico = new ArrayList<>();
			while(resultados.next()) {
				OrdemServicoBean ordemServico = new OrdemServicoBean();
				ordemServico.setId(resultados.getInt("id_ordem_servico"));
				ordemServico.setStatus(resultados.getString("status"));
				ordemServico.setDescricaoProblema(resultados.getString("descricao_problema"));
				ordemServico.setDataRegistro(resultados.getDate("data_registro"));
				
				ordemServico.setDescricaoSolucao(resultados.getString("descricao_solucao"));
				
				if(resultados.getString("pecas_usadas") != null) {
					String[] pecas_usadas = resultados.getString("pecas_usadas").split(",");
					ordemServico.setPecas_usadas(pecas_usadas);
				}
				
				ClienteDao clienteDAO = new ClienteDao();
				ordemServico.setCliente(clienteDAO.getClientePorID(resultados.getInt("cliente_id")));
				
				UsuarioDao usuarioDAO = new UsuarioDao();
				ordemServico.setUsuario(usuarioDAO.getUsuarioPorID(resultados.getInt("usuario_id")));
				
				//Só ficou faltando o pagamento
				
				listaOrdensServico.add(ordemServico);
			}
			
			return listaOrdensServico;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.fabricaConexao.closeConnection();
		}
		return null;
	}
	
	public boolean deletarOrdemServico(OrdemServicoBean ordemServico) {

		try {
			this.conexao = this.fabricaConexao.getConnection();
			String sql = "DELETE FROM ordens_servicos WHERE id_ordem_servico = ?";
			PreparedStatement ps = this.conexao.prepareStatement(sql);
			
			ps.setInt(1, ordemServico.getId());
			
			ps.executeUpdate();
			
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.fabricaConexao.closeConnection();
		}
		
		return false;
	}
	
	//Faltou fazer o de pagamento
	
}
