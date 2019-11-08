package osmServer.Model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="users")
public class VideoItem {
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

    public VideoItem(){

    }

}





