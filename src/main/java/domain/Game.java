package domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@AllArgsConstructor
@Getter
public class Game {

    private Team homeTeam;
    private Team awayTeam;


    @Override
    public String toString() {
        return this.homeTeam.getName() + " - " + this.awayTeam.getName() + ": " + this.homeTeam.getScore() + " - " + this.awayTeam.getScore();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return Objects.equals(homeTeam, game.homeTeam) && Objects.equals(awayTeam, game.awayTeam);
    }

    @Override
    public int hashCode() {
        return Objects.hash(homeTeam, awayTeam);
    }
}
