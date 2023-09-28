package com.barabanov.task.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;


@Data
@SuperBuilder
@NoArgsConstructor
@MappedSuperclass
public abstract class ResourceThing<T extends Serializable> implements BaseEntity<T>
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private T id;

    @Column(nullable = false)
    private Integer resourceId;

}
