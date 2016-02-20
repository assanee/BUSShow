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
import xyz.stepsecret.busshow.API.SignUP_API;
import xyz.stepsecret.busshow.Model.SignIN_Model;
import xyz.stepsecret.busshow.Model.SignUP_Model;
import xyz.stepsecret.busshow.TinyDB.TinyDB;


public class SignupActivity extends AppCompatActivity {


    private EditText input_name,input_Password, inputEmail;
    private TextInputLayout inputLayoutName, inputLayoutEmail;
    private Button btnEnter;

    private String API = "https://stepsecret.xyz";

    public static TinyDB tinydb;

    private String name;
    private String email;
    private String password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        tinydb = new TinyDB(getApplicationContext());

        inputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_name);
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);

        input_name = (EditText) findViewById(R.id.input_name);
        input_Password = (EditText) findViewById(R.id.input_Password);
        inputEmail = (EditText) findViewById(R.id.input_email);

        input_Password.addTextChangedListener(new MyTextWatcher(input_Password));
        input_name.addTextChangedListener(new MyTextWatcher(input_name));
        inputEmail.addTextChangedListener(new MyTextWatcher(inputEmail));

        findViewById(R.id.btn_SignUP).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup();
            }
        });

        findViewById(R.id.btn_SignIN).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
                finish();
            }
        });



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


    private void signup() {
        if (!validateName()) {
            return;
        }
        if (!validatePass()) {
                    return;
        }
        if (!validateEmail()) {
            return;
        }

        name = input_name.getText().toString();
        email = inputEmail.getText().toString();
        password = input_Password.getText().toString();


        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(API).build();
        final SignUP_API signUP_API = restAdapter.create(SignUP_API.class);

        signUP_API.SignUP(name, email, password, "user", new Callback<SignUP_Model>() {
            @Override
            public void success(SignUP_Model signUP_Model, Response response) {

                String Check_error = signUP_Model.getError();

                if (Check_error.equals("false")) {

                    Toast.makeText(getApplicationContext(), "Sign UP Success.",
                            Toast.LENGTH_LONG).show();
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Sign UP NOT Success.",
                            Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void failure(RetrofitError error) {

                Toast.makeText(getApplicationContext(), "Sign UP NOT Success.",
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
    private boolean validateName() {
        if (input_name.getText().toString().trim().isEmpty()) {
            inputLayoutName.setError(getString(R.string.err_msg_name));
            requestFocus(input_name);
            return false;
        } else {
            inputLayoutName.setErrorEnabled(false);
        }

        return true;
    }
 private boolean validatePass() {
        if (input_Password.getText().toString().trim().isEmpty()) {
            inputLayoutName.setError(getString(R.string.err_msg_pass));
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
                case R.id.input_name:
                    validateName();
                    break;
                case R.id.input_Password:
                    validatePass();
                    break;
                case R.id.input_email:
                    validateEmail();
                    break;
            }
        }
    }


}
