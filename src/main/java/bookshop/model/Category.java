package bookshop.model;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Data
@NoArgsConstructor
@SQLRestriction("is_deleted=false")
@SQLDelete(sql = "UPDATE categories SET is_deleted = true WHERE id=?")
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    public Category(Long id) {
        this.id = id;
    }
}
