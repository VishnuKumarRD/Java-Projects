package rdison.Begining.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rdison.Begining.service.TodoService;
import rdison.Begining.models.Todo;

import java.util.List;

@RestController
@RequestMapping("/api/v1/todo")
@Slf4j
public class TodoController {
    @Autowired
    private TodoService todoService;

//    @GetMapping("/creat")
//    String getTodo() {
//       todoService.printTodos();
//        return "I try to create the TodoList";
//    }
//
//    @GetMapping("/delete")
//    String getDeleteTodoId() {
//        return "TodoList is deleted from this Item";
//    }

    //pathvariable
//    @GetMapping("/{id}")
//    String getTodo(@PathVariable int id){
//        return "I try to create the TodoList "+id;
//    }

    //RequestParam
//    @GetMapping()
//    String getToDoPar(@RequestParam("todoid") long id){
//        return "ToDo list id "+id;
//    }

//    @GetMapping("/creating")
//    String createUser(@RequestParam String userId,@RequestParam String password){
//        return "Username is : "+userId+ "<br>" +"Password is : "+password;
//    }

//    @PostMapping("/hide")
//    String createUser(@RequestBody String body){
//        return body;
//    }


//    @PutMapping("/{id}")
//    String getPut(@PathVariable long id){
//        return "ToDo list id "+id;
//    }

//    @DeleteMapping("/{id}")
//    String getDelete(@PathVariable long id){
//        return "ToDo list id "+id;
//    }

    //    ----JPA
    @PostMapping("/create")
    ResponseEntity<Todo> createUser(@RequestBody Todo todo) {
        Todo createdTodo = todoService.createTodo(todo);
        return new ResponseEntity<>(createdTodo, HttpStatus.CREATED);
    }

//    @GetMapping("/{id}")
//    ResponseEntity<Todo> getTodoById(@PathVariable long id) {
//        Todo todoById = todoService.getTodoById(id);
//        return new ResponseEntity<>(todoById, HttpStatus.OK);
//    }


//    swagger doc api error description
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Todo retrived successfully "),
            @ApiResponse(responseCode = "404",description = "Todo was Not Found ")
    })

    //jpa -for getting the specific data in database
    @GetMapping("/{id}")
    ResponseEntity<Todo> getTodoById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(todoService.getTodoById(id), HttpStatus.OK);
        } catch (RuntimeException exception) {
            log.info("error");
            log.error("",exception);
            log.warn("You get an Warning...");
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
    // for getting the all data in that H2 database
    @GetMapping
    ResponseEntity<List<Todo>> getTodos() {
        return new ResponseEntity<List<Todo>>(todoService.getTodos(), HttpStatus.OK);
    }
    //Update the value
    @PutMapping
    ResponseEntity<Todo> updateTodoById(@RequestBody Todo todo) {
        return new ResponseEntity<>(todoService.updateTodo(todo), HttpStatus.OK);
    }
    //delete the data by using id
    @DeleteMapping("/{id}")
    void deleteTodoById(@PathVariable long id) {
        todoService.deleteTodoById(id);
    }


    //pagination - displays page by page content
    @GetMapping("/page")
    ResponseEntity<Page<Todo>> getTodosPaged(@RequestParam int page, @RequestParam int size){
        return new ResponseEntity<>(todoService.getAllTodosPages(page,size),HttpStatus.OK);
    }
}
