package rdison.Begining.models;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

@Entity
@Data //inbuilt getter and setter functions in lombok so we use data...if we need individually then give @getter and @setter
public class Todo {
    @Id
    @GeneratedValue
    Long id;

    @NotNull
    @NotBlank
//    @Size(min=5,max=15)
//            @Pattern(regexp = "^[0-9]{10}$")

    String title;

//    @NotNull
//    @NotBlank
//    String description;
    Boolean isCompleted;

//    @Email
//    String email;
}
