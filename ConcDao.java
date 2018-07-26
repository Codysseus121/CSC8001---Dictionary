
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileInputStream;

public class ConcDao
{
    private final static String url="jdbc:mysql://localhost:3306/concordances?autoReconnect=true&rewriteBatchedStatements=true&useSSL=FALSE&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    //?autoReconnect=true&useSSL=FALSE&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=
    //false&serverTimezone=UTC";
    private final static String USER="root";
    private final static String PASSWORD="";

    static 
    {
        try 
        {
            Class.forName("com.mysql.jdbc.Driver");
        } 
        catch (ClassNotFoundException ex)
        {
        }
    }

    private Connection getConnection() throws SQLException
    {
        return DriverManager.getConnection(url, USER, PASSWORD);
    }

    private void closeConnection(Connection connection) {
        if (connection == null)
            return;
        try {
            connection.close();
        } catch (SQLException ex) {
        }
    }

    public void insertCon(List<Concordance> conclist)  {
        Connection connection = null;
        PreparedStatement statement=null;
        try {
            connection = getConnection(); 
            connection.setAutoCommit(false);
            int i=0;
            String filename="King_James_Bible.txt";
            String fileid = "1";
            statement = connection.prepareStatement(
                "insert into concordance (file_name, lcontext, kwic, rcontext, file_id)" + "values (?, ?, ?, ?, ?)");

            for (Concordance c: conclist)
            {
                statement.setString(1, filename);
                statement.setString(2, c.getLContext());
                statement.setString(3, c.getKwic());
                statement.setString(4, c.getRContext());
                statement.setString(5, fileid);
                statement.addBatch();
                i++;

                if (i % 5000 == 0 || i == conclist.size()) {
                    statement.executeBatch();
                    statement.clearBatch();
                    connection.commit();
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (statement != null){
                try { statement.close();
                    connection.setAutoCommit(true);
                    closeConnection(connection);}
                catch (SQLException e){
                    System.out.println("Exception in finally block");
                }
            }

        }
    }

    public void insertPara(List<String> keywords)  {
        Connection connection = null;
        PreparedStatement statement=null;
        try {
            connection = getConnection(); 
            connection.setAutoCommit(false);
            int i=0;

            statement = connection.prepareStatement(
                "insert into paragraph (paragraph)" + "values (?)");

            for (String para: keywords)
            {
                statement.setString(1, para);
                statement.addBatch();
                i++;

                if (i % 2000 == 0 || i == keywords.size()) {
                    statement.executeBatch();
                    statement.clearBatch();
                    connection.commit();
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (statement != null){
                try { statement.close();
                    connection.setAutoCommit(true);
                    closeConnection(connection);}
                catch (SQLException e){
                    System.out.println("Exception in finally block");
                }
            }
        }
    }

    public void insertFile() throws Exception
    {
        String id = "001";
        String fileName = "C://users//Mitsos//Desktop//Revelation.txt";

        FileInputStream fis = null;
        PreparedStatement pstmt = null;
        Connection conn = null;
        try {
            conn = getConnection();
            conn.setAutoCommit(false);
            File file = new File(fileName);
            fis = new FileInputStream(file);
            pstmt = conn.prepareStatement("insert into PARAGRAPH(paragraph, file_id) values ( ?, ?)");
            pstmt.setString(2, id);
            pstmt.setAsciiStream(1, fis, (int) file.length());
            pstmt.executeUpdate();
            conn.commit();
        } 
        catch (Exception e) 
        {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        } 
        finally 
        {
            pstmt.close();
            fis.close();
            conn.close();
        }
    }

}