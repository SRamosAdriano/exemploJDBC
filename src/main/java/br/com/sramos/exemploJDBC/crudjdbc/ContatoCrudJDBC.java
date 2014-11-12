package br.com.sramos.exemploJDBC.crudjdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ContatoCrudJDBC {

	public void savlar(Contato contato){
		Connection conexao = this.geraConexao();
		
		PreparedStatement insereSt = null;
		
		String sql = "insert into contato(nome, telefone, email, dt_cad, obs) values (?, ?, ?, ?, ?)";
		
		try {
			insereSt = conexao.prepareStatement(sql);
			insereSt.setString(1, contato.getNome());
			insereSt.setString(2, contato.getTelefone());
			insereSt.setString(3, contato.getEmail());
			insereSt.setDate(4, contato.getDataCadastro());
			insereSt.setString(5, contato.getObservacao());
			insereSt.executeUpdate();			
		} catch (SQLException e) {
			System.out.println("Erro ao incluir contato. Mensagem: " + e.getMessage());
		}finally{
			try {
				insereSt.close();
				conexao.close();
			} catch (Throwable e2) {
				System.out.println("Erro ao fechar operações de inserção. Mensagem: " + e2.getMessage());
			}
		}
		
	}
	
	
	public void atualizar(Contato contato){}
	
	public void excluir(Contato contato){}
	
	
	public List<Contato> listar(){
		Connection conexao = this.geraConexao();
		Statement consulta = null;		
		ResultSet resultado = null;		
		Contato contato = null;
		
		List<Contato> contatos = new ArrayList<Contato>();
		
		String sql = "select * from contato";
		
		try {
			consulta = conexao.createStatement();
			resultado = consulta.executeQuery(sql);
			
			while(resultado.next()){
				contato = new Contato();
				contato.setCodigo(new Integer(resultado.getInt("codigo")));
				contato.setNome(resultado.getString("nome"));
				contato.setTelefone(resultado.getString("telefone"));
				contato.setEmail(resultado.getString("email"));
				contato.setDataCadastro(resultado.getDate("dt_cad"));
				contato.setObservacao(resultado.getString("obs"));
				contatos.add(contato);				
			}			
		} catch (SQLException e) {
			System.out.println("Erro ao buscar codigo do contato. Mensagem: "+ e.getMessage());
		}finally{
			try {
				consulta.close();
				resultado.close();
				conexao.close();
			} catch (Throwable e2) {
				System.out.println("Erro ao fechar operações de consulta. Mensagem: "+ e2.getMessage());
			}
		}
		return contatos;
	}
	
	
	public Contato buscaContato(int valor){return null;}
	
	
	public Connection geraConexao(){
		Connection conexao = null;
		try {
			// Registrando a classe JDBC no sistema em tempo de execução
			String url = "jdbc:mysql://localhost/agenda";
			String usuario = "root";
			String senha = "senha$00";
			
			conexao = DriverManager.getConnection(url, usuario, senha);			
		} catch (SQLException e) {
			System.out.println("Ocoreu um erro de SQL. Erro: " + e.getMessage());
		}
		return conexao;
	}
	
	public static void main(String[] args) {
		ContatoCrudJDBC contatoCrudJDBC = new ContatoCrudJDBC();
		
		// Criando primeiro contato
		Contato beltrano = new Contato();
		beltrano.setNome("Beltrano Solar");
		beltrano.setTelefone("(11) 2222-3333");
		beltrano.setEmail("beltrano@teste.com.br");
		beltrano.setDataCadastro(new Date(System.currentTimeMillis()));
		beltrano.setObservacao("Novo cliente");
		contatoCrudJDBC.savlar(beltrano);
		
		// Criando segundo contato
		Contato fulano = new Contato();
		fulano.setNome("Fulano Lunar");
		fulano.setTelefone("(11) 4444-1111");
		fulano.setEmail("fulano@teste.com.br");
		fulano.setDataCadastro(new Date(System.currentTimeMillis()));
		fulano.setObservacao("Novo contato - possivel cliente");
		contatoCrudJDBC.savlar(fulano);
		
		System.out.println("Contatos cadastrados: "+ contatoCrudJDBC.listar().size());
	}
}
