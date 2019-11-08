package osmServer.Controller;


import org.springframework.web.bind.annotation.*;
import osmServer.Model.Credential;
import osmServer.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import osmServer.Service.UserService;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/login")
public class loginController {

    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }


    @RequestMapping("/getAllUsers")
    public List<User> greetings(){
        return userService.getAllUsers();
    }

    @RequestMapping(value = "/saveUser", method= RequestMethod.POST)
    public Long saveUser(@RequestBody User user){
        return userService.saveUser(user);
    }

    @RequestMapping(value = "/check/password", method= RequestMethod.POST)
    public int checkPwd(@RequestBody Credential credential){
        return userService.checkPassword(credential.getUsername(),credential.getPassword());
    }

    @RequestMapping("/getAllUsername")
    public List<String> getAllUserName(){
        return userService.getAllUserName();
    }

    @RequestMapping("/getAllEmail")
    public List<String> getAllEmail(){
        return userService.getAllEmail();
    }

    @RequestMapping("/getUser/{user}")

    public String getUser(@PathVariable("user") String cred){
        return "\""+userService.findUser(cred).getUsername()+"\"";
    }

    
}

