package de.quinilo.insu.core.Utils;

import lombok.Getter;

public class CoordLeak {

    @Getter
    String playerName;
    @Getter
    String coordinate;
    @Getter
    String date;

    public CoordLeak(String playerName, String coordinate, String date) {
        this.playerName = playerName;
        this.coordinate = coordinate;
        this.date = date;
    }
}
