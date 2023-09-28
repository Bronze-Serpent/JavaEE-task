package com.barabanov.task.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;


@Data
@EqualsAndHashCode(callSuper = true, exclude = "player")
@ToString(callSuper = true, exclude = "player")
@NoArgsConstructor
@SuperBuilder
@Entity
public class Currency extends ResourceThing<Long>
{
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer count;

    @ManyToOne(fetch = FetchType.LAZY)
    protected Player player;


    public void setPlayer(Player player)
    {
        this.player = player;
        player.getCurrencies().add(this);
    }

}



