package model;

public class FabricanteBean {
	private int id;
	private String nome;
	private String email;
	private String telefone;
	private String site;
	
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
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getTelefone() {
		return telefone;
	}
	
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	public String getSite() {
		return site;
	}
	
	public void setSite(String site) {
		this.site = site;
	}
	
	@Override
	public String toString() {	//Pois é usado em um select, então é feito isso para fazer o SelectItemsConverter - https://youtu.be/_sNP0RyPMtg
	    return String.format("%s[id=%d]", getClass().getSimpleName(), getId());
	}
	
}
