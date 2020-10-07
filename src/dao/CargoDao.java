package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import factory.FabricaConexao;
import model.CargoBean;

public class CargoDao {
	
	private FabricaConexao fabricaConexao;
    private Connection conexao = null;
	
    public CargoDao() {
    	this.fabricaConexao = new FabricaConexao();
    }
    
    public CargoBean getCargoPorID(int idCargo) {
    	try {
	    	this.conexao = fabricaConexao.getConnection();
	    	CargoBean cargo = new CargoBean();
	    	
	    	String sql = "SELECT * FROM cargos WHERE id_cargo = ? ";
	    	PreparedStatement ps = this.conexao.prepareStatement(sql);
	    	ps.setInt(0, idCargo);
	    	
	    	ResultSet resultado = ps.executeQuery();
	    	if(resultado.next()) {
	    		cargo.setId(resultado.getInt("id_cargo"));
	    		cargo.setNome(resultado.getString("nome"));
	    		cargo.setDescricao(resultado.getString("descricao"));
	    		cargo.setSalario(resultado.getFloat("salario"));
	    	}
	    	
	    	return cargo;
    	} catch (SQLException excecao){
			excecao.printStackTrace();
    	} finally {
    		this.fabricaConexao.closeConnection();
    	}
    	
    	return null;
    }
    
    public ArrayList<CargoBean> listarCargos(){

    	try {
    		this.conexao = fabricaConexao.getConnection();
    		ArrayList<CargoBean> listaCargos = new ArrayList<>();
    		
        	String sql = "SELECT * FROM cargos";
			PreparedStatement ps = this.conexao.prepareStatement(sql);
			ResultSet resultados = ps.executeQuery();
			while(resultados.next()) {	//Enquanto houver resultado
				CargoBean cargo = new CargoBean();
				cargo.setId(resultados.getInt("id_cargo"));
				cargo.setNome(resultados.getString("nome"));
				cargo.setDescricao(resultados.getString("descricao"));
				cargo.setSalario(resultados.getFloat("salario"));
				listaCargos.add(cargo);
			}
			
			return listaCargos;
			
		} catch (SQLException excecao) {
			excecao.printStackTrace();
		} finally {	//https://www.guj.com.br/t/return-dentro-de-try-executa-finally-mesmo-assim-resolvido/290749/2
			this.fabricaConexao.closeConnection();
		}
    	
    	return null;
    	
    }
    
}
