package com.example.portalrap;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.portalrap.Adaptadores.SliderPageAdapter;
import com.example.portalrap.Clases.Base;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public static Boolean TodosPermisos = false;
    FragmentManager adminFragment;
    FragmentTransaction transaccionFragment;
    Fragment FragGlobal;
    ViewPager pager;
    PagerAdapter pagerAdapter;
    BottomNavigationView bottom;
    public static int PosModo = -1, Frecuencia = -1, Segundos = -1 , Minutos = -1;
    FirebaseUser usuarioActual;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    int anchoPantalla, altoPantalla;
    @Override
    public void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser usuarioActual = mAuth.getCurrentUser();
        if(usuarioActual != null){
            //Redirijo a donde necesite. Esto es si ya hay un usuario logueado.
            actualizarUsuario(usuarioActual);
            PasaraFragmentHome();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED   ||
                ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WAKE_LOCK) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.INTERNET,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WAKE_LOCK
            }, 1);
        }
        db = FirebaseFirestore.getInstance();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        anchoPantalla = metrics.widthPixels;
        altoPantalla = metrics.heightPixels;

        bottom = findViewById(R.id.bottomnavigation);
        pager = findViewById(R.id.pager);

        bottom.setOnNavigationItemSelectedListener(listenernav);

        adminFragment = getFragmentManager();
        PasaraFragmentIniciarSesion();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    TodosPermisos = false;
                }
                else {
                    TodosPermisos = true;
                }
            }
    }


    private BottomNavigationView.OnNavigationItemSelectedListener listenernav=  new BottomNavigationView.OnNavigationItemSelectedListener() {
        @SuppressLint("ResourceType")
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


    public void PasaraFragmentIniciarSesion()
    {
        bottom.setVisibility(View.GONE);
        FragGlobal = new FragIniciarSesion();
        transaccionFragment=adminFragment.beginTransaction();
        transaccionFragment.replace(R.id.frameLayout, FragGlobal,null);
        transaccionFragment.commit();

    }
    public void PasaraFragmentRegistro()
    {
        bottom.setVisibility(View.GONE);
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
        transaccionFragment.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
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
        args.putString("desdedur",desdedur);
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
        transaccionFragment.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
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
        transaccionFragment.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
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

    public void actualizarUsuario(FirebaseUser user){
        usuarioActual = user;
    }
    public FirebaseUser obtenerUsuario(){
        return usuarioActual;
    }

    public int obtenerAlto(){
        return altoPantalla;
    }
    public int obtenerAncho(){
        return anchoPantalla;
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


    public void agregarFav(String nombre,String artista,Boolean destacado, String id, String url, Boolean fav, String dur) {
        Map<String, Object> beat = (new Base(artista,nombre, url,dur,destacado, true, id)).toMap();

        db.collection("Usuarios").document(usuarioActual.getUid()).collection("BeatsFav")
                .document(id)
                .set(beat)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.e("CambiarFav", "Bien Ahi");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("CambiarFav", "Error al actualizar: " + e);
                    }
                });

    }
    public void eliminarFav(String id) {

        db.collection("Usuarios").document(usuarioActual.getUid()).collection("BeatsFav")
                .document(id)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.e("CambiarFav", "Bien Ahi");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("CambiarFav", "Error al actualizar: " + e);
                    }
                });
    }


    public void eliminarGrabDeStorage(String url) {

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        StorageReference grabRef  = storageRef.child("Grabaciones/" + usuarioActual.getUid() + "/" + url);


        grabRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // File deleted successfully
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Uh-oh, an error occurred!
            }
        });


    }
    public void eliminarGrabDeFirestore(String id) {

        db.collection("Usuarios").document(usuarioActual.getUid()).collection("Grabaciones")
                .document(id)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.e("CambiarFav", "Bien Ahi");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("CambiarFav", "Error al actualizar: " + e);
                    }
                });
    }
    public void eliminarGrabDeAlmacenamiento(String url) {

        File file = new File(FragEntrenar.localfile, url);
        boolean deleted = file.delete();
        if(deleted)
            Toast.makeText(getApplicationContext(), "Grabación Eliminada", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getApplicationContext(), "Error en la eliminación", Toast.LENGTH_SHORT).show();

    }


/*Paso en limpio lo que hablamos:
- Detener el tiempo cuando grabas
- Grabación automática cuando termina la pista
- AlertDialog para preguntar si está seguro de que desea grabar
*/
}