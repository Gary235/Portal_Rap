package com.example.portalrap;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.portalrap.Adaptadores.SliderPageAdapter;
import com.example.portalrap.FragmentsEntrenamiento.FragCola;
import com.example.portalrap.FragmentsEntrenamiento.FragDuracion;
import com.example.portalrap.FragmentsEntrenamiento.FragEntrenar;
import com.example.portalrap.FragmentsEntrenamiento.FragEstimulo;
import com.example.portalrap.FragmentsEntrenamiento.FragModo;
import com.example.portalrap.FragmentsEntrenamiento.FragTodoListo;
import com.example.portalrap.FragmentsInicio.FragFoto1;
import com.example.portalrap.FragmentsInicio.FragFoto2;
import com.example.portalrap.FragmentsInicio.FragFoto3;
import com.example.portalrap.FragmentsInicio.FragIniciarSesion;
import com.example.portalrap.FragmentsInicio.FragRegistro;
import com.example.portalrap.FragmentsUsuario.FragEditarPerfil;
import com.example.portalrap.FragmentsUsuario.FragFavoritos;
import com.example.portalrap.FragmentsUsuario.FragUsuario;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    FragmentManager adminFragment;
    FragmentTransaction transaccionFragment;
    Fragment FragGlobal;
    ViewPager pager;
    PagerAdapter pagerAdapter;
    BottomNavigationView bottom;
    public static int PosModo = -1, Frecuencia = -1, Segundos = -1 , Minutos = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO,Manifest.permission.INTERNET}, 1000);
        }
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        bottom = findViewById(R.id.bottomnavigation);
        pager = findViewById(R.id.pager);

        bottom.setOnNavigationItemSelectedListener(listenernav);

        adminFragment = getFragmentManager();
        FragGlobal = new FragIniciarSesion();
        transaccionFragment=adminFragment.beginTransaction();
        transaccionFragment.replace(R.id.frameLayout, FragGlobal,null);
        transaccionFragment.commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener listenernav= new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragseleccionado=null;

            switch (item.getItemId()){
                case R.id.item1:
                    fragseleccionado = new FragHome();
                    FragBases.isActionMode = false;
                    FragBases.actionMode = null;
                    FragBases.UserSelection.clear();
                    break;
                case R.id.item2:
                    fragseleccionado = new FragBases();
                    FragBases.isActionMode = false;
                    FragBases.actionMode = null;
                    FragBases.UserSelection.clear();

                    Bundle args = new Bundle();
                    args.putString("desdedur","no");
                    fragseleccionado.setArguments(args);
                    break;
                case R.id.item3:
                    fragseleccionado = new FragUsuario();
                    FragBases.isActionMode = false;
                    FragBases.actionMode = null;
                    FragBases.UserSelection.clear();
                    break;
            }
            transaccionFragment=adminFragment.beginTransaction();
            transaccionFragment.replace(R.id.frameLayout, fragseleccionado,null);
            transaccionFragment.addToBackStack(null);
            transaccionFragment.commit();

            return true;
        }
    };

    public void PasaraFragmentRegistro()
    {
        FragGlobal = new FragRegistro();
        transaccionFragment=adminFragment.beginTransaction();
        transaccionFragment.replace(R.id.frameLayout, FragGlobal);
        transaccionFragment.addToBackStack(null);
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
        FragFavoritos.UserSelection.clear();
        FragBases.UserSelection.clear();
        FragGlobal = new FragHome();
        transaccionFragment=adminFragment.beginTransaction();
        transaccionFragment.replace(R.id.frameLayout, FragGlobal,null);

        transaccionFragment.commit();
    }
    public void PasaraFragmentModo()
    {
        FragGlobal = new FragModo();
        transaccionFragment=adminFragment.beginTransaction();
        transaccionFragment.replace(R.id.frameLayout, FragGlobal);
        transaccionFragment.addToBackStack(null);

        transaccionFragment.commit();
    }
    public void PasaraFragmentEstimulo()
    {
        FragGlobal = new FragEstimulo();
        transaccionFragment=adminFragment.beginTransaction();
        transaccionFragment.replace(R.id.frameLayout, FragGlobal);
        transaccionFragment.addToBackStack(null);

        transaccionFragment.commit();
    }
    public void PasaraFragmentDuracion()
    {
        FragGlobal = new FragDuracion();
        transaccionFragment=adminFragment.beginTransaction();
        transaccionFragment.replace(R.id.frameLayout, FragGlobal);
        transaccionFragment.addToBackStack(null);

        transaccionFragment.commit();
    }
    public void PasaraFragBases(String desdedur)
    {
        FragGlobal = new FragBases();
        Bundle args = new Bundle();
        args.putString("desdedur","si");
        FragGlobal.setArguments(args);
        transaccionFragment=adminFragment.beginTransaction();
        transaccionFragment.replace(R.id.frameLayout, FragGlobal);
       transaccionFragment.addToBackStack(null);

        transaccionFragment.commit();
    }
    public void PasaraFragEditarPerfil()
    {
        FragGlobal = new FragEditarPerfil();
        transaccionFragment=adminFragment.beginTransaction();
        transaccionFragment.replace(R.id.frameLayout, FragGlobal);
        transaccionFragment.addToBackStack(null);

        transaccionFragment.commit();
    }
    public void PasaraFragUsuario()
    {
        FragFavoritos.UserSelection.clear();
        FragBases.UserSelection.clear();
        FragGlobal = new FragUsuario();
        transaccionFragment=adminFragment.beginTransaction();
        transaccionFragment.replace(R.id.frameLayout, FragGlobal);
        transaccionFragment.addToBackStack(null);

        transaccionFragment.commit();
    }
    public void PasaraFragFavoritos()
    {
        FragGlobal = new FragFavoritos();
        FragFavoritos.UserSelection.clear();
        FragBases.UserSelection.clear();
        transaccionFragment=adminFragment.beginTransaction();
        transaccionFragment.replace(R.id.frameLayout, FragGlobal);
        transaccionFragment.addToBackStack(null);

        transaccionFragment.commit();
    }
    public void PasaraFragEntrenar(String desdecola)
    {
        bottom.setVisibility(View.GONE);
        Log.d("desdecola", desdecola);
        FragGlobal = new FragEntrenar();
        transaccionFragment=adminFragment.beginTransaction();
        transaccionFragment.replace(R.id.frameLayout, FragGlobal);
        transaccionFragment.addToBackStack(null);
        transaccionFragment.commit();
    }
    public void PasaraFragTodoListo(String desdedur)
    {
        FragGlobal = new FragTodoListo();
        Bundle args = new Bundle();
        args.putString("desdedur", desdedur);
        FragGlobal.setArguments(args);
        transaccionFragment=adminFragment.beginTransaction();
        transaccionFragment.replace(R.id.frameLayout, FragGlobal);
        transaccionFragment.addToBackStack(null);
        transaccionFragment.commit();
    }

    @Override
    public void onBackPressed() {

        //si no queda ningún fragment sale de este activity
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            // super.onBackPressed();
            finish();
        } else { //si no manda al fragment anterior.
            getFragmentManager().popBackStack();
        }
    }


}