package domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.TreeSet;

@AllArgsConstructor
@Getter
public class GamesSummary {

    private TreeSet<Game> games;

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        int index = 0;
        for (Game game : games) {
            index += 1;
            stringBuilder
                    .append(index)
                    .append(". ")
                    .append(game.getHomeTeam().getName())
                    .append(" ")
                    .append(game.getHomeTeam().getScore())
                    .append(" - ")
                    .append(game.getAwayTeam().getName())
                    .append(" ")
                    .append(game.getAwayTeam().getScore())
                    .append("\n");
        }
        return stringBuilder.toString();
    }
}
