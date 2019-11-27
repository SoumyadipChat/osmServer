package osmServer.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import osmServer.Model.VideoItem;
import osmServer.Repository.UserRepository;
import osmServer.Repository.VideoItemRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
public class MusicService {

    @Autowired
    private VideoItemRepository videoItemRepository;

    public List<VideoItem> getAllItems(String username){
        return videoItemRepository.findByUsername(username).stream()
                .sorted()
                .collect(Collectors.toList());
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

    public Long editname(Long id,String title){
        Optional<VideoItem> videoItem=videoItemRepository.findById(id);
        if(videoItem.isPresent()){
            VideoItem item=videoItem.get();
            item.setTitle(title);
            item=videoItemRepository.save(item);
            return item.getId();
        }
        else{
            return (long)-1;
        }

    }

    public void deleteItemByPlaylist(String playlist,String user){

        List<VideoItem> videos=videoItemRepository.findByUsername(user);
        System.out.println("videos found"+videos.size());
        Consumer<VideoItem> removeAllForPlaylist = videoItem -> {
            if(!StringUtils.isEmpty(videoItem.getPlaylist()) && videoItem.getPlaylist().equals(playlist)){
                videoItemRepository.deleteById(videoItem.getId());
            }
        };
        videos.stream()
                .forEach(removeAllForPlaylist);

    }

}