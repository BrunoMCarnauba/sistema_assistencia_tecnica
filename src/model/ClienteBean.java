package model;

public class ClienteBean extends PessoaBean{
	@Override
	public String toString() {	//Pois � usado em um select, ent�o � feito isso para fazer o SelectItemsConverter - https://youtu.be/_sNP0RyPMtg
	    return String.format("%s[id=%d]", getClass().getSimpleName(), getId());
	}
}
