
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ConcDao {
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
        }
    }

    private Connection getConnection() throws SQLException {
       
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/concordances,","root","");
        //?autoReconnect=true&useSSL=FALSE&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
            
    }

    private void closeConnection(Connection connection) {
        if (connection == null)
            return;
        try {
            connection.close();
        } catch (SQLException ex) {
        }
    }

     
    
    public void insertCon(Concordance con) {
        
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "insert into conc (lcontext, kwic, rcontext, paragraph)" + "values (?, ?, ?, ?)");
            statement.setString(1, con.getLContext());
            statement.setString(2, con.getKwic());
            statement.setString(3, con.getRContext());
            statement.setString(4, con.getPara());
            statement.execute();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            closeConnection(connection);
        }
    }

   
    
}