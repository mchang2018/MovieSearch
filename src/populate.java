
import java.io.FileReader;
import java.sql.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class populate {

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

    private static void removeData() {
        String[] tableNames = new String[] { "Movie", "Genres", "MovieCountries", "MovieFilmingLocations", "TagValues", "MovieTags" };

        try {
            Statement stmt = conn.createStatement();
            for (int i = 0; i < tableNames.length; i++) {
                stmt.execute("DELETE " + tableNames[i]);
                System.out.println(tableNames[i] + " data successfully removed!");
            }
            stmt.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    // This function populates the Movie table
    private static void populateMovies() throws IOException {
        System.out.println("Populating Movie table");

        // String[] tmp;
        String line;

        // open movies file
        BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\Michael\\IdeaProjects\\hw3\\data\\movies.dat"));
        System.out.println("Movies.dat successfully opened.");

        // read the first line (header) of file
        line = reader.readLine();

        // create PreparedStatement for inserting movie data into db
        try {
            PreparedStatement s = conn.prepareStatement(
                    "INSERT INTO Movie(mID, movieTitle, releaseYear, "
                            + "allCriticsRating, allCriticsNumReviews, "
                            + "topCriticsRating, topCriticsNumReviews, "
                            + "audienceRating, audienceNumRatings) "
                            + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)");

            // read remaining lines of data in the file
            while ((line = reader.readLine()) != null) {
                // separate each data field value and store into tmp array
                String[] tmp = line.split("\\t");

                s.setInt(1, Integer.parseInt(tmp[0]));
                s.setString(2, tmp[1]);
                s.setInt(3, Integer.parseInt(tmp[5]));
                setFloatOrNull(s, 4, tmp[7]);
                setIntOrNull(s, 5, tmp[8]);
                setFloatOrNull(s, 6, tmp[12]);
                setIntOrNull(s, 7, tmp[13]);
                setFloatOrNull(s, 8, tmp[17]);
                setIntOrNull(s, 9, tmp[18]);

                s.addBatch();
            }
            s.executeBatch();
            s.close();
            System.out.println("Movie table successfully populated!");
        }
        catch(SQLException e) {
            System.out.println("Movie table population failed.");
            e.printStackTrace();
        }
    }

    // This function populates the Genres table
    private static void populateGenres() throws IOException {
        System.out.println("Populating Genres table");

        // String[] tmp;
        String line;

        // open movies file
        BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\Michael\\IdeaProjects\\hw3\\data\\movie_genres.dat"));
        System.out.println("movie_genres.dat successfully opened.");

        // read the first line (header) of file
        line = reader.readLine();

        // create PreparedStatement for inserting movie data into db
        try {
            PreparedStatement s = conn.prepareStatement(
                    "INSERT INTO Genres VALUES(?,?)");

            // read remaining lines of data in the file
            while ((line = reader.readLine()) != null) {
                // separate each data field value and store into tmp array
                String[] tmp = line.split("\\t");

                s.setInt(1, Integer.parseInt(tmp[0]));
                s.setString(2, tmp[1]);

                s.addBatch();
            }
            s.executeBatch();
            s.close();
            System.out.println("Genres table successfully populated!");
        }
        catch(SQLException e) {
            System.out.println("Genres table population failed.");
            e.printStackTrace();
        }
    }

    // This function populates the MovieCountries table
    private static void populateMovieCountries() throws IOException {
        System.out.println("Populating MovieCountries table");

        // String[] tmp;
        String line;

        // open movies file
        BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\Michael\\IdeaProjects\\hw3\\data\\movie_countries.dat"));
        System.out.println("movie_countries.dat successfully opened.");

        // read the first line (header) of file
        line = reader.readLine();

        // create PreparedStatement for inserting movie data into db
        try {
            PreparedStatement s = conn.prepareStatement(
                    "INSERT INTO MovieCountries VALUES(?,?)");

            // read remaining lines of data in the file
            while ((line = reader.readLine()) != null) {
                // separate each data field value and store into tmp array
                String[] tmp = line.split("\\t");

                s.setInt(1, Integer.parseInt(tmp[0]));

                if (tmp.length == 1) {
                    s.setNull(2, java.sql.Types.VARCHAR);
                }
                else {
                    s.setString(2, tmp[1]);
                }
                s.addBatch();
            }
            s.executeBatch();
            s.close();
            System.out.println("MovieCountries table successfully populated!");
        }
        catch(SQLException e) {
            System.out.println("MovieCountries table population failed.");
            e.printStackTrace();
        }
    }

    // This function populates the MovieFilmingLocations table
    private static void populateMovieFilmingLocations() throws IOException {
        System.out.println("Populating MovieFilmingLocations table");

        // String[] tmp;
        String line;

        // open movies file
        BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\Michael\\IdeaProjects\\hw3\\data\\movie_locations.dat"));
        System.out.println("movie_locations.dat successfully opened.");

        // read the first line (header) of file
        line = reader.readLine();

        // create PreparedStatement for inserting movie data into db
        try {
            PreparedStatement s = conn.prepareStatement(
                    "INSERT INTO MovieFilmingLocations VALUES(?,?)");

            // read remaining lines of data in the file
            while ((line = reader.readLine()) != null) {
                // separate each data field value and store into tmp array
                String[] tmp = line.split("\\t");

                if (tmp.length == 1) {
                    continue;
                    // s.setNull(2, java.sql.Types.VARCHAR);
                }
                else {
                    s.setInt(1, Integer.parseInt(tmp[0]));
                    s.setString(2, tmp[1]);
                }
                s.addBatch();
            }
            s.executeBatch();
            s.close();
            System.out.println("MovieFilmingLocations table successfully populated!");
        }
        catch(SQLException e) {
            System.out.println("MovieFilmingLocations table population failed.");
            e.printStackTrace();
        }
    }

    // This function populates the TagValues table
    private static void populateTagValues() throws IOException {
        System.out.println("Populating TagValues table");

        // String[] tmp;
        String line;

        // open movies file
        BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\Michael\\IdeaProjects\\hw3\\data\\tags.dat"));
        System.out.println("tags.dat successfully opened.");

        // read the first line (header) of file
        line = reader.readLine();

        // create PreparedStatement for inserting movie data into db
        try {
            PreparedStatement s = conn.prepareStatement(
                    "INSERT INTO TagValues VALUES(?,?)");

            // read remaining lines of data in the file
            while ((line = reader.readLine()) != null) {
                // separate each data field value and store into tmp array
                String[] tmp = line.split("\\t");

                s.setInt(1, Integer.parseInt(tmp[0]));
                s.setString(2, tmp[1]);

                s.addBatch();
            }
            s.executeBatch();
            s.close();
            System.out.println("TagValues table successfully populated!");
        }
        catch(SQLException e) {
            System.out.println("TagValues table population failed.");
            e.printStackTrace();
        }
    }

    // This function populates the MovieTags table
    private static void populateMovieTags() throws IOException {
        System.out.println("Populating MovieTags table");

        // String[] tmp;
        String line;

        // open movies file
        BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\Michael\\IdeaProjects\\hw3\\data\\movie_tags.dat"));
        System.out.println("movie_tags.dat successfully opened.");

        // read the first line (header) of file
        line = reader.readLine();

        // create PreparedStatement for inserting movie data into db
        try {
            PreparedStatement s = conn.prepareStatement(
                    "INSERT INTO MovieTags VALUES(?,?,?)");

            // read remaining lines of data in the file
            while ((line = reader.readLine()) != null) {
                // separate each data field value and store into tmp array
                String[] tmp = line.split("\\t");

                s.setInt(1, Integer.parseInt(tmp[0]));
                s.setInt(2, Integer.parseInt(tmp[1]));
                s.setInt(3, Integer.parseInt(tmp[2]));

                s.addBatch();
            }
            s.executeBatch();
            s.close();
            System.out.println("MovieTags table successfully populated!");
        }
        catch(SQLException e) {
            System.out.println("MovieTags table population failed.");
            e.printStackTrace();
        }
    }

    private static void setIntOrNull(PreparedStatement s, int column, String value) throws SQLException {
        if (value.equals("\\N")) {
            s.setNull(column, java.sql.Types.INTEGER);
        }
        else {
            s.setInt(column, Integer.parseInt(value));
        }
    }

    private static void setFloatOrNull(PreparedStatement s, int column, String value) throws SQLException {
        if (value.equals("\\N")) {
            s.setNull(column, java.sql.Types.INTEGER);
        }
        else {
            s.setFloat(column, Float.parseFloat(value));
        }
    }

    public static void main(String[] args) throws IOException {
        connectToDB();
        removeData();
        populateMovies();
        populateGenres();
        populateMovieCountries();
        populateMovieFilmingLocations();
        populateTagValues();
        populateMovieTags();
        closeConnection();
    }
}