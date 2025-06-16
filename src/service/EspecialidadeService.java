package service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import dao.BancoDados;
import dao.EspecialidadeDAO;
import entities.Especialidade;

public class EspecialidadeService {

	public EspecialidadeService() {
		
	}
	
	public Especialidade buscarPorId(int id) throws SQLException, IOException {
		
		Connection conn = BancoDados.conectar();
		return new EspecialidadeDAO(conn).buscarPorId(id);
		
	}
}
