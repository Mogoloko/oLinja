package com.mogollon.dani.olinja.olinja;

/**
 * Created by Daniel Mogollón on 30/01/2016.
 */
public class Partida {

    int id;
    int turn;
    Player optPlayer;
    int redId;
    int blackId;
    int[][] position = new int[8][2];

    public Partida(int id, Player optPlayer, int redId, int blackId, int[][] position) {
        this.id = id;
        this.turn = 0;
        this.optPlayer = optPlayer;
        this.redId = redId;
        this.blackId = blackId;
        this.position = position;
    }

}
