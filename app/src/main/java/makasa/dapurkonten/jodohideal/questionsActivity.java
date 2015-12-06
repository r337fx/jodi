package makasa.dapurkonten.jodohideal;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

import makasa.dapurkonten.jodohideal.app.SQLiteController;

public class questionsActivity extends AppCompatActivity{
    private SQLiteController db;
    TextView pertanyaans;
    int idpertanyaan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        idpertanyaan = 1;
        setContentView(R.layout.activity_question);
        pertanyaans = (TextView)findViewById(R.id.questiont);
        db = new SQLiteController(getApplicationContext());
        HashMap<String,String> tenQuestion = db.getIdQuestion(idpertanyaan);
        String id=tenQuestion.get("id"),
                question_id=tenQuestion.get("question_id"),
                question=tenQuestion.get("question"),
                answer_ops1=tenQuestion.get("answer_ops1"),
                answer_ops2=tenQuestion.get("answer_ops2");
        pertanyaans.setText(question);
    }

    private View.OnClickListener btnListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.goto_previous:
                    if(idpertanyaan>0) {
                        idpertanyaan--;
                        HashMap<String,String> tenQuestion = db.getIdQuestion(idpertanyaan);
                        String id=tenQuestion.get("id"),
                                question_id=tenQuestion.get("question_id"),
                                question=tenQuestion.get("question"),
                                answer_ops1=tenQuestion.get("answer_ops1"),
                                answer_ops2=tenQuestion.get("answer_ops2");
                        pertanyaans.setText(question);
                    }
                    break;
                case R.id.goto_next:
                   if(idpertanyaan<10) {
                        idpertanyaan++;
                        HashMap<String,String> tenQuestion = db.getIdQuestion(idpertanyaan);
                        String id=tenQuestion.get("id"),
                                question_id=tenQuestion.get("question_id"),
                                question=tenQuestion.get("question"),
                                answer_ops1=tenQuestion.get("answer_ops1"),
                                answer_ops2=tenQuestion.get("answer_ops2");
                        pertanyaans.setText(question);
                    }
                    break;
            }
        }
    };

}
