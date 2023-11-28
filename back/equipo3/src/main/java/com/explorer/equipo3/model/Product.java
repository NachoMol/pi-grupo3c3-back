package com.explorer.equipo3.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.*;

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
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;
    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Image> images;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Reservation> reservations;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private List<Policy> policies;


    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "product_detail", joinColumns = { @JoinColumn(name = "product_id")}, inverseJoinColumns = {@JoinColumn(name = "detail_id")})
    @JsonIgnoreProperties("products") // o @JsonIgnore si no necesitas más información del Detail en el JSON
    @BatchSize(size = 10)
    private Set<Detail> details;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private Set<Favorites> favorites = new HashSet<>();

    @CreationTimestamp
    @JsonIgnore
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date created_at;
    @UpdateTimestamp
    @JsonIgnore
    @Column(name = "updated_at", nullable = false)
    private Date updated_at;
    @Column(name = "state")
    private Boolean state ;


    public Product(String name, Double price, Category category, City city) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.city = city;
        this.state = true;
    }
}
