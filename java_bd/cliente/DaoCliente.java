package java_bd.cliente;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import java_bd.Dao;

public class DaoCliente extends Dao<Cliente> {

	@Override
	public List<Cliente> listar() {
		
		List <Cliente> lista = new ArrayList<>();	
		
		try {
						
			Statement estamento = criaConexao().createStatement();	
			ResultSet result = estamento.executeQuery("SELECT * FROM clientes order by id_cliente ASC");
						
			while(result.next()) {
				
				Cliente cliente = new Cliente();
				
				cliente.setId(result.getInt("id_cliente"));
				cliente.setCpf(result.getString("cpf"));
				cliente.setNome(result.getString("nome"));
				cliente.setEmail(result.getString("email"));
				
				lista.add(cliente);
			}		
		}		
		catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return lista;
	}

	@Override
	public void inserir(Cliente cliente) {
		
		try {
			
			PreparedStatement stmt = criaConexao().prepareStatement(
				"INSERT INTO clientes (cpf, nome, email) values (?,?,?)",
				Statement.RETURN_GENERATED_KEYS);
			
				stmt.setString(1, cliente.getCpf());
				stmt.setString(2, cliente.getNome());
				stmt.setString(3, cliente.getEmail());								
				stmt.executeUpdate();    
				
			    ResultSet rs = stmt.getGeneratedKeys();
			        
			    while (rs.next()) 
			    	cliente.setId(rs.getInt(1)); 	
			    
			    stmt.close();									
		}
		catch (SQLException e) {			
			e.printStackTrace();
		}			
	}
	
	public boolean consultaCliente(String cpf) {
		
		try {
			
			Statement estamento = criaConexao().createStatement();	
			ResultSet result = estamento.executeQuery("SELECT count(id_cliente) "
					+ "as quantidade FROM clientes WHERE cpf='"+cpf+"'");
				
			if(result.next()) {
								
				if(result.getInt("quantidade")>0)
					return true;			
			}		
		}		
		catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return false;		
	}
	
	@Override
	public boolean deletar(Cliente cliente) {
		
		try {
			
			if(!consultaCliente(cliente.getCpf()))
				return false;
			
			PreparedStatement stmt = criaConexao().prepareStatement(
						"DELETE from clientes WHERE id_cliente=?");
			
			stmt.setInt(1, cliente.getId());	
			
			stmt.executeUpdate();    

		    stmt.close();
		}
		catch (SQLException e) {			
			
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	@Override
	public boolean atualizar(Cliente cliente) {
		
		try {
			
			PreparedStatement stmt = criaConexao().prepareStatement(
						"UPDATE clientes SET cpf=?, nome=?, email=? WHERE id_cliente=?");
			
			stmt.setString(1, cliente.getCpf());
			stmt.setString(2, cliente.getNome());	
			stmt.setString(3, cliente.getEmail());
			stmt.setInt(4, cliente.getId());	
			
			stmt.executeUpdate();    

		    stmt.close();
		}
		catch (SQLException e) {			
			
			e.printStackTrace();
			return false;
		}
		
		return true;	
	}
	
	public Cliente listagemPorCpf(String cpf) {
		
		Cliente cliente = null;
		
		try {
			
			Statement estamento = criaConexao().createStatement();	
			ResultSet result = estamento.executeQuery("SELECT * FROM clientes where cpf='"+cpf+"'");
					
			if(result.next()) {
				
				cliente = new Cliente();
				
				cliente.setId(result.getInt("id_cliente"));
				cliente.setCpf(result.getString("cpf"));
				cliente.setNome(result.getString("nome"));
				cliente.setEmail(result.getString("email"));
			}		
		}		
		catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return cliente;	
	}

}

