package osmServer.Model;

import org.springframework.util.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="videoitems")
public class VideoItem implements Comparable<VideoItem>{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotBlank
    private String videoId;

    @Column
    @NotBlank
    private String thumbnail;

    @Column
    @NotBlank
    private String title;

    @Column
    @NotBlank
    private String username;

    @Column
    private String lexoRank;

    @Column
    private String playlist;



    public String getPlaylist() {
        return playlist;
    }

    public String getLexoRank() {
        return lexoRank;
    }

    public void setLexoRank(String lexoRank) {
        this.lexoRank = lexoRank;
    }
    public Long getId() {
        return id;
    }

    public String getVideoId() {
        return videoId;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public String getUsername() {
        return username;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public VideoItem(){

    }

    @Override
    public int compareTo(VideoItem videoItem) {
        if(StringUtils.isEmpty(this.getLexoRank()) || StringUtils.isEmpty(videoItem.getLexoRank()) || this.getLexoRank().compareTo(videoItem.getLexoRank())==0){
            return this.getId().compareTo(videoItem.getId());
        }
        else {
            return this.getLexoRank().compareTo(videoItem.getLexoRank());
        }
    }
}





