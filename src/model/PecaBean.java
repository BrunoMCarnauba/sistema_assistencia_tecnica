package model;

public class PecaBean {
	private int id;
	private String nome;
	private float preco;
	private String descricao;
	private int quantidade;
	private TipoPecaBean tipoPeca;
	private FabricanteBean fabricante;
	
	public PecaBean() {
		this.tipoPeca = new TipoPecaBean();
		this.fabricante = new FabricanteBean();
	}
	
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
	
	public float getPreco() {
		return preco;
	}
	
	public void setPreco(float preco) {
		this.preco = preco;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public int getQuantidade() {
		return quantidade;
	}
	
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	
	public TipoPecaBean getTipoPeca() {
		return tipoPeca;
	}
	
	public void setTipoPeca(TipoPecaBean tipoPeca) {
		this.tipoPeca = tipoPeca;
	}
	
	public FabricanteBean getFabricante() {
		return fabricante;
	}
	
	public void setFabricante(FabricanteBean fabricante) {
		this.fabricante = fabricante;
	}
	
}
