package uk.co.ribot.androidboilerplate.ui.home;

import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceView;
import android.view.View;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import uk.co.ribot.androidboilerplate.R;
import uk.co.ribot.androidboilerplate.StatsActivity;
import uk.co.ribot.androidboilerplate.ui.base.BaseActivity;
import uk.co.ribot.androidboilerplate.ui.heatmap.HeatMapActivity;

public class HomeActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        GraphView graph = (GraphView) findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        graph.addSeries(series);
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
