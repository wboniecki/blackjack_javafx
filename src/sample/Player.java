package sample;

public class Player extends PlayerModel {


    @Override
    protected void computeScore(CardModel card) {
        altScore[0] += card.getFirstCardValue();
        altScore[1] += card.getSecondCardValue();

        if(altScore[0]<=21 && altScore[1]<=21) {
            if (Math.abs(21 - altScore[0]) <= Math.abs(21 - altScore[1])) {
                setScore(altScore[0]);
            } else {
                setScore(altScore[1]);
            }
        } else {
            if (altScore[0] <= 21) {
                setScore(altScore[0]);
            } else {
                setScore(altScore[1]);
            }
        }

        if (altScore[0]>21 && altScore[1]>21) {
            setScore(Math.min(altScore[0],altScore[1]));
        }

    }

    @Override
    public void addCardToHand(CardModel card) {
        hand.add(card);
        updateCardNames(card.getName());
        computeScore(card);
    }
}
