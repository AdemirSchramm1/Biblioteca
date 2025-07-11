package modelos;

public class Livro {
	public int isbn;
	public String autor;
	public String titulo;
	public String data_lancamento;
	public boolean alugado;
	
	public Livro() {
	}

	public String toString() {
		if (alugado) {
			return "isbn: " + isbn + ", autor: " + autor + ", titulo: " + titulo + ", data_lancamento: "
					+ data_lancamento + ", alugado: " + "sim";
		} else {
			return "isbn: " + isbn + ", autor: " + autor + ", titulo: " + titulo + ", data_lancamento: "
					+ data_lancamento + ", alugado: " + "não";
		}

	}

	public int getIsbn() {
		return isbn;
	}

	public String getAutor() {
		return autor;
	}

	public String getTitulo() {
		return titulo;
	}

	public String getData_lancamento() {
		return data_lancamento;
	}

	public void setIsbn(int isbn) {
		this.isbn = isbn;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public void setData_lancamento(String data_lancamento) {
		this.data_lancamento = data_lancamento;
	}

	public  boolean isAlugado() {
		return alugado;
	}

	public void setAlugado(boolean alugado) {
		this.alugado = alugado;
	}

}

