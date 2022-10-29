package java_bd.curso;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import java_bd.Dao;


public class DaoCurso extends Dao<Curso> {

	@Override
	public List<Curso> listar() {
		
		List <Curso> lista = new ArrayList<>();	
		
		try {
						
			Statement estamento = criaConexao().createStatement();	
			ResultSet result = estamento.executeQuery("SELECT * FROM cursos order by id_curso ASC");
						
			while(result.next()) {
				
				Curso curso = new Curso();
				
				curso.setId_curso(result.getInt("id_curso"));
				curso.setNome(result.getString("nome"));
				curso.setValor(result.getString("valor"));
				curso.setUrl(result.getString("url"));
								
				lista.add(curso);
			}		
		}		
		catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return lista;
	}

	@Override
	public void inserir(Curso curso) {
		
		try {
			
			PreparedStatement stmt = criaConexao().prepareStatement(
				"INSERT INTO cursos (nome, valor, url) values (?,?,?)",
				Statement.RETURN_GENERATED_KEYS);
			
				stmt.setString(1, curso.getNome());
				stmt.setString(2, curso.getValor());
				stmt.setString(3, curso.getUrl());								
				stmt.executeUpdate();    
				
			    ResultSet rs = stmt.getGeneratedKeys();
			        
			    while (rs.next())
			    	curso.setId_curso(rs.getInt(1));
			    	
			    stmt.close();									
		}
		catch (SQLException e) {			
			e.printStackTrace();
		}		
		
	}
	
	public boolean consultaCurso(String nome) {
		
		try {
			
			Statement estamento = criaConexao().createStatement();	
			ResultSet result = estamento.executeQuery("SELECT count(id_curso) "
					+ "as quantidade FROM cursos WHERE nome='"+nome+"'");
				
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
	public boolean deletar(Curso curso) {
		
		try {
			
			if(!consultaCurso(curso.getNome()))
				return false;
			
			PreparedStatement stmt = criaConexao().prepareStatement(
						"DELETE from cursos WHERE id_curso=?");
			
			stmt.setInt(1, curso.getId_curso());	
			
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
	public boolean atualizar(Curso curso) {
		
		try {
			
			PreparedStatement stmt = criaConexao().prepareStatement(
						"UPDATE cursos SET nome=?, valor=?, url=? WHERE id_curso=?");
			
			stmt.setString(1, curso.getNome());
			stmt.setString(2, curso.getValor());
			stmt.setString(3, curso.getUrl());
			stmt.setInt(4, curso.getId_curso());	
			
			stmt.executeUpdate();    

		    stmt.close();
		}
		catch (SQLException e) {			
			
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public Curso listagemPorNome(String nome) {
		
		Curso curso = null;
		
		try {
			
			Statement estamento = criaConexao().createStatement();	
			ResultSet result = estamento.executeQuery("SELECT * FROM cursos where nome='"+nome+"'");
					
			if(result.next()) {
				
				curso = new Curso();
				
				curso.setId_curso(result.getInt("id_curso"));
				curso.setNome(result.getString("nome"));
				curso.setValor(result.getString("valor"));
				curso.setUrl(result.getString("url"));
			}		
		}		
		catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return curso;	
	}
	
	public Curso listagemPorId(int id) {
		
		Curso curso = null;
		
		try {
			
			Statement estamento = criaConexao().createStatement();	
			ResultSet result = estamento.executeQuery("SELECT * FROM cursos where id_curso="+id);
					
			if(result.next()) {
				
				curso = new Curso();
				
				curso.setId_curso(result.getInt("id_curso"));
				curso.setNome(result.getString("nome"));
				curso.setValor(result.getString("valor"));
				curso.setUrl(result.getString("url"));
			}		
		}		
		catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return curso;	
	}

}
