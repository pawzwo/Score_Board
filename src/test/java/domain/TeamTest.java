package domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TeamTest {

    @Test
    void createTeam_TeamCreatedSuccessfully() {
        Team team = new Team(0, "Mexico");

        assertEquals("Team", team.getClass().getSimpleName());
        assertEquals("Mexico", team.getName());
        assertEquals(0, team.getScore());
    }
}
