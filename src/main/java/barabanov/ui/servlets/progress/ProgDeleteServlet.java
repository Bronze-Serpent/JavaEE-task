package barabanov.ui.servlets.progress;

import barabanov.ORM.DAOProgressJDBC;
import barabanov.service.ProgressService;
import barabanov.ui.DBManager;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;


@WebServlet(name = "ProgDeleteServlet", value = "/ProgDeleteServlet")
public class ProgDeleteServlet extends HttpServlet
{

    private final String progressPage = "progress.jsp";

    private ProgressService progressS;


    @Override
    public void init() throws ServletException
    {
        super.init();

        Connection dbConnection = DBManager.getManager().getConn();
        progressS = new ProgressService(new DAOProgressJDBC(dbConnection));
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        long id = Long.parseLong(request.getParameter("id"));

        try {
            progressS.deleteFromDB(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        request.getRequestDispatcher(progressPage).forward(request, response);
    }
}
