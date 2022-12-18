package barabanov.ui.servlets.item;

import barabanov.ORM.DAOItemJDBC;
import barabanov.service.ItemService;
import barabanov.ui.DBManager;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;


@WebServlet(name = "itemDeleteServlet", value = "/itemDeleteServlet")
public class itemDeleteServlet extends HttpServlet
{

    private final String itemPage = "item.jsp";

    private ItemService itemS;


    @Override
    public void init() throws ServletException
    {
        super.init();

        Connection dbConnection = DBManager.getManager().getConn();
        itemS = new ItemService(new DAOItemJDBC(dbConnection));
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        long id = Long.parseLong(request.getParameter("id"));

        try {
            itemS.deleteFromDB(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        request.getRequestDispatcher(itemPage).forward(request, response);
    }
}
