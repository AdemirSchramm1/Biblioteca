package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import modelos.Funcionario;
import utils.ConexaoDB;

public class FuncionarioDAO {

	public static boolean autenticadorFuncionario(Funcionario funcionario) {
		String sql = "SELECT * FROM tb_funcionario WHERE  nome = ? AND senha = ? LIMIT 1";
		Connection con = ConexaoDB.getConexao();
		try {
		
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setString(1, funcionario.getNomeFuncionario());
			stm.setString(2, funcionario.getSenha());
			stm.execute();
			ResultSet rs = stm.executeQuery();
			if (rs.next()) {
				System.out.println("Você Entrou!");
				return true;
			} else {
				System.out.println("Funcionario não encontrado");
				return false;
			}
		} catch (SQLException e) {
			System.out.println("Erro ao cadastrar-se: " + e.getMessage());
			return false;
		}
	}
	
}
