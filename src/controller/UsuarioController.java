package controller;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import dao.CargoDao;
import dao.UsuarioDao;
import model.CargoBean;
import model.UsuarioBean;
import utilities.FacesUtil;

//Observa��o: Esse ManagedBean foi criado com a inten��o de controlar o Cadastrar - Editar - Excluir e Listar do usu�rio. Sendo o editar, o cadastrar e o listar p�ginas diferentes.

@ManagedBean	// Diz que essa classe � um ManagedBean, respons�vel pela l�gica da tela. No xhtml ela vai ser chamada de usuarioController.
@ViewScoped		// O Bean que tem ViewScoped, s� ir� existir em mem�ria quando estiver manipulando a tela dele.
public class UsuarioController {
	
	private UsuarioBean usuario = new UsuarioBean();
	
	private UsuarioDao usuarioDAO = new UsuarioDao();
	private CargoDao cargoDAO = new CargoDao();
	
	@PostConstruct
	public void init() {
		UsuarioBean usuarioRecebido = (UsuarioBean) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("usuario");	//Se tiver vindo da fun��o editar, ent�o o usu�rio ser� preenchido com o que foi recebido do escopo flash. 
		if(usuarioRecebido != null) {
			this.usuario = usuarioRecebido;
		}
	}
	
	public UsuarioBean getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioBean usuario) {
		this.usuario = usuario;
	}
	
	public ArrayList<CargoBean> listarCargos() {
		return this.cargoDAO.listarCargos();
	}
	
	public ArrayList<UsuarioBean> listarUsuarios(){
		return this.usuarioDAO.listarUsuarios();
	}
	
	private boolean validarCamposCustom() {
		//Outras valida��es
		boolean validacaoAprovada = true;
		if(this.usuario.getTelefone_celular().equals("") && this.usuario.getTelefone_fixo().equals("")) {
			FacesUtil.adicionarMensagemErro("Voc� precisa preencher pelo menos um campo de telefone");
			validacaoAprovada = false;
		}
		
		return validacaoAprovada;
	}
	
	public String cadastrarUsuario() {
		if(this.validarCamposCustom() == true) {	//Se passar na valida��o
			if(this.usuarioDAO.cadastrarUsuario(this.usuario) == true) {
				FacesUtil.adicionarMensagemInfo("Usu�rio cadastrado com sucesso!");
				return("UsuarioListar.xhtml?faces-redirect=true");	//Redireciona o usu�rio para essa p�gina
			}else {
				FacesUtil.adicionarMensagemErro("Houve um erro ao tentar cadastrar o usu�rio. Provavelmente o cpf informado j� esteja cadastrado.");				
			}
		}
		
		return "";
	}
	
	public void excluirUsuario(ActionEvent evento) {
		this.usuario = (UsuarioBean) evento.getComponent().getAttributes().get("usuarioSelecionado");
		
		if(this.usuarioDAO.deletarUsuario(this.usuario) == true) {
			FacesUtil.adicionarMensagemInfo("Usu�rio excluido com sucesso.");
		} else {
			FacesUtil.adicionarMensagemErro("Houve um erro ao tentar excluir o usu�rio.");
		}
	}

	public String editarClicado(UsuarioBean usuarioSelecionado) {
		//https://blog.algaworks.com/facesmessage-depois-do-redirect/ - Nele explica sobre escopo flash
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("usuario", usuarioSelecionado); // incluindo um objeto no escopo Flash
		return "UsuarioEditar.xhtml?faces-redirect=true";	//Chama a outra p�gina que poder� pegar o objeto do escopo flash
	}
	
	public String editarUsuario() {		
		if(this.validarCamposCustom() == true) {	//Se passar na valida��o
			if(this.usuarioDAO.editarUsuario(this.usuario) == true) {
				FacesUtil.adicionarMensagemInfo("Usu�rio editado com sucesso!");
				return("UsuarioListar.xhtml?faces-redirect=true");	//Redireciona o usu�rio para essa p�gina
			}else {
				FacesUtil.adicionarMensagemErro("Houve um erro ao tentar editar o usu�rio.");
			}
		}
		
		return "";
	}

}
