package ru.getnow.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;
import ru.getnow.app.model.VisualPreference;

import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager mViewPager;
    SectionsPagerAdapter mSectionsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> startActivity(new Intent(getBaseContext(), MapActivity.class)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class SectionsPagerAdapter extends FragmentPagerAdapter {

        ArrayList<VisualPreference> tabContent;

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            tabContent = new ArrayList<>();
            //TODO customize icons and content
            tabContent.add(new VisualPreference("Food",
                    new String[] {"Hamburger", "Pizza", "Sushi"},
                    new int[] {R.drawable.ic_card_giftcard, R.drawable.ic_card_giftcard, R.drawable.ic_card_giftcard}));
            tabContent.add(new VisualPreference("Drinks",
                    new String[] {"Whiskey", "Beer", "Coffee"},
                    new int[] {R.drawable.ic_card_giftcard, R.drawable.ic_card_giftcard, R.drawable.ic_card_giftcard}));
            tabContent.add(new VisualPreference( "Souvenirs",
                    new String[] {"Magnets", "Beer", "Coffee"},
                    new int[] {R.drawable.ic_card_giftcard, R.drawable.ic_card_giftcard, R.drawable.ic_card_giftcard}));
        }

        @Override
        public Fragment getItem(int position) {
            return VisualPreferenceFragment.newInstance(tabContent.get(position),
                    getDefaultPrice(tabContent.get(position).getTabDescription()),
                    getDefaultSelection(tabContent.get(position).getTabDescription()+"_"));
        }

        private String getDefaultSelection(String prefName) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
            return preferences.getString(prefName, "");
        }

        private double getDefaultPrice(String prefName) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
            return preferences.getFloat(prefName, 0);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabContent.get(position).getTabDescription();
        }

        @Override
        public int getCount() {
            return tabContent.size();
        }

    }
}
