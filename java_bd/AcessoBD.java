package java_bd;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import java_bd.cliente.ClienteApp;
import java_bd.curso.CursoApp;

public class AcessoBD {
	
	
	private static void mostrarMetaDados() {
		
		try {
			
			DatabaseMetaData meta = Dao.criaConexao().getMetaData();
			String fabricante = meta.getDatabaseProductName();
			String versao = meta.getDatabaseProductVersion();
			System.out.println(fabricante+" <=> "+versao);
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
	}
	
	
	public static void main(String[] args) {
		
		String option = "";
		
		while (!option.equalsIgnoreCase("Sair")) {
			
			String[] opcao = { "Clientes", "Cursos", "Informaçoes do BD", "Sair" };
			
			Object escolha = JOptionPane.showInputDialog(null,
		             "Escolha a consulta:", "Cursos Netbiiss",
		             JOptionPane.INFORMATION_MESSAGE, null,
		             opcao, opcao[0]);
			
			option = escolha.toString();
			
			switch (option) {
			
				case "Clientes":
					
					ClienteApp cliente = new ClienteApp();					
					cliente.iniciar();					
					break;
					
				case "Cursos":
					
					CursoApp curso = new CursoApp();					
					curso.iniciar();
					break;
					
				case "Informaçoes do BD":
					
					mostrarMetaDados();
					break;
													
				case "Sair":
					System.out.println("Encerrando...");
					break;					
			}
			
		}
			
		
	}
	

}
