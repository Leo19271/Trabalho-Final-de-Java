package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entities.Endereco;

public class EnderecoDAO {

	private Connection conn;

	public EnderecoDAO(Connection conn) {

		this.conn = conn;
	}

	public int cadastrar(Endereco endereco) throws SQLException {

		PreparedStatement st = null;
		
		ResultSet rs = null;

		try {

			st = conn.prepareStatement(
					"insert into endereco (estado, cidade, bairro, rua, numero, cep) values (?, ?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
				
			st.setString(1, endereco.getEstado());
			st.setString(2, endereco.getCidade());
			st.setString(3, endereco.getBairro());
			st.setString(4, endereco.getRua());
			st.setInt(5, endereco.getNumero());
			st.setString(6, endereco.getCep());
		
			st.executeUpdate();
			
			rs = st.getGeneratedKeys();
			
			if(rs.next()) {
				return rs.getInt(1);
			}
			
		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.finalizarResultSet(rs);
		}
		return 0;
	}
	
	public int excluir(int idEndereco) throws SQLException {

		PreparedStatement st = null;

		try {

			st = conn.prepareStatement("delete from endereco where idEndereco = ?");

			st.setInt(1, idEndereco);

			return st.executeUpdate();

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}
}
