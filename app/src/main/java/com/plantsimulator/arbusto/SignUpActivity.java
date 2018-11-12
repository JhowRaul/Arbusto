package com.plantsimulator.arbusto;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.plantsimulator.arbusto.model.User;

public class SignUpActivity extends AppCompatActivity {

    private EditText editEmail;
    private EditText editSenha;
    private FirebaseAuth mAuth;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editEmail  = findViewById(R.id.editEmail);
        editSenha = findViewById(R.id.editSenha);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void criar(View view){
        String sLogin = editEmail.getText().toString();
        String sSenha = editSenha.getText().toString();

        final User user = new User();
        user.setNome("Guest");

        mAuth.createUserWithEmailAndPassword(sLogin, sSenha)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(SignUpActivity.this, "Usuario criado com sucesso!", Toast.LENGTH_SHORT).show();

                            FirebaseUser currentUser = mAuth.getCurrentUser();

                            salvarDadosUsuario(currentUser.getUid(), user);

                            Intent intent = new Intent(SignUpActivity.this, InitialActivity.class);
                            startActivity(intent);
                        } else{
                            Toast.makeText(SignUpActivity.this, "Falha na criação de usuário.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void salvarDadosUsuario(String uid, User user){
        mDatabase.child("users").child(uid).setValue(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.e("Salvar dados user:", "Sucesso!");
                        Toast.makeText(SignUpActivity.this, "Dados salvos com sucesso!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("Salvar dados user:", "Falha.");
                        Toast.makeText(SignUpActivity.this, "Não salvou.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void logar(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

}
