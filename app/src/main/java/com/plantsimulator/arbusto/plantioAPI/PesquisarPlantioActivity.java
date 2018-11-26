package com.plantsimulator.arbusto.plantioAPI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.plantsimulator.arbusto.InitialActivity;
import com.plantsimulator.arbusto.LoginActivity;
import com.plantsimulator.arbusto.R;

public class PesquisarPlantioActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisar_plantio);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        // FB
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
        //Fim FB

        if (currentUser != null) {
            String uid = currentUser.getUid();
            //Toast.makeText(PesquisarPlantioActivity.this, "Logado com E-mail Firebase.", Toast.LENGTH_SHORT).show();
        } else if(isLoggedIn) {
            //Toast.makeText(PesquisarPlantioActivity.this, "Logado com FB.", Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(PesquisarPlantioActivity.this, "Você precisa logar.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        mDatabase = FirebaseDatabase.getInstance().getReference();
    }



}
