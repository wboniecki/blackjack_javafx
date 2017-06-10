package sample;

/**
 * Model karty do gry.
 */
public class CardModel {
    private String name;
    private int[] cardValue;
    private  boolean isUsed;

    public CardModel(String name, int firstValue, int secondValue) {
        this.name=name;
        cardValue = new int[2];
        cardValue[0]=firstValue;
        cardValue[1]=secondValue;
        isUsed = false;
    }

    public String getName() {
        return name;
    }

    public int getFirstCardValue() {
        return cardValue[0];
    }

    public int getSecondCardValue() {
        return cardValue[1];
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }
}
