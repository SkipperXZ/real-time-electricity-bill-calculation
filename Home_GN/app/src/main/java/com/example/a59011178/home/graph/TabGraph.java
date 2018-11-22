package com.example.a59011178.home.graph;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a59011178.home.DatabaseHelper;
import com.example.a59011178.home.Item;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.ValueDependentColor;
import android.graphics.Color;

import java.util.ArrayList;




import com.example.a59011178.home.R;

import java.util.List;


public class TabGraph extends Fragment {
    private List<Item> mItemList;
    private DatabaseHelper mHelp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tabgraph, container, false);
        DataPoint dataPoint;

        mHelp = new DatabaseHelper(getContext());
        mItemList = mHelp.getItemList();
        int size = mItemList.size();

        ArrayList<String> day = new ArrayList<>();
        day = getDay();

        ArrayList<String> money = new ArrayList<>();
        money = getMoney();


        //split here
        GraphView graph = rootView.findViewById(R.id.graph);
        BarGraphSeries series = new BarGraphSeries<DataPoint> (new DataPoint[]{

                /*for (int i=0; i<size; i++){
                    new DataPoint()

        }*/




        });


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

    //getDB
    public ArrayList<String> getMoney() {

        mHelp = new DatabaseHelper(getContext());
        mItemList = mHelp.getItemList();

        int size = mItemList.size();


        ArrayList<String> tMoney = new ArrayList<>();



        for (int i = 0; i < size; i++) {

            Item nowItem = mItemList.get(i);

            String TotalMoney = String.valueOf(nowItem.getTotalMoney());


            tMoney.add(TotalMoney);


        }

        return tMoney;
    }

    public ArrayList<String> getDay() {

        mHelp = new DatabaseHelper(getContext());
        mItemList = mHelp.getItemList();

        int size = mItemList.size();


        ArrayList<String> allDate = new ArrayList<>();


        for (int i = 0; i < size; i++) {

            Item nowItem = mItemList.get(i);


            String Date = nowItem.getDate();

            allDate.add(Date);



        }

        return allDate;
    }

}




