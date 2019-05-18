package controller;

import dao.Repository;
import models.Model;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@WebServlet("/")
public class ServletModel extends HttpServlet {

    private Repository repository = Repository.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                doDelete(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        int number = Integer.parseInt(req.getParameter("number"));
        String tag = req.getParameter("tags");
        Model model = new Model(name, number, Collections.singletonList(tag));
        repository.add(model);
        resp.sendRedirect("/");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        resp.getWriter().print(id);
        repository.delete(id);
        resp.sendRedirect("/");
    }
}
