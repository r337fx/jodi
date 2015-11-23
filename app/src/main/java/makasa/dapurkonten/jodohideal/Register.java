package makasa.dapurkonten.jodohideal;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    private EditText inputFirstName, inputLastName, inputEmail, inputPhoneNumber, inputFpassword, inputLpassword;
    private CheckBox agreement;
    private RadioGroup rgSex;
    private RadioButton rbGender;
    private Button btnRegister,inputBirthDay;
    private String urlApi ="http://jodi.licious.id/api/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        inputFirstName = (EditText)findViewById(R.id.firstName);
        inputLastName = (EditText)findViewById(R.id.lastName);
        inputEmail = (EditText)findViewById(R.id.email);
        inputPhoneNumber = (EditText)findViewById(R.id.phoneNumber);
        inputFpassword = (EditText)findViewById(R.id.fPassword);
        inputLpassword = (EditText)findViewById(R.id.lPassword);
        agreement = (CheckBox)findViewById(R.id.agreement);
        rgSex = (RadioGroup)findViewById(R.id.sex);
        btnRegister = (Button)findViewById(R.id.register);
        inputBirthDay = (Button)findViewById(R.id.birthday);

        agreement.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    btnRegister.setEnabled(true);
                } else {
                    btnRegister.setEnabled(false);
                }
            }
        });


    }


    private void registerUser(final String firstName, final String lastName, final String email,
                              final String phoneNumber, final String firstPassword,final String birthDay, final String gender){


        StringRequest requestDaftar = new StringRequest(Request.Method.POST, urlApi,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {


                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    //ambil nilai dari JSON respon API
                                    String  jodiStatus = jsonResponse.getString("status");

                                    if(jodiStatus.equals("success")) {
                                        Toast.makeText(Register.this,jodiStatus,Toast.LENGTH_LONG).show();
                                    }
                                    else{
                                        String jodiMessage = jsonResponse.getString("message");
                                        Toast.makeText(Register.this,jodiMessage,Toast.LENGTH_LONG).show();
                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(Register.this,volleyError.toString(),Toast.LENGTH_LONG).show();
                    }
                }
        ){
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                //parameter, nilai
                params.put("jodiFName", firstName);
                params.put("jodiLName", lastName);
                params.put("jodiEmail", email);
                params.put("jodiPhone", phoneNumber);
                params.put("jodiPassword", firstPassword);
                params.put("jodiDOB", birthDay);
                params.put("jodiGender", gender);
                params.put("jodiRegister","");

                return params;
            }
        };

        //add ke queue request
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(requestDaftar);
    }


    public void daftar(View view){
        //dapatkan value terakhir pada saat button di klik
        String firstName = inputFirstName.getText().toString().trim();
        String lastName = inputLastName.getText().toString().trim();
        String email = inputEmail.getText().toString().trim();
        String phoneNumber = inputPhoneNumber.getText().toString().trim();
        String firstPassword= inputFpassword.getText().toString();
        String lastPassword = inputLpassword.getText().toString();
        String birthDay = inputBirthDay.getText().toString().trim();

        int selectedID = rgSex.getCheckedRadioButtonId();
        rbGender = (RadioButton)findViewById(selectedID);
        String gender = rbGender.getText().toString().trim();

        //cek untuk pastikan user mengisi seluruh form
        if (!firstName.isEmpty() && !lastName.isEmpty() && !email.isEmpty() && !phoneNumber.isEmpty()
                && !firstPassword.isEmpty() && !lastPassword.isEmpty() && !birthDay.equals("Date of Birth") ){
            if (firstPassword.equals(lastPassword)){

                registerUser(firstName, lastName, email, phoneNumber, firstPassword, birthDay, gender);
            }
            else {
                AlertDialog infoPass = new AlertDialog.Builder(Register.this).create();
                infoPass.setTitle("Jodoh Ideal");
                infoPass.setMessage("Maaf password yang Anda masukan tidak cocok. Silahkan cek kembali.");
                infoPass.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                infoPass.show();
            }
        }
        else {
            AlertDialog info = new AlertDialog.Builder(Register.this).create();
            info.setTitle("Jodoh Ideal");
            info.setMessage("Silahkan isi seluruh form yang tersedia sebelum Anda melanjutkan pendaftaran");
            info.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            info.show();
        }
    }
    public void dob (View view){
        DialogFragment dob = new datepicker();
        dob.show(getFragmentManager(), "Date Picker");
    }
}
