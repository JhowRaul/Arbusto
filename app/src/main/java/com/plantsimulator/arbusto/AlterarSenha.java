package com.plantsimulator.arbusto;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AlterarSenha extends AppCompatActivity implements View.OnClickListener {

    private TextView textAlterar;
    private EditText editSenhaAntiga;
    private EditText editNovaSenha;
    private EditText editConfirmaNovaSenha;
    private Button btnSalvar; // OnClickListener
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
        btnSalvar = findViewById(R.id.btnSalvar);
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

        final FirebaseUser currentUser = mAuth.getCurrentUser();

        final String email = currentUser.getEmail();
        final String oldpass = editSenhaAntiga.getText().toString();

        final String newPass = editNovaSenha.getText().toString();
        final String confirmaNewPass = editConfirmaNovaSenha.getText().toString();

        if(newPass.equals(confirmaNewPass)) {
            AuthCredential credential = EmailAuthProvider.getCredential(email, oldpass);



            currentUser.reauthenticate(credential)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {


                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                currentUser.updatePassword(newPass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(AlterarSenha.this, "Senha alterada com sucesso.", Toast.LENGTH_SHORT).show();

                                            Intent intent = new Intent(AlterarSenha.this, EditarPerfilActivity.class);
                                            startActivity(intent);
                                        } else {
                                            Toast.makeText(AlterarSenha.this, "Falha na alteração de senha.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(AlterarSenha.this, "Falha na autenticação.", Toast.LENGTH_SHORT).show();
                            }
                        }


                    });



        } else {
            Toast.makeText(AlterarSenha.this, "Confirmação de nova senha diferente da nova senha.", Toast.LENGTH_SHORT).show();
        }

        progressSalvar.setVisibility(ProgressBar.GONE);
    }
}
