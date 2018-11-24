package com.example.a59011178.home.graph;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.a59011178.home.DatabaseHelper;
import com.example.a59011178.home.Item;
import com.example.a59011178.home.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;


public class TabGraph extends Fragment  {

    private List<Item> mItemList;
    private DatabaseHelper mHelp;
    private Spinner mGraph_spinner;
    List<PieEntry> entries = new ArrayList<PieEntry>();
    PieChart chart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mHelp = new DatabaseHelper(this.getContext());
        mItemList=mHelp.getItemList();

        View rootView = inflater.inflate(R.layout.fragment_tabgraph, container, false);
        chart = (PieChart)rootView.findViewById(R.id.pieChart);
        chart = initChart(chart);

        mGraph_spinner = (Spinner) rootView.findViewById(R.id.Graph_spinner);
        String[] spinnerArray = getResources().getStringArray(R.array.graph_type);
        ArrayAdapter<String> graphArrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line,spinnerArray);
        mGraph_spinner.setAdapter(graphArrayAdapter);
        mGraph_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedText = mGraph_spinner.getSelectedItem().toString();
                if(selectedText.equals("Total money")){
                    entries= new ArrayList<PieEntry>();
                    for (Item data : mItemList) {
                      entries.add(new PieEntry(((float)data.getPower()*(float)mItemList.get(0).getTotalMoney()/3600)/1000,data.getName()));
                    }
                    chart.setCenterText(new SpannableString("Usage Fee (Baht)"));

                }else if(selectedText.equals("Power used")){
                    entries= new ArrayList<PieEntry>();
                    for (Item data : mItemList) {
                        entries.add(new PieEntry((float)data.getPower()*(float)data.getHr(),data.getName()));
                    }
                    chart.setCenterText(new SpannableString("Power used (Watt)"));
                }else if(selectedText.equals("Time used")){
                    entries= new ArrayList<PieEntry>();
                    for (Item data : mItemList) {
                        entries.add(new PieEntry(((float)data.getHr()/3600),data.getName()));
                    }
                    chart.setCenterText(new SpannableString("Time used (Hour)"));
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
                chart.notifyDataSetChanged();
                chart.invalidate(); // refresh

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        return rootView;
    }


    private PieChart initChart(PieChart chart){

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




