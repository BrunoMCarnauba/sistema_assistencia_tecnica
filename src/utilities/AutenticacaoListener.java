package utilities;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.omnifaces.util.Faces;

import controller.LoginController;
import model.UsuarioBean;

@SuppressWarnings("serial")
public class AutenticacaoListener implements PhaseListener{
	
	//Observação: Nessa classe foram usadas funções do omnifaces
	//Tutorial: https://youtu.be/oHG04WIZLIk?list=PL_GwGUsBlNyfI0W3ggfffhBdJUqB4981Z por Sérgio Delfino
	
	@Override
	public void afterPhase(PhaseEvent arg0) {
		String paginaAtual = Faces.getViewId();	//Recebe o "caminho" da página atual. Exemplo: "/pages/Login.xhtml"
		
		boolean isPaginaAutenticacao = paginaAtual.contains("Login.xhtml");	//Verifica se a página em que o usuário está é a de login
		
		if(!isPaginaAutenticacao) {	//Se não for a página de autenticação (que é pública) tem que verificar se o usuário está logado, para poder permitir entrar na página que ele quer
			LoginController loginController = Faces.getSessionAttribute("loginController");
			
			if(loginController == null) {	//Se o usuário nunca entrou na tela de login (nunca instanciou o ManagedBean)
				Faces.navigate("/pages/Login.xhtml");	//Redireciona para a página de login
				return; 
			}
			
			UsuarioBean usuario = (UsuarioBean) SessionUtil.getSession().getAttribute("usuario");	//Pegando o objeto usuário que estão na sessão
			if(usuario == null) {	//Se não estiver logado
				Faces.navigate("/pages/Login.xhtml");	//Redireciona para a página de login
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
