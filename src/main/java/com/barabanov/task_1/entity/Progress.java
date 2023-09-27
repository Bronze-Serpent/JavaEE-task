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
public class Progress extends PlayersThing<Long>
{
    @Column(nullable = false)
    private Integer score;

    @Column(nullable = false)
    private Integer maxScore;


    @Override
    public void setPlayer(Player player) {
        this.player = player;
        player.getProgresses().add(this);
    }
}
