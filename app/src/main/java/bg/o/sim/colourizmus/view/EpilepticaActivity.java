package bg.o.sim.colourizmus.view;import android.content.Context;import android.content.Intent;import android.graphics.Color;import android.os.Bundle;import android.os.CountDownTimer;import androidx.appcompat.app.AppCompatActivity;import java.util.Random;import bg.o.sim.colourizmus.R;public class EpilepticaActivity extends AppCompatActivity {public static void start(Context context){Intent starter = new Intent(context, EpilepticaActivity.class);context.startActivity(starter);}@Override protected void onCreate(Bundle savedInstanceState){super.onCreate(savedInstanceState);setContentView(R.layout.activity_epileptica);(findViewById(R.id.epileptica_button)).setOnClickListener(v -> {v.setClickable(false);final Random r = new Random();CountDownTimer cd = new CountDownTimer(7000, 66){@Override public void onTick(long millisUntilFinished){findViewById(R.id.activity_epileptica_base).setBackgroundColor(Color.rgb(r.nextInt(256), r.nextInt(256), r.nextInt(256)));}@Override public void onFinish(){v.setClickable(true);}};cd.start();});}}
//# I R CANSUR ლ༼◕_◕ ლ༽ --!>
