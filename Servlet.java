import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

// http://localhost:8080/Laba7/Servlet?name=Artur Klimov&number=4318&defaultSize=false&array=2_1_-4_34_-5423_-23_65_43
// 10 0000

@WebServlet(urlPatterns = {"/Servlet"})
public class Servlet extends HttpServlet {

    private int countofReload = 1;
    private final int minimalFontSize = 5;
    private int fontSize = 0;
    private int sumEvenPositiveNumbers = 0; // Чётные положительные
    private int sumOddNegativeNumbers = 0; // Нечётные отрицательные
    private PrintWriter out;
    private boolean arrayWithInvalidNumbers = false;
    private String studentName;
    private String studentGroupNumber;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        studentName = request.getParameter("name");
        studentGroupNumber = request.getParameter("group");
        Task(request.getParameter("array"));
        if (checkDefaultSize(request.getParameter("defaultSize"))) {
            fontSize = 0;
        }
        response.setContentType("text/html;charset=UTF-8");
        out = response.getWriter();
        setHeaderAndOpenHtmlPage();
        checkFontSize();
        setMainTableInHtmlPage();
        closeHtmlPage();
        countofReload++;
    }
    
    private void Task(String arrayFromParameter) {
        String[] arrayWithNumbers = arrayFromParameter.split("_");
        ArrayList<Integer> listInt = new ArrayList<>();
        arrayWithInvalidNumbers = false;
        sumEvenPositiveNumbers = 0;
        sumOddNegativeNumbers = 0;
        try {
            for ( String elem: arrayWithNumbers) {
                listInt.add(Integer.parseInt(elem));
            }
            for (Integer number : listInt) {
                if (isNumberEvenPositive(number)) {
                    sumEvenPositiveNumbers += number;
                }
                if (isNumberOddNegative(number)) {
                    sumOddNegativeNumbers += number;
                }
            }
        }
        catch (NumberFormatException ignored){
            sumEvenPositiveNumbers = 0;
            sumOddNegativeNumbers = 0;
            arrayWithInvalidNumbers = true;
        }
    }

    private boolean isNumberEvenPositive(int number) {
        return number % 2 == 0 && number > 0;
    }

    private boolean isNumberOddNegative(int number) {
        return number % 2 == -1 && number < 0;
    }


    private void closeHtmlPage() {
        out.println("</body>");
        out.println("</html>");
    }

    private void setMainTableInHtmlPage() {
        out.println("<h2>" + studentName + " " + studentGroupNumber + "</h2>" +
                "<h" + fontSize + ">" +
                "   <table border=0>" +
                "       <tr><td>Вы посетитель №: " + countofReload + "</td>" +
                "       <tr><td>Размер текста: " + fontSize + "</td>" +
                "   </table>" +
                "</h" + fontSize + ">");
        if (arrayWithInvalidNumbers) {
                out.println("<h" + fontSize + ">" +
                        "   <table border=0>" +
                        "<tr><td>Неправильные аргументы!</td>" +
                        "   </table>" +
                        "</h" + fontSize + ">");
        }
        else {
            out.println("<h" + fontSize + ">" +
                    "   <table border=0>" +
                    "       <tr><td> Сумма чётных положительных чисел = " + sumEvenPositiveNumbers + "</td>" +
                    "       <tr><td> Сумма нечётных отрицательных чисел = " + sumOddNegativeNumbers + "</td>" +
                    "   </table>" +
                    "</h" + fontSize + ">");
        }
    }

    private void checkFontSize() {
        if (fontSize < minimalFontSize){
            fontSize++;
        }
        else {
            out.println("<h1> Минимальный размер текста достигнут! </h1>");
        }
    }

    private void setHeaderAndOpenHtmlPage() {
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Laba 7</title>");
        out.println("</head>");
        out.println("<body>");
    }

    private boolean checkDefaultSize(String isDefaultSizeString) {
        return isDefaultSizeString != null && isDefaultSizeString.equals("true");
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
