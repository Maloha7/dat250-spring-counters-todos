package no.hvl.dat250.rest.todos;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * Rest-Endpoint for todos.
 */
@RestController
public class TodoController{
  List<Todo> todos = new ArrayList<>();
  Random random = new Random();

    @ExceptionHandler
    public ResponseEntity<String> handle(IOException ex) {
        return ResponseEntity.ok("test");
    }

  @PostMapping("/todos")
  public Todo createTodo(@RequestBody Todo todo){
    todo.setId(random.nextLong());
    todos.add(todo);
    return todo;
  }

  @GetMapping("/todos/{id}")
  public ResponseEntity getTodo(@PathVariable long id) throws IOException {
      for (Todo todo : todos) {
          if (todo.getId() == id) {
              return ResponseEntity.ok(todo);
          }
      }
      String msg = String.format(TODO_WITH_THE_ID_X_NOT_FOUND, id);
      return ResponseEntity.ok(msg);
  }

  @PutMapping("/todos/{id}")
  public Todo updateTodo(@PathVariable long id, @RequestBody Todo updatedTodo){
      for (Todo todo : todos) {
          if (todo.getId() == id) {
              todo.setSummary(updatedTodo.getSummary());
              todo.setDescription(updatedTodo.getDescription());
          }
      }
      return updatedTodo;
  }

  @DeleteMapping("/todos/{id}")
  public ResponseEntity deleteTodo(@PathVariable long id){
        if (todos.removeIf(todo -> todo.getId() == id)){
            return ResponseEntity.ok("Ok");
        } else {
            String msg = String.format(TODO_WITH_THE_ID_X_NOT_FOUND, id);
            return ResponseEntity.ok(msg);
        }
  }

  @GetMapping("/todos")
  public List<Todo> getAll(){
    return todos;
  }

  public static final String TODO_WITH_THE_ID_X_NOT_FOUND = "Todo with the id %s not found!";
}
