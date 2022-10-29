package java_bd.pagamento;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import java_bd.cliente.Cliente;
import java_bd.cliente.DaoCliente;
import java_bd.curso.Curso;
import java_bd.curso.DaoCurso;

public class PagamentoApp {
	
	private Curso curso;
	private Cliente cliente;
	private Pagamento pag;
	private DaoPagamento daopag;
	private DaoCurso daoCurso;
	private DaoCliente daoCliente;
	
	
	public PagamentoApp() {
		curso = new Curso();
		cliente = new Cliente();
		pag = new Pagamento();
		daopag = new DaoPagamento();
		daoCurso = new DaoCurso();
		daoCliente = new DaoCliente();		
	}
	
	public void cadastrarPagamento() {
		
		String option = "";
		String cpf = "";
		
		while (!option.equalsIgnoreCase("Sair")) {
			
			String[] opcao = { "Cadastrar pagamento", "Mostrar faturamento", 
					"Mostrar pagamento por cliente", "Sair" };
			
			Object escolha = JOptionPane.showInputDialog(null,
		             "Escolha a operação:", "Sistema de gerenciamento de pagamentos",
		             JOptionPane.INFORMATION_MESSAGE, null,
		             opcao, opcao[0]);
			
			option = escolha.toString();
			
			switch (option) {
			
				case "Cadastrar pagamento":
					
					System.out.println("[1]: Cadastrando pagamento:");
					
					cpf = JOptionPane.showInputDialog(null, "Digite cpf:", "Cadastro de pagamento",
				             JOptionPane.INFORMATION_MESSAGE);
					
					cadastraPagamento(cpf);
					
					break;
					
				case "Mostrar faturamento":
					
					System.out.println("[2]: Faturamento:");
					faturamento();
					break;
					
				case "Mostrar pagamento por cliente":
					
					System.out.println("[3]: Faturamento por cliente:");
					cpf = JOptionPane.showInputDialog(null, "Digite cpf:", "Cadastro de pagamento",
				             JOptionPane.INFORMATION_MESSAGE);
					
					faturamentoPorCliente(cpf);					
					break;
													
				case "Sair":
					System.out.println("Encerrando...");
					break;					
			}
			
		}
	}
	
	private void faturamento() {
		
		double valor_bruto = 0.0;
		
		List<Pagamento> lista = daopag.listar();
		
		for(Pagamento pg: lista) {
			curso = daoCurso.listagemPorId(pg.getChav_estr_curso()); 
			String v = curso.getValor().replace(",", ".");
			BigDecimal valor = converteParaBigDecimal(v);
			
			valor_bruto += valor.doubleValue();			
		}
		
		System.out.println("\nO faturamento total é: R$ "+formataPreco(""+valor_bruto));
		
	}
	
	private void faturamentoPorCliente(String cpf) {
		
		if (!daoCliente.consultaCliente(cpf)) {
			System.out.println("Cliente ainda não cadastrado!");
			return;
		}
		
		Cliente cliente = daoCliente.listagemPorCpf(cpf);
		
		int id_cliente = cliente.getId();
		
		if (!daopag.clienteTemPagamento(id_cliente)) {
			System.out.println("Cliente ainda não possui curso!");
			return;
		}
		
		double valor_bruto = 0.0;
		
		List<Pagamento> lista = daopag.listarPorCliente(id_cliente);
				
		for(Pagamento pg: lista) {
			curso = daoCurso.listagemPorId(pg.getChav_estr_curso()); 
			String v = curso.getValor().replace(",", ".");
			BigDecimal valor = converteParaBigDecimal(v);
			
			valor_bruto += valor.doubleValue();			
		}
		
		System.out.println("\nCliente: "+cliente.getNome()+""
				+ "\nPagamento no total de: R$ "+formataPreco(""+valor_bruto));
		
	}

	private void cadastraPagamento(String cpf) {
		
		if (!daoCliente.consultaCliente(cpf)) {
			System.out.println("Cliente ainda não cadastrado!");
			return;
		}
				
		cliente = daoCliente.listagemPorCpf(cpf);
		
		escolherCurso();
		
		int id_cliente = cliente.getId();
		int id_curso = curso.getId_curso();
		
		if (!daopag.clienteTemCurso(id_cliente, id_curso)) {
			pag.setChav_estr_cliente(id_cliente);
			pag.setChav_estr_curso(id_curso);
			pag.setData(dataAtual());
			
			daopag.inserir(pag);
			
			System.out.println("Pagamento cadastrado com sucesso!");
			
			return;
		}
		
		System.out.println("Cliente já possui esse curso!");
	}
			
	private void escolherCurso() {
		
		String [] opcao;
		
		List <Curso> lista = daoCurso.listar();
		
		opcao = new String[lista.size()];
		
		for (int i = 0; i < lista.size(); i++)
			opcao[i] = lista.get(i).getNome();
		
		Object escolha = JOptionPane.showInputDialog(null,
	             "Escolha o curso:", "Sistema de gerenciamento de pagamentos",
	             JOptionPane.INFORMATION_MESSAGE, null,
	             opcao, opcao[0]);
						
		curso = daoCurso.listagemPorNome(escolha.toString());		
		
	}
	
	private java.sql.Date dataAtual() {
		
		return new java.sql.Date(new Date().getTime()); 
		
	}
	
	private String formataPreco(String s) {
		
		String aux = s;
		
		if(aux.contains(".")) {				
			aux = aux.replace(".", ",");				
			if(aux.indexOf(",") == aux.length()-1)
				aux += "00";
			else if(aux.indexOf(",") == aux.length()-2)
				aux += "0";
			else if(aux.length() - aux.indexOf(",")>3)
				aux = aux.substring(0, aux.indexOf(",")+3);
		}
		else
			aux += ",00";			
		
		return aux;
	}
	
	private BigDecimal converteParaBigDecimal(String s) {
		
		return new BigDecimal(s);
	}


}
