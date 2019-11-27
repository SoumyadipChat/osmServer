package osmServer.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import osmServer.Model.Credential;
import osmServer.Model.User;
import osmServer.Model.VideoItem;
import osmServer.Service.MusicService;
import osmServer.Service.UserService;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/music")
public class MusicController {

    @Autowired
    private MusicService musicService;

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }


    @RequestMapping("/getAll/{user}")
    public List<VideoItem> greetings(@PathVariable("user") String user){
        return musicService.getAllItems(user);
    }

    @RequestMapping("/update/{id}/{title}")
    public Long updateTitle(@PathVariable("id") Long id,@PathVariable("title") String title){
        return musicService.editname(id,title);
    }

    @RequestMapping(value = "/saveItem", method= RequestMethod.POST)
    public Long saveVideo(@RequestBody VideoItem video){
        return musicService.addItem(video);
    }

    @RequestMapping(value = "/deleteItem", method= RequestMethod.POST)
    public int delVideo(@RequestBody VideoItem video){
        musicService.deleteItem(video);
        return 1;
    }

}
