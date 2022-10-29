package java_bd.curso;

import java.util.List;
import javax.swing.JOptionPane;

public class CursoApp {
	
	private DaoCurso daoCurso;
	private Curso curso;
	
	private String nome;
	private String valor;
	private String url;
	
	public CursoApp() {
		daoCurso = new DaoCurso();
		curso = new Curso();		
	}

	public void iniciar() {
		
		CursoApp curso = new CursoApp();
		
		String option = "";
				
		while (!option.equalsIgnoreCase("Sair")) {
			
			String[] opcao = { "Mostrar todos os cursos", "Cadastrar novo curso", 
					"Atualizar informações de curso", "Deletar curso", "Sair" };
			
			Object escolha = JOptionPane.showInputDialog(null,
		             "Escolha a operação:", "Sistema de gerenciamento de cursos",
		             JOptionPane.INFORMATION_MESSAGE, null,
		             opcao, opcao[0]);
			
			option = escolha.toString();
			
			switch (option) {
			
				case "Mostrar todos os cursos":
					
					System.out.println("[1]: Todos os cursos:");
					curso.mostrarListaCursos();
					break;
					
				case "Cadastrar novo curso":
					
					System.out.println("[2]: Novo curso:");
					cadastrarInfo();			
					curso.novoCurso(nome, valor, url);
					break;
					
				case "Atualizar informações de curso":
					
					System.out.println("[3]: Atualizar informações de curso:");
					
					nome = JOptionPane.showInputDialog(null, "Digite nome do curso:", "Cadastro",
				             JOptionPane.INFORMATION_MESSAGE);	
					
					curso.atualizarCurso(nome);
					break;
					
				case "Deletar curso":
					
					System.out.println("[4]: Deletar curso:");
					nome = JOptionPane.showInputDialog(null, "Digite nome do curso:", "Cadastro",
				             JOptionPane.INFORMATION_MESSAGE);
					
					curso.deletarCurso(nome);
					break;
									
				case "Sair":
					System.out.println("Encerrando...");
					break;					
			}
			
		}
		
	}
	
	private void cadastrarInfo() {
		
		nome = JOptionPane.showInputDialog(null, "Digite nome do curso:", "Cadastro",
	             JOptionPane.INFORMATION_MESSAGE);

		valor = JOptionPane.showInputDialog(null, "Digite o valor do curso em reais (R$):", "Cadastro",
	             JOptionPane.INFORMATION_MESSAGE);
				
		url = JOptionPane.showInputDialog(null, "Digite a url do curso:", "Cadastro",
	             JOptionPane.INFORMATION_MESSAGE);		
	}
	
	private void mostrarListaCursos() {
		
		List <Curso> lista = daoCurso.listar();
		
		System.out.println("*******Nome***********URL********");
		
		lista.forEach(e->System.out.println(e.getNome()+" <=> "+e.getUrl()));
	}
	
	private void novoCurso(String nome, String valor,  String url) {
		
		 if (daoCurso.consultaCurso(nome)) {
			 System.out.println("Curso já cadastrado"); 
			 return;
		 }
		 
		 curso.setNome(nome);
		 curso.setValor(valor);
		 curso.setUrl(url);
		 
		 daoCurso.inserir(curso);	
		 
		 System.out.println("Curso cadastrado com sucesso!");
	}
	
	private void atualizarCurso(String n) {
		
		curso = daoCurso.listagemPorNome(n);
		
		cadastrarInfo();
		
		Curso curso_atual = new Curso();
		
		curso_atual.setId_curso(curso.getId_curso());
		
		curso_atual.setNome(nome);
		curso_atual.setValor(valor);
		curso_atual.setUrl(url);
		
		if(daoCurso.atualizar(curso_atual)) {
			System.out.println("Curso atualizado com sucesso!");
			return;
		}
		
		System.out.println("Não foi possível atualizar.\nTente novamente mais tarde.");			
		
	}
	
	private void deletarCurso(String nome) {
		
		curso = daoCurso.listagemPorNome(nome);
		
		if(daoCurso.deletar(curso)){
			System.out.println("Curso deletado com sucesso!");
			return;
		}
		
		System.out.println("Não foi possível deletar cliente.\nTente novamente mais tarde.");
	}
		

}

