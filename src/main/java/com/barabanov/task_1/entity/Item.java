package com.barabanov.task_1.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;


@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Entity
public class Item extends PlayersThing<Long>
{
    @Column(nullable = false)
    private Integer count;

    @Column(nullable = false)
    private Integer level;


    @Override
    public void setPlayer(Player player) {
        this.player = player;
        player.getItems().add(this);
    }
}
