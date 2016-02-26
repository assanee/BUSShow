package xyz.stepsecret.busshow;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import xyz.stepsecret.busshow.API.SignIN_API;
import xyz.stepsecret.busshow.Model.SignIN_Model;
import xyz.stepsecret.busshow.TinyDB.TinyDB;


public class LoginActivity extends AppCompatActivity {


    private EditText input_Password, inputEmail;
    private TextInputLayout inputLayoutName, inputLayoutEmail;


    private String API = "https://stepsecret.xyz";

    public static TinyDB tinydb;

    private String email;
    private String password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tinydb = new TinyDB(getApplicationContext());

        inputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_name);
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);

        input_Password = (EditText) findViewById(R.id.input_Password);
        inputEmail = (EditText) findViewById(R.id.input_email);

        input_Password.addTextChangedListener(new MyTextWatcher(input_Password));
        inputEmail.addTextChangedListener(new MyTextWatcher(inputEmail));

        findViewById(R.id.btn_SignIN).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        findViewById(R.id.btn_SignUP).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(i);
                finish();
            }
        });


        check_login();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Exit");
        dialog.setIcon(R.mipmap.ic_launcher);
        dialog.setCancelable(true);
        dialog.setMessage("Do you want to exit?");
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                finish();
            }
        });

        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        dialog.show();
    }


    private void login() {
        if (!validatePass()) {
            return;
        }

        if (!validateEmail()) {
            return;
        }

        email = inputEmail.getText().toString();
        password = input_Password.getText().toString();


        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(API).build();
        final SignIN_API signIN_api = restAdapter.create(SignIN_API.class);

        signIN_api.SignIN(email, password, new Callback<SignIN_Model>() {
            @Override
            public void success(SignIN_Model signIN_Model, Response response) {

                String Check_error = signIN_Model.getError();

                if (Check_error.equals("false")) {
                    tinydb.putBoolean("login", true);
                    tinydb.putString("userName", signIN_Model.getName());
                    tinydb.putString("email", signIN_Model.getEmail());
                    tinydb.putString("ApiKey", signIN_Model.getApiKey());
                    tinydb.putString("flow", "RED");
                    tinydb.putBoolean("AUTO", false);
                    tinydb.putBoolean("showPush", false);
                    tinydb.putInt("ColorBG", 16777215);
                    tinydb.putInt("ColorRF", -43231);

                    Toast.makeText(getApplicationContext(), "Sign IN Success.",
                            Toast.LENGTH_LONG).show();
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Sign IN NOT Success.",
                            Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void failure(RetrofitError error) {

                Toast.makeText(getApplicationContext(), "Sign IN NOT Success.",
                        Toast.LENGTH_LONG).show();

            }
        });



    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    // Validating name
    private boolean validatePass() {
        if (input_Password.getText().toString().trim().isEmpty()) {
            inputLayoutName.setError(getString(R.string.err_msg_name));
            requestFocus(input_Password);
            return false;
        } else {
            inputLayoutName.setErrorEnabled(false);
        }

        return true;
    }

    // Validating email
    private boolean validateEmail() {
        String email = inputEmail.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            inputLayoutEmail.setError(getString(R.string.err_msg_email));
            requestFocus(inputEmail);
            return false;
        } else {
            inputLayoutEmail.setErrorEnabled(false);
        }

        return true;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;
        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.input_Password:
                    validatePass();
                    break;
                case R.id.input_email:
                    validateEmail();
                    break;
            }
        }
    }

    public void check_login()
    {
        Boolean login = tinydb.getBoolean("login", false);

        if(login==true)
        {
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
            finish();
        }
    }

}
