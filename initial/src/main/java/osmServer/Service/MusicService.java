package osmServer.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import osmServer.Model.VideoItem;
import osmServer.Repository.UserRepository;
import osmServer.Repository.VideoItemRepository;

import java.util.List;

@Service
public class MusicService {

    @Autowired
    private VideoItemRepository videoItemRepository;

    public List<VideoItem> getAllItems(String username){
        return videoItemRepository.findByUsername(username);
    }

    public Long addItem(VideoItem video){
        VideoItem vdo=videoItemRepository.save(video);
        return vdo.getId();
    }

    public void deleteItem(VideoItem video){
        try{
            videoItemRepository.delete(video);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

}