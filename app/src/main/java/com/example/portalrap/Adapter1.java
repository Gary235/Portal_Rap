package com.example.portalrap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class Adapter1 extends PagerAdapter {

    private List<Model> list;
    private LayoutInflater inflater;
    private Context context;

    public Adapter1(List<Model> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.item,container,false);

        ImageView imagenmodo;
        TextView textoModo;
        imagenmodo = v.findViewById(R.id.imagenModo);
        textoModo = v.findViewById(R.id.textomodo);
        imagenmodo.setImageResource(list.get(position).getImage());
        textoModo.setText(list.get(position).getTexto());

        container.addView(v,0);
        return v;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
