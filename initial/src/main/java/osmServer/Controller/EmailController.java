package osmServer.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.*;
import osmServer.Model.User;
import osmServer.Service.OTPService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/mail")
public class EmailController {

    @Autowired
    private OTPService otpService;

    @RequestMapping(value="/sendMail",method= RequestMethod.POST)
    public int sendMail(@RequestBody User user){
        int otpSent = -1;
        try{
           otpService.sendOTP(user);
           otpSent=1;
        }catch (MailException m){
            System.out.println(m.getMessage());
        }

        return otpSent;
    }

    @RequestMapping("/getOTP")
    public String getOTP(){
        return otpService.encryptedOTP();
    }

}
