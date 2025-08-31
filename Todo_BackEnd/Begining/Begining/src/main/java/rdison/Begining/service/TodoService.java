package rdison.Begining.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rdison.Begining.models.Todo;
import rdison.Begining.repository.TodoRepository;

import java.util.List;

@Service
public class TodoService {
    @Autowired
    private TodoRepository todoRepository;

//    public void printTodos(){
//        System.out.println(todoRepository.getAllTodos());
//    }

        //JPA
    public Todo createTodo(Todo todo){
         return todoRepository.save(todo);//create and update pandradhukku save use pandrom
    }

    public Todo getTodoById(Long id){
        return todoRepository.findById(id).orElseThrow(()->new RuntimeException("Todo not Found buddy..."));
    }

    public List<Todo> getTodos(){
        return todoRepository.findAll();
    }

    public Todo updateTodo(Todo todo){
        return todoRepository.save(todo);
    }

    public void deleteTodoById(Long id){
        todoRepository.delete(getTodoById(id));
    }

    public void deleteTodo(Todo todo){
        todoRepository.delete(todo);
    }


    //pagination
    public Page<Todo> getAllTodosPages(int page, int size){
        Pageable pageable= PageRequest.of(page,size);
        return todoRepository.findAll(pageable);
    }
}
