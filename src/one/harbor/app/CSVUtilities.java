/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package one.harbor.app;

import com.opencsv.CSVReader;// DL the .jar, right click Project > Properties > Libraries > Add Jar
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

/**
 *
 * @author eimi_
 */
public class CSVUtilities {
     /**
      * Creates the MemberInfo CSV from custom_report.CSV and department_summary.CSV
      * @param partnerFile The file that houses the members.
      * @param pastorFile The file that houses the Oversight Pastors for each Community Group.
      * @throws IOException 
      */
     public static void CreateMemberInfo(String partnerFile, String pastorFile) throws IOException
    {   
        CSVReader       partnerReader = null;           // The primary CSV reader for the program
        List<String>    members = new ArrayList<>();    // This will hold all the members
        String          member;                         // Holds the member string to be imported into members
        String          newCSVFile = "MemberInfo.csv";  // The CSV filename for the Member Info
        FileWriter      writer = null;                  // The file writer for MemberInfo.csv
        int             index = 0;                      // Index for tblMemberInfo

        try
        {
            // Read the partner file and seperate it into a new Member()
            partnerReader = new CSVReader(new FileReader(partnerFile),',');
            String[] nextLinePartner;            

            while ((nextLinePartner = partnerReader.readNext()) != null)
            {
                MemberInfo info = new MemberInfo();
                String [] _site = nextLinePartner[2].split(" - ");
                String [] _groups = nextLinePartner[3].split(",");
                if (nextLinePartner[0].trim().equals("Last Name")){
                    // Set the Headers
                    info.setFullName("Full Name")
                            .setCommunityGroup("Community Group")
                            .setHospitality("Hospitality")
                            .setKidsMinistry("Kids Ministry")
                            .setYouth("Youth")
                            .setMusicAndMedia("Music And Media")
                            .setBenevolence("Benevolence")
                            .setOversightPastor("Oversight Pastor")
                            .setCommunity("Community")
                            .setServing("Serving")
                            .setPartner("Partner")
                            .setSite("Site")
                            .setFirstStepsDate("First Steps Date")
                            .setPartnershipClassDate("Partnership Date");
                    //continue;
                }
                else{
                    // Set the group flags
                    for (String groups:_groups) {
                        groups = groups.trim();
                        if (groups.contains("BFT Community") || groups.contains("MHC Community") || groups.contains("SBO Community") || groups.contains("Brandywine Community")) { info.setCommunity("Yes"); info.setCommunityGroup(groups); info.setOversightPastor(GetOversightPastor(groups, pastorFile));}
                        if (groups.contains("Hospitality")) {info.setHospitality("Yes");}
                        if (groups.contains("KM Volunteer") || groups.contains("Kids")) {info.setKidsMinistry("Yes");}
                        if (groups.contains("Youth Leaders")) {info.setYouth("Yes");}
                        if (groups.contains("Music")) {info.setMusicAndMedia("Yes");}
                        if (groups.contains("Benevolence")) {info.setBenevolence("Yes");}
                    }
                    if (info.getHospitality().equals("Yes") || info.getKidsMinistry().equals("Yes") || info.getYouth().equals("Yes") || info.getMusicAndMedia().equals("Yes") || info.getBenevolence().equals("Yes")) {
                        info.setServing("Yes");
                    }
                    info.setFullName(nextLinePartner[1].trim() + " " + nextLinePartner[0].trim())
                            .setSite(_site[1].trim())
                            .setFirstStepsDate(nextLinePartner[4].trim())
                            .setPartnershipClassDate(nextLinePartner[5].trim())
                            .setPartner(nextLinePartner[6].trim());
                }
                
                // Put everything in one string to be put into members collection
                member = info.getFullName() + "," +
                        info.getCommunityGroup() + "," +
                        info.getHospitality() + "," +
                        info.getKidsMinistry() + "," +
                        info.getYouth() + "," +
                        info.getMusicAndMedia() + "," +
                        info.getBenevolence() + "," +
                        info.getOversightPastor() + "," +
                        info.getCommunity() + "," +
                        info.getServing() + "," +
                        info.getPartner() + "," +
                        info.getSite() + "," +
                        info.getFirstStepsDate() + "," +
                        info.getPartnershipClassDate();
                members.add(member);
            }
            // Clear the previous MemberInfo.csv, if it exists.
            writer = new FileWriter(newCSVFile);
            writer.close();

            // Write the new MemberInfo
            writer = new FileWriter(newCSVFile, true);
            for(String mem : members){
                if(index==0){writer.write("Index,"+mem + "\r\n");}else{writer.write(index+","+mem + "\r\n");}
                index += 1;
                //System.out.println(mem); // for debugging
            }            
            partnerReader.close();
            writer.close();
        }
        catch (IOException ex)
        {
        }
        finally
        {
            // you know, in case something goes bad, these NEED to close.
            try
            {
                writer.close();
                partnerReader.close();
            }
            catch (IOException | NullPointerException e)
            {
            }
        }
    }

     /**
      * Gets the Oversight Pastor from the pastorFile and adds it to MemberInfo.
      * @param communityGroup The community group the member belongs to.
      * @param pastorFile The filename of the department summary.
      * @return The name of the Oversight Pastor for this Community Group.
      * @throws FileNotFoundException 
      */
     private static String GetOversightPastor(String communityGroup, String pastorFile) throws FileNotFoundException
     {
         String         oversightPastor = "None Found"; // the name of the Oversight Pastor
         CSVReader      pastorReader = null;            // Reader for the pastorFile
         String[]       nextLinePastor;
         try {
             // Read the pastorFile and pick out the Oversight Pastor for each Community Group member.
             pastorReader = new CSVReader(new FileReader(pastorFile),',');
             while ((nextLinePastor = pastorReader.readNext()) != null){
                 if (nextLinePastor[2].trim().equals(communityGroup.trim())) {
                     oversightPastor = nextLinePastor[5].trim();
                 }
                 // if the member's a Comm Group leader, make his Pastor Scott, until I can find some better way to get the right information...
                 else if(communityGroup.contains("Leaders")){                     
                     oversightPastor = "Scott Beierwaltes";
                 }
             }
             // Obviously, return the Oversight Pastor for the member.
             return oversightPastor;
         }
         // if there are any errors, make the Oversight Pastor = "None Found"
         // This tells me there's an error with this member.
         catch (FileNotFoundException e){
             System.out.println("File not Found");
             return oversightPastor;
         }
         catch (IOException ex) {
             System.out.println(ex);
             return oversightPastor;
         }
         finally{
             try {
                 pastorReader.close();
             }
             catch(IOException e){
                 System.out.println(e);
             }
         }
     }
     
     /**
      * Primary method to make the CSVs
      * @param custReptSource File path of custom_report CSV.
      * @param deptSumSource File path of department_summary CSV.
      * @param memberInfoSource File path of MemberInfo CSV
      * @param makeOversightCSV True = Make CSV for all Oversight Pastors
      * @throws SQLException
      * @throws IOException 
      */   
     public static void WorkWithOHPSDatabase(String custReptSource, String deptSumSource, Boolean makeOversightCSV) throws SQLException, IOException{
         Connection     OHPS_con = HSQLDB_Connection.DBConnection();    // Creates the connection to OHPS database      
         Statement      OHPS_stmt = OHPS_con.createStatement();         // The Statement that runs queries on the OHPS database
         int            result = 0;                                     // Keeps track of totals for changes
         ResultSet      qResult = null;                                 // Holds results for queries
         String         totalFilename = "OversightPastorTotals.csv";    // Filename for the Oversight Pastor totals
         final String   custReptTable = "tblCustomReport";              // Table name for Partners
         final String   deptSumTable = "tblDeptSummary";                // Table name for Pastors and Community Groups
         final String   memberInfoTable = "tblMemberInfo";              // Table name for Members information
         /*String*/         custReptSource = "\"custom_report.csv\"";       // TEMP. Will change to property. For now, its the filename and location of the partner CSV
         /*String*/         deptSumSource = "\"department_summary.csv\"";   // TEMP. Will change to property. For now, its the filename and location of the pastor CSV
         String         memberInfoSource = "\"MemberInfo.csv\"";        // TEMP. Will change to property. For now, its the filename and location of the MemberInfo CSV
                  
         try{
             // These are commented out.  Use for debugging purposes.
             //ClearTable(OHPS_stmt, custReptTable);
             //ClearTable(OHPS_stmt, deptSumTable);
             //ClearTable(OHPS_stmt, memberInfoTable);
             
             // Create the tables.
             // NOTE: if you don't drop the tables at the end, you don't HAVE to re-create them, as they'll be in the .script file.
             OHPS_stmt.executeUpdate("CREATE TEXT TABLE IF NOT EXISTS "+custReptTable+" ("
                     + "last_name VARCHAR(50) NOT NULL,"
                     + "first_name VARCHAR(50) NOT NULL,"
                     + "campus VARCHAR(30) NOT NULL,"
                     + "groups VARCHAR(500),"
                     + "first_steps VARCHAR(10),"
                     + "partnership VARCHAR(10),"
                     + "membership_type VARCHAR(10));");             
             
             System.out.println(custReptTable + " table created successfully.");
             
             OHPS_stmt.executeUpdate("CREATE TEXT TABLE IF NOT EXISTS "+deptSumTable+" ("
                     + "department VARCHAR(50) NOT NULL,"
                     + "campus VARCHAR(50) NOT NULL,"
                     + "group_name VARCHAR(50) NOT NULL,"
                     + "main_group_leader VARCHAR(50) NOT NULL,"
                     + "assistant_group_leaders VARCHAR(10),"
                     + "coach VARCHAR(10),"
                     + "director VARCHAR(10),"
                     + "total_participants VARCHAR(3));");
             
             System.out.println(deptSumTable + " table created successfully.");
             
             OHPS_stmt.executeUpdate("CREATE TEXT TABLE IF NOT EXISTS "+memberInfoTable+" ("
                     + "\"Index\" VARCHAR(5) NOT NULL,"
                     + "\"Full Name\" VARCHAR(50) NOT NULL,"
                     + "\"Community Group\" VARCHAR(50) NOT NULL,"
                     + "\"Hospitality\" VARCHAR(10) NOT NULL,"
                     + "\"Kids Ministry\" VARCHAR(10) NOT NULL,"
                     + "\"Youth\" VARCHAR(10) NOT NULL,"
                     + "\"Music and Media\" VARCHAR(10) NOT NULL,"
                     + "\"Benevolence\" VARCHAR(10) NOT NULL,"
                     + "\"Oversight Pastor\" VARCHAR(50),"
                     + "\"Community\" VARCHAR(10) NOT NULL,"
                     + "\"Serving\" VARCHAR(10) NOT NULL,"
                     + "\"Partner\" VARCHAR(10),"
                     + "\"Site\" VARCHAR(50) NOT NULL,"
                     + "\"First Steps Date\" VARCHAR(10),"
                     + "\"Partnership Date\" VARCHAR(10));");             
             
             System.out.println(memberInfoTable + " table created successfully.");
             
             //Populate the tables
             CSVtoTable(OHPS_stmt,custReptTable,custReptSource);
             CSVtoTable(OHPS_stmt,deptSumTable,deptSumSource);
             CSVtoTable(OHPS_stmt,memberInfoTable,memberInfoSource);
             
             // TEST TO GET ALL GROUPS AND PEOPLE IN THEM
             String sGroup = "Groups";
             String fullName = null;
             String[] groups = null;
             Statement s = OHPS_con.createStatement();             
             ResultSet pResult = s.executeQuery("SELECT first_name, last_name, groups FROM tblCustomReport");
             FileWriter tWriter = new FileWriter(sGroup+".csv");
             int index = 0;
             
             while(pResult.next()){
                 fullName = pResult.getString("first_name")+" "+pResult.getString("last_name");
                 groups = pResult.getString("groups").split(",");
                 for(String group : groups){
                     if(index==0){tWriter.write("Index,Full Name,Groups\r\n");}else{tWriter.write(index+","+fullName+","+group+"\r\n");}                                          
                 }
                 index += 1;
             }
             tWriter.close();
             // END TEST
             if(makeOversightCSV){
                 // Populate qResult with each Oversight Pastor name, then create a CSV with the MemberInfo for each of their Community Groups
                 qResult = OHPS_stmt.executeQuery("SELECT DISTINCT \"Oversight Pastor\" FROM "+memberInfoTable+" WHERE \"Oversight Pastor\" <> 'Oversight Pastor'");
                 
                 try (FileWriter totalFilenameHeader = new FileWriter(totalFilename)) {
                     totalFilenameHeader.write("Pastor,Oversees\r\n");
                 }
                 while(qResult.next()){
                     //System.out.println(qResult.getString("Oversight Pastor")); // <- for debugging
                     MakeOversightPastorCSV(OHPS_con,memberInfoTable,qResult.getString("Oversight Pastor"),totalFilename);
                     result += 1;
                 }
                 System.out.println("Oversight Pastor CSVs created.\r\n"+result+ " rows affected.");
             }             
         }
         catch(SQLException ex){ ex.printStackTrace(System.out); }
         finally{if(OHPS_con.isClosed()){System.out.println("Connection already closed.");}else{OHPS_stmt.execute("SHUTDOWN");OHPS_con.close();System.out.println("Connection successfully closed.");}}
     }
     
     /**
      * Creates a table source from a CSV.
      * @param stmt Statement variable for the connection.
      * @param tableName Name of the table that's being set from a CSV.
      * @param tableSourceCSV Name of the CSV that is used to populate the table.
      */
     private static void CSVtoTable(Statement stmt, String tableName, String tableSourceCSV){
         // Populate tableName with the information found in the tableSourceCSV.
         try{
             stmt.executeUpdate("SET TABLE "+tableName+" SOURCE "+tableSourceCSV);
             System.out.println(tableSourceCSV + " added successfully to "+tableName+".");
         }
         catch (SQLException ex){
             System.out.println("Problem in CSVtoTable.\r\n");
             ex.printStackTrace(System.out);
         }
     }
     
     /**
      * Creates a CSV for each Oversight Pastor detailing the members in the Community Groups they oversee.
      * @param con The connection to the database that is being worked with.
      * @param tableName The table name of the table that is being worked with.
      * @param pastorName The pastor's name that is being worked with.
      * @param totalFilename The name of the oversight pastor summary file.
      * @throws SQLException
      * @throws IOException 
      */
     private static void MakeOversightPastorCSV(Connection con, String tableName, String pastorName, String totalFilename) throws SQLException, IOException{
         String             getPastorNameSQL = "SELECT * FROM "+tableName+" WHERE \"Oversight Pastor\" LIKE ? "; // The SQL query to get the member who's Oversight Pastor matches the pastorName.
         PreparedStatement  getOversightPastorName = null;  // The prepared Statement that runs the getPastorNameSQL parameter query
         ResultSet          qResult = null;                 // Holds the results of the prepared statement
         String             member;                         // Holds member information from Member Class
         List<String>       members = new ArrayList<>();    // Holds each 'member' string
         String             columnHeader = "";              // Holds the column headers
         FileWriter         pastorWriter = null;            // Writes each oversight pastor CSV file
         FileWriter         totalWriter = null;             // Writes the oversight totals CSV file
         int                memberTotal = 0;                // Variable for member totals
         
         try{
             // run the prepared statement
             getOversightPastorName = con.prepareStatement(getPastorNameSQL);
             getOversightPastorName.setObject(1, pastorName);
             qResult = getOversightPastorName.executeQuery();
             
             // Much easier to write over and over than qResult.getMetaData()
             ResultSetMetaData meta = qResult.getMetaData();
             
             // Get column headers from the table
             for (int i = 1; i <= meta.getColumnCount(); i++) {
                 if(i!=meta.getColumnCount()){
                     columnHeader += meta.getColumnLabel(i)+",";}
                 else{
                     columnHeader += meta.getColumnLabel(i);}                 
             }
             
             // Get information for each member column by column
             while(qResult.next()){
                 member = "";
                 for (int i = 1; i <= meta.getColumnCount(); i++) {                     
                     if(i!=meta.getColumnCount()){
                         member += qResult.getString(meta.getColumnLabel(i))+",";
                     }
                     else{
                         member += qResult.getString(meta.getColumnLabel(i));}
                 }
                 members.add(member);
             }
             // Make new file
             pastorWriter = new FileWriter(pastorName + ".csv");
             
             // Write the column header and the information from members
             pastorWriter.write(columnHeader+"\r\n");
             for(String mem : members){
                 pastorWriter.write(mem + "\r\n");
                 memberTotal += 1;
             }
             pastorWriter.write("Total: "+memberTotal);
             pastorWriter.close();             
             totalWriter = new FileWriter(totalFilename,true);
             totalWriter.write(pastorName+","+memberTotal+"\r\n");
             totalWriter.close();
         }
         catch (SQLException ex){
             System.out.println("\r\nProblem in MakeOversightPastorCSVs():");
             ex.printStackTrace(System.out);
         }
     }
     
     /**
      * Clears the table with 'DROP TABLE'
      * @param stmt The Statement variable for the connection
      * @param tableName Table that will be removed
      */
     private static void ClearTable(Statement stmt, String tableName){
         try {
             stmt.executeQuery("DROP TABLE IF EXISTS "+tableName+";");
         } catch (SQLException ex) {
             System.out.println("Could not drop table: "+tableName+".");
         }
     }
}
