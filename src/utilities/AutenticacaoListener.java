package utilities;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.omnifaces.util.Faces;

import controller.LoginController;
import model.UsuarioBean;

@SuppressWarnings("serial")
public class AutenticacaoListener implements PhaseListener{
	
	//Observa��o: Nessa classe foram usadas fun��es do omnifaces
	//Tutorial: https://youtu.be/oHG04WIZLIk?list=PL_GwGUsBlNyfI0W3ggfffhBdJUqB4981Z por S�rgio Delfino
	
	@Override
	public void afterPhase(PhaseEvent arg0) {
		String paginaAtual = Faces.getViewId();	//Recebe o "caminho" da p�gina atual. Exemplo: "/pages/Login.xhtml"
		
		boolean isPaginaAutenticacao = paginaAtual.contains("Login.xhtml");	//Verifica se a p�gina em que o usu�rio est� � a de login
		
		if(!isPaginaAutenticacao) {	//Se n�o for a p�gina de autentica��o (que � p�blica) tem que verificar se o usu�rio est� logado, para poder permitir entrar na p�gina que ele quer
			LoginController loginController = Faces.getSessionAttribute("loginController");
			
			if(loginController == null) {	//Se o usu�rio nunca entrou na tela de login (nunca instanciou o ManagedBean)
				Faces.navigate("/pages/Login.xhtml");	//Redireciona para a p�gina de login
				return; 
			}
			
			UsuarioBean usuario = (UsuarioBean) SessionUtil.getSession().getAttribute("usuario");	//Pegando o objeto usu�rio que est�o na sess�o
			if(usuario == null) {	//Se n�o estiver logado
				Faces.navigate("/pages/Login.xhtml");	//Redireciona para a p�gina de login
				return; 
			}
		}
	}

	@Override
	public void beforePhase(PhaseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

}
