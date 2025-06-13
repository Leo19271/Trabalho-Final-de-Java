package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import entities.TipoExame;

public class TipoExameDAO {

	private Connection conn;

	public TipoExameDAO(Connection conn) {

		this.conn = conn;
	}

	public int cadastrar(TipoExame tipoExame) throws SQLException {

		PreparedStatement st = null;

		try {
			
			st = conn.prepareStatement(
					"insert into Tipoexame (nome, valor, orientacoes) values (?, ?, ?)");

			st.setString(1, tipoExame.getNome());
			st.setDouble(2, tipoExame.getValor());
			st.setString(3, tipoExame.getOrientacoes());

			return st.executeUpdate();

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}
	
	public int excluir(int idTipoExame) throws SQLException {

		PreparedStatement st = null;

		try {

			st = conn.prepareStatement("delete from tipoExame where idTipoExame = ?");

			st.setInt(1, idTipoExame);

			return st.executeUpdate();

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}
}
