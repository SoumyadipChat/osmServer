package osmServer.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import osmServer.Model.User;

import java.util.Properties;
import java.util.Random;

@Service
public class OTPService {

    private JavaMailSender javaMailSender;

    @Autowired
    public OTPService(JavaMailSender javaMailSender){
        this.javaMailSender=javaMailSender;
    }

    private int otp;

    public int getOtp() {
        return otp;
    }

    public void setOtp(int otp) {
        this.otp = otp;
    }

    public int sendOTP(User user){

        int max = 9999;
        int min = 1000;
        int range = max - min + 1;
        int otp=(int)(Math.random() * range) + min;
        setOtp(otp);

        SimpleMailMessage mail=new SimpleMailMessage();
        mail.setTo(user.getEmail());
        mail.setFrom("onestopmusic.osm@yahoo.com");
        mail.setSubject("OTP for Sign up");
        mail.setText("Confirm details for signup:\nUsername: "+user.getUsername()+"\nPassword: "+user.getPassword()+"\n Your OTP is "+otp);

        javaMailSender.send(mail);
        return otp;
    }

    public String encryptedOTP(){
        int base=getOtp();
        int max = 9;
        int min = 1;
        int range = max - min + 1;
        int secretCode=(int)(Math.random() * range) + min;
        base*=secretCode;
        base+=secretCode;
        return base+"."+secretCode;
    }

}
