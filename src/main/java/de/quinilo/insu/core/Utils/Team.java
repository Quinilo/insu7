package de.quinilo.insu.core.Utils;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class Team {

    public List<InsuPlayer> players;
    @Getter
    String name;
    @Getter
    boolean dead;
    @Getter @Setter
    int x, y, z = 0;

    public Team(List<InsuPlayer> players, String name, boolean dead) {
        this.players = players;
        this.name = name;
        this.dead = dead;
    }
}
