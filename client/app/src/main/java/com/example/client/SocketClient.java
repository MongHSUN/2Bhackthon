package com.example.client;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class SocketClient extends Activity {

    double lat=25.0195226,lng=121.5416295;
    String device_name="default";
    public void OpenMap(){
        Intent intent = new Intent(this,MapsActivity.class);
        intent.putExtra("send_lat",String.valueOf(lat));
        intent.putExtra("send_lng",String.valueOf(lng));
        intent.putExtra("send_name",device_name);
        startActivity(intent);
    }
    private void set_information(){
        TextView TextView =(TextView)findViewById(R.id.TextView01);
        Calendar cal = new GregorianCalendar();
        int flag=cal.get(Calendar.AM_PM);
        if(flag==Calendar.AM)
            cal.set( Calendar.AM_PM, Calendar.PM );
        else
            cal.set( Calendar.AM_PM, Calendar.AM );

        String info = "\n\n\n"+cal.getTime()+"\n\ndevice name : "+ device_name+"\n\nlatitude : " + lat +
                "\n\nlongitude : " + lng +"     "+"\n\nProvider : GPS " ;
        TextView.setText(info);
    }
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client);

        Intent it=getIntent();
        device_name = it.getStringExtra("send_name");


        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        TextView TextView01 =(TextView)findViewById(R.id.TextView01);
        InetAddress serverIp;
        try {
            serverIp = InetAddress.getByName("140.112.30.32");
            int serverPort=4000;
            Socket clientSocket=new Socket(serverIp,serverPort);

            System.out.println("connecting...");
            BufferedReader  br=new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String input=br.readLine();
            System.out.println(input);
            br.close();
            clientSocket.close();
            System.out.println("close");

            String[] tokens = input.split(" ");
            lat=Double.parseDouble(tokens[0]);
            lng=Double.parseDouble(tokens[1]);
            System.out.println(lat);
            System.out.println(lng);
            set_information();

        } catch (IOException e) {
            TextView01.setText( "Connect error.");
        }
        Button map_btn = (Button)this.findViewById(R.id.button);
        map_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                OpenMap();
            }
        });

        final ImageButton refresh_btn = (ImageButton)this.findViewById(R.id.imageButton1);
        refresh_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "refreshed", Toast.LENGTH_SHORT).show();
                onCreate(savedInstanceState);
            }
        });

        ImageButton delete_btn = (ImageButton)this.findViewById(R.id.imageButton2);
        delete_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
            }
        });
    }
 }