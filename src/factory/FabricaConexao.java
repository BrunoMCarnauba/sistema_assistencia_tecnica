package factory;

import java.sql.Connection;
import java.sql.DriverManager;

public class FabricaConexao {

	private static final String driver = "org.postgresql.Driver";	//Indentificação do banco de dados
	private static final String caminho = "jdbc:postgresql://localhost:5432/sistema_assistencia_tecnica";		//Local do banco de dados (A última palavra é o nome do banco)
	private static final String usuario = "postgres";				//Usuário do banco de dados
	private static final String senha = "post";						//Senha do banco de dados
	private static Connection conexao = null;
	
	
	public static Connection getConnection() {	//Realiza conexão com o banco de dados
		try {	//Tenta conectar-se
			Class.forName(driver);	//Qual driver será usado para realizar conexão
			conexao = DriverManager.getConnection(caminho, usuario, senha);	//Realiza a conexão com o banco
		}catch (Exception e) {	//Se não conseguir conectar-se
			throw new RuntimeException("Erro na conexão: ",e);
		}		
		return conexao;
	}
	
	public static void closeConnection() {	//Fecha a conexão com o banco de dados
		if (conexao != null) {	//Se estiver conectado
			try {
				conexao.close();	//Fecha a conexão
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}
