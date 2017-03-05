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
import tikape.runko.database.Database;

public class Alue {
    private int id;
    private String otsikko;
    private String kuvaus;
    private Database database;
    
    public Alue(int id, String otsikko, String kuvaus, Database database) {
        this.id = id;
        this.otsikko = otsikko;
        this.kuvaus = kuvaus;
        this.database = database;
    }
    
    public String getOtsikko() {
        return this.otsikko;
    }
    public int getId() {
        return this.id;
    }
    public String getKuvaus() {
        return this.kuvaus;
    }
    
    public int getViestienmaara() throws SQLException {
        Connection conn = this.database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) AS viestien_maara FROM Viesti, Keskustelu WHERE Viesti.keskustelu_id = Keskustelu.id AND Keskustelu.alue_id = ?");
        
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
        PreparedStatement stmt = conn.prepareStatement("SELECT aika FROM Viesti, Keskustelu WHERE alue_id = ? AND Keskustelu.id = Viesti.keskustelu_id ORDER BY aika DESC LIMIT 1");
        
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
