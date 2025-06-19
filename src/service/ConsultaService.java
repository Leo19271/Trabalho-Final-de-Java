package service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import dao.BancoDados;
import dao.ConsultaDAO;
import entities.Consulta;

public class ConsultaService {

	public ConsultaService() {
		
	}
	
	public void cadastrar(Consulta consulta)  throws SQLException, IOException {
		
		Connection conn = BancoDados.conectar();
		new ConsultaDAO(conn).cadastrar(consulta);
	}
}
