package makasa.dapurkonten.jodohideal;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    roundimage round;
    ImageView imageView;
    sessionmanager session;
    TextView coba;


    //variable for selection intent
    private final int PICKER = 1;
    //variable to store the currently selected image
    private int currentPic = 0;
    //adapter for gallery view
    private PicAdapter imgAdapt;
    //gallery object
    private Gallery picGallery;
    //image view for larger display
    private ImageView picView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //slide image
        //get the large image view
        picView = (ImageView) findViewById(R.id.largePicture);

        //get the gallery view
        picGallery = (Gallery) findViewById(R.id.imgGallery);

        //create a new adapter
        imgAdapt = new PicAdapter(this);
        //set the gallery adapter
        picGallery.setAdapter(imgAdapt);

        //set long click listener for each gallery thumbnail item
        picGallery.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            //handle long clicks
            public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {
                //update the currently selected position so that we assign the imported bitmap to correct item
                currentPic = position;
                //take the user to their chosen image selection app (gallery or file manager)
                Intent pickIntent = new Intent();
                pickIntent.setType("image/*");
                pickIntent.setAction(Intent.ACTION_GET_CONTENT);
                //we will handle the returned data in onActivityResult
                startActivityForResult(Intent.createChooser(pickIntent, "Select Picture"), PICKER);
                return true;
            }
        });

        //set the click listener for each item in the thumbnail gallery
        picGallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //handle clicks
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                //set the larger image view to display the chosen bitmap calling method of adapter class
                picView.setImageBitmap(imgAdapt.getPic(position));
            }
        });

        //done image slide

        coba = (TextView)findViewById(R.id.coba);
        session = new sessionmanager(getApplicationContext());
        //session.checkLogin();
        session.checkLoginMain();

        // get user data from session
        HashMap<String, String> user = session.getUserDetails();
        String firstName = user.get(sessionmanager.SES_FIRST_NAME);

        coba.setText(firstName);

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
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    public class PicAdapter extends BaseAdapter {

        //use the default gallery background image
        int defaultItemBackground;

        //gallery context
        private Context galleryContext;

        //array to store bitmaps to display
        private Bitmap[] imageBitmaps;
        //placeholder bitmap for empty spaces in gallery
        Bitmap placeholder;

        //constructor
        public PicAdapter(Context c) {

            //instantiate context
            galleryContext = c;

            //create bitmap array
            imageBitmaps  = new Bitmap[10];
            //decode the placeholder image
            placeholder = BitmapFactory.decodeResource(getResources(), R.drawable.ic_cake);

            //set placeholder as all thumbnail images in the gallery initially
            for(int i=0; i<imageBitmaps.length; i++)
                imageBitmaps[i]=placeholder;

            //get the styling attributes - use default Andorid system resources
            TypedArray styleAttrs = galleryContext.obtainStyledAttributes(R.styleable.PicGallery);
            //get the background resource
            defaultItemBackground = styleAttrs.getResourceId(
                    R.styleable.PicGallery_android_galleryItemBackground, 0);
            //recycle attributes
            styleAttrs.recycle();
        }

        //BaseAdapter methods

        //return number of data items i.e. bitmap images
        public int getCount() {
            return imageBitmaps.length;
        }

        //return item at specified position
        public Object getItem(int position) {
            return position;
        }

        //return item ID at specified position
        public long getItemId(int position) {
            return position;
        }

        //get view specifies layout and display options for each thumbnail in the gallery
        public View getView(int position, View convertView, ViewGroup parent) {

            //create the view
            ImageView imageView = new ImageView(galleryContext);
            //specify the bitmap at this position in the array
            imageView.setImageBitmap(imageBitmaps[position]);
            //set layout options
            imageView.setLayoutParams(new Gallery.LayoutParams(300, 200));
            //scale type within view area
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            //set default gallery item background
            imageView.setBackgroundResource(defaultItemBackground);
            //return the view
            return imageView;
        }

        //custom methods for this app

        //helper method to add a bitmap to the gallery when the user chooses one
        public void addPic(Bitmap newPic)
        {
            //set at currently selected index
            imageBitmaps[currentPic] = newPic;
        }

        //return bitmap at specified position for larger display
        public Bitmap getPic(int posn)
        {
            //return bitmap at posn index
            return imageBitmaps[posn];
        }
    }

    /**
     * Handle returning from gallery or file manager image selection
     * - import the image bitmap
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            //check if we are returning from picture selection
            if (requestCode == PICKER) {

                //the returned picture URI
                Uri pickedUri = data.getData();

                //declare the bitmap
                Bitmap pic = null;
                //declare the path string
                String imgPath = "";

                //retrieve the string using media data
                String[] medData = { MediaStore.Images.Media.DATA };
                //query the data
                Cursor picCursor = managedQuery(pickedUri, medData, null, null, null);
                if(picCursor!=null)
                {
                    //get the path string
                    int index = picCursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    picCursor.moveToFirst();
                    imgPath = picCursor.getString(index);
                }
                else
                    imgPath = pickedUri.getPath();

                //if and else handle both choosing from gallery and from file manager

                //if we have a new URI attempt to decode the image bitmap
                if(pickedUri!=null) {

                    //set the width and height we want to use as maximum display
                    int targetWidth = 600;
                    int targetHeight = 400;

                    //sample the incoming image to save on memory resources

                    //create bitmap options to calculate and use sample size
                    BitmapFactory.Options bmpOptions = new BitmapFactory.Options();

                    //first decode image dimensions only - not the image bitmap itself
                    bmpOptions.inJustDecodeBounds = true;
                    BitmapFactory.decodeFile(imgPath, bmpOptions);

                    //work out what the sample size should be

                    //image width and height before sampling
                    int currHeight = bmpOptions.outHeight;
                    int currWidth = bmpOptions.outWidth;

                    //variable to store new sample size
                    int sampleSize = 1;

                    //calculate the sample size if the existing size is larger than target size
                    if (currHeight>targetHeight || currWidth>targetWidth)
                    {
                        //use either width or height
                        if (currWidth>currHeight)
                            sampleSize = Math.round((float)currHeight/(float)targetHeight);
                        else
                            sampleSize = Math.round((float)currWidth/(float)targetWidth);
                    }
                    //use the new sample size
                    bmpOptions.inSampleSize = sampleSize;

                    //now decode the bitmap using sample options
                    bmpOptions.inJustDecodeBounds = false;

                    //get the file as a bitmap
                    pic = BitmapFactory.decodeFile(imgPath, bmpOptions);

                    //pass bitmap to ImageAdapter to add to array
                    imgAdapt.addPic(pic);
                    //redraw the gallery thumbnails to reflect the new addition
                    picGallery.setAdapter(imgAdapt);

                    //display the newly selected image at larger size
                    picView.setImageBitmap(pic);
                    //scale options
                    picView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                }
            }
        }
        //superclass method
        super.onActivityResult(requestCode, resultCode, data);
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
            // Handle the camera action
        } else if (id == R.id.nav_chat) {

        } else if (id == R.id.nav_setting) {

        } else if (id == R.id.nav_logout) {
            session.logoutUser();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
