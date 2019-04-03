package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.Repository;
import models.Model;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet("/rest/*")
public class RestController extends HttpServlet {

    private Repository repository = Repository.getInstance();
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        switch (req.getPathInfo()){
            case "/getAll" :
                getAll(req, resp);
                break;
            case "/getById" :
                getById(req, resp);
                break;
            default:
                break;
        }
    }

    private void getAll(HttpServletRequest req, HttpServletResponse resp){
        resp.addHeader("Content-Type", "application/json");
        List<Model> models = repository.getAll();
        try {
            mapper.writeValue(resp.getOutputStream(), models);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //localhost:8080/rest/getById?id=цифрка
    private void getById(HttpServletRequest req, HttpServletResponse resp){
        resp.addHeader("Content-Type", "application/json");
        Optional<Model> modelOptional = repository.getById(Integer.valueOf(req.getParameter("id")));
        modelOptional.ifPresent(model -> {
            try {
                mapper.writeValue(resp.getOutputStream(), model);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
