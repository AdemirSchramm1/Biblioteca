package modelos;

public class Funcionario {
	private String nomeFuncionario;
	private String senha;

	public Funcionario() {
	}

	@Override
	public String toString() {
		return "nome: " + nomeFuncionario + ", senha: " + senha;
	}

	public String getNomeFuncionario() {
		return nomeFuncionario;
	}

	public String getSenha() {
		return senha;
	}

	public void setNomeFuncionario(String nomeFuncionario) {
		this.nomeFuncionario = nomeFuncionario;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
