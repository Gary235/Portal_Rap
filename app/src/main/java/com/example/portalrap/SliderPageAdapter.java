package com.example.portalrap;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

public class SliderPageAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> fragmentList;

   public SliderPageAdapter(FragmentManager fm,List<Fragment> fragmentList){
       super(fm);
       this.fragmentList=fragmentList;
   }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
