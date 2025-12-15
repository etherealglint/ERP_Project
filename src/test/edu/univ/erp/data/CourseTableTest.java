package test.edu.univ.erp.data;

import edu.univ.erp.data.CourseTable;
import edu.univ.erp.data.ERPDatabaseConnection;
import edu.univ.erp.data.UsersTable;
import edu.univ.erp.domain.Course;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CourseTableTest {

    private CourseTable courseTable;

    @BeforeEach
    void setup() throws SQLException{
        ERPDatabaseConnection.enableTestMode();
        courseTable=new CourseTable();

        Connection con=ERPDatabaseConnection.getConnection();
        con.createStatement().execute("DELETE FROM students");
        con.createStatement().execute("DELETE FROM sections");
        con.createStatement().execute("DELETE from courses");
    }

    @Test
    void insertCourse() throws SQLException{
        Course c=new Course("ABC1","abc",2);
        boolean a=courseTable.insertCourse(c);
        assertTrue(a);

        try(Connection con=ERPDatabaseConnection.getConnection();
        PreparedStatement stmt=con.prepareStatement("SELECT * FROM courses WHERE code=?")){
            stmt.setString(1,"ABC1");
            ResultSet rs=stmt.executeQuery();

            assertTrue(rs.next());
            assertEquals("ABC1",rs.getString("code"));
            assertEquals("abc",rs.getString("title"));
            assertEquals(2,rs.getInt("credits"));
        }
    }

}
