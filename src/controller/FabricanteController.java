package controller;

import java.io.Serializable;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import dao.FabricanteDao;
import model.FabricanteBean;
import utilities.FacesUtil;


@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class FabricanteController implements Serializable{

	FabricanteBean fabricante = new FabricanteBean();
	
	FabricanteDao fabricanteDAO = new FabricanteDao();

	public FabricanteBean getFabricante() {
		return fabricante;
	}

	public void setFabricante(FabricanteBean fabricante) {
		this.fabricante = fabricante;
	}
	
	
	public ArrayList<FabricanteBean> listarFabricantes(){
		return this.fabricanteDAO.listarFabricantes();
	}
	
	public void novo() {
		this.fabricante = new FabricanteBean();
	}
	
	public void btnSalvar() {
		if(this.fabricante.getId() == 0) {
			this.cadastrarFabricante();
		}else {
			this.editarFabricante();
		}
	}
	
	public void cadastrarFabricante() {
		if(this.fabricanteDAO.cadastrarFabricante(this.fabricante) == true) {
			FacesUtil.adicionarMensagemInfo("Fabricante cadastrado com sucesso!");
			this.novo();
		} else {
			FacesUtil.adicionarMensagemErro("Houve um erro ao tentar cadastrar o fabricante.");
		}
	}
	
	public void btnEditar(ActionEvent evento) {
		this.fabricante = (FabricanteBean) evento.getComponent().getAttributes().get("fabricanteSelecionado");
	}
	
	public void editarFabricante() {
		if(this.fabricanteDAO.editarFabricante(this.fabricante)) {
			FacesUtil.adicionarMensagemInfo("Fabricante editado com sucesso!");
		}else {
			FacesUtil.adicionarMensagemErro("Houve um erro ao tentar editar o fabricante.");
		}
	}
	
	public void deletarFabricante(ActionEvent evento) {
		this.fabricante = (FabricanteBean) evento.getComponent().getAttributes().get("fabricanteSelecionado");
		
		if(this.fabricanteDAO.deletarFabricante(this.fabricante) == true) {
			FacesUtil.adicionarMensagemInfo("Fabricante deletado com sucesso!");
		} else {
			FacesUtil.adicionarMensagemErro("Houve um erro ao tentar deletar o fabricante.");
		}
	}
	
}
