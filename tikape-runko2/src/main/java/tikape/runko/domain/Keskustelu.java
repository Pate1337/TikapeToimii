/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.domain;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import tikape.runko.database.Database;
import tikape.runko.database.KeskusteluDao;

/**
 *
 * @author paavo
 */
public class Keskustelu {
    private int id;
    private String otsikko;
    private int alueID;
    private Timestamp uusinViesti;
    private Database database;
//    private int viestienMaara;
    
    
    public Keskustelu(int id, String otsikko, int alueID, Database database) {
        this.id = id;
        this.otsikko = otsikko;
        this.alueID = alueID;
        this.database = database;
//        this.viestienMaara = 0;
        
    }
    public String getOtsikko() {
        return this.otsikko;
    }
    public int getId() {
        return this.id;
    }
    public int getViestienmaara() throws SQLException {
        Connection conn = this.database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) AS viestien_maara FROM Viesti WHERE keskustelu_id = ?");
        
        stmt.setInt(1, this.id);
        ResultSet rs = stmt.executeQuery();
        
        int viestienMaara = 0;
        while (rs.next()) {
            viestienMaara = rs.getInt("viestien_maara");
        }
        rs.close();
        stmt.close();
        conn.close();
        return viestienMaara;
    }
    
    public String getViimeisinviesti() throws SQLException {
        Connection conn = this.database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT aika FROM Viesti WHERE keskustelu_id = ? ORDER BY aika DESC LIMIT 1");
        
        stmt.setInt(1, this.id);
        ResultSet rs = stmt.executeQuery();
        String aika = "Ei viestejä";
        while (rs.next()) {
            aika = rs.getString("aika");
        }
        rs.close();
        stmt.close();
        conn.close();
        return aika;
    }
    
}
