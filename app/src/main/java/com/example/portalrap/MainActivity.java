package com.example.portalrap;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    FragmentManager adminFragment;
    FragmentTransaction transaccionFragment;
    ViewPager pager;
    PagerAdapter pagerAdapter;
    BottomNavigationView bottom;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bottom = findViewById(R.id.bottomnavigation);
        pager = findViewById(R.id.pager);

        bottom.setOnNavigationItemSelectedListener(listenernav);



        adminFragment = getFragmentManager();
        Fragment fraginisesion;
        fraginisesion = new FragIniciarSesion();
        transaccionFragment=adminFragment.beginTransaction();
        transaccionFragment.replace(R.id.frameLayout, fraginisesion);
        transaccionFragment.commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener listenernav= new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragseleccionado=null;

            switch (item.getItemId()){
                case R.id.item1:
                    fragseleccionado = new FragHome();
                    break;
                case R.id.item2:
                    fragseleccionado = new FragBases();
                    break;
                case R.id.item3:
                    fragseleccionado = new FragUsuario();
                    break;
            }
            transaccionFragment=adminFragment.beginTransaction();
            transaccionFragment.replace(R.id.frameLayout, fragseleccionado);
            transaccionFragment.commit();

            return true;
        }
    };

    public void PasaraFragmentRegistro()
    {
        Fragment fragreg;
        fragreg = new FragRegistro();
        transaccionFragment=adminFragment.beginTransaction();
        transaccionFragment.replace(R.id.frameLayout, fragreg);
        transaccionFragment.commit();
    }

    public void PasaraFragmentSlider()
    {
        pager.setVisibility(View.VISIBLE);
        List<androidx.fragment.app.Fragment> list = new ArrayList<>();
        list.add(new FragFoto1());
        list.add(new FragFoto2());
        list.add(new FragFoto3());

        pagerAdapter = new SliderPageAdapter(getSupportFragmentManager(),list);
        pager.setAdapter(pagerAdapter);

    }
    public void PasaraFragmentHome()
    {
        pager.setVisibility(View.GONE);
        bottom.setVisibility(View.VISIBLE);
        Fragment fraghome;
        fraghome = new FragHome();
        transaccionFragment=adminFragment.beginTransaction();
        transaccionFragment.replace(R.id.frameLayout, fraghome);
        transaccionFragment.commit();
    }
    public void PasaraFragmentModo()
    {
        Fragment fragmodo;
        fragmodo = new FragModo();
        transaccionFragment=adminFragment.beginTransaction();
        transaccionFragment.replace(R.id.frameLayout, fragmodo);
        transaccionFragment.commit();
    }
    public void PasaraFragmentEstimulo()
    {
        Fragment fragestimulo;
        fragestimulo = new FragEstimulo();
        transaccionFragment=adminFragment.beginTransaction();
        transaccionFragment.replace(R.id.frameLayout, fragestimulo);
        transaccionFragment.commit();
    }


}