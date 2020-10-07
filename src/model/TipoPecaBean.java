package model;

public class TipoPecaBean {
	private int id;
	private String nome;
	private String descricao;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@Override
	public String toString() {	//Pois é usado em um select, então é feito isso para fazer o SelectItemsConverter - https://youtu.be/_sNP0RyPMtg
	    return String.format("%s[id=%d]", getClass().getSimpleName(), getId());
	}
	
}
