package com.example.a59011178.home.graph;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a59011178.home.DatabaseHelper;
import com.example.a59011178.home.Item;
import com.example.a59011178.home.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.util.List;


public class TabGraph extends Fragment {

    private List<Item> mItemList;
    private DatabaseHelper mHelp;

    BarGraphSeries series = new BarGraphSeries<DataPoint>(new DataPoint[0]);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tabgraph, container, false);
        DataPoint dataPoint;

        getDataPoint();

        //split here
        GraphView graph = rootView.findViewById(R.id.graph);

        graph.addSeries(series);

// styling
        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                return Color.rgb((int) data.getX() * 255 / 4, (int) Math.abs(data.getY() * 255 / 6), 100);
            }
        });

        series.setSpacing(50);

// draw values on top
        series.setDrawValuesOnTop(true);
        series.setValuesOnTopColor(Color.RED);
//series.setValuesOnTopSize(50);


        //not after root

        return rootView;
    }

    private DataPoint[] getDataPoint() {

        mHelp = new DatabaseHelper(getContext());
        mItemList = mHelp.getItemList();
        int size = mItemList.size();

        int money[] = getMoney();
        int id[] = getDay();

        DataPoint[] dp = new DataPoint[size];

        String[] columns = {""};

        for (int i = 0; i < size; i++) {
            dp[i] = new DataPoint(money[i], id[i]);
        }

        return null;
    }

    //getDB
    public int[] getMoney() {

        mHelp = new DatabaseHelper(getContext());
        mItemList = mHelp.getItemList();

        int size = mItemList.size();
        int[] allMoney = new int[size];

        for (int i = 0; i < size; i++) {
            Item nowItem = mItemList.get(i);
            int TotalMoney = nowItem.getTotalMoney();
            allMoney[i] = TotalMoney;
        }
        return allMoney;
    }

    public int[] getDay() {

        mHelp = new DatabaseHelper(getContext());
        mItemList = mHelp.getItemList();

        int size = mItemList.size();
        int[] allDate = new int[size];

        for (int i = 0; i < size; i++) {
            Item nowItem = mItemList.get(i);
            int id = nowItem.getId();

            allDate[i] = id;
        }
        return allDate;
    }

}




