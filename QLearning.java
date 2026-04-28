package bj;

import java.util.Random;

public class QLearning {

    static final int WIDTH = 28;
    static final int HEIGHT = 10;
    static final int ACTIONS = 2;
    static final int EPISODES = 10000000;
    static final double ALPHA = 0.003;
    static final double GAMMA = 0.95;
    static double EPSILON = 0.9;
    static final double EPSILON_DECAY = 0.9995;
    static final double EPSILON_MIN = 0.01;
    static final int GOAL = 21;

    static BJ bj = new BJ();

    static double[][][] Q = new double[WIDTH][HEIGHT][ACTIONS];

    static Random rand = new Random();

    static HeatmapVisualizer visualizer = new HeatmapVisualizer(WIDTH, HEIGHT);

    static double winrate = 0.0;
    static int wins = 0;

    public static void main(String[] args) {

        initQ();
        visualizer.update(Q);

        for (int ep = 0; ep < EPISODES; ep++) {

            runEpisode();

            if (EPSILON > EPSILON_MIN) {
                EPSILON *= EPSILON_DECAY;
            }

//            if (ep % 1000 == 0) {
//                visualizer.update(Q);
//                Thread.sleep(50);
//            }
            if (ep % 10000 == 0) visualizer.update(Q);
            if (ep % 100000 == 0) System.out.println("Episode " + ep + " completed.");
        }

        System.out.println("\n\nTotal wins: " + wins + ", Total losses: " + (EPISODES - wins));
        winrate = (double) wins / EPISODES;
        System.out.printf("Winrate: %.2f%%\n", winrate * 100);
        visualizer.update(Q);

    }

    public static void initQ() {
        for (int y = 0; y < Q.length; y++) {
            for (int x = 0; x < Q[y].length; x++) {
                for (int a = 0; a < Q[y][x].length; a++) {
                    Q[y][x][a] = 10 * Math.random();
                }
            }
        }
    }

    static void runEpisode() {
        bj.flushDeck();
        bj.pregame();

        int pScore = bj.count(bj.playerdeck);
        int dScore = bj.count(bj.dealerdeck);
        int pAce;
        pAce = (bj.playableAce()) ? 1 : 0;

        boolean playing = true;

        while (playing) {
            int reward = 0;
            int action = 0;
            if (pScore <= GOAL) action = chooseAction(pScore, dScore, pAce);

            if (action == 1) {
                bj.draw(bj.playerdeck);
                int nScore = bj.count(bj.playerdeck);
                int nAce = (bj.playableAce()) ? 1 : 0;
                if (nScore > GOAL){
                    reward -= 10;
                    playing = false;
                }

                updateReward(dScore, pScore, pAce, nScore, nAce, action, reward);
                pScore = nScore;
                pAce = nAce;

            } else {
                bj.dealerdraw();
                reward = bj.hasWon();
                playing = false;

                updateReward(dScore, pScore, pAce, pScore, pAce, action, reward);
            }
        }
        if (bj.hasWon() > 0) wins++;
    }

    static int chooseAction(int pScore, int dScore, int pAce) {
        if (rand.nextDouble() < EPSILON) {
            return rand.nextInt(ACTIONS);
        }

        double best = -1e9;
        int bestA = 0;
        for (int a = 0; a < ACTIONS; a++) {
            if (Q[getPlayerIndex(pScore, pAce)][dScore - 2][a] > best) {
                best = Q[getPlayerIndex(pScore, pAce)][dScore - 2][a];
                bestA = a;
            }
        }
        return bestA;
    }

    static void updateReward(int dScore, int pScore, int pAce, int nScore, int nAce, int action, int reward) {
        double oldQ = Q[getPlayerIndex(pScore, pAce)][dScore - 2][action];
        double maxNextQ = 0;

        if (action == 1 && nScore <= GOAL) {
            maxNextQ = maxQ(nScore, nAce, dScore);
        }

        Q[getPlayerIndex(pScore, pAce)][dScore - 2][action] = oldQ + ALPHA * (reward + GAMMA * maxNextQ - oldQ);
    }


    static double maxQ(int pScore, int pAce, int dScore) {
        double best = Q[getPlayerIndex(pScore, pAce)][dScore - 2][0];

        for (int a = 1; a < ACTIONS; a++) {
            best = Math.max(best, Q[getPlayerIndex(pScore, pAce)][dScore - 2][a]);
        }
        return best;
    }

    static int getPlayerIndex(int pScore, int pAce) {
        if (pAce == 1) {
            return 18 + (pScore - 12);
        } else {
            return pScore - 4;
        }
    }
}