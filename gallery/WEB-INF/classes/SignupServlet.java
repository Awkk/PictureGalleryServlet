import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.sql.*;

public class SignupServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userid = request.getParameter("userid");
        String password = request.getParameter("password");
        boolean success = false;

        try {
            try {
                Class.forName("oracle.jdbc.OracleDriver");
            } catch (Exception ex) {
            }
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "AW", "12345");
            Statement stmt = con.createStatement();

            PasswordAuthentication pa = new PasswordAuthentication();
            password = pa.hash(password);

            ResultSet result = stmt.executeQuery("SELECT count(*) FROM USERS WHERE USERID = '" + userid + "'");
            result.next();

            int updateReturn = -1;
            if (result.getInt(1) == 0) {
                updateReturn = stmt.executeUpdate(
                        "INSERT INTO users (userid, password) VALUES ('" + userid + "','" + password + "')");
            }

            success = updateReturn == 1;

            stmt.close();
            con.close();
        } catch (SQLException ex) {
            System.out.println("\n--- SQLException caught ---\n");
            while (ex != null) {
                System.out.println("Message: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("ErrorCode: " + ex.getErrorCode());
                ex = ex.getNextException();
                System.out.println("");
            }
        }

        request.setAttribute("message", success ? "Account successfully created." : "UserID already taken");
        response.setContentType("text/html");
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
}