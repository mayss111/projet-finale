package Utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    private static final String url ="jdbc:mysql://localhost:3306/projetjava";
    private static final String user="root";
    private static final String password ="";
    public static Connection getConnection(){
        Connection connection = null;
        try {
            connection=DriverManager.getConnection(url,user,password);
            System.out.println("connected to the database");
        }catch (SQLException e){
            System.err.println("error connecting to the database:"+e.getMessage());
            throw new RuntimeException("failed to connect to the database",e);

        }catch (Exception e){
            System.err.println("error connecting to the database:"+e.getMessage());
        }


        return connection;}

        public static void  closeconnection (Connection connection){
            if (connection != null){
                try{
                    connection.close();
                    System.out.println("connection closed");
                } catch (SQLException e){
                    System.out.println("error closing the connection :"+ e.getMessage());
                }
            }
        }
}

