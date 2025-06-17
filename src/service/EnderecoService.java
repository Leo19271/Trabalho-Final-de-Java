package service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import dao.BancoDados;
import dao.EnderecoDAO;
import entities.Endereco;

public class EnderecoService {

	public EnderecoService() {
		
	}
	
	public Endereco buscarPorId(int id) throws SQLException, IOException {
		
		Connection conn = BancoDados.conectar();
		return new EnderecoDAO(conn).buscarPorId(id);
		
	}
	
	public void editarEndereco(Endereco endereco) throws SQLException, IOException {
		
		Connection conn = BancoDados.conectar();
		new EnderecoDAO(conn).editarEndereco(endereco);
	}
	
	public void excluirEndereco(int id) throws SQLException, IOException {
		
		Connection conn = BancoDados.conectar();
		new EnderecoDAO(conn).excluir(id);
	}
}
