package makasa.dapurkonten.jodohideal;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View.OnClickListener;
import android.text.method.ScrollingMovementMethod;
import android.app.Activity;
import android.app.DialogFragment;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;


public class Register extends AppCompatActivity {
    private TextView tos;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        tos = (TextView)findViewById(R.id.tos);
  //      tos.setMovementMethod(new ScrollingMovementMethod());
    }
        public void dob (View view){
            DialogFragment dob = new datepicker();
            dob.show(getFragmentManager(), "Date Picker");
        }
    public void profile(View view){
        CheckBox terms= (CheckBox) findViewById(R.id.terms);
        TextView error_msg=(TextView) findViewById(R.id.error_msg);
        if(terms.isChecked()) {
            Intent i = new Intent().setClass(this, EditProfile.class);
            startActivity(i);
        }
        else{
            error_msg.setText("you must check agreement");
        }
    }

}
