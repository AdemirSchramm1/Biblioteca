package modelos;

import java.sql.Date;

public class Usuario extends Livro {
	private String nome;
	private String cpf;
	private Date data_alugado;
	private Date data_devolver;

	public Usuario() {
	}
	@Override
	public String toString() {
		return "cpf: " + cpf + " isbn: " + isbn + " data alugado: " 
	+ data_alugado + " data devolver: " + data_devolver;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public void setData_alugado(Date data_alugado) {
		this.data_alugado = data_alugado;
	}

	public Date getData_alugado() {
		return data_alugado;
	}

	public void setData_devolver(Date data_devolver) {
		this.data_devolver = data_devolver;
	}

	public Date getData_devolver() {
		return data_devolver;
	}
}

