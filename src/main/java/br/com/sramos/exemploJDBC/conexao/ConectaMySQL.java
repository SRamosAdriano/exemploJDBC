package br.com.sramos.exemploJDBC.conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConectaMySQL {
	public static void main(String[] args) {
		Connection conexao = null;
		try {
			// Registrando a classe JDBC no sistema em tempo de execução
			String url = "jdbc:mysql://localhost/agenda";
			String usuario = "root";
			String senha = "senha$00";
			
			conexao = DriverManager.getConnection(url, usuario, senha);
			System.out.println("Conectou!");
		} catch (SQLException e) {
			System.out.println("Ocoreu um erro de SQL. Erro: " + e.getMessage());
		}finally {
			try {
				conexao.close();
			} catch (SQLException e2) {
				System.out.println("Erro ao fechar conexão. Erro: " + e2.getMessage());
			}
		}		
	}
}
