package com.example.portalrap;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;

public class FragHome extends Fragment implements View.OnClickListener{

    Button btnEntrenar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.home,container,false);

        btnEntrenar = v.findViewById(R.id.btnentrenar);
        btnEntrenar.setOnClickListener(this);



        return v;
    }

    @Override
    public void onClick(View v) {

        MainActivity main=(MainActivity) getActivity();
        main.PasaraFragmentModo();


    }
}
