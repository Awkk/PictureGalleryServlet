import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.sql.*;

public class LoginServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String userid = request.getParameter("userid");
        String password = request.getParameter("password");
        byte[] id = {};
        boolean loggedIn = false;

        try {
            try {
                Class.forName("oracle.jdbc.OracleDriver");
            } catch (Exception ex) {
            }
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "AW", "12345");
            Statement stmt = con.createStatement();
            ResultSet result = stmt.executeQuery("SELECT id, password FROM USERS WHERE USERID = '" + userid + "'");
            PasswordAuthentication pa = new PasswordAuthentication();

            if (result.next()) {
                id = result.getBytes(1);
                loggedIn = pa.authenticate(password, result.getString(2));
            }

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
        } catch (Exception e) {
        }

        if (loggedIn) {
            HttpSession session = request.getSession();
            session.setAttribute("userid", userid);
            session.setAttribute("id", id);
            response.sendRedirect("/gallery");
        } else {
            request.setAttribute("message", "UserID or Password not correct.");
            response.setContentType("text/html");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }

    }

}