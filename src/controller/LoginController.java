package controller;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.HttpSession;

import dao.UsuarioDao;
import model.UsuarioBean;
import utilities.FacesUtil;
import utilities.SessionUtil;

//Tutoriais para fazer o login: https://www.youtube.com/watch?v=WpaKxSpCqzg&list=PL_GwGUsBlNyfI0W3ggfffhBdJUqB4981Z&index=141 por S�rgio Delfino
//E PhaseListener: https://www.youtube.com/watch?v=oHG04WIZLIk&list=PL_GwGUsBlNyfI0W3ggfffhBdJUqB4981Z&index=158 por S�rgio Delfino

@SuppressWarnings("serial")
@ManagedBean
@SessionScoped	//O controller ir� existir enquanto a sess�o existir
public class LoginController implements Serializable{
	
	private UsuarioBean usuario = new UsuarioBean();

	private UsuarioDao usuarioDAO = new UsuarioDao();
	
	public UsuarioBean getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioBean usuario) {
		this.usuario = usuario;
	}
	
	
	public String autenticarUsuario() {
		UsuarioBean usuarioAutenticado = this.usuarioDAO.autenticarUsuario(this.usuario);
		if(usuarioAutenticado != null) {
			HttpSession sessao = SessionUtil.getSession();
			sessao.setAttribute("usuario", usuarioAutenticado);
			
			FacesUtil.adicionarMensagemInfo("Bem-vindo, "+usuarioAutenticado.getNome()+"!");
			
			return "Home.xhtml?faces-redirect=true";
		} else {
			FacesUtil.adicionarMensagemErro("Email ou senha inv�lido");
			return "Login.xhtml?faces-redirect=true";
		}
	}
	
	public String logout() {
		SessionUtil.invalidate();
		return "Login.xhtml?faces-redirect=true";
	}
	
}
