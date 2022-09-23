package ca.cmpt276.a2.model;

public class PlayerScore {

    protected int playerNumber;
    protected int numberOfCards;
    protected int numberOfWagers;
    protected int sumOfCards;
    protected int score;

    public PlayerScore(int playerNumber, int numberOfCards, int sumOfCards, int numberOfWagers) {
        // if any negative value is passed in
        // or the player number exceeds 4 (can only have max 4 players in 1 game)
        // throw exception
        if (playerNumber < 0 ||
                playerNumber > 4 ||
                numberOfCards < 0 ||
                sumOfCards < 0 ||
                numberOfWagers < 0) {
            throw new IllegalArgumentException("Invalid input. Cannot have negative number input.");
        }
        this.playerNumber = playerNumber;
        this.numberOfCards = numberOfCards;
        this.sumOfCards = sumOfCards;
        this.numberOfWagers = numberOfWagers;

        calculateScore();
    }

    public void calculateScore() {

        // if there are 0 cards, we will just return a score of 0
        if (numberOfCards == 0) {
            return;
        }

        // add up the number of points on their point cards, then subtract 20
        int score = this.sumOfCards - 20;
        // then multiply the score by the multiplier of the amount of wager cards
        score = score * (numberOfWagers + 1);
        // and if the player has 8 cards in total (including wagers) or more
        // then we will add 20 onto the final score
        if ((numberOfCards + numberOfWagers) >= 8) {
            score += 20;
        }

        this.score = score;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public int getNumberOfCards() {
        return numberOfCards;
    }

    public int getNumberOfWagers() {
        return numberOfWagers;
    }

    public int getSumOfCards() {
        return sumOfCards;
    }

    public int getScore() {
        return score;
    }
}
