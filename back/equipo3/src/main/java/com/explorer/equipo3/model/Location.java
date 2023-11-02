package com.explorer.equipo3.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "locations")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "city", nullable = false)
    private String city;
    @Column(name = "province", nullable = false)
    private String province;
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

    public Location(String city, String province, String country) {
        this.city = city;
        this.province = province;
        this.country = country;
    }
}
