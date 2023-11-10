package com.explorer.equipo3.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false, length = 50)
    private String name;
    @Column(name = "price", nullable = false)
    private Double price;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
    private Set<Image> images;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "product_detail", joinColumns = { @JoinColumn(name = "product_id")}, inverseJoinColumns = {@JoinColumn(name = "detail_id")})
    @JsonIgnoreProperties("products") // o @JsonIgnore si no necesitas más información del Detail en el JSON
    private Set<Detail> details = new HashSet<>();

    @CreationTimestamp
    @JsonIgnore
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date created_at;
    @UpdateTimestamp
    @JsonIgnore
    @Column(name = "updated_at", nullable = false)
    private Date updated_at;


    public Product(String name, Double price, Category category, City city) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.city = city;
    }
}
