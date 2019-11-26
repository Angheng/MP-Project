/*
Author: 20181683 임중혁
Last Modification date: 19.11.16
Function: MainActivity's View Pager Adapter.
 */


package com.bungae1112.final_proj.mainActivity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.bungae1112.final_proj.mainActivity.listView.ListFragment;
import com.bungae1112.final_proj.mainActivity.mapView.MapFragment;

public class PagerAdapter extends FragmentStatePagerAdapter
{

    int numOfTabs;

    public PagerAdapter(FragmentManager fm, int numOfTabs)
    {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public Fragment getItem(int position)
    {

        switch (position)
        {

            case 0:
                return new MapFragment();

            case 1:
                return new ListFragment("total");

            case 2:
                return new ListFragment("pub");
            case 3:
                return new ListFragment("izakaya");
            case 4:
                return new ListFragment("Pocha");

        }

        return null;
    }

    @Override
    public int getCount()
    {
        return numOfTabs;
    }
}
