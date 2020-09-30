import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GalleryServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String userid = (String) session.getAttribute("userid");
        String action = request.getParameter("action");

        try {

            try {
                Class.forName("oracle.jdbc.OracleDriver");
            } catch (Exception ex) {
            }
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "AW", "12345");
            Statement stmt = con.createStatement();

            if (action.equals("getList")) {
                String phase = request.getParameter("phase");
                if (phase == null)
                    phase = "";

                ResultSet result = stmt.executeQuery(
                        "SELECT RAWTOHEX(p.id) AS PHOTOID FROM PHOTOS p LEFT JOIN USERS u ON u.ID = p.USERID WHERE u.USERID = '"
                                + userid + "' AND (FILENAME LIKE '%" + phase + "%' OR CAPTION LIKE '%" + phase + "%')");

                List<String> photoIDs = new ArrayList<>();

                while (result.next()) {
                    photoIDs.add(result.getString("PHOTOID"));
                }

                response.setContentType("application/json");
                PrintWriter out = response.getWriter();
                String json = "[";
                for (String id : photoIDs) {
                    json += "\"" + id + "\",";
                }
                if (json.length() != 1)
                    json = json.substring(0, json.length() - 1);
                json += "]";

                out.println(json);
                out.close();

            } else if (action.equals("getPicInfo")) {
                String photoID = request.getParameter("photoid");

                ResultSet result = stmt.executeQuery(
                        "SELECT FILENAME, CAPTION , DATETAKEN, PICTURE FROM PHOTOS WHERE ID = '" + photoID + "'");

                if (result.next()) {
                    String fileName = result.getString("FILENAME");
                    String caption = result.getString("CAPTION");
                    String date = result.getDate("DATETAKEN").toString();

                    byte[] buff = new byte[1024];
                    Blob blob = result.getBlob("PICTURE");
                    File file = new File("../../images/" + photoID);
                    InputStream is = blob.getBinaryStream();
                    FileOutputStream fos = new FileOutputStream(file);
                    for (int b = is.read(buff); b != -1; b = is.read(buff)) {
                        fos.write(buff, 0, b);
                    }
                    is.close();
                    fos.close();

                    response.setContentType("application/json");
                    PrintWriter out = response.getWriter();
                    String json = "{\"fileName\":\"" + fileName + "\",\"caption\":\"" + caption + "\",\"date\":\""
                            + date + "\"}";

                    out.println(json);
                    out.close();
                }
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
        }
    }
}