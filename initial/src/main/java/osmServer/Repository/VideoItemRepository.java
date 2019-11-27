package osmServer.Repository;

        import org.springframework.data.jpa.repository.JpaRepository;
        import osmServer.Model.VideoItem;

        import java.util.List;

public interface VideoItemRepository extends JpaRepository<VideoItem,Long> {
    List<VideoItem> findByUsername(String username);
}