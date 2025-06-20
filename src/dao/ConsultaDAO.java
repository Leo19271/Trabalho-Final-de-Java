package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import entities.Consulta;
import entities.Medico;
import entities.Paciente;

public class ConsultaDAO {

    private Connection conn;

    public ConsultaDAO(Connection conn) {
        this.conn = conn;
    }

    public synchronized int cadastrar(Consulta consulta) throws SQLException {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                "INSERT INTO consulta (horaConsulta, idMedico, idPaciente, realizado) VALUES (?, ?, ?, ?)"
            );

            st.setTimestamp(1, Timestamp.valueOf(consulta.getHoraConsulta()));
            st.setInt(2, consulta.getMedico().getId());
            st.setInt(3, consulta.getPaciente().getId());
            st.setBoolean(4, consulta.isRealizada());

            return st.executeUpdate();
        } finally {
            BancoDados.finalizarStatement(st);
            BancoDados.desconectar();
        }
    }

    public synchronized int excluir(int idConsulta) throws SQLException {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("DELETE FROM consulta WHERE idConsulta = ?");
            st.setInt(1, idConsulta);
            return st.executeUpdate();
        } finally {
            BancoDados.finalizarStatement(st);
            BancoDados.desconectar();
        }
    }

    public synchronized int atualizarStatusRealizado(int idConsulta, boolean realizado) throws SQLException {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("UPDATE consulta SET realizado = ? WHERE idConsulta = ?");
            st.setBoolean(1, realizado);
            st.setInt(2, idConsulta);
            return st.executeUpdate();
        } finally {
            BancoDados.finalizarStatement(st);
            BancoDados.desconectar();
        }
    }

    public List<Consulta> buscarConsultasPorMedico(Consulta consulta) throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Consulta> lista = new ArrayList<>();

        try {
            st = conn.prepareStatement("SELECT * FROM consulta WHERE idMedico = ?");
            st.setInt(1, consulta.getMedico().getId());
            rs = st.executeQuery();

            while (rs.next()) {
                Consulta c = new Consulta();
                c.setIdConsulta(rs.getInt("idConsulta"));
                c.setHoraConsulta(rs.getString("horaConsulta"));
                c.setRealizada(rs.getBoolean("realizado"));

                Medico medico = new Medico();
                medico.setId(rs.getInt("idMedico"));
                c.setMedico(medico);

                Paciente paciente = new Paciente();
                paciente.setId(rs.getInt("idPaciente"));
                c.setPaciente(paciente);

                lista.add(c);
            }

        } finally {
            BancoDados.finalizarResultSet(rs);
            BancoDados.finalizarStatement(st);
            BancoDados.desconectar();
        }

        return lista;
    }

    public List<Consulta> buscarConsultasPorMedicoEData(Consulta consulta) throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Consulta> lista = new ArrayList<>();

        try {
            st = conn.prepareStatement("SELECT * FROM consulta WHERE idMedico = ? AND DATE(horaConsulta) = ?");
            st.setInt(1, consulta.getMedico().getId());
            st.setString(2, consulta.getHoraConsulta());
            rs = st.executeQuery();

            while (rs.next()) {
                Consulta c = new Consulta();
                c.setIdConsulta(rs.getInt("idConsulta"));
                c.setHoraConsulta(rs.getString("horaConsulta"));
                c.setRealizada(rs.getBoolean("realizado"));

                Medico medico = new Medico();
                medico.setId(rs.getInt("idMedico"));
                c.setMedico(medico);

                Paciente paciente = new Paciente();
                paciente.setId(rs.getInt("idPaciente"));
                c.setPaciente(paciente);

                lista.add(c);
            }

        } finally {
            BancoDados.finalizarResultSet(rs);
            BancoDados.finalizarStatement(st);
            BancoDados.desconectar();
        }

        return lista;
    }

    public List<Consulta> buscarConsultasPorPaciente(Consulta consulta) throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Consulta> lista = new ArrayList<>();

        try {
            st = conn.prepareStatement("SELECT * FROM consulta WHERE idPaciente = ?");
            st.setInt(1, consulta.getPaciente().getId());
            rs = st.executeQuery();

            while (rs.next()) {
                Consulta c = new Consulta();
                c.setIdConsulta(rs.getInt("idConsulta"));
                c.setHoraConsulta(rs.getString("horaConsulta"));
                c.setRealizada(rs.getBoolean("realizado"));

                Medico medico = new Medico();
                medico.setId(rs.getInt("idMedico"));
                c.setMedico(medico);

                Paciente paciente = new Paciente();
                paciente.setId(rs.getInt("idPaciente"));
                c.setPaciente(paciente);

                lista.add(c);
            }

        } finally {
            BancoDados.finalizarResultSet(rs);
            BancoDados.finalizarStatement(st);
            BancoDados.desconectar();
        }

        return lista;
    }

    public List<Consulta> buscarConsultasPorPacienteEData(Consulta consulta) throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Consulta> lista = new ArrayList<>();

        try {
            st = conn.prepareStatement("SELECT * FROM consulta WHERE idPaciente = ? AND DATE(horaConsulta) = ?");
            st.setInt(1, consulta.getPaciente().getId());
            st.setString(2, consulta.getHoraConsulta());
            rs = st.executeQuery();

            while (rs.next()) {
                Consulta c = new Consulta();
                c.setIdConsulta(rs.getInt("idConsulta"));
                c.setHoraConsulta(rs.getString("horaConsulta"));
                c.setRealizada(rs.getBoolean("realizado"));

                Medico medico = new Medico();
                medico.setId(rs.getInt("idMedico"));
                c.setMedico(medico);

                Paciente paciente = new Paciente();
                paciente.setId(rs.getInt("idPaciente"));
                c.setPaciente(paciente);

                lista.add(c);
            }

        } finally {
            BancoDados.finalizarResultSet(rs);
            BancoDados.finalizarStatement(st);
            BancoDados.desconectar();
        }

        return lista;
    }
}