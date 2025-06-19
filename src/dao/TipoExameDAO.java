package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entities.TipoExame;

public class TipoExameDAO {

    private Connection conn;

    public TipoExameDAO(Connection conn) {
        this.conn = conn;
    }

    public synchronized int cadastrar(TipoExame tipoExame) throws SQLException {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement(
                    "INSERT INTO TipoExame (nome, valor, orientacoes) VALUES (?, ?, ?)");

            st.setString(1, tipoExame.getNome());
            st.setDouble(2, tipoExame.getValor());
            st.setString(3, tipoExame.getOrientacoes());

            return st.executeUpdate();

        } finally {
            BancoDados.finalizarStatement(st);
            BancoDados.desconectar();
        }
    }

    public synchronized int excluir(int idTipoExame) throws SQLException {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement("DELETE FROM TipoExame WHERE idTipo = ?");

            st.setInt(1, idTipoExame);

            return st.executeUpdate();

        } finally {
            BancoDados.finalizarStatement(st);
            BancoDados.desconectar();
        }
    }

    public synchronized int editar(TipoExame tipoExame) throws SQLException {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement(
                    "UPDATE TipoExame SET nome = ?, valor = ?, orientacoes = ? WHERE idTipo = ?");

            st.setString(1, tipoExame.getNome());
            st.setDouble(2, tipoExame.getValor());
            st.setString(3, tipoExame.getOrientacoes());
            st.setInt(4, tipoExame.getId());

            return st.executeUpdate();

        } finally {
            BancoDados.finalizarStatement(st);
            BancoDados.desconectar();
        }
    }

    public List<TipoExame> buscarTodos() throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<TipoExame> lista = new ArrayList<>();

        try {
            st = conn.prepareStatement("SELECT * FROM TipoExame");
            rs = st.executeQuery();

            while (rs.next()) {
                TipoExame tipoExame = new TipoExame();

                tipoExame.setId(rs.getInt("idTipo"));
                tipoExame.setNome(rs.getString("nome"));
                tipoExame.setValor(rs.getDouble("valor"));
                tipoExame.setOrientacoes(rs.getString("orientacoes"));

                lista.add(tipoExame);
            }

        } finally {
            BancoDados.finalizarResultSet(rs);
            BancoDados.finalizarStatement(st);
            BancoDados.desconectar();
        }

        return lista;
    }

    public TipoExame buscarPorId(int idTipoExame) throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;
        TipoExame tipoExame = null;

        try {
            st = conn.prepareStatement("SELECT * FROM TipoExame WHERE idTipo = ?");
            st.setInt(1, idTipoExame);

            rs = st.executeQuery();

            if (rs.next()) {
                tipoExame = new TipoExame();
                tipoExame.setId(rs.getInt("idTipo"));
                tipoExame.setNome(rs.getString("nome"));
                tipoExame.setValor(rs.getDouble("valor"));
                tipoExame.setOrientacoes(rs.getString("orientacoes"));
            }

        } finally {
            BancoDados.finalizarResultSet(rs);
            BancoDados.finalizarStatement(st);
            BancoDados.desconectar();
        }

        return tipoExame;
    }

    public TipoExame buscarPorNome(String nome) throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement("SELECT * FROM TipoExame WHERE nome LIKE ?");
            st.setString(1, "%" + nome + "%");

            rs = st.executeQuery();

            if (rs.next()) {
                TipoExame tipoExame = new TipoExame();
                tipoExame.setId(rs.getInt("idTipo"));
                tipoExame.setNome(rs.getString("nome"));
                tipoExame.setValor(rs.getDouble("valor"));
                tipoExame.setOrientacoes(rs.getString("orientacoes"));
                
                return tipoExame;
            }

        } finally {
            BancoDados.finalizarResultSet(rs);
            BancoDados.finalizarStatement(st);
            BancoDados.desconectar();
        }
        return null;
    }
}
