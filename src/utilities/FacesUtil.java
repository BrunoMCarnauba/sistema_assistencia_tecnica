package utilities;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class FacesUtil {
//Tutorial - Exibi��o de mensagens: https://youtu.be/O9KyLxzpxdQ de S�rgio Delfino
//Como exibir a FacesMessage depois do redirect: https://blog.algaworks.com/facesmessage-depois-do-redirect/
		
	public static void adicionarMensagemInfo(String mensagem) {
		FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, mensagem, mensagem);	//Par�metros: Grau do erro, erro resumido e erro detalhado
		
		FacesContext facesContext = FacesContext.getCurrentInstance();	//Captura o contexto do JSF
		facesContext.addMessage(null, facesMessage);	//Adiciona a mensagem no contexto
		facesContext.getExternalContext().getFlash().setKeepMessages(true);	//Para manter as mensagens durante um tempo suficiente para mostr�-la ap�s um redirect
	} 
	
	public static void adicionarMensagemErro(String mensagem) {
		FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagem, mensagem);	//Par�metros: Grau do erro, erro resumido e erro detalhado
		
		FacesContext facesContext = FacesContext.getCurrentInstance();	//Captura o contexto do JSF
		facesContext.addMessage(null, facesMessage);	//Adiciona a mensagem no contexto
	}
}
