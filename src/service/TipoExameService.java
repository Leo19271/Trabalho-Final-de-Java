package service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dao.BancoDados;
import dao.TipoExameDAO;
import entities.TipoExame;

public class TipoExameService {

    public TipoExameService() {
        
    }
    
    public void cadastrarTipoExame(TipoExame tipoExame) throws SQLException, IOException {
        Connection conn = BancoDados.conectar();
        new TipoExameDAO(conn).cadastrar(tipoExame);
    }
    
    public void excluirTipoExame(TipoExame tipoExame) throws SQLException, IOException {
        Connection conn = BancoDados.conectar();
        new TipoExameDAO(conn).excluir(tipoExame.getId());
    }
    
    public void editarTipoExame(TipoExame tipoExame) throws SQLException, IOException {
        Connection conn = BancoDados.conectar();
        new TipoExameDAO(conn).editar(tipoExame);
    }
    
    public List<TipoExame> buscarTodos() throws SQLException, IOException {
        Connection conn = BancoDados.conectar();
        return new TipoExameDAO(conn).buscarTodos();
    }
    
    public TipoExame buscarPorId(int idTipoExame) throws SQLException, IOException {
        Connection conn = BancoDados.conectar();
        return new TipoExameDAO(conn).buscarPorId(idTipoExame);
    }
    
    public TipoExame buscarPorNome(String nome) throws SQLException, IOException {
        Connection conn = BancoDados.conectar();
        return new TipoExameDAO(conn).buscarPorNome(nome);
    }
}