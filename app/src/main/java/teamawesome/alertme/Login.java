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

public class Login extends ActionBarActivity {



    //inicion con firebase autenticacion
    private EditText TextEmail;
    private EditText TextPassword;
    private Button btnRegistrar, btnLogin;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    private Button btnCambiar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticvity_login);


        btnCambiar = (Button) findViewById(R.id.btnDatos);

        btnCambiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), DatosLogin.class);
                startActivityForResult(intent, 0);
            }
        });


        firebaseAuth = FirebaseAuth.getInstance();


        TextEmail = (EditText) findViewById(R.id.txtCorreo);
        TextPassword = (EditText) findViewById(R.id.txtContra);

        btnRegistrar = (Button) findViewById(R.id.btnRegistrar);
        btnLogin = (Button) findViewById(R.id.btnIniciar);

        progressDialog = new ProgressDialog(this);

        //asociamos un oyente al evento clic del botón




        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loguearUsuario();
            }
        });



    }





    private void loguearUsuario() {
        //Obtenemos el email y la contraseña desde las cajas de texto
        final String email = TextEmail.getText().toString().trim();
        String password = TextPassword.getText().toString().trim();

        //Verificamos que las cajas de texto no esten vacías
        if (TextUtils.isEmpty(email)) {//(precio.equals(""))
            Toast.makeText(this, "Ingrese su usuario o registrese para iniciar ", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Falta ingresar la contraseña", Toast.LENGTH_LONG).show();
            return;
        }


        progressDialog.setMessage("Iniciando Sesion...");
        progressDialog.show();

        //loguear usuario
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if (task.isSuccessful()) {
                            int pos = email.indexOf("@");
                            String user = email.substring(0, pos);
                            Toast.makeText(Login.this, "Bienvenido " , Toast.LENGTH_LONG).show();
                            Intent intencion = new Intent(getApplication(), SplashActivity.class);

                            startActivity(intencion);


                        } else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {//si se presenta una colisión
                                Toast.makeText(Login.this, "Usuario incorrecto ", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Login.this, "Usuario o Contraseña incorrecto ", Toast.LENGTH_LONG).show();
                            }
                        }
                        progressDialog.dismiss();
                    }
                });


    }

}
