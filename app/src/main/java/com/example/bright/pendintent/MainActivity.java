package com.example.bright.pendintent;

import android.Manifest;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import android.net.Uri;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;

import static android.net.wifi.WifiManager.*;


public class MainActivity extends AppCompatActivity {

    WifiManager wifiManager;
    List scannedResult, apName, mAddr, missingrSsi, rSsi, brssi, timeStamp, xlist, ylist,macAddress;
    int x, y, k, lvl,size;
    String xcoordinate, ycoordinate, outPut;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView2);
        wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        scannedResult = new ArrayList();
        apName = new ArrayList();
        mAddr = new ArrayList();
        missingrSsi = new ArrayList();
        rSsi = new ArrayList();
        brssi = new ArrayList();
        timeStamp = new ArrayList();
        xlist = new ArrayList();
        ylist = new ArrayList();
        macAddress = new ArrayList();
        outPut = "";
        int k = 0;
    }

    static Map<String, List> apNamebssid = new HashMap<String, List>();
    static Map<String, List<Integer>> bSsidrssi = new HashMap<String, List<Integer>>();
    static StringBuilder data = new StringBuilder();

    public void doProcess(View view) {
        if (scannedResult.size() > 0) {
            scannedResult.clear();
        }
        data.setLength(0);
        mAddr.clear();
        apName.clear();
        boolean b = ((WifiManager) getSystemService(WIFI_SERVICE)).startScan();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    1);

            if (wifiManager.isWifiEnabled()) {
                Toast.makeText(getApplicationContext(), "on", Toast.LENGTH_SHORT).show();
                new AsyncTask<Void, String, String>() {
                    @Override
                    protected String doInBackground(Void... voids) {
                        List<ScanResult> scanResultList = wifiManager.getScanResults();
                        for (int i = 0; i < scanResultList.size(); i++) {

                            scannedResult.add(scanResultList.get(i).SSID + "\n");
                            scannedResult.add(scanResultList.get(i).BSSID + "\n");
                            scannedResult.add(scanResultList.get(i).level + "\n");

                        }
                        return scannedResult.toString();
                    }

                    @Override
                    protected void onPostExecute(String s) {
                        textView.setText(s);
                    }
                }.execute();
            } else {
                wifiManager.setWifiEnabled(false);
                new AsyncTask<Void, String, String>() {
                    @Override
                    protected String doInBackground(Void... voids) {
                        List<ScanResult> scanResultList = wifiManager.getScanResults();
                        for (int i = 0; i < scanResultList.size(); i++) {
                            scannedResult.add(scanResultList.get(i).SSID + "\n");
                            scannedResult.add(scanResultList.get(i).BSSID + "\n");
                            scannedResult.add(scanResultList.get(i).level + "\n");
                            System.out.print("I am here ");
                        }
                        return scannedResult.toString();
                    }

                    @Override
                    protected void onPostExecute(String s) {
                        textView.setText(s);
                    }
                }.execute();
            }
        } else {
            if (wifiManager.isWifiEnabled()) {
                Toast.makeText(getApplicationContext(), "Scan Is Done!!", Toast.LENGTH_SHORT).show();
                new AsyncTask<Void, String, String>() {
                    @Override
                    protected String doInBackground(Void... voids) {

                        List<ScanResult> scanResultList = wifiManager.getScanResults();
                        EditText xcoor = (EditText) findViewById(R.id.xcoor);
                        EditText ycoor = (EditText) findViewById(R.id.ycoor);
                        String xcoordinate = xcoor.getText().toString();
                        String ycoordinate = ycoor.getText().toString();
                        //System.out.print("I am here" + "\n");
                        boolean g = xcoordinate.matches("") || ycoordinate.matches("");
                        //System.out.print(g);
                        //scannedResult.add(xcoordinate);
                        //scannedResult.add(ycoordinate);
                        if (xcoordinate.matches("") || ycoordinate.matches("")) {
                            Toast.makeText(getApplicationContext(), "you missed a coordinate", Toast.LENGTH_LONG).show();
                            //TextView myTextView = (TextView) findViewById(R.id.textView2);
                            //textView.setText("");
                            scannedResult.clear();
                            //System.out.print("It is empty " + "\n");
                        }
                        if (xcoordinate.matches("") == false & ycoordinate.matches("") == false) {
                            boolean h = xcoordinate.matches("") || ycoordinate.matches("");
                            //System.out.print(h + "\n");
                            int x = Integer.parseInt(xcoor.getText().toString());
                            int y = Integer.parseInt(ycoor.getText().toString());
                            //System.out.print("My xcoor is: ");
                            //System.out.print(x + "\n");
                            scannedResult.add(x);
                            xlist.add(x);
                            ylist.add(y);

                            int m = -150;
                            missingrSsi = Collections.nCopies(k, m);
                            rSsi.addAll(missingrSsi);
                            System.out.print(missingrSsi);
                            for (int i = 0; i < scanResultList.size(); i++) {
                                //String z = scanResultList.get(2).BSSID;
                                scannedResult.add(scanResultList.get(i).SSID + "\n");
                                scannedResult.add(scanResultList.get(i).BSSID + "\n");
                                scannedResult.add(scanResultList.get(i).level + "\n");
                                mAddr.add(scanResultList.get(i).BSSID);
                                timeStamp.add(scanResultList.get(i).timestamp);

                                //Check if we had the ssid before:
                                //Also we add the bsside as a new key to bSsidrssi and a new list as a value for key and the value will be rssi
                                if (scanResultList.get(i).frequency < 2600) {
                                    if (apNamebssid.containsKey(scanResultList.get(i).SSID)) {
                                        if (apNamebssid.get(scanResultList.get(i).SSID).contains(scanResultList.get(i).BSSID)) {
                                            bSsidrssi.get(scanResultList.get(i).BSSID).add(scanResultList.get(i).level);
                                            //apName.add(scanResultList.get(i).SSID);
                                            System.out.print("\n1st If statement\n");
                                        }
                                        //If it is a newly discovered bssid,//If yes, then we only need to add the new bssid name to list corresponding to key(ssid) as well as add as new key
                                        if (apNamebssid.get(scanResultList.get(i).SSID).contains(scanResultList.get(i).BSSID) == false) {
                                            apNamebssid.get(scanResultList.get(i).SSID).add(scanResultList.get(i).BSSID);
                                            bSsidrssi.put(scanResultList.get(i).BSSID, new ArrayList<Integer>());
                                            apName.add(scanResultList.get(i).SSID);
                                            bSsidrssi.get(scanResultList.get(i).BSSID).addAll(Collections.nCopies(k, m));
                                            bSsidrssi.get(scanResultList.get(i).BSSID).add(scanResultList.get(i).level);
                                            System.out.print("\n2nd If statement\n");
                                        }
                                    }
                                    //If the ssid is not in the keys
                                    //If no then we just add the ssid as a new key and initialize a new list as value for key, and the value will be bssid
                                    if (apNamebssid.containsKey(scanResultList.get(i).SSID) == false) {
                                        if (k > 0) {
                                            apNamebssid.put(scanResultList.get(i).SSID, new ArrayList<String>());
                                            apNamebssid.get(scanResultList.get(i).SSID).add(scanResultList.get(i).BSSID);
                                            bSsidrssi.put(scanResultList.get(i).BSSID, new ArrayList<Integer>());
                                            bSsidrssi.get(scanResultList.get(i).BSSID).addAll(Collections.nCopies(k, m));
                                            bSsidrssi.get(scanResultList.get(i).BSSID).add(scanResultList.get(i).level);
                                            apName.add(scanResultList.get(i).SSID);
                                            System.out.print("\n3rd If statement\n");
                                        }
                                        if (k == 0) {
                                            apNamebssid.put(scanResultList.get(i).SSID, new ArrayList<String>());
                                            apNamebssid.get(scanResultList.get(i).SSID).add(scanResultList.get(i).BSSID);
                                            bSsidrssi.put(scanResultList.get(i).BSSID, new ArrayList<Integer>());
                                            bSsidrssi.get(scanResultList.get(i).BSSID).add(scanResultList.get(i).level);
                                            apName.add(scanResultList.get(i).SSID);
                                            System.out.print("\n4th If statement\n");
                                        }


                                    }
                                }

                            }
                            //List brssi = bSsidrssi.keySet();

                            List brssi = new ArrayList();
                            //System.out.print(brssi + "I am here345    ");
                            brssi.addAll(bSsidrssi.keySet());
                            System.out.print(brssi + "I am here345    ");
                            //System.out.print("\n"+bSsidrssi+"\n");
                            //TRYING COMPARING THE LIST
                            //System.out.print(brssi.get(0) + "  nigga \n");
                            List sAme = new ArrayList();
                            for (int i = 0; i < brssi.size(); i++) {
                                boolean srL = mAddr.contains(brssi.get(i)) == false;
                                boolean uk = false;
                                System.out.print("\n" + srL + "," + uk + "\n");
                                //System.out.print(brssi.get(i));
                                //The problem here is it keeps invoking function of if statement even when false
                                if ((mAddr.contains(brssi.get(i)) == false)) {
                                    bSsidrssi.get(brssi.get(i)).add(-150);
                                    sAme.add(brssi.get(i));
                                    System.out.print("\n" + bSsidrssi + "\n");
                                    //return scannedResult.toString();
                                    //System.out.print("\n I am hereeeeee \n");
                                }


                            }
                            //lvl = bSsidrssi.get(mAddr.get(0)).get(k);
                            //System.out.print("\n\n\nqwertty   "+lvl+"qwert  \n\n\n");
                            k += 1;
                            //return scannedResult.toString();

                        }
                        System.out.print("\n" + k + "\n");
                        System.out.print("\n" + bSsidrssi + "\n");
                        outPut+="1";
                        System.out.print(outPut+"\n");
                        return scannedResult.toString();


                    }

                    @Override
                    protected void onPostExecute(String s) {
                        textView.setText(s);
                        //System.out.print(scannedResult);
                    }
                }.execute();
            } else {
                wifiManager.setWifiEnabled(true);
                new AsyncTask<Void, String, String>() {
                    @Override
                    protected String doInBackground(Void... voids) {
                        List<ScanResult> scanResultList = wifiManager.getScanResults();
                        for (int i = 0; i < scanResultList.size(); i++) {
                            scannedResult.add(scanResultList.get(i).SSID + "\n");
                            scannedResult.add(scanResultList.get(i).BSSID + "\n");
                            scannedResult.add(scanResultList.get(i).level + "\n");
                        }
                        return scannedResult.toString();
                    }

                    @Override
                    protected void onPostExecute(String s) {
                        textView.setText(s);
                    }
                }.execute();
            }
        }
    }
    //public void

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == 1
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (wifiManager.isWifiEnabled()) {
                textView.setText("");
                new AsyncTask<Void, String, String>() {
                    @Override
                    protected String doInBackground(Void... voids) {
                        List<ScanResult> scanResultList = wifiManager.getScanResults();
                        for (int i = 0; i < scanResultList.size(); i++) {
                            scannedResult.add(scanResultList.get(i).SSID + "\n");
                        }
                        return scannedResult.toString();
                    }

                    @Override
                    protected void onPostExecute(String s) {
                        textView.setText(s);
                    }
                }.execute();
            } else {
                wifiManager.setWifiEnabled(true);
                new AsyncTask<Void, String, String>() {
                    @Override
                    protected String doInBackground(Void... voids) {
                        List<ScanResult> scanResultList = wifiManager.getScanResults();
                        for (int i = 0; i < scanResultList.size(); i++) {
                            scannedResult.add(scanResultList.get(i).SSID + "\n");
                        }
                        return scannedResult.toString();
                    }

                    @Override
                    protected void onPostExecute(String s) {
                        textView.setText(s);
                    }
                }.execute();
            }
        }

    }

    public void dataProcess (View view) {
        //generate data
        System.out.print("\n\n\n"+k+"   This is itt328475628374578\n\n\n");
        System.out.print("I am here");
        //StringBuilder data = new StringBuilder();
        mAddr.clear();
        mAddr.addAll(bSsidrssi.keySet());
        apName.clear();
        apName.addAll(apNamebssid.keySet());

        System.out.print("\n\n\n"+mAddr+"\n\n\n");


        data.append("Time Stamp, x,y");
        //appending all

        for (int c = 0; c< mAddr.size(); c++ ){
            for (int h = 0; h < apName.size(); h++){
                if (apNamebssid.get(apName.get(h)).contains(mAddr.get(c))){
                    data.append("," + apName.get(h));
                }
            }
        }

        data.append("\n,,");
        //apppending all bssid
        for (int v = 0; v < mAddr.size(); v++) {
            data.append("," + mAddr.get(v));
        }
        data.append("\n");
        //lvl = bSsidrssi.get(mAddr.get(0)).get(2);
        macAddress.addAll(bSsidrssi.get(mAddr.get(0)));


        System.out.print("\n"+macAddress+"\n");
        System.out.print("\n"+xlist+"\n");
        System.out.print("\n"+k+"\n");
        macAddress.clear();
        //System.out.print("\n\n\n qwerty"+lvl+"\n\n\n querty");
        //apend all RSSI with respect to bssid
        for (int b = 0; b < k; b++) {
            data.append(timeStamp.get(b).toString()+"," + xlist.get(b).toString() + "," + ylist.get(b).toString());
            for (int j = 0; j < mAddr.size(); j++) {
                lvl = bSsidrssi.get(mAddr.get(j)).get(b);
                data.append("," + String.valueOf(lvl));
            }
            data.append("\n");
        }
        //System.out.print("we did it!! \n");
        System.out.print(data);
        mAddr.clear();
    }


    public void export(View view) {
        try {
            //saving the file into device
            FileOutputStream out = openFileOutput("data.csv", Context.MODE_PRIVATE);
            out.write(data.toString().getBytes());
            out.close();

            //exporting
            Context context = getApplicationContext();
            File filelocation = new File(getFilesDir(), "data.csv");
            Uri path = FileProvider.getUriForFile(context, "com.example.bright.pendintent.fileprovider", filelocation);
            Intent fileIntent = new Intent(Intent.ACTION_SEND);
            fileIntent.setType("text/csv");
            fileIntent.putExtra(Intent.EXTRA_SUBJECT, "Data");
            fileIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            fileIntent.putExtra(Intent.EXTRA_STREAM, path);
            startActivity(Intent.createChooser(fileIntent, "Send mail"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}











