package makasa.dapurkonten.jodohideal.app;

/**
 * Created by pr1de on 26/11/15.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

public class SQLiteController extends SQLiteOpenHelper {

    private static final String INI = SQLiteController.class.getSimpleName();

    // Seluruh variabel statis di definisikan
    // versi db yang dipake
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "jodi";

    // tabel untuk login dan value akan disimpan pada tabel ini
    private static final String TABLE_USER = "user";

    // nama kolom
    private static final String COL_ID = "id";
    private static final String COL_FIRST_NAME = "first_name";
    private static final String COL_LAST_NAME = "last_name";
    private static final String COL_EMAIL = "email";
    private static final String COL_GENDER = "gender";
    private static final String COL_AGE = "age";
    private static final String COL_RACE = "race";
    private static final String COL_RELIGION = "religion";
    private static final String COL_HEIGHT = "height";
    private static final String COL_LOCATION = "location";
    private static final String COL_HOROSCOPE = "horoscope";
    private static final String COL_JOB = "job";
    private static final String COL_USER_DETAIL = "user_detail";

    public SQLiteController(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + COL_ID + " INTEGER PRIMARY KEY,"
                + COL_FIRST_NAME + " TEXT,"
                + COL_LAST_NAME + " TEXT,"
                + COL_EMAIL + " TEXT UNIQUE,"
                + COL_GENDER + " TEXT,"
                + COL_AGE + " TEXT,"
                + COL_RACE + " TEXT,"
                + COL_RELIGION + " TEXT,"
                + COL_HEIGHT+ " TEXT,"
                + COL_LOCATION + " TEXT,"
                + COL_HOROSCOPE + " TEXT,"
                + COL_JOB + " TEXT,"
                + COL_USER_DETAIL + " TEXT" +
                ")";
        db.execSQL(CREATE_LOGIN_TABLE);

        Log.d(INI, "Database tables created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop table yg lama
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

        // buat kembali tabel
        onCreate(db);
    }

    /**
     * Simpen detail user ke tabel
     * */

    public void addUser(String id, String fname, String lname, String email,
                        String gender, String age, String race, String religion, String height,
                        String location, String horoscope, String job, String userDetail) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // key, value
        values.put(COL_ID, id);
        values.put(COL_FIRST_NAME, fname);
        values.put(COL_LAST_NAME, lname);
        values.put(COL_EMAIL, email);
        values.put(COL_GENDER, gender);
        values.put(COL_AGE, age);
        values.put(COL_RACE, race);
        values.put(COL_RELIGION, religion);
        values.put(COL_HEIGHT, height);
        values.put(COL_LOCATION, location);
        values.put(COL_HOROSCOPE, horoscope);
        values.put(COL_JOB, job);
        values.put(COL_USER_DETAIL, userDetail);

        // Insert
        long uid = db.insert(TABLE_USER, null, values);
        db.close();

        Log.d(INI, "New user inserted into sqlite: " + uid);
    }

    /**
     * Getting user data from database
     * */

    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM " + TABLE_USER;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // arahkan kursor si sqlite ke baris pertama table
        // ibarat fetch array mysql
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            user.put("id", cursor.getString(1));
            user.put("first_name", cursor.getString(2));
            user.put("last_name", cursor.getString(3));
            user.put("email", cursor.getString(4));
            user.put("gender", cursor.getString(5));
            user.put("age", cursor.getString(6));
            user.put("race", cursor.getString(7));
            user.put("religion", cursor.getString(8));
            user.put("height", cursor.getString(9));
            user.put("location", cursor.getString(10));
            user.put("horoscope", cursor.getString(11));
            user.put("job", cursor.getString(12));
           // user.put("user_detail", cursor.getString(13));

        }
        cursor.close();
        db.close();
        // return user
        Log.d(INI, "Fetching user from Sqlite: " + user.toString());

        return user;
    }

    /**
     * buat ulang db
     * apus semua table and dan buat ulang table lagi
     * */

    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_USER, null, null);
        db.close();

        Log.d(INI, "Deleted all user info from sqlite");
    }

}