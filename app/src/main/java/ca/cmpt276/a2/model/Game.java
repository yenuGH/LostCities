package ca.cmpt276.a2.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Game {

    ArrayList<PlayerScore> playerList;
    ArrayList<PlayerScore> winningPlayers = new ArrayList<>();
    LocalDateTime datePlayed;

    public Game(ArrayList<PlayerScore> playerList) {
        this.playerList = playerList;
        this.datePlayed = LocalDateTime.now();
        calculateScores();
    }

    public String gameInfo() {
        String scores = "";
        String winners = "";
        String info;
        for (int i = 0; i < playerList.size(); i++) {
            scores += Integer.toString(playerList.get(i).getScore());
            // will only print "vs" in between scores
            if (i < playerList.size() - 1) {
                scores += " vs ";
            }
        }

        // will only print winners delimited by commas if there is more than one
        for (int i = 0; i < winningPlayers.size(); i++) {
            winners += Integer.toString(winningPlayers.get(i).getPlayerNumber());
            // will only print "," in between player numbers
            if (i < winningPlayers.size() - 1) {
                winners += ", ";
            }
        }

        // formatting the date to make it look pretty
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("(@yyyy-mm-dd HH:mm)");
        String formattedDatePlayed = datePlayed.format(dateTimeFormatter);

        // create the final string
        info = scores + ", " + "winner player(s): " + winners + " " + formattedDatePlayed;

        return info;

    }

    public void calculateScores() {

        ArrayList<PlayerScore> winningPlayers = new ArrayList<>();
        PlayerScore tempPlayer = null; // set it to the first one

        for (int i = 0; i < playerList.size(); i++) {

            // This is to mainly check for ties and first index (if it is null)
            // if the current index's score is the same as the last one:
            // set tempPlayer to the current one and add it into winningPlayers as they have
            // the same score
            if (tempPlayer == null || playerList.get(i).getScore() == tempPlayer.getScore()) {
                tempPlayer = playerList.get(i);
                winningPlayers.add(tempPlayer);
            }

            // if the current index's score is higher than the previous one:
            // we clear winningPlayers first as there could be more than one of the same
            // score in there
            // update tempPlayer and add it into winningPlayers
            if (playerList.get(i).getScore() > tempPlayer.getScore()) {
                winningPlayers.clear();
                tempPlayer = playerList.get(i);
                winningPlayers.add(tempPlayer);
            }

        }

        this.winningPlayers = winningPlayers;
    }

    public ArrayList<PlayerScore> getPlayerList() { return playerList; }

    public ArrayList<PlayerScore> getWinningPlayers() { return winningPlayers; }

    public LocalDateTime getDatePlayed() { return datePlayed; }
}
