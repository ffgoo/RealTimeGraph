package com.jinasoft.realtimegraph;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;


import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private Handler mHandler;
    LineChart chart;
    int X_RANGE =50;
    int DATA_RANGE =30;

    ArrayList<Entry> xVal;
    LineDataSet setXcomp;
    ArrayList<String> xVals;
    ArrayList<ILineDataSet> lineDataSets;
    LineData lineData;

    TextView tvAvg;
    ArrayList<Float> AvgArray = new ArrayList<>();

    Float sum=(float)0;
    Float Avg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            init();
            threadStart();



    }

    private void init(){
        tvAvg = findViewById(R.id.Avg);
        chart = findViewById(R.id.chart);
        chartInit();
    }

    private void chartInit() {

        chart.setAutoScaleMinMaxEnabled(true);
        xVal = new ArrayList<>();
        setXcomp = new LineDataSet(xVal, "new");
        setXcomp.setColor(Color.RED);
        setXcomp.setDrawValues(false);
        setXcomp.setDrawCircles(false);
        setXcomp.setAxisDependency(YAxis.AxisDependency.LEFT);
        lineDataSets = new ArrayList<>();
        lineDataSets.add(setXcomp);

        xVals= new ArrayList<>();
        for(int i =0;i<X_RANGE; i++) {
        xVals.add("");
        }
        lineData = new LineData(lineDataSets);
        chart.setData(lineData);
        chart.invalidate();
    }
    public void chartUpdate(float x){
        if(xVal.size()>DATA_RANGE){
            xVal.remove(0);
            AvgArray.remove(0);
            for(int i=0; i<DATA_RANGE;i++){
                xVal.get(i).setX(i);
            }
        }
        AvgArray.add(x);
        xVal.add(new Entry(xVal.size(),x));
        setXcomp.notifyDataSetChanged();
        chart.notifyDataSetChanged();
        chart.invalidate();
    }
    Handler handler = new Handler(){
       @Override
       public void handleMessage(Message msg){
           if(msg.what==0){
               int a=0;
                a=(int)(Math.random()*100);
               chartUpdate(a);
               sum=(float)0;
               for(int i=0; i<AvgArray.size(); i++){
                   sum +=AvgArray.get(i);
               }
               Avg = sum / AvgArray.size();
               String Avg2=String.format("%.2f",Avg);
               tvAvg.setText(Avg2);
           }
       }
    };
    class MyThread extends Thread{
        @Override
        public void run() {
            while(true){
                handler.sendEmptyMessage(0);
                try{
                    Thread.sleep(500);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }
    private void threadStart(){
        MyThread thread = new MyThread();
//        thread.setDaemon(true);
        thread.start();
    }
}
