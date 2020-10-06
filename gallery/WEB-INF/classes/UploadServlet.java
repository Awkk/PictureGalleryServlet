import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.servlet.*;
import java.io.*;
import java.nio.file.Paths;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@MultipartConfig
public class UploadServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        boolean success = false;
        String datePicked = request.getParameter("datePicked");
        String caption = request.getParameter("caption");
        Part filePart = request.getPart("myFile");
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

        try {
            try {
                Class.forName("oracle.jdbc.OracleDriver");
            } catch (Exception ex) {
            }
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "AW", "12345");
            PreparedStatement pstmt = con.prepareStatement(
                    "INSERT INTO PHOTOS (UserID, Filename, Caption, DateTaken, Picture) VALUES (?,?,?,?,?)");

            Date date = new SimpleDateFormat("yyyy-MM-dd").parse((request.getParameter("datePicked")));

            pstmt.setBytes(1, (byte[]) request.getSession(false).getAttribute("id"));
            pstmt.setString(2, fileName);
            pstmt.setString(3, caption);
            pstmt.setDate(4, new java.sql.Date(date.getTime()));
            pstmt.setBinaryStream(5, filePart.getInputStream());

            success = pstmt.executeUpdate() == 1;

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

        request.setAttribute("message", success ? "Picture successfully uploaded"
                : "Picture upload failed\n" + fileName + "\n" + caption + "\n" + datePicked);

        request.getRequestDispatcher("upload.jsp").forward(request, response);
    }
}