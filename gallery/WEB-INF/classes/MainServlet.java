import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;

public class MainServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(true);
        String userid = (String) session.getAttribute("userid");

        if (userid == null) {
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
        response.setContentType("text/html");
        request.getRequestDispatcher("gallery.jsp").include(request, response);

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}