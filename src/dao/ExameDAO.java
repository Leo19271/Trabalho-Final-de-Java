package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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
	
	public List<Exame> buscarExamesPorMedico(Exame exame) throws SQLException {
		
	    PreparedStatement st = null;
	    ResultSet rs = null;
	    
	    List<Exame> lista = new ArrayList<>();
	    
	    try {
	    	
	        st = conn.prepareStatement(
	            "SELECT * FROM exame WHERE idMedico = ?"
	        );
	        
	        st.setInt(1, exame.getMedico().getId());
	        
	        rs = st.executeQuery();

	        while (rs.next()) {
	            Exame e1 = new Exame();

	            e1.setIdExame(rs.getInt("idExame"));
	            e1.setDataRealizacao(rs.getString("dataRealizacao"));
	            e1.setRealizado(rs.getBoolean("Realizado"));
	            e1.getPaciente().setId(rs.getInt("idPaciente"));
	            e1.getMedico().setId(rs.getInt("idMedico"));
	            e1.getTipoExame().setId(rs.getInt("idTipo"));

	            lista.add(e1);
	            
	        }

	        return lista;
	    } finally {
	        BancoDados.finalizarStatement(st);
	        BancoDados.finalizarResultSet(rs);
	        BancoDados.desconectar();
	    }
	}
	
	public List<Exame> buscarExamesPorMedicoEData(Exame exame) throws SQLException {
	    PreparedStatement st = null;
	    ResultSet rs = null;
	    List<Exame> lista = new ArrayList<>();

	    try {
	        String sql = "SELECT * FROM exame WHERE idMedico = ? AND DATE(dataRealizacao) = ?";
	        st = conn.prepareStatement(sql);
	        st.setInt(1, exame.getMedico().getId());
	        st.setDate(2, Date.valueOf(exame.getDataRealizacao()));
	        rs = st.executeQuery();

	        while (rs.next()) {
	            Exame e = new Exame();
	            e.setIdExame(rs.getInt("idExame"));
	            e.getMedico().setId(rs.getInt("idMedico"));
	            e.getPaciente().setId(rs.getInt("idPaciente"));
	            e.getTipoExame().setId(rs.getInt("idTipo"));
	            e.setDataRealizacao(rs.getString("dataRealizacao"));
	            e.setRealizado(rs.getBoolean("realizado"));

	            lista.add(e);
	        }
	    } finally {
	        BancoDados.finalizarResultSet(rs);
	        BancoDados.finalizarStatement(st);
	    }

	    return lista;
	}
	
	public synchronized void atualizarStatusRealizado(int idExame, boolean realizado) throws SQLException {
	    PreparedStatement st = null;

	    try {
	        st = conn.prepareStatement("UPDATE exame SET realizado = ? WHERE idExame = ?");
	        st.setBoolean(1, realizado);
	        st.setInt(2, idExame);

	        st.executeUpdate();
	    } finally {
	        BancoDados.finalizarStatement(st);
	        BancoDados.desconectar();
	    }
	}
	
	public List<Exame> buscarExamesPorPaciente(Exame exame) throws SQLException {
		
	    PreparedStatement st = null;
	    ResultSet rs = null;
	    List<Exame> lista = new ArrayList<>();

	    try {
	        st = conn.prepareStatement(
	            "SELECT * FROM exame WHERE idPaciente = ?"
	        );

	        st.setInt(1, exame.getPaciente().getId());

	        rs = st.executeQuery();

	        while (rs.next()) {
	            Exame e1 = new Exame();

	            e1.setIdExame(rs.getInt("idExame"));
	            e1.setDataRealizacao(rs.getString("dataRealizacao"));
	            e1.setRealizado(rs.getBoolean("realizado"));
	            e1.getPaciente().setId(rs.getInt("idPaciente"));
	            e1.getMedico().setId(rs.getInt("idMedico"));
	            e1.getTipoExame().setId(rs.getInt("idTipo"));

	            lista.add(e1);
	        }

	        return lista;
	    } finally {
	        BancoDados.finalizarStatement(st);
	        BancoDados.finalizarResultSet(rs);
	        BancoDados.desconectar();
	    }
	}
	
	public List<Exame> buscarExamesPorPacienteEData(Exame exame) throws SQLException {
		
	    PreparedStatement st = null;
	    ResultSet rs = null;
	    List<Exame> lista = new ArrayList<>();

	    try {
	        String sql = "SELECT * FROM exame WHERE idPaciente = ? AND DATE(dataRealizacao) = ?";
	        st = conn.prepareStatement(sql);

	        st.setInt(1, exame.getPaciente().getId());
	        st.setDate(2, Date.valueOf(exame.getDataRealizacao()));

	        rs = st.executeQuery();

	        while (rs.next()) {
	            Exame e = new Exame();

	            e.setIdExame(rs.getInt("idExame"));
	            e.getMedico().setId(rs.getInt("idMedico"));
	            e.getPaciente().setId(rs.getInt("idPaciente"));
	            e.getTipoExame().setId(rs.getInt("idTipo"));
	            e.setDataRealizacao(rs.getString("dataRealizacao"));
	            e.setRealizado(rs.getBoolean("realizado"));

	            lista.add(e);
	        }
	    } finally {
	        BancoDados.finalizarResultSet(rs);
	        BancoDados.finalizarStatement(st);
	    }

	    return lista;
	}
	
	public Exame buscarExamePorDataEHora(Exame exame) throws SQLException {
	    PreparedStatement st = null;
	    ResultSet rs = null;

	    try {
	        st = conn.prepareStatement("SELECT * FROM exame WHERE dataRealizacao = ?");
	        st.setString(1, exame.getDataRealizacao());
	        rs = st.executeQuery();

	        if (rs.next()) {
	        	
	            Exame e = new Exame();
	            e.setIdExame(rs.getInt("idExame"));
	            e.setDataRealizacao(rs.getString("dataRealizacao"));
	            e.setRealizado(rs.getBoolean("Realizado"));
	            e.getPaciente().setId(rs.getInt("idPaciente"));
	            e.getMedico().setId(rs.getInt("idMedico"));
	            e.getTipoExame().setId(rs.getInt("idTipo"));

	            return e;
	        }

	    } finally {
	        BancoDados.finalizarResultSet(rs);
	        BancoDados.finalizarStatement(st);
	        BancoDados.desconectar();
	    }

	    return null;
	}
	
	public List<Exame> buscarPorData(String data) throws SQLException {

	    PreparedStatement st = null;
	    ResultSet rs = null;
	    List<Exame> lista = new ArrayList<>();

	    try {
	        String sql = "SELECT * FROM exame WHERE DATE(dataRealizacao) = ?";
	        st = conn.prepareStatement(sql);
	        st.setDate(1, Date.valueOf(data));

	        rs = st.executeQuery();

	        while (rs.next()) {
	            Exame e = new Exame();

	            e.setIdExame(rs.getInt("idExame"));
	            e.setDataRealizacao(rs.getString("dataRealizacao"));
	            e.setRealizado(rs.getBoolean("realizado"));
	            e.getPaciente().setId(rs.getInt("idPaciente"));
	            e.getMedico().setId(rs.getInt("idMedico"));
	            e.getTipoExame().setId(rs.getInt("idTipo"));

	            lista.add(e);
	        }

	    } finally {
	        BancoDados.finalizarResultSet(rs);
	        BancoDados.finalizarStatement(st);
	    }

	    return lista;
	}

	public List<Exame> buscarPorTipo(int idTipo) throws SQLException {

	    PreparedStatement st = null;
	    ResultSet rs = null;
	    List<Exame> lista = new ArrayList<>();

	    try {
	        String sql = "SELECT * FROM exame WHERE idTipo = ?";
	        st = conn.prepareStatement(sql);
	        st.setInt(1, idTipo);

	        rs = st.executeQuery();

	        while (rs.next()) {
	            Exame e = new Exame();

	            e.setIdExame(rs.getInt("idExame"));
	            e.setDataRealizacao(rs.getString("dataRealizacao"));
	            e.setRealizado(rs.getBoolean("realizado"));
	            e.getPaciente().setId(rs.getInt("idPaciente"));
	            e.getMedico().setId(rs.getInt("idMedico"));
	            e.getTipoExame().setId(rs.getInt("idTipo"));

	            lista.add(e);
	        }

	    } finally {
	        BancoDados.finalizarResultSet(rs);
	        BancoDados.finalizarStatement(st);
	    }

	    return lista;
	}

	public List<Exame> buscarPorDataETipo(Exame exame) throws SQLException {

	    PreparedStatement st = null;
	    ResultSet rs = null;
	    List<Exame> lista = new ArrayList<>();

	    try {
	        String sql = "SELECT * FROM exame WHERE DATE(dataRealizacao) = ? AND idTipo = ?";
	        st = conn.prepareStatement(sql);
	        st.setDate(1, Date.valueOf(exame.getDataRealizacao()));
	        st.setInt(2, exame.getTipoExame().getId());

	        rs = st.executeQuery();

	        while (rs.next()) {
	            Exame e = new Exame();

	            e.setIdExame(rs.getInt("idExame"));
	            e.setDataRealizacao(rs.getString("dataRealizacao"));
	            e.setRealizado(rs.getBoolean("realizado"));
	            e.getPaciente().setId(rs.getInt("idPaciente"));
	            e.getMedico().setId(rs.getInt("idMedico"));
	            e.getTipoExame().setId(rs.getInt("idTipo"));

	            lista.add(e);
	        }

	    } finally {
	        BancoDados.finalizarResultSet(rs);
	        BancoDados.finalizarStatement(st);
	    }

	    return lista;
	}

	public List<Exame> buscarTodos() throws SQLException {

	    PreparedStatement st = null;
	    ResultSet rs = null;
	    List<Exame> lista = new ArrayList<>();

	    try {
	        String sql = "SELECT * FROM exame";
	        st = conn.prepareStatement(sql);

	        rs = st.executeQuery();

	        while (rs.next()) {
	            Exame e = new Exame();

	            e.setIdExame(rs.getInt("idExame"));
	            e.setDataRealizacao(rs.getString("dataRealizacao"));
	            e.setRealizado(rs.getBoolean("realizado"));
	            e.getPaciente().setId(rs.getInt("idPaciente"));
	            e.getMedico().setId(rs.getInt("idMedico"));
	            e.getTipoExame().setId(rs.getInt("idTipo"));

	            lista.add(e);
	        }

	    } finally {
	        BancoDados.finalizarResultSet(rs);
	        BancoDados.finalizarStatement(st);
	    }

	    return lista;
	}
}
