package controller;

import java.io.Serializable;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import dao.FabricanteDao;
import dao.PecaDao;
import dao.TipoPecaDao;
import model.FabricanteBean;
import model.PecaBean;
import model.TipoPecaBean;
import utilities.FacesUtil;

@SuppressWarnings("serial")	//Para sumir com uma advertência - https://youtu.be/iHwaZU2YSbo?t=402
@ManagedBean	// Diz que essa classe é um ManagedBean, responsável pela lógica da tela. No xhtml ela vai ser chamada de pecaController.
@ViewScoped	// O Bean que tem ViewScoped, só irá existir em memória quando estiver manipulando a tela dele.
public class PecaController implements Serializable{

	private PecaBean peca = new PecaBean();
	
	private PecaDao pecaDAO = new PecaDao();
	private TipoPecaDao tipoPecaDAO = new TipoPecaDao();
	private FabricanteDao fabricanteDAO = new FabricanteDao();

	public PecaBean getPeca() {
		return peca;
	}

	public void setPeca(PecaBean peca) {
		this.peca = peca;
	}

	
	public void novaPeca() {
		this.peca = new PecaBean();
	}
	
	public ArrayList<PecaBean> listarPecas() {
		return this.pecaDAO.listarPecas();
	}
	
	public ArrayList<TipoPecaBean> listarTiposPecas(){	//usado no select de tipos de peças
		return this.tipoPecaDAO.listarTiposPecas();
	}
	
	public ArrayList<FabricanteBean> listarFabricantes(){	//usado no select de fabricantes
		return this.fabricanteDAO.listarFabricantes();
	}
	
	public void cadastrarPeca() {
		if(this.pecaDAO.cadastrarPeca(this.peca) == true) {
			FacesUtil.adicionarMensagemInfo("Peça cadastrada com sucesso!");
			this.novaPeca();
		}else {
			FacesUtil.adicionarMensagemErro("Houve um erro ao tentar cadastrar a peça.");
		}
	}
	
	public void btnSalvar() {
		if(this.peca.getId() == 0) {	//O id é gerado automaticamente, então se o id for igual a 0 (se ainda não tiver sido gerado), então quer dizer que ainda não foi feito o cadastro desse objeto.
			this.cadastrarPeca();
		}else {	//Se o objeto que está sendo manipulado já tiver sido cadastrado, então essa ação de salvar é uma edição
			this.editarPeca();
		}
	}
	
	public void btnEditar(ActionEvent evento) {
		this.peca = (PecaBean) evento.getComponent().getAttributes().get("pecaSelecionada");
	}
	
	public void editarPeca() {
		if(this.pecaDAO.editarPeca(this.peca) == true) {
			FacesUtil.adicionarMensagemInfo("Cadastro de peça editado com sucesso!");
		}else {
			FacesUtil.adicionarMensagemErro("Houve um erro ao tentar editar o cadastro da peça.");
		}
	}
	
	public void deletarPeca(ActionEvent evento) {
		this.peca = (PecaBean) evento.getComponent().getAttributes().get("pecaSelecionada");
		if(this.pecaDAO.deletarPeca(this.peca) == true) {
			FacesUtil.adicionarMensagemInfo("Peça deletada com sucesso!");
		}else {
			FacesUtil.adicionarMensagemErro("Houve um erro ao tentar deletar a peça.");
		}
	}
}
