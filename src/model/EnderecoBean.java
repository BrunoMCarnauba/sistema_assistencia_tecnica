package model;

import dao.EnderecoDao;

public class EnderecoBean {
	
	private int id;
	private String cep;
	private String estado;
	private String cidade;
	private String logradouro;
	private String bairro;
	private String complemento;
	private int numero_propriedade;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCep() {
		return cep;
	}
	
	public void setCep(String cep) {
		this.cep = cep;
	}
	
	public String getEstado() {
		return estado;
	}
	
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public String getCidade() {
		return cidade;
	}
	
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	
	public String getLogradouro() {
		return logradouro;
	}
	
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}
	
	public String getBairro() {
		return bairro;
	}
	
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	
	public String getComplemento() {
		return complemento;
	}
	
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	
	public int getNumero_propriedade() {
		return numero_propriedade;
	}
	
	public void setNumero_propriedade(int numero_propriedade) {
		this.numero_propriedade = numero_propriedade;
	}
	
	//Outros métodos
	
	public int cadastrarEndereco() {
		EnderecoDao enderecoDao = new EnderecoDao();
		return enderecoDao.cadastrarEndereco(this);
	}
	
	public boolean deletarEndereco(int idEndereco) {
		EnderecoDao enderecoDao = new EnderecoDao();
		return enderecoDao.deletarEndereco(idEndereco);
	}
}
