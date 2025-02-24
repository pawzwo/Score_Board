import domain.GamesSummary;
import domain.ScoreBoard;
import service.EventService;
import service.GamesSummaryService;
import service.ScoreBoardService;

import java.util.LinkedHashMap;
import java.util.List;

public class Driver {

    public static void main(String[] args) {
        ScoreBoardService scoreBoardService = new ScoreBoardService();
        EventService eventService = new EventService(scoreBoardService);
        GamesSummaryService gamesSummaryService = new GamesSummaryService();
        ScoreBoard scoreBoard = new ScoreBoard(new LinkedHashMap<>());

        List<String> events = provideData();
        for (int i = 0; i < events.size(); i++) {
            eventService.readEvent(scoreBoard, events.get(i));
            if (i % 10 == 0 || i == 41) {
                System.out.println(scoreBoard);
                GamesSummary gamesSummary = gamesSummaryService.generateSummary(scoreBoard);
                System.out.println(gamesSummary);
            }
        }
        GamesSummary gamesSummary = gamesSummaryService.generateSummary(scoreBoard);
        System.out.println(scoreBoard);
        System.out.println(gamesSummary);

    }

    private static List<String> provideData () {
        return List.of(
                "Mexico,Canda,0,0,START",
                "Mexico,Canda,0,1,UPDATE",
                "Spain,Brazil,0,0,START",
                "France,Germany,0,0,START",
                "Spain,Brazil,0,1,UPDATE",
                "Spain,Brazil,0,2,UPDATE",
                "France,Germany,1,0,UPDATE",
                "Mexico,Canada,0,2,UPDATE",
                "Spain,Brazil,1,2,UPDATE",
                "Uruguay,Italy,0,0,START",
                "Uruguay,Italy,1,0,UPDATE",
                "Argentina,Australia,0,0,START",
                "Uruguay,Italy,2,0,UPDATE",
                "Uruguay,Italy,3,0,UPDATE",
                "Spain,Brazil,2,2,UPDATE",
                "Argentina,Australia,0,1,UPDATE",
                "France,Germany,1,1,UPDATE",
                "France,Germany,2,1,UPDATE",
                "Argentina,Australia,1,1,UPDATE",
                "Argentina,Australia,2,1,UPDATE",
                "Uruguay,Italy,4,0,UPDATE",
                "Spain,Brazil,3,2,UPDATE",
                "Spain,Brazil,4,2,UPDATE",
                "Spain,Brazil,5,2,UPDATE",
                "Uruguay,Italy,5,0,UPDATE",
                "Argentina,Australia,3,1,UPDATE",
                "Spain,Brazil,6,2,UPDATE",
                "Spain,Brazil,7,2,UPDATE",
                "Uruguay,Italy,6,0,UPDATE",
                "Spain,Brazil,8,2,UPDATE",
                "France,Germany,2,2,UPDATE",
                "Spain,Brazil,9,2,UPDATE",
                "Uruguay,Italy,6,1,UPDATE",
                "Uruguay,Italy,6,2,UPDATE",
                "Uruguay,Italy,6,3,UPDATE",
                "Uruguay,Italy,6,4,UPDATE",
                "Uruguay,Italy,6,5,UPDATE",
                "Uruguay,Italy,6,6,UPDATE",
                "Spain,Brazil,10,2,UPDATE",
                "Mexico,Canada,0,3,UPDATE",
                "Mexico,Canada,0,4,UPDATE",
                "Mexico,Canada,0,5,UPDATE",
                "Mexico,Canada,0,5,END",
                "Spain,Brazil,0,0,END",
                "France,Germany,2,1,END",
                "Uruguay,Italy,6,6,END",
                "Argentina,Australia,2,1,END");
    }
}
