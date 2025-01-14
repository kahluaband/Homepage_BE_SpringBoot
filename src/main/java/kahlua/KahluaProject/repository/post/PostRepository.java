package kahlua.KahluaProject.repository.post;

import kahlua.KahluaProject.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {

    Optional<Post> findById(Long id);
}
