package rdison.Begining.service;


import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Service;
import rdison.Begining.models.User;
import rdison.Begining.repository.UserRepository;



@Service

public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User createUser(User user){
        return userRepository.save(user);//create and update pandradhukku save use pandrom
    }

    public User getUserById(Long id){
        return userRepository.findById(id).orElseThrow(()->new RuntimeException("User not Found buddy..."));
    }


}


