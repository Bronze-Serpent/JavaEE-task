package barabanov.ui.servlets.progress;

import barabanov.ORM.DAOCurrencyJDBC;
import barabanov.ORM.DAOProgressJDBC;
import barabanov.entity.IDToken;
import barabanov.entity.Item;
import barabanov.entity.Progress;
import barabanov.service.CurrencyService;
import barabanov.service.ProgressService;
import barabanov.ui.DBManager;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;


@WebServlet(name = "ProgCreateServlet", value = "/ProgCreateServlet")
public class ProgCreateServlet extends HttpServlet
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
        long playerId = Long.parseLong(request.getParameter("playerId"));
        long resourceId = Long.parseLong(request.getParameter("resourceId"));
        long score = Long.parseLong(request.getParameter("score"));
        long maxScore = Long.parseLong(request.getParameter("maxScore"));

        Progress progress = new Progress(new IDToken(id, playerId, resourceId), score, maxScore);

        try {
            progressS.writeToDB(progress);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        request.getRequestDispatcher(progressPage).forward(request, response);
    }
}
