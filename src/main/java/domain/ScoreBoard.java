package domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.LinkedHashMap;

@AllArgsConstructor
@Getter
public class ScoreBoard {

    private LinkedHashMap<String, Game> games;

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        games.values().forEach(game -> stringBuilder.append(game.toString()).append("\n"));
        return stringBuilder.toString();
    }
}
