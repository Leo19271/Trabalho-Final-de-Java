package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import entities.Consulta;

public class ConsultaDAO {

	private Connection conn;

	public ConsultaDAO(Connection conn) {

		this.conn = conn;
	}

	public synchronized int cadastrar(Consulta consulta) throws SQLException {

		PreparedStatement st = null;

		try {
				
			st = conn.prepareStatement(
					"insert into consulta (horaConsulta, idMedico, idPaciente) values (?, ?, ?)");

			st.setTimestamp(1, Timestamp.valueOf(consulta.getHoraConsulta()));
			st.setInt(2, consulta.getMedico().getId());
			st.setInt(3, consulta.getPaciente().getId());	

			return st.executeUpdate();

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}
	
	public synchronized int excluir(int idConsulta) throws SQLException {

		PreparedStatement st = null;

		try {

			st = conn.prepareStatement("delete from consulta where idConsulta = ?");

			st.setInt(1, idConsulta);

			return st.executeUpdate();

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}
}
