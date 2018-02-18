package uk.co.ribot.androidboilerplate.ui.home;

import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Path;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceView;
import android.view.View;

import com.google.maps.android.heatmaps.WeightedLatLng;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.HashSet;
import java.util.Set;

import uk.co.ribot.androidboilerplate.R;
import uk.co.ribot.androidboilerplate.StatsActivity;
import uk.co.ribot.androidboilerplate.audio.AudioManager;
import uk.co.ribot.androidboilerplate.ui.base.BaseActivity;
import uk.co.ribot.androidboilerplate.ui.heatmap.HeatMapActivity;

public class HomeActivity extends BaseActivity {

    private Set<WeightedLatLng> dataSet;
    private final int MAXTIME = 10000;
    private long scheduledTime = System.currentTimeMillis();
    private AudioManager audioManager;
    private LocationManager locationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        GraphView graph = (GraphView) findViewById(R.id.graph);
        graph.getLegendRenderer().setVisible(false);
        dataSet = new HashSet<WeightedLatLng>();
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        graph.addSeries(series);
    }

    protected void run(){
        if(System.currentTimeMillis()- scheduledTime >= MAXTIME){
            dataSet.add(new WeightedLatLng(co, getWeight((int)this.audioManager.averageDBOverTime())));
        };
    }

    public void onButtonMap(View view){
        Intent intent = new Intent(getApplicationContext(), HeatMapActivity.class);
        startActivity(intent);
    }

    public void onButtonStats(View view){
        Intent intent = new Intent(getApplicationContext(), StatsActivity.class);
        startActivity(intent);
    }

}
