package com.plantsimulator.arbusto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InitialActivity extends AppCompatActivity {

    private TextView textNome;
    private TextView textLogin;
    private ImageView imgPerfil;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        // FB
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
        //Fim FB

        textNome = findViewById(R.id.editLogin);
        textLogin = findViewById(R.id.textLogin);

        if (currentUser != null) {
            String email = currentUser.getEmail();
            String uid = currentUser.getUid();

            // recuperarDadosUsuario();

            textLogin.setText("Email: " + email);
            //textNome.setText("Nome: " + nome);
            //imgPerfil.set

        } else if(isLoggedIn) {
            Toast.makeText(InitialActivity.this, "Logado com FB.", Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(InitialActivity.this, "Você precisa logar.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

    public void telaEditarPerfil(View view){
        Intent intent = new Intent(this, EditarPerfilActivity.class);
        startActivity(intent);
    }

    public void sair(View view){
        AccessToken.setCurrentAccessToken(null);
        mAuth.signOut();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
