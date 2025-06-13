package dao;

import java.sql.Connection;
import java.sql.PreparedStatement; 
import java.sql.SQLException;

import entities.Especialidade;

public class EspecialidadeDAO {

	private Connection conn;

	public EspecialidadeDAO(Connection conn) {

		this.conn = conn;
	}

	public int cadastrar(Especialidade especialidade) throws SQLException {

		PreparedStatement st = null;

		try {
			
			st = conn.prepareStatement(
					"insert into especialidade (nome) values (?)");

			st.setString(1, especialidade.getNome());
			
			return st.executeUpdate();

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}
	
	public int excluir(int idEspecialidade) throws SQLException {

		PreparedStatement st = null;

		try {

			st = conn.prepareStatement("delete from especialidade where idEspecialidade = ?");

			st.setInt(1, idEspecialidade);

			return st.executeUpdate();

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}
}
