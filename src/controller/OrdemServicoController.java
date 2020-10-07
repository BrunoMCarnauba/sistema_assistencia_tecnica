package controller;

import java.io.Serializable;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import dao.ClienteDao;
import dao.OrdemServicoDao;
import dao.PecaDao;
import dao.UsuarioDao;
import model.ClienteBean;
import model.OrdemServicoBean;
import model.PecaBean;
import model.UsuarioBean;
import utilities.FacesUtil;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class OrdemServicoController implements Serializable{

	private OrdemServicoBean ordemServico = new OrdemServicoBean();
	
	private OrdemServicoDao ordemServicoDAO = new OrdemServicoDao();
	private ClienteDao clienteDAO = new ClienteDao();
	private UsuarioDao usuarioDAO = new UsuarioDao();
	private PecaDao pecaDao = new PecaDao();
	
	public OrdemServicoBean getOrdemServico() {
		return ordemServico;
	}

	public void setOrdemServico(OrdemServicoBean ordemServico) {
		this.ordemServico = ordemServico;
	}
	
	public ArrayList<OrdemServicoBean> listarOrdensServicos(){
		return this.ordemServicoDAO.listarOrdensServicos();
	}
	
	public ArrayList<ClienteBean> listarClientes(){
		return this.clienteDAO.listarClientes();
	}
	
	public ArrayList<UsuarioBean> listarUsuarios(){
		return this.usuarioDAO.listarUsuarios();
	}
	
	public ArrayList<PecaBean> listarPecas(){
		return this.pecaDao.listarPecas();
	}
	
	public void novaOS() {
		this.ordemServico = new OrdemServicoBean();
	}
	
	public void btnSalvar() {
		if(this.ordemServico.getId() == 0) {
			this.cadastrarOrdemServico();
		}else {
			this.editarOrdemServico();
		}
	}
	
	public void cadastrarOrdemServico() {
		if(this.ordemServicoDAO.cadastrarOrdemServico(this.ordemServico) == true) {
			FacesUtil.adicionarMensagemInfo("Ordem de serviço cadastrada com sucesso!");
			this.novaOS();
		}else {
			FacesUtil.adicionarMensagemErro("Houve um erro ao tentar cadastrar a ordem de serviço.");
		}
	}
	
	public void btnEditar(ActionEvent evento) {
		this.ordemServico = (OrdemServicoBean) evento.getComponent().getAttributes().get("ordemServicoSelecionada");
	}
	
	public void editarOrdemServico() {
		if(this.ordemServicoDAO.editarCadastro(this.ordemServico) == true) {
			FacesUtil.adicionarMensagemInfo("Ordem de serviço editado com sucesso!");
		}else {
			FacesUtil.adicionarMensagemErro("Houve um erro ao tentar editar a ordem de serviço.");
		}
	}
	
	public void deletarOrdemServico(ActionEvent evento) {
		this.ordemServico = (OrdemServicoBean) evento.getComponent().getAttributes().get("ordemServicoSelecionada");
		
		if(this.ordemServicoDAO.deletarOrdemServico(this.ordemServico) == true) {
			FacesUtil.adicionarMensagemInfo("Ordem de serviço deletada com sucesso!");
		} else {
			FacesUtil.adicionarMensagemErro("Houve um erro ao tentar deletar a ordem de serviço.");
		}
	}
	
	public void btnAtualizar(ActionEvent evento) {
		this.ordemServico = (OrdemServicoBean) evento.getComponent().getAttributes().get("ordemServicoSelecionada");
	}
	
	public void atualizarOrdemServico() {
		if(this.ordemServicoDAO.atualizarOrdemServico(this.ordemServico) == true) {
			FacesUtil.adicionarMensagemInfo("Ordem de serviço atualizada com sucesso!");
		} else {
			FacesUtil.adicionarMensagemErro("Houve um erro ao tentar atualizar a ordem de serviço.");
		}
	}
	
	public void efetuarPagamento() {
		FacesUtil.adicionarMensagemInfo("A funcionalidade de pagamento ainda não está disponível.");
	}
	
}
