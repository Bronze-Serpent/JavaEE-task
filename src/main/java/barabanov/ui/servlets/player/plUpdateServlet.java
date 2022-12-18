package barabanov.ui.servlets.player;

import barabanov.ORM.DAOCurrencyJDBC;
import barabanov.ORM.DAOItemJDBC;
import barabanov.ORM.DAOPlayerJDBC;
import barabanov.ORM.DAOProgressJDBC;
import barabanov.entity.Player;
import barabanov.service.CurrencyService;
import barabanov.service.ItemService;
import barabanov.service.PlayerService;
import barabanov.service.ProgressService;
import barabanov.ui.DBManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;


@WebServlet(name = "plUpdateServlet", value = "/plUpdateServlet")
public class plUpdateServlet extends HttpServlet
{

    private final String playerPage = "player.jsp";

    private PlayerService playerS;


    @Override
    public void init() throws ServletException
    {
        super.init();

        Connection dbConnection = DBManager.getManager().getConn();

        CurrencyService currencyS = new CurrencyService(new DAOCurrencyJDBC(dbConnection));
        ItemService itemS = new ItemService(new DAOItemJDBC(dbConnection));
        ProgressService progressS = new ProgressService(new DAOProgressJDBC(dbConnection));
        playerS = new PlayerService(new DAOPlayerJDBC(dbConnection), currencyS, itemS, progressS);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String id = request.getParameter("playerId");
        String newName = request.getParameter("name");

        try {
            playerS.updateDB(new Player(Integer.parseInt(id),newName));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        request.getRequestDispatcher(playerPage).forward(request, response);
    }
}
