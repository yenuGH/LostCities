package ca.cmpt276.a2.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import ca.cmpt276.a2.R;

public class GameInfoCardModel {

    private static final int[] GAME_WINNER_ICONS = {R.drawable.winner_1, R.drawable.winner_2, R.drawable.winner_draw};
    private static final int WINNER_PLAYER1 = 0;
    private static final int WINNER_PLAYER2 = 1;
    private static final int WINNER_DRAW = 2;

    String gameWinner;
    int gameWinnerAmount;
    String gameScores;
    String gameTimePlayed;
    int gameWinnersIcon;
    Game game;

    public GameInfoCardModel(Game game) {

        // storing the game to each card that is created will allow us to easily modify / access a game later on
        this.game = game;

        // retrieve info from game
        ArrayList<PlayerScore> playerList = game.getPlayerList();
        ArrayList<PlayerScore> winningPlayers = game.getWinningPlayers();
        LocalDateTime datePlayed = game.getDatePlayed();


        // get scores of all players
        String scores = "";
        for (int i = 0; i < playerList.size(); i++) {
            scores += Integer.toString(playerList.get(i).getScore());
            // will only print "vs" in between scores
            if (i < playerList.size() - 1) {
                scores += " vs ";
            }
        }
        gameScores = scores;

        // get the winning players
        String winners = "";
        for (int i = 0; i < winningPlayers.size(); i++) {
            winners += Integer.toString(winningPlayers.get(i).getPlayerNumber() + 1);
            // will only print "," in between player numbers
            if (i < winningPlayers.size() - 1) {
                winners += ", ";
            }
        }
        gameWinner = winners;
        gameWinnerAmount = winningPlayers.size();

        // get the date played
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("(@yyyy-MM-dd HH:mm)");
        gameTimePlayed = datePlayed.format(dateTimeFormatter);

        // get the proper image integer
        if (winningPlayers.size() > 1){ // if there is more than 1 winner
            gameWinnersIcon = GAME_WINNER_ICONS[WINNER_DRAW];
        }
        else {
            // if there is only one winner, the string will contain only a single number
            switch (Integer.parseInt(gameWinner)){
                case 1:
                    gameWinnersIcon = GAME_WINNER_ICONS[WINNER_PLAYER1];
                    break;
                case 2:
                    gameWinnersIcon = GAME_WINNER_ICONS[WINNER_PLAYER2];
                    break;
            }
        }

    }

    public Game getGame() { return game; }

    public String getGameWinners() { return gameWinner; }

    public String getGameScores() { return gameScores; }

    public String getGameTimePlayed() { return gameTimePlayed; }

    public int getGameWinnersIcon() { return gameWinnersIcon; }
}
