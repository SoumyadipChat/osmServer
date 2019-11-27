package osmServer.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import osmServer.Model.Playlist;


import java.util.List;

public interface PlaylistRepository extends JpaRepository<Playlist,Long> {
    List<Playlist> findByUsername(String username);
}