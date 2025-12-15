package test.edu.univ.erp.data;

import edu.univ.erp.data.AuthDatabaseConnection;
import edu.univ.erp.data.ERPDatabaseConnection;
import edu.univ.erp.data.UsersTable;
import org.mindrot.jbcrypt.BCrypt;

import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;


public class UsersTableTest {

    private UsersTable usersTable;

    @BeforeEach
    void setup() throws SQLException {
//        ERPDatabaseConnection.enableTestMode();
        AuthDatabaseConnection.enableTestMode();
        usersTable=new UsersTable();
        Connection con=AuthDatabaseConnection.getConnection();
        con.createStatement().execute("DELETE FROM users_auth");
    }

    @Test
    void testInsertUser(){
        String username="testing";
        String password="ok";
        String role="student";

        int userId=usersTable.insertUser(username,password,role);

        assertTrue(userId>0, "user ID should be generated and greater than 0.");
        assertTrue(usersTable.userNameExists(username), "Inserted username must exist in table. ");
    }

    @Test
    void testUserNameExistsFalse(){
        String username="123";
        boolean exists=usersTable.userNameExists(username);
        assertFalse(exists, "Username should not exist.");
    }

}
