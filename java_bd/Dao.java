package java_bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public abstract class Dao<T> {
	
	private static Connection conexao;
	
	public abstract List<T> listar(); 
	
	public abstract void inserir(T a);
	
	public abstract boolean deletar(T b);
	
	public abstract boolean atualizar(T c);		
	
	public static Connection criaConexao() {
		
		try {		
			
			Class.forName("com.mysql.jdbc.Driver");			  
			
			if(conexao==null)
				conexao = DriverManager.getConnection("jdbc:mysql://localhost/formacaojava", "root", "");
		} 
		
		catch (SQLException e) {		
			e.printStackTrace();
		}
		
		catch (ClassNotFoundException e) {			
			e.printStackTrace();
		}
		return conexao;	
	}

	
	public static boolean temConexao() {
		
		return conexao != null;
	}

}
