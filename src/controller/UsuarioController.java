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

//Observação: Esse ManagedBean foi criado com a intenção de controlar o Cadastrar - Editar - Excluir e Listar do usuário. Sendo o editar, o cadastrar e o listar páginas diferentes.

@ManagedBean	// Diz que essa classe é um ManagedBean, responsável pela lógica da tela. No xhtml ela vai ser chamada de usuarioController.
@ViewScoped		// O Bean que tem ViewScoped, só irá existir em memória quando estiver manipulando a tela dele.
public class UsuarioController {
	
	private UsuarioBean usuario = new UsuarioBean();
	
	private UsuarioDao usuarioDAO = new UsuarioDao();
	private CargoDao cargoDAO = new CargoDao();
	
	@PostConstruct
	public void init() {
		UsuarioBean usuarioRecebido = (UsuarioBean) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("usuario");	//Se tiver vindo da função editar, então o usuário será preenchido com o que foi recebido do escopo flash. 
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
		//Outras validações
		boolean validacaoAprovada = true;
		if(this.usuario.getTelefone_celular().equals("") && this.usuario.getTelefone_fixo().equals("")) {
			FacesUtil.adicionarMensagemErro("Você precisa preencher pelo menos um campo de telefone");
			validacaoAprovada = false;
		}
		
		return validacaoAprovada;
	}
	
	public String cadastrarUsuario() {
		if(this.validarCamposCustom() == true) {	//Se passar na validação
			if(this.usuarioDAO.cadastrarUsuario(this.usuario) == true) {
				FacesUtil.adicionarMensagemInfo("Usuário cadastrado com sucesso!");
				return("UsuarioListar.xhtml?faces-redirect=true");	//Redireciona o usuário para essa página
			}else {
				FacesUtil.adicionarMensagemErro("Houve um erro ao tentar cadastrar o usuário. Provavelmente o cpf informado já esteja cadastrado.");				
			}
		}
		
		return "";
	}
	
	public void excluirUsuario(ActionEvent evento) {
		this.usuario = (UsuarioBean) evento.getComponent().getAttributes().get("usuarioSelecionado");
		
		if(this.usuarioDAO.deletarUsuario(this.usuario) == true) {
			FacesUtil.adicionarMensagemInfo("Usuário excluido com sucesso.");
		} else {
			FacesUtil.adicionarMensagemErro("Houve um erro ao tentar excluir o usuário.");
		}
	}

	public String editarClicado(UsuarioBean usuarioSelecionado) {
		//https://blog.algaworks.com/facesmessage-depois-do-redirect/ - Nele explica sobre escopo flash
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("usuario", usuarioSelecionado); // incluindo um objeto no escopo Flash
		return "UsuarioEditar.xhtml?faces-redirect=true";	//Chama a outra página que poderá pegar o objeto do escopo flash
	}
	
	public String editarUsuario() {		
		if(this.validarCamposCustom() == true) {	//Se passar na validação
			if(this.usuarioDAO.editarUsuario(this.usuario) == true) {
				FacesUtil.adicionarMensagemInfo("Usuário editado com sucesso!");
				return("UsuarioListar.xhtml?faces-redirect=true");	//Redireciona o usuário para essa página
			}else {
				FacesUtil.adicionarMensagemErro("Houve um erro ao tentar editar o usuário.");
			}
		}
		
		return "";
	}

}
