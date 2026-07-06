package com.example.firebase;

import android.os.Bundle;
import android.view.TextureView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference HumedadRef,presionRef,VelocidadRef,TemperauraRef;
    EditText txtTemperatura;
    EditText txtSetTemperatura, txtSetHumedad, txtSetPresion, txtSetVelocidad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        database = FirebaseDatabase.getInstance();

        HumedadRef =    database.getReference("sensores/humedad");

        presionRef = database.getReference("sensores/presion");

        VelocidadRef =  database.getReference("sensores/velocidad");

        TemperauraRef =     database.getReference("sensores/temperatura");
        TextView txtTemp=findViewById(R.id.valor_Temperatura);
        TextView txtHumedad=findViewById(R.id.valor_Humedad);
        TextView txtPresion=findViewById(R.id.valor_Presion);
        TextView txtVelocidad=findViewById(R.id.valor_velocidad);

        txtSetTemperatura = findViewById(R.id.setvalor_Temperatura);
        txtSetHumedad = findViewById(R.id.setvalor_Humedad);
        txtSetPresion = findViewById(R.id.setvalor_Presion);
        txtSetVelocidad = findViewById(R.id.setvalor_Velocidad);

        TemperauraRef.addValueEventListener(setListener(txtTemp,"c"));
        HumedadRef.addValueEventListener(setListener(txtHumedad,"%"));
        presionRef.addValueEventListener(setListener(txtPresion,"hPa"));
        VelocidadRef.addValueEventListener(setListener(txtVelocidad,"Km/h"));

    }

    public ValueEventListener setListener(TextView txt, String UnidadMedida){

        return (new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                txt.setText(snapshot.getValue().toString() + " " + UnidadMedida);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                txt.setText("");
            }
        });

    }

    public void clickBotonTemp(View view){
        TemperauraRef.setValue(Float.parseFloat(txtSetTemperatura.getText().toString()));
    }

    public void clickBotonHumedad(View view){
        HumedadRef.setValue(Float.parseFloat(txtSetHumedad.getText().toString()));
    }

    public void clickBotonPresion(View view){
        presionRef.setValue(Float.parseFloat(txtSetPresion.getText().toString()));
    }

    public void clickBotonVelocidad(View view){
        VelocidadRef.setValue(Float.parseFloat(txtSetVelocidad.getText().toString()));
    }
}