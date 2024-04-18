package bookshop.model;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Data
@SQLRestriction("is_deleted=false")
@SQLDelete(sql = "UPDATE books SET is_deleted = true WHERE id=?")
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "author", nullable = false)
    private String author;
    @Column(name = "isbn", unique = true, nullable = false)
    private String isbn;
    @Column(name = "price", nullable = false)
    private BigDecimal price;
    @Column(name = "description")
    private String description;
    @Column(name = "cover_image")
    private String coverImage;
    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;
}
