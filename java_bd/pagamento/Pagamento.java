package java_bd.pagamento;

import java.sql.Date;

public class Pagamento {
	
	private int id;
	private int chav_estr_cliente;
	private int chav_estr_curso;
	private Date data;
	
	private String data_aux;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getChav_estr_cliente() {
		return chav_estr_cliente;
	}

	public void setChav_estr_cliente(int chav_estr_cliente) {
		this.chav_estr_cliente = chav_estr_cliente;
	}

	public int getChav_estr_curso() {
		return chav_estr_curso;
	}

	public void setChav_estr_curso(int chav_estr_curso) {
		this.chav_estr_curso = chav_estr_curso;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getData_aux() {
		return data_aux;
	}

	public void setData_aux(String data_aux) {
		this.data_aux = data_aux;
	}
	
	
	

}
