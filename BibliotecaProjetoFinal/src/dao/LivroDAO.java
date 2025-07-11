package dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import modelos.Livro;
import modelos.Usuario;
import utils.ConexaoDB;

public class LivroDAO {
	public static void selectLivroEspecifico(Livro livro) {
		String slq = "SELECT * FROM tb_livro WHERE titulo LIKE ?";
		Connection con = ConexaoDB.getConexao();
		try {
			PreparedStatement stm = con.prepareStatement(slq);
			stm.setString(1, "%" + livro.getTitulo() + "%");
			ResultSet rs = stm.executeQuery();
			while(rs.next()) {
				Livro livroLista = new Livro();
				livroLista.setAutor(rs.getString("autor"));
				livroLista.setTitulo(rs.getString("titulo"));
				livroLista.setIsbn(rs.getInt("isbn"));
				livroLista.setData_lancamento(rs.getString("data_lancamento"));
				livroLista.setAlugado(rs.getBoolean("alugado"));
				System.out.println(livroLista.toString());
			}
		} catch (SQLException e) {
			System.out.println("Erro ao pesquisar Livro: " + e.getMessage());
		}
	}

	public static void delete(Livro livro) {

		String sql = "DELETE FROM tb_livro WHERE isbn = ?";
		Connection con = ConexaoDB.getConexao();
		try {
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setInt(1, livro.getIsbn());
			stm.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Erro ao excluir o livro: " + e.getMessage());
		}
	}

	public static void update(Livro livro) {
		String sql = "UPDATE tb_livro SET autor = ?, titulo = ?, data_lancamento = ? WHERE isbn = ?";
		Connection con = ConexaoDB.getConexao();
		try {
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setString(1, livro.getAutor());
			stm.setString(2, livro.getTitulo());
			stm.setString(3, livro.getData_lancamento());
			stm.setInt(4, livro.getIsbn());
			stm.executeUpdate();
			System.out.println("Livro alterado com sucesso!");

		} catch (SQLException e) {
			System.out.println("Erro ao alterar o livro: " + e.getMessage());
		}
	}

	public static void insert(Livro livro) {
		String sql = "INSERT INTO tb_livro(autor , titulo, data_lancamento)VALUES(?, ?, ?)";
		Connection con = ConexaoDB.getConexao();
		try {
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setString(1, livro.getAutor());
			stm.setString(2, livro.getTitulo());
			stm.setString(3, livro.getData_lancamento());
			stm.executeUpdate();
			System.out.println("Livro adicionado com sucesso!");
		} catch (SQLException e) {
			System.out.println("Erro ao inserir livro: " + e.getMessage());
		}
	}

	public static void select(Livro livro) {
		String sql = "SELECT * FROM tb_livro";
		Connection con = ConexaoDB.getConexao();
		try {
			PreparedStatement stm = con.prepareStatement(sql);
			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
				Livro livroLista = new Livro();
				livroLista.setAutor(rs.getString("autor"));
				livroLista.setTitulo(rs.getString("titulo"));
				livroLista.setIsbn(rs.getInt("isbn"));
				livroLista.setData_lancamento(rs.getString("data_lancamento"));
				livroLista.setAlugado(rs.getBoolean("alugado"));
				System.out.println(livroLista.toString());
			}
		} catch (SQLException e) {
			System.out.println("Erro ao buscar o produto" + e.getMessage());
		}
	}

	public static void liberarLivro(Livro livro, Usuario usuario) {
		String sqlContador = "SELECT count(*) FROM tb_emprestimo WHERE cpf_usuario = ?;";
		// verifica se o usuario ja tem 3 livros alugados ou não//
		Connection con = ConexaoDB.getConexao();

		try {
			PreparedStatement stmContador = con.prepareStatement(sqlContador);
			stmContador.setString(1, usuario.getCpf());
			ResultSet rsContador = stmContador.executeQuery();
			if (rsContador.next()) {
				long contador = rsContador.getLong("count(*)");
				if (contador < 3) {
					String sqlEmprestimo = "INSERT INTO tb_emprestimo(cpf_usuario, isbn_livro, data_alugado, data_devolver) VALUES(?,?,NOW(),NOW())";
					PreparedStatement stmEmprestimo = con.prepareStatement(sqlEmprestimo);
					stmEmprestimo.setString(1, usuario.getCpf());
					stmEmprestimo.setInt(2, livro.getIsbn());
					stmEmprestimo.executeUpdate();

					String sqlLivro = "UPDATE tb_livro set alugado = 1 where isbn = ?";
					PreparedStatement stmLivro = con.prepareStatement(sqlLivro);
					stmLivro.setInt(1, livro.getIsbn());
					stmLivro.executeUpdate();
					livro.alugado = true;
					System.out.println("Livro liberado!");
				} else {
					System.out.println("O usuario ja tem 3 livros alugados!");
					
				}
			}
		} catch (SQLException e) {
			System.out.println("Erro livro não cadastrado: " + e.getMessage());
		}
	}

	public static void devolverLivro(Usuario usuario, Livro livro) {
		// O nomes "stmEmprestimo" e "stmLivro"são referentes as tabelas e precisa ser//
		// feito dois PreparedStatement//
		String sqlConsultaUsuario = "SELECT * FROM tb_emprestimo WHERE cpf_usuario = ? AND isbn_livro = ?";
		String sqlConsultaAlugado = "SELECT alugado FROM tb_livro WHERE isbn = ?";
		String sqlDevolver = "DELETE FROM tb_emprestimo WHERE cpf_usuario = ? AND isbn_livro = ?";
		String sqlALugado = "UPDATE tb_livro SET alugado = 0 WHERE isbn = ? ";
		Connection con = ConexaoDB.getConexao();

		try {
			PreparedStatement stmConsultaUsuario = con.prepareStatement(sqlConsultaUsuario);
			stmConsultaUsuario.setString(1, usuario.getCpf());
			stmConsultaUsuario.setInt(2, livro.getIsbn());
			ResultSet rsConsultaUsuario = stmConsultaUsuario.executeQuery();
			if (rsConsultaUsuario.next()) {
				PreparedStatement stmDevolver = con.prepareStatement(sqlDevolver);
				stmDevolver.setString(1, usuario.getCpf());
				stmDevolver.setInt(2, livro.getIsbn());
				stmDevolver.executeUpdate();

				PreparedStatement stmConsultaAlugado = con.prepareStatement(sqlConsultaAlugado);
				// O sqlConsultaAlugado Verifca na tabela tb_livro se o livro esta alugado ou não//
				stmConsultaAlugado.setInt(1, livro.getIsbn());
				ResultSet rs = stmConsultaAlugado.executeQuery();

				if (rs.next()) {
					rs.getBoolean("alugado");
					if (rs.getBoolean(1)) {

						PreparedStatement stmUpdateLivro = con.prepareStatement(sqlALugado);
						stmUpdateLivro.setInt(1, livro.getIsbn());
						stmUpdateLivro.executeUpdate();
						livro.alugado = false;
						System.out.println("Livro devolvido!");
					} else {
						System.out.println("Este livro ja foi devolvido");
					}
				}
			} else {
				System.out.println("Usuario ou livro não encontrados");
			}
		} catch (SQLException e) {
			System.out.println("Erro livro não cadastrado: " + e.getMessage());
		}
	}
}
