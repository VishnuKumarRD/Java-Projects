package rdison.Begining.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import rdison.Begining.models.Todo;
import rdison.Begining.models.User;

import java.util.Optional;
//import org.springframework.stereotype.Component;

//@Component
//public class TodoRepository {
//    String getAllTodos(){
//        return "Todos";
//    }
//}

public interface TodoRepository extends JpaRepository<Todo, Long> {


}
