package bookshop.model;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Data
@SQLRestriction("is_deleted=false")
@SQLDelete(sql = "UPDATE cart_items SET is_deleted = true WHERE id=?")
@Table(name = "cart_items")
public class CartItem {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    private ShoppingCart shoppingCart;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;
    @Column(name = "quantity", nullable = false)
    private int quantity;
    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;
}
