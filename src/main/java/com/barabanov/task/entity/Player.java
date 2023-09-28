package com.barabanov.task.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Data
@ToString(exclude = {"currencies", "items", "progresses"})
@EqualsAndHashCode(exclude = {"currencies", "items", "progresses"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Player implements BaseEntity<Long>
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String nickname;

    @Builder.Default
    @OneToMany(mappedBy = "player")
    private List<Currency> currencies = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "player")
    private List<Item> items = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "player")
    private List<Progress> progresses = new ArrayList<>();
}
