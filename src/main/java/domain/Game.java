package domain;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@RequiredArgsConstructor
@Getter
@Setter
public class Game {

    private int index;
    @NonNull
    private Team homeTeam;
    @NonNull
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
