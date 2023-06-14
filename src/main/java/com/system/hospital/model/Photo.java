package com.system.hospital.model;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name= "photo")
@AllArgsConstructor @NoArgsConstructor @Builder @Getter @Setter
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String photoName;
    private String photoUrl;
}
