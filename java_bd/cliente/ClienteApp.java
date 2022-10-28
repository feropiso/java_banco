package java_bd.cliente;

import java.util.List;
import javax.swing.JOptionPane;

public class ClienteApp {
	
	private DaoCliente daoCliente;
	private Cliente cliente;
	
	private String nome;
	private String cpf;
	private String email;
	
	public ClienteApp() {
		daoCliente = new DaoCliente();
		cliente = new Cliente();		
	}

	public void iniciar() {
		
		ClienteApp sgbd = new ClienteApp();
		
		String option = "";
				
		while (!option.equalsIgnoreCase("Sair")) {
			
			String[] opcao = { "Mostrar todos os clientes", "Cadastrar novo cliente", 
					"Atualizar cadastro de cliente", "Deletar cliente", "Sair" };
			
			Object escolha = JOptionPane.showInputDialog(null,
		             "Escolha a operação:", "Sistema de gerenciamento de clientes",
		             JOptionPane.INFORMATION_MESSAGE, null,
		             opcao, opcao[0]);
			
			option = escolha.toString();
			
			switch (option) {
			
				case "Mostrar todos os clientes":
					
					System.out.println("[1]: Todos os clientes:");
					sgbd.mostrarListaClientes();
					break;
					
				case "Cadastrar novo cliente":
					
					System.out.println("[2]: Novo cliente:");
					cadastrarInfo();			
					sgbd.novoCliente(cpf, nome, email);
					break;
					
				case "Atualizar cadastro de cliente":
					
					System.out.println("[3]: Atualizar cadastro:");
					
					cpf = JOptionPane.showInputDialog(null, "Digite cpf:", "Cadastro",
				             JOptionPane.INFORMATION_MESSAGE);	
					
					sgbd.atualizarCadastro(cpf);
					break;
					
				case "Deletar cliente":
					
					System.out.println("[4]: Deletar cliente:");
					cpf = JOptionPane.showInputDialog(null, "Digite cpf:", "Cadastro",
				             JOptionPane.INFORMATION_MESSAGE);
					
					sgbd.deletarCliente(cpf);
					break;
									
				case "Sair":
					System.out.println("Encerrando...");
					break;					
			}
			
		}
		
	}
	
	private void cadastrarInfo() {

		cpf = JOptionPane.showInputDialog(null, "Digite cpf:", "Cadastro",
	             JOptionPane.INFORMATION_MESSAGE);
		
		nome = JOptionPane.showInputDialog(null, "Digite nome:", "Cadastro",
	             JOptionPane.INFORMATION_MESSAGE);
		
		email = JOptionPane.showInputDialog(null, "Digite email:", "Cadastro",
	             JOptionPane.INFORMATION_MESSAGE);		
	}
	
	private void mostrarListaClientes() {
		
		List <Cliente> lista = daoCliente.listar();
		
		System.out.println("*******CPF***********NOME********");
		
		lista.forEach(e->System.out.println(e.getCpf()+" <=> "+e.getNome()));
	}
	
	private void novoCliente(String cpf, String nome, String email) {
		
		 if (daoCliente.consultaCliente(cpf)) {
			 System.out.println("Cliente já cadastrado"); 
			 return;
		 }
		 
		 cliente.setCpf(cpf);
		 cliente.setNome(nome);
		 cliente.setEmail(email);
		 
		 daoCliente.inserir(cliente);	
		 
		 System.out.println("Cliente cadastrado com sucesso!");
	}
	
	private void atualizarCadastro(String c) {
		
		cliente = daoCliente.listagemPorCpf(c);
		
		cadastrarInfo();
		
		Cliente cliente_atual = new Cliente();
		
		cliente_atual.setId(cliente.getId());
		
		cliente_atual.setCpf(cpf);
		cliente_atual.setNome(nome);
		cliente_atual.setEmail(email);
		
		if(daoCliente.atualizar(cliente_atual)) {
			System.out.println("Cliente atualizado com sucesso!");
			return;
		}
		
		System.out.println("Não foi possível atualizar.\nTente novamente mais tarde.");			
		
	}
	
	private void deletarCliente(String cpf) {
		
		cliente = daoCliente.listagemPorCpf(cpf);
		
		if(daoCliente.deletar(cliente)){
			System.out.println("Cliente deletado com sucesso!");
			return;
		}
		
		System.out.println("Não foi possível deletar cliente.\nTente novamente mais tarde.");
	}
		

}
