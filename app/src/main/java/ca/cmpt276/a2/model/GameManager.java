package ca.cmpt276.a2.model;

import java.util.ArrayList;

public class GameManager {

    ArrayList<Game> gameList = new ArrayList<>();

    public void createGame(ArrayList<PlayerScore> playerList) {
        Game newGame = new Game(playerList);
        gameList.add(newGame);
    }


    // Singleton support
    private static GameManager instance;
    private GameManager() {
        // private to prevent anything else from instantiating
    }
    public static GameManager getInstance(){
        if (instance == null){
            instance = new GameManager();
        }
        return instance;
    }

    public String getGameInfo() {

        if (gameList.size() == 0) {
            return "No games";
        }

        String gameInfo = "";
        for (int i = 0; i < gameList.size(); i++) {
            gameInfo += Integer.toString(i + 1) + ". " + gameList.get(i).gameInfo() + "\n";
        }
        return gameInfo;
    }

    public Game getSpecificGame(int index) {
        return gameList.get(index);
    }

    public void deleteSpecificGame(int index) {
        gameList.remove(index);
    }

    public int getNumberOfGames() {
        return gameList.size();
    }

    public ArrayList<Game> getGameList() { return gameList; }
}
