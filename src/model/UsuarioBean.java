package model;

import java.util.Date;

public class UsuarioBean extends PessoaBean{
	private CargoBean cargo;
	private String senha;
	private Date data_nascimento;
	
	public UsuarioBean() {
		this.cargo = new CargoBean();
	}
	
	public CargoBean getCargo() {
		return cargo;
	}
	public void setCargo(CargoBean cargo) {
		this.cargo = cargo;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public Date getData_nascimento() {
		return data_nascimento;
	}
	public void setData_nascimento(Date data_nascimento) {
		this.data_nascimento = data_nascimento;
	}

	@Override
	public String toString() {	//Pois é usado em um select, então é feito isso para fazer o SelectItemsConverter - https://youtu.be/_sNP0RyPMtg
	    return String.format("%s[id=%d]", getClass().getSimpleName(), getId());
	}
	
}
