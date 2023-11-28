package p9Reflection.DirectedTaskGraphs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import p9Reflection.DirectedTaskGraphs.annotations.Annotations.DependsOn;
import p9Reflection.DirectedTaskGraphs.annotations.Annotations.FinalResult;
import p9Reflection.DirectedTaskGraphs.annotations.Annotations.Operation;
import p9Reflection.DirectedTaskGraphs.databases.Database;

public class BestGamesFinder {
  private Database database = new Database();

  @Operation("All-Games")
  public Set<String> getAllGames() {
    return database.readAllGames();
  }

  @Operation("Game-To-Price")
  public Map<String, Float> getGameToPrice(@DependsOn("All-Games") Set<String> allGames) {
    return database.readGameToPrice(allGames);
  }

  @Operation("Game-To-Rating")
  public Map<String, Float> getGameToRating(@DependsOn("All-Games") Set<String> allGames) {
    return database.readGameToRatings(allGames);
  }

  @Operation("Score-To-Game")
  public SortedMap<Double, String> scoreGames(
      @DependsOn("Game-To-Price") Map<String, Float> gameToPrice,
      @DependsOn("Game-To-Rating") Map<String, Float> gameToRating) {
    SortedMap<Double, String> scoreToGame = new TreeMap<>();
    for (String gameName : gameToPrice.keySet()) {
      double score = (double) gameToRating.get(gameName) / gameToPrice.get(gameName);
      scoreToGame.put(score, gameName);
    }
    return scoreToGame;
  }

  @FinalResult
  public List<String> getTopGames(
      @DependsOn("Score-To-Game") SortedMap<Double, String> scoreToGame) {
    return new ArrayList<>(scoreToGame.values());
  }
}
