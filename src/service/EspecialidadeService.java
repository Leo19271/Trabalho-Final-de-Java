package service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dao.BancoDados;
import dao.EspecialidadeDAO;
import entities.Especialidade;

public class EspecialidadeService {

	public EspecialidadeService() {
		
	}
	
	public void cadastrar(Especialidade especialidade)  throws SQLException, IOException {
		
		Connection conn = BancoDados.conectar();
		new EspecialidadeDAO(conn).cadastrar(especialidade);
	}
	
	public void excluir(Especialidade especialidade) throws SQLException, IOException {
		
		Connection conn = BancoDados.conectar();
		new EspecialidadeDAO(conn).excluir(especialidade.getIdEspecialidade());
	}
	
	public void editar(Especialidade especialidade) throws SQLException, IOException {
		
		Connection conn = BancoDados.conectar();
		new EspecialidadeDAO(conn).editar(especialidade);
	}
	
	public List<Especialidade> buscarTodos() throws SQLException, IOException {
		
		Connection conn = BancoDados.conectar();
		return new EspecialidadeDAO(conn).buscarTodos();
		
	}
	
	public Especialidade buscarPorId(int id) throws SQLException, IOException {
		
		Connection conn = BancoDados.conectar();
		return new EspecialidadeDAO(conn).buscarPorId(id);
		
	}
	
	public Especialidade buscarPorNome(String nome) throws SQLException, IOException {
		
		Connection conn = BancoDados.conectar();
		return new EspecialidadeDAO(conn).buscarPorNome(nome);
		
	}
}
