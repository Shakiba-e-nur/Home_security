package system.faisalshakiba.com.home_security;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;

public class Splash_activity extends AppCompatActivity {

    ProgressBar mprogressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_activity);

        mprogressbar = (ProgressBar)findViewById(R.id.progressBar);
        ObjectAnimator anim1 = ObjectAnimator.ofInt(mprogressbar, "progress",0,100);
        anim1.setDuration(4000);
        anim1.setInterpolator(new DecelerateInterpolator());
        anim1.start();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(Splash_activity.this, MainActivity.class));
                finish();
            }
        },3000);
    }
}
