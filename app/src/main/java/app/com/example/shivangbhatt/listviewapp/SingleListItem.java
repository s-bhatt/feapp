package app.com.example.shivangbhatt.listviewapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.text.ClipboardManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

/**
 * Created by shivang.bhatt on 22/01/15.
 */
public class SingleListItem extends Activity {

    GPSTracker gps;
    double latitude;
    double longitude;
    String url;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.single_list_item_view);

        TextView txtProduct = (TextView) findViewById(R.id.product_label);

        Intent i = getIntent();
        String product = i.getStringExtra("product");
        // displaying selected product name
        txtProduct.setText(product);

        gps = new GPSTracker(SingleListItem.this);
        if(gps.canGetLocation()){

            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
            url = "http://maps.google.com/maps?saddr="+latitude+","+longitude+"&daddr="+12.964831+","+77.597466;

            // \n is for new line
            Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
            ClipboardManager _clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            _clipboard.setText(url);

        }else{
            gps.showSettingsAlert();
        }

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //second working code with two locations
                String url = "http://maps.google.com/maps?saddr="+12.937034+","+77.777624+"&daddr="+12.964831+","+77.597466;
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(url));
                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                startActivity(intent);



                // String uri = String.format(Locale.ENGLISH, "geo:%f,%f", 10.10, 23.23);
//                String label = "Cinnamon & Toast";
                //first working code with single location
               /* String uriBegin = "geo:12.964831,77.597466";
                String query = "12.964831, 77.597466";
                String encodedQuery = Uri.encode(query);
                String uriString = uriBegin + "?q=" + encodedQuery;
                Uri uri = Uri.parse( uriString );
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, uri );
                startActivity( intent ); */

               /* Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                Context context = getApplicationContext();
                context.startActivity(intent); */
            }
        });

        Button locbutton = (Button) findViewById(R.id.smsbutton);

        locbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage("+919470992979", null, url, null, null);
                    Toast.makeText(getApplicationContext(), "message sent", Toast.LENGTH_SHORT).show();

            }
        });

        Button selmbutton = (Button) findViewById(R.id.selmbutton);
        selmbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //code to open contact in whatsapp
                Uri mUri = Uri.parse("smsto:+919470992979");
                Intent mIntent = new Intent(Intent.ACTION_SENDTO, mUri);
                //mIntent.setType("text/plain");
                mIntent.putExtra(mIntent.EXTRA_TEXT, url);
                mIntent.setPackage("com.whatsapp");



                // mIntent.putExtra("hello", "world");
                // mIntent.putExtra("chat",true);
                //mIntent.putExtra(Intent.EXTRA_PHONE_NUMBER, "+919470992979");
                startActivity(mIntent);

            }
        });

        Button selcbutton  = (Button) findViewById(R.id.selcbutton);
        selcbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // code to forward message in whatsapp

                    Intent waIntent = new Intent(Intent.ACTION_SEND);
                    waIntent.setType("text/plain");
                    String text = "http://maps.google.com/maps?saddr="+latitude+","+longitude+"&daddr="+12.964831+","+77.597466;
                    waIntent.setPackage("com.whatsapp");

                    if (waIntent != null) {
                        waIntent.putExtra(Intent.EXTRA_TEXT, text);//
                        waIntent.putExtra(Intent.EXTRA_PHONE_NUMBER, "+919470992979");
                        // startActivity(Intent.createChooser(waIntent, "Share with"));
                        startActivity(waIntent);
                    }


            }
        });



    }
}
