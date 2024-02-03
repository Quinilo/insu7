package de.quinilo.insu.core.Utils;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

public class InsuPlayer {

    @Getter
    String name;
    @Getter @Setter
    String lastLogin;
    @Getter @Setter
    UUID uuid;
    @Getter @Setter
    boolean dead;
    @Getter
    String team;
    @Getter
    Rank rank;
    @Getter @Setter
    int strikeLevel = 0;

    public InsuPlayer(String name, UUID uuid, boolean dead, String team, Rank rank) {
        this.name = name;
        this.uuid = uuid;
        this.dead = dead;
        this.team = team;
        this.rank = rank;
        this.lastLogin = "kekw";
    }
}

