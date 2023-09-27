package com.barabanov.task_1.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;


@Data
@ToString(exclude = "player")
@EqualsAndHashCode(exclude = "player")
@SuperBuilder
@NoArgsConstructor
@MappedSuperclass
public abstract class PlayersThing <T extends Serializable> implements BaseEntity<T>
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private T id;

    @Column(nullable = false)
    private Integer resourceId;

    @ManyToOne
    protected Player player;

}
