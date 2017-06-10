package sample;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Zawiera model talii oraz wartosci punktowe kart.
 */
public class DeckModel {
    private List<CardModel> pik;
    private List<CardModel> kier;
    private List<CardModel> karo;
    private List<CardModel> trefl;
    private int deckCount;

    public DeckModel() {
        pik = new ArrayList<>();
        kier = new ArrayList<>();
        karo = new ArrayList<>();
        trefl = new ArrayList<>();
        deckCount = 0;
    }

    public void newDeck() {
        //2-10
        for (int i=2; i<11;i++) {
            pik.add(new CardModel(i+" pik",i,i));
            kier.add(new CardModel(i+" kier",i,i));
            karo.add(new CardModel(i+" karo",i,i));
            trefl.add(new CardModel(i+" trefl",i,i));
            deckCount += 4;
        }
        //AS-y
        pik.add(new CardModel("AS pik",1,11));
        kier.add(new CardModel("AS kier",1,11));
        karo.add(new CardModel("AS karo",1,11));
        trefl.add(new CardModel("AS trefl",1,11));
        //KRÓLE
        pik.add(new CardModel("KROL pik",10,10));
        kier.add(new CardModel("KROL kier",10,10));
        karo.add(new CardModel("KROL karo",10,10));
        trefl.add(new CardModel("KROL trefl",10,10));
        //DAMY
        pik.add(new CardModel("DAMA pik",10,10));
        kier.add(new CardModel("DAMA kier",10,10));
        karo.add(new CardModel("DAMA karo",10,10));
        trefl.add(new CardModel("DAMA trefl",10,10));
        //WALETY
        pik.add(new CardModel("WALET pik", 10,10));
        kier.add(new CardModel("WALET kier", 10,10));
        karo.add(new CardModel("WALET karo", 10,10));
        trefl.add(new CardModel("WALET trefl", 10,10));
        deckCount += 16;
    }

    public CardModel drawCard() {
        Random random = new Random();
        List<CardModel> cardColor = new ArrayList<>();
        int color;
        int valOfCard;
        if(deckCount>0){
            do {
                color = random.nextInt(4);
                switch (color) {
                    case 0:
                        cardColor = trefl;
                        break;
                    case 1:
                        cardColor = karo;
                        break;
                    case 2:
                        cardColor = kier;
                        break;
                    case 3:
                        cardColor = pik;
                        break;
                }
                valOfCard = random.nextInt(13);
            } while (cardColor.get(valOfCard).isUsed()==true);
            cardColor.get(valOfCard).setUsed(true);
            deckCount--;
            return cardColor.get(valOfCard);
        }

    return null;
    }
    public int getDeckCount() {
        return deckCount;
    }

    public void reset() {
        pik.clear();
        kier.clear();
        karo.clear();
        trefl.clear();
        deckCount = 0;
    }
}
