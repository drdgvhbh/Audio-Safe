package uk.co.ribot.androidboilerplate.ui.main;

import android.util.Pair;

/**
 * Created by spenc on 2018-02-18.
 */

public class NoiseEvent {
    private Pair coord;
    private double weight;

    public NoiseEvent(Pair coord_n, double weight_n){
        coord = coord_n;
        weight = weight_n;
    }
}
