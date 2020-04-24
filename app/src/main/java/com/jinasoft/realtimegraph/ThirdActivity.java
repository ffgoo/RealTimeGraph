package com.jinasoft.realtimegraph;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.List;

public class ThirdActivity extends AppCompatActivity {

    private LineChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    chart =findViewById(R.id.chart);

    chart.getDescription().setEnabled(true);
        Description des = chart.getDescription();
        des.setEnabled(true);
        des.setText("REALTIMEGRAPH");
        des.setTextSize(15f);

        chart.setTouchEnabled(false);
        chart.setDragEnabled(false);
        chart.setScaleEnabled(false);
        chart.setAutoScaleMinMaxEnabled(true);
        chart.setPinchZoom(false);

        chart.getXAxis().setDrawGridLines(true);
        chart.getXAxis().setDrawAxisLine(false);

        chart.getXAxis().setEnabled(true);
        chart.getXAxis().setDrawGridLines(true);

        Legend I = chart.getLegend();
        I.setEnabled(true);
        I.setFormSize(10f);
        I.setTextSize(12f);
        I.setTextColor(Color.RED);

        YAxis leftAxis = chart.getAxisLeft();

        leftAxis.setEnabled(true);
        leftAxis.setTextColor(Color.RED);
        leftAxis.setDrawGridLines(true);
        leftAxis.setGridColor(Color.BLACK);

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(false);

        chart.invalidate();



        addEntry(27.25);
        addEntry(27.0);
        addEntry(28);
        addEntry(29);
        addEntry(30);
    }
    private void addEntry(double num){
        LineData data = chart.getData();

        if(data==null){
            data=new LineData();
        }
        ILineDataSet set = data.getDataSetByIndex(0);

        if(set == null){
            set = createSet();
            data.addDataSet(set);
        }

        data.addEntry(new Entry((float)set.getEntryCount(),(float)num),0);
        data.notifyDataChanged();

        chart.notifyDataSetChanged();

        chart.setVisibleXRangeMaximum(150);

        chart.moveViewTo(data.getEntryCount(),50f,YAxis.AxisDependency.LEFT);

    }
    private LineDataSet createSet(){
      LineDataSet set= new LineDataSet(null,"REALTIME");
      set.setLineWidth(1f);
      set.setDrawValues(false);
      set.setColor(Color.WHITE);
      set.setMode(LineDataSet.Mode.LINEAR);
      set.setDrawCircles(false);
      set.setHighLightColor(Color.rgb(190,190,190));
      return set;
    }
}
