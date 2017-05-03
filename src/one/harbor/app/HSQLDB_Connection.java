/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package one.harbor.app;

/**
 * http://hsqldb.org/doc/2.0/guide/index.html for all info on HSQLDB.
 * @author eimi_
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import OneHarborAppUI.MessageBox;

public class HSQLDB_Connection {
    
    public static Connection DBConnection(){
        // TODO code application logic here
        
        try {
            Connection con;

            //Registering the HSQLDB JDBC driver
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            // Allow full paths            
            System.setProperty("textdb.allow_full_path", "true");            
            // Create the connection with HSQLDB
            con = DriverManager.getConnection("jdbc:hsqldb:file:OHPS","SA","");            
            if(con != null){
                System.out.println("Connection created successfully");
            }
            else{
                System.out.println("Problem creating connection");
                MessageBox.ErrorBox("Error creating connection to DB. \r\nContact Jim Lynch for help.", "Error");
            }
         return con;   
        }
        catch (ClassNotFoundException | SQLException e){
            e.printStackTrace(System.out);
            MessageBox.ErrorBox("Error creating connection to DB.\r\nContact Jim Lynch for help.", "Error", e);
            return null;
        }        
    }
}
