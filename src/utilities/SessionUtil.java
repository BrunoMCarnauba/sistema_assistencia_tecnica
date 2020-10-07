package utilities;

import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

public class SessionUtil implements Serializable {
	
	public static HttpSession getSession() {	//Retorna o contexto da sessão
		FacesContext contexto = FacesContext.getCurrentInstance();
		HttpSession sessao = (HttpSession) contexto.getExternalContext().getSession(false);
		return sessao;
	}
	
	public static void invalidate() {	//Encerra a sessão
		getSession().invalidate();
	}
}
