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
			FacesUtil.adicionarMensagemInfo("Tipo de peça cadastrado com sucesso!");
			this.novoTipoPeca();
		} else {
			FacesUtil.adicionarMensagemErro("Houve um erro ao tentar cadastrar o tipo de paça.");
		}
	}
	
	public void btnSalvar() {
		if(this.tipoPeca.getId() == 0) {	//O ID é gerado automaticamente ao cadastrar um tipo de peça, então se for igual a 0 (mesmo que nulo) quer dizer que esse objeto ainda não foi cadastrado.
			this.cadastrarTipoPeca();
		} else {	//Se já tiver sido cadastrado, então quer dizer que o pedido é para salvar uma edição de cadastro.
			this.editarTipoPeca();
		}
	}
	
	public void btnEditar(ActionEvent evento) {
		this.tipoPeca = (TipoPecaBean) evento.getComponent().getAttributes().get("tipoPecaSelecionado");
	}
	
	public void editarTipoPeca() {
		if(this.tipoPecaDAO.editarTipoPeca(this.tipoPeca) == true) {
			FacesUtil.adicionarMensagemInfo("Tipo de peça editado com sucesso!");
		} else {
			FacesUtil.adicionarMensagemErro("Houve um erro ao tentar editar o tipo de peça.");
		}
	}
	
	public void deletarTipoPeca(ActionEvent evento) {
		this.tipoPeca = (TipoPecaBean) evento.getComponent().getAttributes().get("tipoPecaSelecionado");
		if(this.tipoPecaDAO.deletarTipoPeca(this.tipoPeca) == true) {
			FacesUtil.adicionarMensagemInfo("Tipo de peça deletado com sucesso!");
		} else {
			FacesUtil.adicionarMensagemErro("Houve um erro ao tentar deletar o tipo de peça.");
		}
	}

}
