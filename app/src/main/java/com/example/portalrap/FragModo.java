package com.example.portalrap;

import android.animation.ArgbEvaluator;
import android.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;


public class FragModo extends Fragment {

        ViewPager viewPager;
        Adapter1 adapter1;
        ArgbEvaluator argbEvaluator = new ArgbEvaluator();
        List<Model> lista =  new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_frag_modo,container,false);

        viewPager = v.findViewById(R.id.viewpager2);

        lista.add(new Model(R.drawable.ic_objetos,"Foto Inicial 1"));
        lista.add(new Model(R.drawable.fotoinicial_2,"Foto Inicial 2"));
        lista.add(new Model(R.drawable.fotoinicial_3,"Foto Inicial 3"));

        adapter1 = new Adapter1(lista,getActivity());
        viewPager.setAdapter(adapter1);
        viewPager.setPadding(130,0,130,0);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        return v;
    }



}