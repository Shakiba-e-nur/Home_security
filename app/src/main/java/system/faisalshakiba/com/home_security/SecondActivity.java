package system.faisalshakiba.com.home_security;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.speech.RecognizerIntent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Locale;

public class SecondActivity extends AppCompatActivity implements View.OnTouchListener{
    int status=0;
    Thread thread=new Thread(new Runnable() {
        @Override
        public void run() {
            if(status==0)
                txtSpeechInput.setText("Warning!! Gass Leaked!!");
            if(status==1)
                txtSpeechInput.setText("Congratulations!!Gass Leakage Resolved!!");
            if(status==2)
                txtSpeechInput.setText("Wanring!! Door Open!!");
            if(status==3)
                txtSpeechInput.setText("Success!! Door Close!!");
        }
        ;
    });
    HttpRequestAsynTask_connect opt;
    private TextView txtSpeechInput;
    private ImageButton btnSpeak;
    private final int REQ_CODE_SPEECH_INPUT = 100;

    //check any error occurred or not
    String msg="Error";
    boolean error=false;
    //check server sending response back or not
    boolean isDataBack=false;
    String rep="";
    //Initializing Socket type variables
    Socket socket=null,clientsocket=null;
    ServerSocket serverSocket;
    //reading data from server
    public InputStreamReader inputStreamReader;
    private Button b1,b2,b3,b4,b5,b6,currentBtn;

    //Make Strings by getting server data
    public BufferedReader bufferedReader;
    String _ip;
    String _port;
    int flag=0;
    boolean isButtonPressed=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);


        b1=(Button) findViewById(R.id.button1);
        b2=(Button) findViewById(R.id.button2);
        b3=(Button) findViewById(R.id.button4);
        b4=(Button) findViewById(R.id.button5);
//        b5=(Button) findViewById(R.id.button5);
//        b6=(Button) findViewById(R.id.button6);

        b1.setOnTouchListener(this);
        b2.setOnTouchListener(this);
        b3.setOnTouchListener(this);
        b4.setOnTouchListener(this);
        //b5.setOnTouchListener(this);
      //  b6.setOnTouchListener(this);
        Intent intent=getIntent();
        _ip= intent.getStringExtra("IP");
        _port="80";
//        int permissionCheck = ContextCompat.checkSelfPermission(this,
//                Manifest.permission.INTERNET);
//        int permissionCheck2 = ContextCompat.checkSelfPermission(this,
//                Manifest.permission.VIBRATE);
//        int permissionCheck3 = ContextCompat.checkSelfPermission(this,
//                Manifest.permission.ACCESS_WIFI_STATE);
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_WIFI_STATE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_WIFI_STATE)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            }
        }else {

            // No explanation needed, we can request the permission.

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_WIFI_STATE},
                    123);

            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
            // app-defined int constant. The callback method gets the
            // result of the request.
        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.VIBRATE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.VIBRATE)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            }
        }else {

            // No explanation needed, we can request the permission.

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.VIBRATE},
                    123);

            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
            // app-defined int constant. The callback method gets the
            // result of the request.
        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.INTERNET)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            }
        }else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.INTERNET},
                        123);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }

new Thread(new Runnable() {
    @Override
    public void run() {
        try {
            Thread.sleep(1000);
            flag=0;
            new HttpRequestAsynTask_connect(
                    getBaseContext(),"null",_ip,_port
            ).execute();
            Thread.sleep(2000);
            flag=1;
            new HttpRequestAsynTask_connect(
                    getBaseContext(),"L",_ip,_port
            ).execute();
        }catch (Exception e)
        {
            Toast.makeText(getBaseContext(),"Error: "+e,Toast.LENGTH_LONG).show();
        }
    }
}).start();

        Toast.makeText(getBaseContext(),"IP: "+_ip,Toast.LENGTH_LONG).show();
        txtSpeechInput = (TextView) findViewById(R.id.voictext);
        btnSpeak = (ImageButton) findViewById(R.id.imageButton);

        //CONNECTION
       // flag=1;

//        new HttpRequestAsynTask_connect(
//                getBaseContext(),null,_ip,_port
//        ).execute();
       // Toast.makeText(getBaseContext(),"Data: "+null,Toast.LENGTH_LONG).show();
     //   error_print();
        // hide the action bar
//        getActionBar().hide();

        btnSpeak.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                askSpeechInput();
            }
        });
//        b1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//        }
//    });
    }
    // Showing google speech input dialog

    private void askSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Hi speak something");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {

        }
    }

    // Receiving speech input

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    txtSpeechInput.setText(result.get(0));
                    String parameterValue=result.get(0);
                    try
                    {
                        flag=2;
                            if(result.get(0).equals("connect"))
                            {
                               //opt.sendRequest_openport(_ip,_port);
                                flag=1;
                            }
                            else if(result.get(0).equals("light on"))
                            {
                                parameterValue="U";
                            }
                            else if(result.get(0).equals("light off"))
                            {
                                parameterValue="L";
                            }
                            else if(result.get(0).equals("fan on"))
                            {
                                parameterValue="M";
                            }
                            else if(result.get(0).equals("fan off"))
                            {
                                parameterValue="N";
                            }
                            new HttpRequestAsynTask_connect(
                                    getBaseContext(),parameterValue,_ip,_port
                            ).execute();
                            Toast.makeText(getBaseContext(),"Data: "+parameterValue,Toast.LENGTH_LONG).show();

                           // error_print();


                    }catch (Exception e)
                    {
                        Toast.makeText(getBaseContext(),"Error: "+e,Toast.LENGTH_LONG).show();
                    }
                }
                break;
            }

        }
    }

    private void error_print() {
        if(rep.equals("ERROR"))
        {
//            warn.setBackgroundResource(R.drawable.warn1);
//            warn.getBackground().setAlpha(255);
            Toast.makeText(getBaseContext(), "NETWORK ERROR!", Toast.LENGTH_LONG).show();
        }
        else if(rep.equals("ok"))
        {
//            warn.setBackgroundResource(R.drawable.warning_blue);
//            warn.getBackground().setAlpha(255);
            Toast.makeText(getBaseContext(), "Command Sennt", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        String data="";
        if(view.getId()==b1.getId())
        {
            currentBtn=b1;
            data="U";
        }
        else if(view.getId()==b2.getId())
        {
            currentBtn=b2;
            data="L";
        }
        else if(view.getId()==b3.getId())
        {
            currentBtn=b3;
            data="M";
        }
        else if(view.getId()==b4.getId())
        {
            currentBtn=b4;
            data="N";
        }
        else if(view.getId()==b5.getId())
        {
            currentBtn=b5;
        }
        else if(view.getId()==b6.getId())
        {
            currentBtn=b6;
        }
        flag=2;
        switch (motionEvent.getAction())
        {
            case MotionEvent.ACTION_UP:
                currentBtn.setAlpha(1.0f);
                try
            {
               // opt.sendRequest_wrData(data,_ip);
                new HttpRequestAsynTask_connect(
                        getBaseContext(),data,_ip,_port
                ).execute();
                Toast.makeText(getBaseContext(),"Data: "+data,Toast.LENGTH_LONG).show();
            }catch (Exception e)
            {
                Toast.makeText(getBaseContext(),"Error: "+e,Toast.LENGTH_LONG).show();
            }
                break;
            case MotionEvent.ACTION_DOWN:
                currentBtn.setAlpha(0.5f);

                break;
            case MotionEvent.ACTION_MOVE:break;

        }
        return false;
    }

    //for connection
    private class HttpRequestAsynTask_connect extends AsyncTask<Void, Void, Void>
    {
        private String ip_address,requestReplay,port_name;
        private Context context;
        private AlertDialog alartdialog;
        private String parameter;
        private String parametervalue;

        public HttpRequestAsynTask_connect(Context context, String parametervalue,
                                           String ip_address, String port_name) {
            // TODO Auto-generated constructor stub
            this.context=context;
            this.ip_address=ip_address;
            this.parametervalue=parametervalue;
            this.port_name=port_name;

        }
        //Request to connect with server
        public String sendRequest_openport(String ip_address,String port_number)
        {
            String serverResponse="ERROR";

            try{
                socket = new Socket(ip_address,Integer.parseInt(port_number));
                flag=1;

                error=false;
                serverResponse="ok";
                Log.d("RCV","B4 RUN METHOD RUN");
                new Thread(new Listen()).start();
             //   thread.start();
                return serverResponse;
            }catch(Exception e)
            {
                error=true;

                return serverResponse;
            }




        }
        //Run the thread for receive data when server response

        class Listen implements Runnable{

            @Override
            public void run() {

                String info="Not Initialized";
                try{

                    StringBuilder responseString = new StringBuilder();


                }catch(Exception e){
                    Log.d("RCV","Could not listen Port");
                }
                Log.d("RCV","INSIDE RUN");
                while(true)
                {

                    try{
                        StringBuilder responseString = new StringBuilder();
                        BufferedReader bufferedReader = null;

                        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        String str;
                        if((str = bufferedReader.readLine()) != null){
                            while ((str = bufferedReader.readLine()) != null) {
                                responseString.append(str);
                                int len=str.length();
                                char ch=str.charAt(len-1);
                                String s=""+ch;

                                //if server send data v then android will give vibration and beep
                                if(s.equals("h"))
                                {
                                    Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

                                    // Vibrate for 1000 milliseconds
                                    vb.vibrate(1000);
                                    ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
                                            for(int i=0;i<10;i++){
                                                toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200);
                                                try {
                                                    Thread.sleep(300);
                                                } catch (InterruptedException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 1000);
                                            try {
                                                Thread.sleep(2000);
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }
                                            toneG.release();
                                        }
                                    }).start();
                                    toneG.release();

                                    txtSpeechInput.setText("Warning!! Gass Leaked !!!");
                              status=0;

                                }
                                else if(s.equals("g")){
                                    Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

                                    // Vibrate for 1000 milliseconds
                                    vb.vibrate(1000);
                                    ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
                                            for(int i=0;i<2;i++){
                                                toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200);
                                                try {
                                                    Thread.sleep(300);
                                                } catch (InterruptedException e) {
                                                    e.printStackTrace();
                                                }
                                            }

                                            toneG.release();
                                        }
                                    }).start();
                                    toneG.release();

                                    txtSpeechInput.setText("Congratulations!! Gass Leaked Resolved!!!");
                                    status=1;

                                }
                                else   if(s.equals("i"))
                                {
                                    Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

                                    // Vibrate for 1000 milliseconds
                                    vb.vibrate(1000);
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
                                            for(int i=0;i<5;i++){
                                                toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200);
                                                try {
                                                    Thread.sleep(500);
                                                } catch (InterruptedException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 1000);
                                            try {
                                                Thread.sleep(2000);
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }
                                            toneG.release();
                                        }
                                    }).start();
                                    txtSpeechInput.setText("Warning!! Door Opened !!!");
                                    status=2;
                                }else   if(s.equals("j"))
                                {
                                    Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

                                    // Vibrate for 1000 milliseconds
                                    vb.vibrate(1000);
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
                                            for(int i=0;i<3;i++){
                                                toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200);
                                                try {
                                                    Thread.sleep(1000);
                                                } catch (InterruptedException e) {
                                                    e.printStackTrace();
                                                }
                                            }

                                            toneG.release();
                                        }
                                    }).start();

                                    txtSpeechInput.setText("Success!! Door Closed !!!");
                                    status=3;
                                }
                                Log.d("response","Data: "+s);


                            }




                        }else{
                            Log.d("Response", "Server not responding");
                        }
                    }catch(Exception e)
                    {
                        Log.d("RCV", "Error: "+e );
                    }
                }



            }

        }
        public String sendRequest_close() throws UnknownHostException, IOException
        {

            String serverResponse="ERROR";


            try{
                socket.close();
                flag=0;
                error=false;
                Toast.makeText(getBaseContext(), "CONNECTED", Toast.LENGTH_LONG).show();
                serverResponse="ok";
            }catch(Exception e)
            {

                error=true;
            }



            return serverResponse;

        }
        public String sendRequest_wrData(String parameterValue,String ip_address) throws UnknownHostException, IOException
        {
            Log.d("RCV2",msg);
            String serverResponse="ERROR";
            try{
                DataOutputStream DOS = new DataOutputStream(socket.getOutputStream());
                DOS.writeUTF(parameterValue);


                flag=2;
                error=false;
                serverResponse="ok";

            }catch(Exception e)
            {

                error=true;
                serverResponse="ERROR";
                msg="Error: "+e;
            }


            return serverResponse;

        }


        @Override
        protected Void doInBackground(Void... voids) {
            // TODO Auto-generated method stub
            try {
                if(flag==0)
                    requestReplay=sendRequest_close();
                else if(flag==1){
                    requestReplay=sendRequest_openport(ip_address,port_name);
                    rep=requestReplay;

                }
                else if(flag==2)
                    requestReplay=sendRequest_wrData(parametervalue,ip_address);

            } catch (IOException e) {
                // TODO Auto-generated catch block

                e.printStackTrace();
            }
            return null;
        }
        protected void onPostExecution(Void avoid)
        {
            //alartdialog.setMessage(requestReplay);
        }
        protected void onPreExecution() {
            //alartdialog.setMessage("Sending data to server... Pleasewait");

        }

    }

    @Override
    public void onBackPressed() {
        flag=0;

        new HttpRequestAsynTask_connect(
                getBaseContext(),null,_ip,_port
        ).execute();
        Toast.makeText(getBaseContext(),"Data: "+null,Toast.LENGTH_LONG).show();
        finish();
    }

    boolean presstwich=false;
}
