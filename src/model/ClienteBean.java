package model;

public class ClienteBean extends PessoaBean{
	@Override
	public String toString() {	//Pois é usado em um select, então é feito isso para fazer o SelectItemsConverter - https://youtu.be/_sNP0RyPMtg
	    return String.format("%s[id=%d]", getClass().getSimpleName(), getId());
	}
}
