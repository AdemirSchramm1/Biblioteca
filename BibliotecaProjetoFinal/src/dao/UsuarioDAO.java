package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import modelos.Usuario;
import utils.ConexaoDB;

public class UsuarioDAO {
	public static void selectLivroUsuario(Usuario usuario) {
		
		String sqlData = "SELECT data_devolver FROM tb_emprestimo";
		float multa = 2.50f;
		String sql = "SELECT * FROM tb_emprestimo WHERE cpf_usuario = ?";
		Connection con = ConexaoDB.getConexao();
		try {
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setString(1, usuario.getCpf());
			stm.execute();
			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
				Usuario usuarioAlugados = new Usuario();
				usuarioAlugados.setCpf(rs.getString("cpf_usuario"));
				usuarioAlugados.setIsbn(rs.getInt("isbn_livro"));
				usuarioAlugados.setData_alugado(rs.getDate("data_alugado"));
				usuarioAlugados.setData_devolver(rs.getDate("data_devolver"));
				PreparedStatement stmData = con.prepareStatement(sqlData);
				ResultSet rsHorario = stmData.executeQuery();
				if (rsHorario.next()) {
					Date data_devolver = rs.getDate("data_devolver");
					LocalDate dataAgora = LocalDate.now();
					LocalDate dataPrevista = data_devolver.toLocalDate();
					long diasAtraso = ChronoUnit.DAYS.between(dataPrevista, dataAgora);
					if (diasAtraso > 0) {
						float diasMulta = diasAtraso * multa;
						System.out.println(usuarioAlugados.toString() + " " + diasMulta + " R$");
					}
			}
				System.out.println(usuarioAlugados.toString());
			}
	} catch (SQLException e) {
		System.out.println("Erro: " + e.getMessage());
	}
}
	public static void cadastrar(Usuario usuario) {
		String sql = "INSERT INTO tb_usuario(nome, cpf_usuario) VALUES(?, ?)";
		Connection con = ConexaoDB.getConexao();
		try {
			PreparedStatement stm = con.prepareStatement(sql);
			if (usuario.getCpf().length() == 11) {
				stm.setString(1, usuario.getNome());
				stm.setString(2, usuario.getCpf());
				stm.executeUpdate();
				System.out.println("Usuario cadastrado com sucesso!");
			} else {
				System.out.println("O cpf do usuario é invalido");
			}
		} catch (SQLException e) {
			System.out.println("Erro ao cadastrar usuario: " + e.getMessage());
		}
	}
}
