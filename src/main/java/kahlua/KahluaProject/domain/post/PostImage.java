package kahlua.KahluaProject.domain.post;

import jakarta.persistence.*;
import kahlua.KahluaProject.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "postImage")
public class PostImage extends BaseEntity {

    @Id
    @Column(name="image_id")
    private Long id;

    @Column(nullable = false)
    private String url;

    @ManyToOne
    @JoinColumn(name = "POST_ID")
    private Post post;
}
