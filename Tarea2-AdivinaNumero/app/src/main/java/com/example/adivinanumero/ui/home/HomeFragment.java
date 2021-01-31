package com.example.adivinanumero.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.adivinanumero.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeFragment extends Fragment {

    private EditText etNumero;
    private Button btComparar;
    private TextView tvMensaje;
    private TextView tvHint;
    private String valor;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        //Var
        etNumero = root.findViewById(R.id.etNumero);
        tvMensaje = root.findViewById(R.id.tvMensaje);
        tvHint = root.findViewById(R.id.tvHint);
        btComparar = root.findViewById(R.id.btCompara);

        //BD
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("númeroSecreto");


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                valor = snapshot.getValue(String.class);//Obtengo el valor de la BD

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                tvMensaje.setText("Error");
            }
        });

        btComparar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int numeroMagico = Integer.parseInt(valor);
                String numeroIngresado = etNumero.getText().toString();
//Validaciones para ver si el usuario adivina el numero
                if(numeroIngresado.isEmpty()){
                    tvMensaje.setText("Ingrese un número válido");
                }else{
                    if( numeroMagico==Integer.parseInt(numeroIngresado)){
                        tvMensaje.setText("¡Felicidades, el número era "+valor+"!");
                    }else if(numeroMagico>Integer.parseInt(numeroIngresado)){
                        tvMensaje.setText("El número secreto es mayor al que ingresó");
                    }else if(numeroMagico<Integer.parseInt(numeroIngresado)){
                        tvMensaje.setText("El número secreto es menor al que ingresó");
                    }

                }
            }
        });
        return root;
    }
}