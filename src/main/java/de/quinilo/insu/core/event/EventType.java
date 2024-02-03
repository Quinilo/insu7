package de.quinilo.insu.core.event;

import lombok.Getter;

public enum EventType {

    INVENTORY_SWAP(false, "Inventar-Chaos", "Die Items in deinem Inventar werden zufällig vertauscht"),
    POISON_RAIN(true, "Gift-Regen", "Es regnet gift (besser nicht berühren)"),
    BLINDNESS(true, "Blindness", "Das Sichtfeld aller Spieler wird stark eingeschränkt"),
    GOLDEN_APPLE(false, "Glückstreffer", "Mit etwas glück bekommst du einen Goldenen Apfel geschenkt"),
    SHOW_NEXT_ENEMY(false, "Nah Dran", "Du erfährst, wie weit der nächste Spieler entfernt ist"),
    POISON_WATER(true, "Giftiges-Wasser", "Jeder Spieler der mit Wasser in berührunf kommt kassiert damage (auf INSU 3 angelehnt)");

    @Getter
    boolean temporaryEvent;
    @Getter
    String displayName;
    @Getter
    String description;

    EventType(boolean temporaryEvent, String displayName, String description) {
        this.temporaryEvent = temporaryEvent;
        this.displayName = displayName;
        this.description = description;
    }

}
