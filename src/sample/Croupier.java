package sample;

public class Croupier extends PlayerModel {

    @Override
    protected void computeScore(CardModel card) {
        altScore[0] += card.getFirstCardValue();
        altScore[1] += card.getSecondCardValue();

        if (score>0) {
            altHiddenScore[0] += card.getFirstCardValue();
            altHiddenScore[1] += card.getSecondCardValue();

            if (Math.abs(21-altHiddenScore[0]) <= Math.abs(21-altHiddenScore[1])) {
                hiddenScore = altHiddenScore[0];
            } else {
                hiddenScore = altHiddenScore[1];
            }
        }

        if (Math.abs(21-altScore[0]) <= Math.abs(21-altScore[1])) {
            setScore(altScore[0]);
        } else {
            setScore(altScore[1]);
        }
    }

    @Override
    public void addCardToHand(CardModel card) {
        if (score == 0) {
            hand.add(card);
            updateCardNames("ZAKRYTA KARTA!");
        } else {
            hand.add(card);
            updateCardNames(card.getName());
        }
        computeScore(card);
    }

    public int getHiddenScore() {
        return hiddenScore;
    }

    public void showCard() {
        ownCardNames="";
        updateCardNames(hand.get(0).getName());
        updateCardNames(hand.get(1).getName());
    }

}
