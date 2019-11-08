package osmServer.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import osmServer.Model.User;
import osmServer.Repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Long saveUser(User user){
        int emailUsage=userRepository.findByEmail(user.getEmail()).size();
        int usrnmUsage=userRepository.findByUsername(user.getUsername()).size();
        if(emailUsage>0 || usrnmUsage>0){
            return (long)-1;
        }
        else{
            User savedVal=userRepository.save(user);
            return savedVal.getId();
        }
    }

    public User findUser(String user){
        User result=new User();
        List<User> userList=userRepository.findByEmail(user);
        userList.addAll(userRepository.findByUsername(user));
        return userList.size()>0?userList.get(0):result;
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public int checkPassword(String credentials,String password){
        User result=findUser(credentials);
        if(!StringUtils.isEmpty(result.getPassword())){
           if(result.getPassword().equals(password)){
               return 1;
           }
           else {
               return -1;
           }
        }
        return 0;
    }

    public List<String> getAllUserName(){
            return userRepository.findAll().stream()
                    .map(user->{return user.getUsername();})
                    .collect(Collectors.toList());
    }

    public List<String> getAllEmail(){
        return userRepository.findAll().stream()
                .map(user->{return user.getEmail();})
                .collect(Collectors.toList());
    }

}
