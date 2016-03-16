package com.example.dkuz.myapplication;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.bluetooth.*;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.Set;

public class MainActivity extends AppCompatActivity  implements OnClickListener{
    private final static int REQUEST_ENABLE_BT = 1;
    BluetoothAdapter bluetooth;
    Button btnconnect,btnview,btnscreen;
    TextView mlogout;
    BluetoothAdapter mBluetoothAdapter;
    ListView mArrayAdapter;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnconnect  = (Button) findViewById(R.id.btnconnect);
        btnconnect.setOnClickListener(this);

        btnview  = (Button) findViewById(R.id.btnView);
        btnview.setOnClickListener(this);

        btnscreen  = (Button) findViewById(R.id.btnScreen);
        btnscreen.setOnClickListener(this);



      //  mArrayAdapter = (ListView) findViewById(R.id.mArrayAdapter);
        mlogout = (TextView) findViewById(R.id.mLogOut);




        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.dkuz.myapplication/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.dkuz.myapplication/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }


    private void connectBLE() {
        bluetooth = BluetoothAdapter.getDefaultAdapter();
        if (bluetooth != null) {
            mlogout.setText("BLE Start"); // С Bluetooth все в порядке.

            if (bluetooth.isEnabled()) {
                // Bluetooth включен. Работаем.
                 mlogout.append("\n BLE is on");
            } else {
                // Bluetooth выключен. Предложим пользователю включить его.
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }

        }
    }

    private void viewBLE() {
        String status;
        if(bluetooth.isEnabled()){
            String mydeviceaddress= bluetooth.getAddress();
            String mydevicename= bluetooth.getName();
            status= mydevicename+" : "+ mydeviceaddress;
        }
        else
        {
            status="Bluetooth выключен";
        }
        mlogout.append("\n"+status);
        Toast.makeText(this, status, Toast.LENGTH_LONG).show();
    }

    private final BroadcastReceiver mReceiver=new BroadcastReceiver(){
        public void onReceive(Context context, Intent intent){
            String action= intent.getAction();
// Когда найдено новое устройство
            if(BluetoothDevice.ACTION_FOUND.equals(action)){
// Получаем объект BluetoothDevice из интента
                BluetoothDevice device= intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
//Добавляем имя и адрес в array adapter, чтобы показвать в ListView
                mArrayAdapter.add(device.getName()+"\n"+ device.getAddress());
            }
        }
    };

    private void viewDeviceBT()
    {   String s;
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> pairedDevices= mBluetoothAdapter.getBondedDevices();
// Если список спаренных устройств не пуст
        if(pairedDevices.size()>0){
// проходимся в цикле по этому списку
            for(BluetoothDevice device: pairedDevices){
// Добавляем имена и адреса в mArrayAdapter, чтобы показать
// через ListView
                s=device.getName() + "\n" + device.getAddress();
                mlogout.append("\n"+s);
               // mArrayAdapter.cle
            }
        }

        // Регистрируем BroadcastReceiver
        IntentFilter filter=new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter);// Не забудьте снять регистрацию в onDestroy

    }
    @Override
    public void onClick(View v) {
        Log.d("!!!!!!!", "!!!!!!!!!!!!!!!!!!!!!!");

        switch (v.getId()) {
            case R.id.btnconnect:
                connectBLE();
                break;
            case R.id.btnView:
                viewBLE();
                break;

            case R.id.btnScreen:
                viewDeviceBT();
                break;
        }

        }
/*
        switch (v.getId())
        {
            case R.id.button0: s="0";
                break;
            case R.id.button1: s="1";
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.a150));

                break;
            case R.id.button2: s="2";
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.a150));
                break;
            case R.id.button3: s="3";
                break;
            case R.id.button4: s="4";
                break;
            case R.id.button5: s="5";
                break;
            case R.id.button6: s="6";
                break;
            case R.id.button7: s="7";
                break;
            case R.id.button8:
                s= "";
                bluetooth= BluetoothAdapter.getDefaultAdapter();

                if(bluetooth!=null)
                {
                    // С Bluetooth все в порядке.
                }

                break;



        }
        mtextView.setText(R.string.text1);
     */

}
