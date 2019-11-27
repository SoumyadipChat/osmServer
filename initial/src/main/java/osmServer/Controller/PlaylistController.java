package osmServer.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import osmServer.Model.Credential;
import osmServer.Model.Playlist;
import osmServer.Model.User;
import osmServer.Model.VideoItem;
import osmServer.Service.MusicService;
import osmServer.Service.PlaylistService;
import osmServer.Service.UserService;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/playlist")
public class PlaylistController {

    @Autowired
    private PlaylistService playlistService;

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }


    @RequestMapping("/getAll/{user}")
    public List<Playlist> getAllPlaylist(@PathVariable("user") String user){
        return playlistService.getAllPlaylists(user);
    }

    @RequestMapping(value = "/saveItem", method= RequestMethod.POST)
    public Long saveVideo(@RequestBody Playlist playlist){
        return playlistService.addItem(playlist);
    }

    @RequestMapping(value = "/deleteItem", method= RequestMethod.POST)
    public int delPL(@RequestBody Playlist playlist){
        playlistService.deleteItem(playlist);
        return 1;
    }

    @RequestMapping(value = "/editItem", method= RequestMethod.POST)
    public int editPL(@RequestBody Playlist playlist){
        playlistService.editPlaylistItem(playlist);
        return 1;
    }



}
