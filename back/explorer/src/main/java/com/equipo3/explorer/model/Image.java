package com.equipo3.explorer.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
@Table(name = "image")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;
    private String title;
    @ManyToOne
    @JoinColumn(name = "model_id", nullable = false)
    private Model model;

    public Image() {
    }

    public Image(Long id, String url, String title, Model model) {
        this.id = id;
        this.url = url;
        this.title = title;
        this.model = model;
    }
}
