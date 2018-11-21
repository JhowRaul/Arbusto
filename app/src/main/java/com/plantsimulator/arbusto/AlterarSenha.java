package com.plantsimulator.arbusto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AlterarSenha extends AppCompatActivity implements View.OnClickListener {

    private TextView textAlterar;
    private EditText editSenhaAntiga;
    private EditText editNovaSenha;
    private EditText editConfirmaNovaSenha;
    private Button btnSalvar;
    private ProgressBar progressSalvar;
    //private Button btnCancelar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar_senha);

        textAlterar = findViewById(R.id.textAlterar);
        editSenhaAntiga = findViewById(R.id.editSenhaAntiga);
        editNovaSenha= findViewById(R.id.editNovaSenha);
        editConfirmaNovaSenha = findViewById(R.id.editConfirmaNovaSenha);
        progressSalvar = findViewById(R.id.progressSalvar);
        btnSalvar.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            String email = currentUser.getEmail();

            textAlterar.setText("Alterar senha de " + email);
        }
    }

    public void cacelar(View view){
        Toast.makeText(this, "Cancelado.", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, EditarPerfilActivity.class);
        startActivity(intent);
    }


    @Override
    public void onClick(View view) {
        progressSalvar.setVisibility(ProgressBar.VISIBLE);

        FirebaseUser currentUser = mAuth.getCurrentUser();
        final String email = currentUser.getEmail();

        // Em contrução...
        //AuthCredential credential = EmailAuthProvider

        progressSalvar.setVisibility(ProgressBar.GONE);

        Intent intent = new Intent(this, InitialActivity.class);
        startActivity(intent);
    }
}
