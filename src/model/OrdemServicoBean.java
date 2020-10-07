package model;

import java.util.Date;

public class OrdemServicoBean {
	private int id;
	private String status;
	private ClienteBean cliente;
	private String descricaoProblema;
	private UsuarioBean usuario;
	private String descricaoSolucao;
	private String[] pecas_usadas;
	private PagamentoBean pagamento;
	private Date dataRegistro;
	
	public OrdemServicoBean() {
		this.cliente = new ClienteBean();
		this.usuario = new UsuarioBean();
		this.pagamento = new PagamentoBean();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public ClienteBean getCliente() {
		return cliente;
	}
	
	public void setCliente(ClienteBean cliente) {
		this.cliente = cliente;
	}
	
	public String getDescricaoProblema() {
		return descricaoProblema;
	}
	
	public void setDescricaoProblema(String descricaoProblema) {
		this.descricaoProblema = descricaoProblema;
	}
	
	public UsuarioBean getUsuario() {
		return usuario;
	}
	
	public void setUsuario(UsuarioBean usuario) {
		this.usuario = usuario;
	}
	
	public PagamentoBean getPagamento() {
		return pagamento;
	}
	
	public void setPagamento(PagamentoBean pagamento) {
		this.pagamento = pagamento;
	}
	
	public String getDescricaoSolucao() {
		return descricaoSolucao;
	}
	
	public void setDescricaoSolucao(String descricaoSolucao) {
		this.descricaoSolucao = descricaoSolucao;
	}

	public Date getDataRegistro() {
		return dataRegistro;
	}

	public void setDataRegistro(Date dataRegistro) {
		this.dataRegistro = dataRegistro;
	}

	public String[] getPecas_usadas() {
		return pecas_usadas;
	}

	public void setPecas_usadas(String[] pecas_usadas) {
		this.pecas_usadas = pecas_usadas;
	}
	
	public String pecas_usadas_toString() {
		String pecasUsadas = ""; 
		for(int i = 0; i < this.pecas_usadas.length; i++) {
			if(i > 0) {
				pecasUsadas += ",";
			}
			pecasUsadas += this.pecas_usadas[i];
		}
		
		return pecasUsadas;
	}

}
