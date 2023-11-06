package com.explorer.equipo3.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "country")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "country", nullable = false)
    private String country;
    @CreationTimestamp
    @JsonIgnore
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date created_at;
    @UpdateTimestamp
    @JsonIgnore
    @Column(name = "updated_at", nullable = false, updatable = false)
    private Date updated_at;
    @OneToMany(mappedBy = "country", fetch = FetchType.EAGER)
    private Set<Province> provinces;

    public Country(  String country) {
        this.country = country;
    }
}
