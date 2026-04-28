package bj;

import java.util.Collections;
import java.util.Stack;

public class BJ {
    //                  A,2,3,4,5,6,7,8,9,10
    int[] playerdeck = {0,0,0,0,0,0,0,0,0,0};
    int[] dealerdeck = {0,0,0,0,0,0,0,0,0,0};

    Stack<Integer> deck = new Stack<>();

    public void pregame() {
        deck.clear();
        for (int i = 0; i < 52; i++) {
            deck.add(i);
        }
        Collections.shuffle(deck);

        draw(playerdeck);
        draw(playerdeck);
        draw(dealerdeck);
    }

    public void draw( int[] fordrawdeck) {
        if (!deck.isEmpty()) {
            int card = deck.pop();
            int cardValue = card/4;
            if (cardValue > 9) {
                cardValue = 9;
            }
            fordrawdeck[cardValue]++;
        }
    }

    public void flushDeck() {
        playerdeck = new int[]{0,0,0,0,0,0,0,0,0,0};
        dealerdeck = new int[]{0,0,0,0,0,0,0,0,0,0};
    }

    public int count(int[]countdeck) {
        int j = 0;
        for(int i = 1; i < countdeck.length+1; i++) {
            j+= i * countdeck[i-1];
        }
        if (j < 21 && countdeck[0] > 0) {
            for(int i = 0; i < countdeck[0];i++) {
                if(j+10 <= 21) {
                    j+=10;
                }
            }
        }
        return j;
    }

    public void dealerdraw() {
        while(count(dealerdeck) < 17){
            draw(dealerdeck);
        }
    }

    public int hasWon() {
        int pScore = count(playerdeck);
        int dScore = count(dealerdeck);

        if (pScore > 21) {
            return -10;
        }
        if (dScore > 21) {
            return 10;
        }
        if (pScore > dScore) {
            return 10;
        } else if (pScore == dScore) {
            return 0;
        } else {
            return -10;
        }
    }

    public boolean playableAce() {
        if (playerdeck[0] == 0) return false;

        int score = 0;
        for (int i = 1; i < playerdeck.length;i++) {
            score += playerdeck[i] * (i + 1);
        }
        score += 11;
        return score <= 21;
    }
}