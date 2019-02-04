package teamawesome.alertme;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class DatosLogin extends ActionBarActivity {

    private EditText Correo;
    private EditText Password;
    private Button btnRegistrar,BtnRegistrarr;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Correo = (EditText) findViewById(R.id.txtcorreo1);
        Password = (EditText) findViewById(R.id.txtContra1);

        btnRegistrar = (Button) findViewById(R.id.btnRegistrar);
        BtnRegistrarr = (Button) findViewById(R.id.btnIniciar2);

        firebaseAuth = FirebaseAuth.getInstance();




        progressDialog = new ProgressDialog(this);

        //asociamos un oyente al evento clic del botón

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrarUsuario();
            }
        });
        BtnRegistrarr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DatosLogin.this,Login.class));
                finish();
            }
        });




    }


    private void registrarUsuario() {

        //Obtenemos el email y la contraseña desde las cajas de texto
        String email = Correo.getText().toString().trim();
        String password = Password.getText().toString().trim();

        //Verificamos que las cajas de texto no esten vacías
        if (TextUtils.isEmpty(email)) {//(precio.equals(""))
            Toast.makeText(this, "Ingrese un correo para registrarse", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Falta ingresar la contraseña", Toast.LENGTH_LONG).show();
            return;
        }



        progressDialog.setMessage("Registrando usuario en linea...");
        progressDialog.show();

        //registramos un nuevo usuario
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if (task.isSuccessful()) {

                            Toast.makeText(DatosLogin.this, "Se ha registrado el usuario exitosamente " , Toast.LENGTH_LONG).show();
                            progressDialog.hide();
                            finish();
                            startActivity(new Intent(DatosLogin.this, Login.class));
                        } else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {//si se presenta una colisión
                                Toast.makeText(DatosLogin.this, "Ese usuario ya existe ", Toast.LENGTH_SHORT).show();
                            }else
                            {

                                Toast.makeText(DatosLogin.this, "contraseña debe tener mini mo 6 caracteres", Toast.LENGTH_SHORT).show();


                            }                      }
                        progressDialog.dismiss();
                    }
                });

    }

}
