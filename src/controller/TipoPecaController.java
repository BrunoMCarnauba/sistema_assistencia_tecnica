package controller;

import java.io.Serializable;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import dao.TipoPecaDao;
import model.TipoPecaBean;
import utilities.FacesUtil;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class TipoPecaController implements Serializable{
	
	private TipoPecaBean tipoPeca;
	
	private TipoPecaDao tipoPecaDAO = new TipoPecaDao();

	public TipoPecaBean getTipoPeca() {
		return tipoPeca;
	}

	public void setTipoPeca(TipoPecaBean tipoPeca) {
		this.tipoPeca = tipoPeca;
	}
	
	
	public void novoTipoPeca() {
		this.tipoPeca = new TipoPecaBean();
	}
	
	public ArrayList<TipoPecaBean> listarTiposPecas(){
		return this.tipoPecaDAO.listarTiposPecas();
	}
	
	public void cadastrarTipoPeca() {
		if(this.tipoPecaDAO.cadastrarTipoPeca(this.tipoPeca) == true) {
			FacesUtil.adicionarMensagemInfo("Tipo de pe�a cadastrado com sucesso!");
			this.novoTipoPeca();
		} else {
			FacesUtil.adicionarMensagemErro("Houve um erro ao tentar cadastrar o tipo de pa�a.");
		}
	}
	
	public void btnSalvar() {
		if(this.tipoPeca.getId() == 0) {	//O ID � gerado automaticamente ao cadastrar um tipo de pe�a, ent�o se for igual a 0 (mesmo que nulo) quer dizer que esse objeto ainda n�o foi cadastrado.
			this.cadastrarTipoPeca();
		} else {	//Se j� tiver sido cadastrado, ent�o quer dizer que o pedido � para salvar uma edi��o de cadastro.
			this.editarTipoPeca();
		}
	}
	
	public void btnEditar(ActionEvent evento) {
		this.tipoPeca = (TipoPecaBean) evento.getComponent().getAttributes().get("tipoPecaSelecionado");
	}
	
	public void editarTipoPeca() {
		if(this.tipoPecaDAO.editarTipoPeca(this.tipoPeca) == true) {
			FacesUtil.adicionarMensagemInfo("Tipo de pe�a editado com sucesso!");
		} else {
			FacesUtil.adicionarMensagemErro("Houve um erro ao tentar editar o tipo de pe�a.");
		}
	}
	
	public void deletarTipoPeca(ActionEvent evento) {
		this.tipoPeca = (TipoPecaBean) evento.getComponent().getAttributes().get("tipoPecaSelecionado");
		if(this.tipoPecaDAO.deletarTipoPeca(this.tipoPeca) == true) {
			FacesUtil.adicionarMensagemInfo("Tipo de pe�a deletado com sucesso!");
		} else {
			FacesUtil.adicionarMensagemErro("Houve um erro ao tentar deletar o tipo de pe�a.");
		}
	}

}
