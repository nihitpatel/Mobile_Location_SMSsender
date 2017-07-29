package com.example.nihit.location;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.CellInfo;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;
    private Button mlocationbtn;
    private TextView mcellid;

    private String telNumber;
    private String msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mlocationbtn = (Button) findViewById(R.id.loationField);
        mcellid = (TextView) findViewById(R.id.cellidField);

        mlocationbtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                GsmCellLocation location = null;
                final TelephonyManager telephony = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                if (telephony.getPhoneType() == TelephonyManager.PHONE_TYPE_GSM) {
                    location = (GsmCellLocation) telephony.getCellLocation();
                    telNumber = telephony.getLine1Number();
                    List<CellInfo> info = telephony.getAllCellInfo();
                    if (location != null) {
                        mcellid.setText("LAC: " + location.getLac() + " CID: " + location.getCid() + "\nMobile No.: " + telNumber);
                        msg = "LAC: " + location.getLac() + "\n" + "CID: " + location.getCid() + "\n" + "Mobile No.: " + telNumber;

                        SmsManager smsManager = SmsManager.getDefault();

                        smsManager.sendTextMessage("9662913184", null, msg, null, null);

                        Toast.makeText(MainActivity.this, "SMS SENT", Toast.LENGTH_SHORT).show();

                        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        Location lo = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                        double lati =  lo.getLatitude();
                        double longi = lo.getLongitude();
                        double alti = lo.getAltitude();

                        mcellid.append("\nlatitude :  " + lati + "\nlongitude : " + longi + "\nAltitude : " + alti);



                /*        Intent smsIntent = new Intent(Intent.ACTION_VIEW);

                        smsIntent.setData(Uri.parse("smsto:"));
                        smsIntent.setType("vnd.android-dir/mms-sms");
                        smsIntent.putExtra("address"  , new String ("9662913184"));
                        smsIntent.putExtra("sms_body"  , msg);

                        try {
                            startActivity(smsIntent);
                            finish();

                        } catch (android.content.ActivityNotFoundException ex) {
                            Toast.makeText(MainActivity.this,
                                    "SMS faild, please try again later.", Toast.LENGTH_SHORT).show();
                        }
                 */
                    }
                }




            }
        });
    }






}
