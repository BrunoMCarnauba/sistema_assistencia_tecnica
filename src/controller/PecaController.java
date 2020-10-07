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

@SuppressWarnings("serial")	//Para sumir com uma advert�ncia - https://youtu.be/iHwaZU2YSbo?t=402
@ManagedBean	// Diz que essa classe � um ManagedBean, respons�vel pela l�gica da tela. No xhtml ela vai ser chamada de pecaController.
@ViewScoped	// O Bean que tem ViewScoped, s� ir� existir em mem�ria quando estiver manipulando a tela dele.
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
	
	public ArrayList<TipoPecaBean> listarTiposPecas(){	//usado no select de tipos de pe�as
		return this.tipoPecaDAO.listarTiposPecas();
	}
	
	public ArrayList<FabricanteBean> listarFabricantes(){	//usado no select de fabricantes
		return this.fabricanteDAO.listarFabricantes();
	}
	
	public void cadastrarPeca() {
		if(this.pecaDAO.cadastrarPeca(this.peca) == true) {
			FacesUtil.adicionarMensagemInfo("Pe�a cadastrada com sucesso!");
			this.novaPeca();
		}else {
			FacesUtil.adicionarMensagemErro("Houve um erro ao tentar cadastrar a pe�a.");
		}
	}
	
	public void btnSalvar() {
		if(this.peca.getId() == 0) {	//O id � gerado automaticamente, ent�o se o id for igual a 0 (se ainda n�o tiver sido gerado), ent�o quer dizer que ainda n�o foi feito o cadastro desse objeto.
			this.cadastrarPeca();
		}else {	//Se o objeto que est� sendo manipulado j� tiver sido cadastrado, ent�o essa a��o de salvar � uma edi��o
			this.editarPeca();
		}
	}
	
	public void btnEditar(ActionEvent evento) {
		this.peca = (PecaBean) evento.getComponent().getAttributes().get("pecaSelecionada");
	}
	
	public void editarPeca() {
		if(this.pecaDAO.editarPeca(this.peca) == true) {
			FacesUtil.adicionarMensagemInfo("Cadastro de pe�a editado com sucesso!");
		}else {
			FacesUtil.adicionarMensagemErro("Houve um erro ao tentar editar o cadastro da pe�a.");
		}
	}
	
	public void deletarPeca(ActionEvent evento) {
		this.peca = (PecaBean) evento.getComponent().getAttributes().get("pecaSelecionada");
		if(this.pecaDAO.deletarPeca(this.peca) == true) {
			FacesUtil.adicionarMensagemInfo("Pe�a deletada com sucesso!");
		}else {
			FacesUtil.adicionarMensagemErro("Houve um erro ao tentar deletar a pe�a.");
		}
	}
}
