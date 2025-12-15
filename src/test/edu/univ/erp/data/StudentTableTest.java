package test.edu.univ.erp.data;

import edu.univ.erp.data.ERPDatabaseConnection;
import edu.univ.erp.data.StudentTable;
import edu.univ.erp.domain.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StudentTableTest {

    private StudentTable s;

    @BeforeEach
    void setup() throws SQLException {
        ERPDatabaseConnection.enableTestMode();
        s=new StudentTable();

        Connection con=ERPDatabaseConnection.getConnection();
        con.createStatement().execute("DELETE FROM students");
    }

    @Test
    void insertStudent() throws SQLException{
//        Student snew=new Student(2024,"2","test",2000);
        s.insertStudent(2024,"2","test",2000);
        try(Connection con=ERPDatabaseConnection.getConnection();
        PreparedStatement stmt=con.prepareStatement("Select * FROM students WHERE user_id=? ")){
            stmt.setInt(1,2024);
            ResultSet rs=stmt.executeQuery();

            assertTrue(rs.next());
            assertEquals(2024,rs.getInt("user_id"));
            assertEquals("2",rs.getString("roll_no"));
            assertEquals("test",rs.getString("program"));
            assertEquals(2000,rs.getInt("year"));
        }

    }

}
