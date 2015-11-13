package makasa.dapurkonten.jodohideal;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

public class Register extends AppCompatActivity {

    private TextView tos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tos = (TextView)findViewById(R.id.tos);
        tos.setMovementMethod(new ScrollingMovementMethod());
    }

    public void profile(View view){
        Intent i = new Intent().setClass(this, EditProfile.class);
        startActivity(i);
    }

}
