package java_bd.pagamento;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java_bd.Dao;

public class DaoPagamento extends Dao<Pagamento> {

	@Override
	public List<Pagamento> listar() {
		
		List<Pagamento> lista = new ArrayList<>();
		
		try {
			
			Statement estamento = criaConexao().createStatement();	
			ResultSet result = estamento.executeQuery("SELECT * FROM pagamentos");
					
		
			while(result.next()) {
				
				Pagamento pagamento = new Pagamento();
				
				pagamento.setId(result.getInt("id_pagamento"));
				pagamento.setChav_estr_cliente(result.getInt("fk_cliente"));
				pagamento.setChav_estr_curso(result.getInt("fk_curso"));
				pagamento.setData(result.getDate("data"));
				
				lista.add(pagamento);					
			}		
		}		
		catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return lista;
		
	}

	@Override
	public void inserir(Pagamento pagamento) {
		
		try {
			
			PreparedStatement stmt = criaConexao().prepareStatement(
				"INSERT INTO pagamentos (fk_cliente, fk_curso, data) VALUES (?,?,?)",
				Statement.RETURN_GENERATED_KEYS);
				
				stmt.setInt(1, pagamento.getChav_estr_cliente());
				stmt.setInt(2, pagamento.getChav_estr_curso());
				stmt.setDate(3, pagamento.getData());
				
				stmt.executeUpdate();    
				
			    ResultSet rs = stmt.getGeneratedKeys();
			        
			    while (rs.next())
			    	pagamento.setId(rs.getInt(1));
			    	 			             
			    stmt.close();
			
		}
		catch (SQLException e) {			
			e.printStackTrace();
		}
		
	}

	@Override
	public boolean deletar(Pagamento pagamento) {
		
		return false;
	}

	@Override
	public boolean atualizar(Pagamento pagamento) {
		
		return false;
	}
	
	public boolean clienteTemCurso(int id_cliente, int id_curso) {
		
		try {
			
			Statement estamento = criaConexao().createStatement();	
			ResultSet result = estamento.executeQuery("SELECT count(id_pagamento) as quantidade "
					+ "FROM pagamentos WHERE fk_cliente="+id_cliente+" AND fk_curso="+id_curso);
				
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
	
	public boolean clienteTemPagamento(int id_cliente) {
		
		try {
			
			Statement estamento = criaConexao().createStatement();	
			ResultSet result = estamento.executeQuery("SELECT count(id_pagamento) as quantidade "
					+ "FROM pagamentos WHERE fk_cliente="+id_cliente);
				
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
	
	public List<Pagamento> listarPorCliente(int id) {
		
		List<Pagamento> lista = new ArrayList<>();
		
		try {
			
			Statement estamento = criaConexao().createStatement();	
			ResultSet result = estamento.executeQuery("SELECT * FROM pagamentos WHERE fk_cliente="+id);
					
		
			while(result.next()) {
				
				Pagamento pagamento = new Pagamento();
				
				pagamento.setId(result.getInt("id_pagamento"));
				pagamento.setChav_estr_cliente(result.getInt("fk_cliente"));
				pagamento.setChav_estr_curso(result.getInt("fk_curso"));
				pagamento.setData(result.getDate("data"));
				
				lista.add(pagamento);					
			}		
		}		
		catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return lista;
		
	}


}
