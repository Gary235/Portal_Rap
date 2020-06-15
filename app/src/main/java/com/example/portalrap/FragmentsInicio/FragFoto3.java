package com.example.portalrap.FragmentsInicio;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.portalrap.MainActivity;
import com.example.portalrap.R;


public class FragFoto3 extends Fragment implements View.OnClickListener {

    ImageButton btn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v;
        v = inflater.inflate(R.layout.foto3,container,false);

        btn = v.findViewById(R.id.btnEmpezar);
        btn.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {

        MainActivity main=(MainActivity) getActivity();
        main.PasaraFragmentHome();

    }
}
