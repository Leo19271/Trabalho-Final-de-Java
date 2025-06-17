package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import entities.Exame;

public class ExameDAO {

	private Connection conn;

	public ExameDAO(Connection conn) {

		this.conn = conn;
	}

	public synchronized int cadastrar(Exame exame) throws SQLException {

		PreparedStatement st = null;

		try {
			
			st = conn.prepareStatement(
					"insert into exame (dataRealizacao, realizado, idPaciente, idMedico, idTipo) values (?, ?, ?, ?, ?)");

			st.setTimestamp(1, Timestamp.valueOf(exame.getDataRealizacao()));
			st.setBoolean(2, false);
			st.setInt(3, exame.getPaciente().getId());
			st.setInt(4, exame.getMedico().getId());
			st.setInt(5, exame.getTipoExame().getId());
		
			return st.executeUpdate();

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}
	
	public synchronized int excluir(int idExame) throws SQLException {

		PreparedStatement st = null;

		try {

			st = conn.prepareStatement("delete from exame where idExame = ?");

			st.setInt(1, idExame);

			return st.executeUpdate();

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}
}
