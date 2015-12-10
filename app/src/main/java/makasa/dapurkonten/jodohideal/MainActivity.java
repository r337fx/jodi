package makasa.dapurkonten.jodohideal;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import makasa.dapurkonten.jodohideal.adapter.ListPartnerAdapter;
import makasa.dapurkonten.jodohideal.app.SQLiteController;
import makasa.dapurkonten.jodohideal.object.Partner;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    roundimage round;
    ImageView imageView;
    sessionmanager session;
    private SQLiteController db;
    TextView txtNama, txtTinggi, txtLokasi,txtHoroskop, txtPekerjaan, txtAgama, txtTentang, txtDrawerNama,txtDrawerEmail;
    private static String INI = MainActivity.class.getSimpleName();
    private List<Partner> partnerList = new ArrayList<Partner>();
    private ListView listView;
    private ListPartnerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new SQLiteController(getApplicationContext());

        session = new sessionmanager(getApplicationContext());
        //session.checkLogin();
        session.checkLoginMain();

        // tarik data user dari sqlite
        HashMap<String, String> profile = db.getUserDetails();
        String id = profile.get("id");
        String gender = profile.get("gender");
        String age = profile.get("age");
        String race = profile.get("race");
        String religion = profile.get("religion");
        String height = profile.get("height");
        String location = profile.get("location");
        String horoscope = profile.get("horoscope");
        String job = profile.get("job");
        String userDetail = profile.get("user_detail");

        // tarik data dari tabel

        // tarik data user dari session
        HashMap<String, String> user = session.getUserDetails();
        String firstName = user.get(sessionmanager.SES_FIRST_NAME);
        String lastname = user.get(sessionmanager.SES_LAST_NAME);
        String email = user.get(sessionmanager.SES_EMAIL);


        //set dalam textview
        txtNama = (TextView)findViewById(R.id.txtProfilNama);
        txtDrawerNama = (TextView)findViewById(R.id.txtDrawerNama);
        txtDrawerEmail = (TextView)findViewById(R.id.txtDrawerEmail);
        txtLokasi = (TextView)findViewById(R.id.txtProfilLokasi);
        txtTentang = (TextView)findViewById(R.id.txtProfilTentang);

        txtNama.setText(firstName + " " + lastname + "," + age);
        txtDrawerNama.setText(firstName);
        txtDrawerEmail.setText(email);
        txtLokasi.setText(location);
        txtTentang.setText(userDetail);

        imageView = (ImageView) findViewById(R.id.imageView);
        //Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.avatar);
        //round = new roundimage(bm);
        //imageView.setImageDrawable(round);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ed = new Intent(getApplicationContext(), EditProfile.class);
                startActivity(ed);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /**
         * coba nampilin data dari sqlite
         *
         * ArrayList<HashMap<String, String>> arrayListPartner = db.getAllPartner();
        if (arrayListPartner.size() > 0) {

            for (int i = 0; i < arrayListPartner.size(); i++) {

                // ambil masing-masing hasmap dari arrayListPartner
                HashMap<String, String> hashMapRecordPartner = arrayListPartner
                        .get(i);

                // JSONObject jsonChildNode = arrayBiodata.getJSONObject(i);
                String partner_id = hashMapRecordPartner.get("partner_id"),
                        partner_fname = hashMapRecordPartner.get("partner_fname"),
                        partner_lname = hashMapRecordPartner.get("partner_lname"),
                        partner_match = hashMapRecordPartner.get("partner_match"),
                        partner_notmatch = hashMapRecordPartner.get("partner_notmatch");
                TextView cocokNama = (TextView)findViewById(R.id.txtCocokNama);
                cocokNama.setText(partner_fname + " " + lastname);

            }
        } **/
    }



    /**@Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }**/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

        }
        else if (id == R.id.nav_profile) {
            Intent prfl = new Intent(this, Profile.class);
            startActivity(prfl);
        }
        else if (id == R.id.nav_pasangan) {
            Intent psg = new Intent(getApplicationContext(), CariPasangan.class);
            startActivity(psg);
        }
        else if (id == R.id.nav_chat){
            Intent cht = new Intent(getApplicationContext(), Chat.class);
            startActivity(cht);
        }
        else if (id == R.id.nav_logout) {
            db.deleteUsers();
            session.logoutUser();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
