package academy.digitallab.store.customerservice.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;


import java.io.Serializable;

@Data
@Entity
@Table(name="tbl_customers")
public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "numberID can't be empty")
    @Size( min = 8 , max = 8, message = "number_id size must be 8")
    @Column(name = "number_id" , unique = true ,length = 8, nullable = false)
    private String numberID;

    @NotEmpty(message = "first_name field can't be empty")
    @Column(name="first_name", nullable=false)
    private String firstName;

    @NotEmpty(message = "lastName field can't be empty")
    @Column(name="last_name", nullable=false)
    private String lastName;

    @NotEmpty(message = "email field can't be empty")
    @Email(message = "incorrect email format")
    @Column(unique=true, nullable=false)
    private String email;

    @Column(name="photo_url")
    private String photoUrl;


    @NotNull(message = "region field can't be empty")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    private Region region;

    private String state;
}
