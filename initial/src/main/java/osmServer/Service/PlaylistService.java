package osmServer.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import osmServer.Model.Playlist;
import osmServer.Model.VideoItem;
import osmServer.Repository.PlaylistRepository;
import osmServer.Repository.VideoItemRepository;

import java.util.List;
import java.util.function.Consumer;

@Service
public class PlaylistService {

    @Autowired
    private PlaylistRepository playlistRepository;
    @Autowired
    private MusicService musicService;

    public List<Playlist> getAllPlaylists(String username){
        return playlistRepository.findByUsername(username);
    }

    public Long addItem(Playlist playlist){
        Playlist pl=playlistRepository.save(playlist);
        return pl.getId();
    }

    public void deleteItem(Playlist playlist){
        try{
            musicService.deleteItemByPlaylist(playlist.getPlaylist(),playlist.getUsername());
            playlistRepository.delete(playlist);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void editPlaylistItem(Playlist playlist){

        playlistRepository.save(playlist);

    }
}
