package osmServer.Model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="playlists")
public class Playlist {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotBlank
    private String username;

    @Column
    @NotBlank
    private String playlist;

    @Column
    @NotBlank
    private String isDefault;

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPlaylist() {
        return playlist;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public Playlist(){

    }




}
