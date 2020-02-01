import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class hw3 {

    private static Connection conn = null;

    private static void connectToDB() {
        try {
            // DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
            conn = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/orcl", "Scott", "tiger");
            System.out.println("Connection successful!");
        }
        catch (SQLException E){
            System.out.println("Connection unsuccessful.");
        }
    }

    private static void closeConnection() {
        try {
            conn.close();
            System.out.println("Connection closed successfully!");
        }
        catch (SQLException E) {
            System.out.println("Connection not closed.");
        }
    }

    public static Connection getConnection() {
        return conn;
    }

    public static void main(String[] args) {
        // Make connection to the db
        connectToDB();

        // Create GUI
        MovieSearchGUI frame = new MovieSearchGUI();
        frame.setVisible(true);

        // Close the connection when user closes the application window
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                closeConnection();
            }
        });
    }
}
