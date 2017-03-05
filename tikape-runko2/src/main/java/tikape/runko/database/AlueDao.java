package tikape.runko.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Alue;
public class AlueDao implements Dao<Alue, Integer> {
    
    private Database database;
    
    public AlueDao(Database database) {
        this.database = database;   
    }

    @Override
    public Alue findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Alue WHERE id = ?");
        stmt.setObject(1, key);
        
        ResultSet rs = stmt.executeQuery();
        
        if (!rs.next())
            return null;
    
        int id = rs.getInt("id");
        String otsikko = rs.getString("otsikko");
        String kuvaus = rs.getString("kuvaus");
        
        rs.close();
        stmt.close();
        connection.close();
        
        return new Alue(id, otsikko, kuvaus, database);
    }

    @Override
    public List<Alue> findAll() throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Alue");

        ResultSet rs = stmt.executeQuery();
        List<Alue> alueet = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String otsikko = rs.getString("otsikko");
            String kuvaus = rs.getString("kuvaus");

            alueet.add(new Alue(id, otsikko, kuvaus, database));
        }

        rs.close();
        stmt.close();
        connection.close();

        return alueet; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void luoAlue(String otsikko, String kuvaus) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Alue(otsikko, kuvaus) VALUES (?, ?)");
        stmt.setObject(1, otsikko);
        stmt.setObject(2, kuvaus);
        

        stmt.execute();
        
        stmt.close();
        connection.close();
    }
    
    
}
