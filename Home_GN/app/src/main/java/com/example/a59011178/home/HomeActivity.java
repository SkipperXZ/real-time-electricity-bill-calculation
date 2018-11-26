package com.example.a59011178.home;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.a59011178.home.cal.TabCal;
import com.example.a59011178.home.edit.EditActivity;
import com.example.a59011178.home.equip.TabEquip;
import com.example.a59011178.home.graph.TabGraph;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.jakewharton.threetenabp.AndroidThreeTen;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private long backPressedTime;
    private Toast backToast;
    private DatabaseHelper mHelp;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidThreeTen.init(this);
        setContentView(R.layout.activity_home);

// ------------------------------------------------------------------------------------------------->
//        mHelp = new DatabaseHelper(HomeActivity.this);
//        mHelp.allChangeToManual();
// ------------------------------------------------------------------------------------------------->

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        mViewPager.setCurrentItem(1);
        FloatingActionButton buttonAddMore = (FloatingActionButton)findViewById(R.id.fab);

        buttonAddMore.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(HomeActivity.this, AddItemActivity.class);
                        startActivity(intent);
                    }
                }
        );
    }

    @Override
    public void onBackPressed(){
        if (backPressedTime + 1000 > System.currentTimeMillis()){
            backToast.cancel();
            super.onBackPressed();
            return;
        } else {
            backToast = Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml. action_Edit_Equip
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent i2=new Intent(getApplicationContext(),SettingActivity.class);
                startActivity(i2);
                break;
            case R.id.action_Log_out:

                FirebaseAuth.getInstance().signOut();
                Intent BackpressedIntent = new Intent();
                BackpressedIntent .setClass(getApplicationContext(),LoginActivity.class);
                BackpressedIntent .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(BackpressedIntent );
                finish();

                break;
            case R.id.action_cut_out:
                final DatabaseHelper mHelper = new DatabaseHelper(HomeActivity.this);

                AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);

                builder.setTitle("Start new month");
                builder.setMessage("Are you sure to reset all money?\n(All your equipment are remain)");

                builder.setPositiveButton("Reset", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mHelper.cutOut();
                        mHelper.newMonth();
                        Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                        Toast.makeText(getApplicationContext(), "Start new month", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton("Maybe next time", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.show();

                break;
        }

        return true;
    }

    //delete PlaceholderFragment

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            //Returning the current tab
            switch (position){
                case 0 :
                    TabCal tab1 =  new TabCal();
                    return tab1;
                case 1 :
                    TabEquip tab2 = new TabEquip();

                    return tab2;
                case 2 :
                    TabGraph tab3 = new TabGraph();
                    return tab3;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
        @Override
        public CharSequence getPageTitle(int postion){
            switch (postion){
                case 0 :
                    return "equipment";
                case 1 :
                    return "graph";
                case 2 :
                    return "sublist_calculate";

            }
                return null;}
    }
}
