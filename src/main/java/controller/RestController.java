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
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@WebServlet("/rest/*")
public class RestController extends HttpServlet {

    private Repository repository = Repository.getInstance();
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        switch (req.getPathInfo()){
            case "/save" :
                save(req, resp);
                break;
            default:
                break;
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        switch (req.getPathInfo()){
            case "/getAll" :
                getAll(req, resp);
                break;
            case "/getById" :
                getById(req, resp);
                break;
            case "/save":
                saveData(req, resp);
                break;
            case "/delete" :
                delete(req, resp);
                break;
            default:
                resp.getWriter().print("Not found.");
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
    private void save(HttpServletRequest req, HttpServletResponse resp){
        try {
            Model model = mapper.readValue(req.getReader(), Model.class);
            model.setDate(new Date());
            repository.add(model);
        } catch (IOException e) {
            e.printStackTrace();
            //500 - HttpCode Internal Server Error
            resp.setStatus(500);
            try {
                resp.getWriter().print(e.getLocalizedMessage());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    //localhost:8888/rest/save?name=name&number=12&listTag=java
    private void saveData(HttpServletRequest req, HttpServletResponse resp){
        try {
            String name = req.getParameter("name");
            int number = Integer.valueOf(req.getParameter("number"));
            String[] listTag = req.getParameterValues("listTag");
            if(name!=null) {
                Model model = new Model(name, number, Arrays.asList(listTag));
                repository.add(model);
                resp.getWriter().print("Done.");
            } else resp.getWriter().print("Not correct data.");
        } catch (Exception e) {
            e.printStackTrace();
            //500 - HttpCode Internal Server Error
            resp.setStatus(500);
            try {
                resp.getWriter().print(e.getLocalizedMessage());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    //localhost:8888/rest/delete?id=1
    private void delete(HttpServletRequest req, HttpServletResponse resp){
        int id= Integer.valueOf(req.getParameter("id"));
        repository.delete(id);
        try {
            resp.getWriter().print("Done");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
