package barabanov.ui.servlets.currency;

import barabanov.ORM.DAOCurrencyJDBC;
import barabanov.entity.Currency;
import barabanov.entity.IDToken;
import barabanov.service.CurrencyService;
import barabanov.ui.DBManager;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;


@WebServlet(name = "CurDeleteServlet", value = "/CurDeleteServlet")
public class CurDeleteServlet extends HttpServlet
{

    private final String CurrencyPage = "currency.jsp";

    private CurrencyService currencyS;


    @Override
    public void init() throws ServletException
    {
        super.init();

        Connection dbConnection = DBManager.getManager().getConn();
        currencyS = new CurrencyService(new DAOCurrencyJDBC(dbConnection));
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        long id = Long.parseLong(request.getParameter("id"));

        try {
            currencyS.deleteFromDB(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        request.getRequestDispatcher(CurrencyPage).forward(request, response);
    }
}
