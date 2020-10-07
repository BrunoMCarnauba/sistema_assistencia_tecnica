package factory;

import java.sql.Connection;
import java.sql.DriverManager;

public class FabricaConexao {

	private static final String driver = "org.postgresql.Driver";	//Indentifica��o do banco de dados
	private static final String caminho = "jdbc:postgresql://localhost:5432/sistema_assistencia_tecnica";		//Local do banco de dados (A �ltima palavra � o nome do banco)
	private static final String usuario = "postgres";				//Usu�rio do banco de dados
	private static final String senha = "post";						//Senha do banco de dados
	private static Connection conexao = null;
	
	
	public static Connection getConnection() {	//Realiza conex�o com o banco de dados
		try {	//Tenta conectar-se
			Class.forName(driver);	//Qual driver ser� usado para realizar conex�o
			conexao = DriverManager.getConnection(caminho, usuario, senha);	//Realiza a conex�o com o banco
		}catch (Exception e) {	//Se n�o conseguir conectar-se
			throw new RuntimeException("Erro na conex�o: ",e);
		}		
		return conexao;
	}
	
	public static void closeConnection() {	//Fecha a conex�o com o banco de dados
		if (conexao != null) {	//Se estiver conectado
			try {
				conexao.close();	//Fecha a conex�o
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}
