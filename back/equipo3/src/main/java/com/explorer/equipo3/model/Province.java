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
@Table(name = "province")
public class Province {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "province", nullable = false)
    private String province;
    @CreationTimestamp
    @JsonIgnore
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date created_at;
    @UpdateTimestamp
    @JsonIgnore
    @Column(name = "updated_at", nullable = false, updatable = false)
    private Date updated_at;
    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;
    @OneToMany(mappedBy = "province",fetch = FetchType.LAZY)
    private Set<City> cities;


    public Province(String province) {
        this.province = province;

    }
}
