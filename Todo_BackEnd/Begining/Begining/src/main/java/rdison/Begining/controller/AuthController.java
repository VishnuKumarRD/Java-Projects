package rdison.Begining.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rdison.Begining.models.User;
import rdison.Begining.repository.UserRepository;
import rdison.Begining.service.UserService;
import rdison.Begining.util.JwtUtil;

import java.util.Map;

@RestController
@RequiredArgsConstructor //or Autowired use pannalam instance create pandradhuku
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Map<String, String> body) {
        String email=body.get("email");

        String password=passwordEncoder.encode(body.get("password"));//to encrypt the password


        //Email validation already exist or not
        if(userRepository.findByEmail(email).isPresent()){
            return new ResponseEntity<>("Email is already exists",HttpStatus.CONFLICT);
        }
        userService.createUser(User.builder().email(email).password(password).build());
        return new ResponseEntity<>("Successfully Registered",HttpStatus.CREATED);
    }

    //Login Authentication
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> body) {//email,password is sent by the user
        String email = body.get("email");
        String password = body.get("password");

        //if user is not found
        var userOptional=userRepository.findByEmail(email);
        if(userOptional.isEmpty()){
            return new ResponseEntity<>("User not Registered",HttpStatus.UNAUTHORIZED);
        }


        User user=userOptional.get();
        if (!passwordEncoder.matches(password,user.getPassword())){ //Compares the Database password and user Entered password
            return new ResponseEntity<>("Invalid User",HttpStatus.UNAUTHORIZED);
        }
        String token=jwtUtil.generateToken(email);//if match password to generate the Token for the user
        return ResponseEntity.ok(Map.of("token",token));

    }
}

