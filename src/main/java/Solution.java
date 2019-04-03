import dao.Repository;
import models.Model;

import java.util.Arrays;
import java.util.Date;

public class Solution {
    public static final String DB_DRIVER = "org.h2.Driver";
    public static void main(String[] args) throws ClassNotFoundException {
        Class.forName(DB_DRIVER);
        Model model=new Model("sad", 12, Arrays.asList("sad", "asd"));
        System.out.println(model.getDate());
        Repository repository = new Repository();
        repository.add(new Model("sad", 12, Arrays.asList("sdf", "sdf")));
        //repository.getById(1);
    }
}
