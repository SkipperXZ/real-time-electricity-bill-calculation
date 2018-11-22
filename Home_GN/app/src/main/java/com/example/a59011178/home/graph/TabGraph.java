package com.example.a59011178.home.graph;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.DropBoxManager;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a59011178.home.DatabaseHelper;
import com.example.a59011178.home.Item;
import android.graphics.Color;

import java.util.ArrayList;

import com.example.a59011178.home.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.util.List;


public class TabGraph extends Fragment  {

    private List<Item> mItemList;
    private DatabaseHelper mHelp;
    List<PieEntry> entries = new ArrayList<PieEntry>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mHelp = new DatabaseHelper(this.getContext());
        mItemList=mHelp.getItemList();
        View rootView = inflater.inflate(R.layout.fragment_tabgraph, container, false);
        PieChart chart = (PieChart)rootView.findViewById(R.id.pieChart);
        chart = initChart(chart);

        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(false);

        entries= new ArrayList<PieEntry>();
        for (Item data : mItemList) {
            entries.add(new PieEntry(data.getPower(),data.getName()));
        }
        PieDataSet dataSet = new PieDataSet(entries, "Name");
        ArrayList<Integer> colors = new ArrayList<>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);


        PieData pieData = new  PieData(dataSet);
        pieData.setValueTextColor(Color.WHITE);
        pieData.setValueTextSize(11f);
        //  pieData.setValueTypeface(tfLight);
        chart.setData(pieData);
        chart.invalidate(); // refresh
        chart.setCenterText(generateCenterSpannableText());



        return rootView;
    }
    private SpannableString generateCenterSpannableText() {

        SpannableString s = new SpannableString("POWER");
        return s;
    }

    private PieChart initChart(PieChart chart){
        chart.setCenterText(generateCenterSpannableText());

        chart.setExtraOffsets(20.f, 0.f, 20.f, 0.f);

        chart.setDrawHoleEnabled(true);
        chart.setHoleColor(Color.WHITE);

        chart.setTransparentCircleColor(Color.WHITE);
        chart.setTransparentCircleAlpha(110);

        chart.setHoleRadius(58f);
        chart.setTransparentCircleRadius(61f);

        chart.setDrawCenterText(true);

        chart.setRotationAngle(0);
        chart.setRotationEnabled(true);
        chart.setHighlightPerTapEnabled(true);
        chart.animateY(1400, Easing.EaseInOutQuad);
        return chart;
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }
}




