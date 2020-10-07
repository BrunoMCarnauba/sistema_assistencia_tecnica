package controller;

import java.io.Serializable;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import dao.ClienteDao;
import model.ClienteBean;
import utilities.FacesUtil;

@SuppressWarnings("serial")	//Para sumir com uma advertência - https://youtu.be/iHwaZU2YSbo?t=402
@ManagedBean	// Diz que essa classe é um ManagedBean, responsável pela lógica da tela. No xhtml ela vai ser chamada de clienteController.
@ViewScoped		// O Bean que tem ViewScoped, só irá existir em memória quando estiver manipulando a tela dele.
public class ClienteController implements Serializable {
	
	private ClienteBean cliente = new ClienteBean();
	
	private ClienteDao clienteDAO = new ClienteDao();
	
	public ClienteBean getCliente() {
		return cliente;
	}
	
	public void setCliente(ClienteBean cliente) {
		this.cliente = cliente;
	}
	
	public ArrayList<ClienteBean> listarClientes(){
		return this.clienteDAO.listarClientes();
	}
	
	public void novoCliente() {
		this.cliente = new ClienteBean();
	}
	
	public void btnSalvar() {	//Serve tanto para o cadastro quanto para o editar
		if(this.cliente.getId() == 0) {	//O id é gerado automaticamente e não setado pelo usuário, então se não existir, quer dizer que é um novo cadastro e não edição. https://stackoverflow.com/questions/13747859/how-to-check-if-an-int-is-a-null
			this.cadastrarCliente();
		} else {
			this.editarCliente();
		}
	}
	
	private boolean validarCamposCustom() {
		//Outras validações
		boolean validacaoAprovada = true;
		if(this.cliente.getTelefone_celular().equals("") && this.cliente.getTelefone_fixo().equals("")) {
			FacesUtil.adicionarMensagemErro("Você precisa preencher pelo menos um campo de telefone");
			validacaoAprovada = false;
		}
		
		return validacaoAprovada;
	}
	
	public void cadastrarCliente() {		
		if(this.validarCamposCustom() == true) {	//Se passar na validação
			if(this.clienteDAO.cadastrarCliente(this.cliente) == true) {
				FacesUtil.adicionarMensagemInfo("Cliente cadastrado com sucesso!");
				novoCliente();	//Para limpar o objeto e consequentemente os campos do formulário com o auxilio da propriedade update do commandButton.
			} else {
				FacesUtil.adicionarMensagemErro("Houve um erro ao tentar cadastrar o cliente.");
			}
		}
	}
	
	
	public void btnEditar(ActionEvent evento) {
		this.cliente = (ClienteBean) evento.getComponent().getAttributes().get("clienteSelecionado");
	}
	
	public void editarCliente() {
		if(this.validarCamposCustom() == true) {	//Se passar na validação
			if(this.clienteDAO.editarCliente(this.cliente) == true) {
				FacesUtil.adicionarMensagemInfo("Cliente editado com sucesso!");
			} else {
				FacesUtil.adicionarMensagemErro("Houve um erro ao tentar editar o cliente.");
			}
		}
	}
	
	public void deletarCliente(ActionEvent evento) {
		this.cliente = (ClienteBean) evento.getComponent().getAttributes().get("clienteSelecionado");
		
		if(this.clienteDAO.deletarCliente(this.cliente) == true) {
			FacesUtil.adicionarMensagemInfo("Cliente deletado com sucesso!");
		} else {
			FacesUtil.adicionarMensagemErro("Houve um erro ao tentar deletar o cliente.");
		}
	}
	
}
