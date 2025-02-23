package domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@AllArgsConstructor
@Getter
public class Team {

    private int score;
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return score == team.score && Objects.equals(name, team.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(score, name);
    }
}
