package academy.digitallab.store.product.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table( name = "tbl_products")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {


    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;


    @NotEmpty(message = "field name can't be empty")
    private String name;

    private String description;

    private Integer stock;

    private Double price;

    private String status;

    @Column( name = "create_at")
    private Date createAt;

    @NotNull(message = "category field can't be empty")
    @ManyToOne( fetch = FetchType.LAZY)
    @JoinColumn( name = "category_id")
    private Category category;
}
