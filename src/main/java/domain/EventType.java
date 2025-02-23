package domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EventType {

    START_OR_UPDATE("Start or update of the game"),
    FINISH("End of the game");

    private final String event;
}