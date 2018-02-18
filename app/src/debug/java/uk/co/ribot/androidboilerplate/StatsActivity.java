package uk.co.ribot.androidboilerplate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import uk.co.ribot.androidboilerplate.ui.heatmap.HeatMapActivity;
import uk.co.ribot.androidboilerplate.ui.home.HomeActivity;

public class StatsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
    }

    public void onButtonMap(View view) {
        Intent intent = new Intent(getApplicationContext(), HeatMapActivity.class);
        startActivity(intent);
    }

    public void onButtonHome(View view) {
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
    }
}
