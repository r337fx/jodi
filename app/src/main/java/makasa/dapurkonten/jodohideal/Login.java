package makasa.dapurkonten.jodohideal;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class Login extends AppCompatActivity {
    private static final String REGISTER_URL = "http://jodi.licious.id/api/";

    private TextView info;
    private EditText editTextUsername, editTextPassword;
    private static String INI = Login.class.getSimpleName();

    //facebook punya
    private LoginButton loginButton;
    private CallbackManager callbackManager;

    sessionmanager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //manggil sesion manager
        session = new sessionmanager(getApplicationContext());
        //session.checkLogin();

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_login);
        info = (TextView)findViewById(R.id.message);
        loginButton = (LoginButton)findViewById(R.id.login_button);
        editTextUsername = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.password);

        // login dengan facebook
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Profile profile = Profile.getCurrentProfile();
                info.setText(
                        "User ID: "
                                + profile.getId() +
                                "Nama:" + profile.getName()
                );
            }

            @Override
            public void onCancel() {
                info.setText("Login attempt canceled.");
            }

            @Override
            public void onError(FacebookException e) {
                info.setText("Login attempt failed.");
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    // link menuju register
    public void register (View view){
        Intent i = new Intent(Login.this, Register.class);
        startActivity(i);
    }

    //link menuju main activity
    public void main (View view){
        Intent i = new Intent(Login.this, MainActivity.class);
        startActivity(i);
    }

    //proses login
    private void loginUser(){
        final String email = editTextUsername.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d(INI, response.toString());

                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            //ambil nilai dari JSON respon API
                            String  jodiStatus = jsonResponse.getString("status");

                                    if(jodiStatus.equals("success")) {
                                        JSONObject dataUser = jsonResponse.getJSONObject("data");
                                        String jodiUserID = dataUser.getString("user_id"),
                                                jodiEmail = dataUser.getString("email"),
                                                jodiFirstName = dataUser.getString("first_name"),
                                                jodiLastName = dataUser.getString("last_name"),
                                                jodiGender = dataUser.getString("gender"),
                                                jodiBirthday = dataUser.getString("birth_date");
                                        session.buatSesiLogin(jodiUserID, jodiEmail, jodiFirstName, jodiLastName, jodiGender, jodiBirthday);
                                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(i);
                                        finish();
                                    }
                                    else{
                                        String jodiMessage = jsonResponse.getString("message");
                                        info.setText(jodiMessage);
                                    }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Login.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            //proses kirim parameter ke
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("jodiEmail",email);
                params.put("jodiPassword",password);
                params.put("jodiLogin","");
                return params;
            }

        };

RequestQueue requestQueue = Volley.newRequestQueue(this);
requestQueue.add(stringRequest);
        }

public void login (View view){
        loginUser();
        }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Do you want to Exit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user select "No", just cancel this dialog and continue with app
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
