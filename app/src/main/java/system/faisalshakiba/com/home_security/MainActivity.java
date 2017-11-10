package system.faisalshakiba.com.home_security;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity{

    public Button but1;
    private EditText ip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ip=(EditText) findViewById(R.id.server_ip);
        but1=(Button)findViewById(R.id.done);
        but1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,SecondActivity.class);
                intent.putExtra("IP",ip.getText().toString());
                startActivity(intent);
                finish();
            }
        });

    }


}
