package leo.me.la.simpleman;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class MainActivity extends AppCompatActivity {

    private static final int CODE_DRAW_OVER_OTHER_APP_PERMISSION = 2084;
    InterstitialAd mInterstitialAd;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHandler = new Handler();
        initAds();
        initWindow();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mHandler != null)
            mHandler.removeCallbacks(null);
        findViewById(R.id.giphy).setVisibility(View.VISIBLE);
        findViewById(R.id.btn).setVisibility(View.INVISIBLE);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                initViews();
            }
        }, 5000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler != null)
            mHandler.removeCallbacks(null);
        showInterstitial();
    }

    public boolean shouldInitPermission() {
        //Check if the application has draw over other apps permission or not?
        //This permission is by default available for API<23. But for API > 23
        //you have to ask for the permission in runtime.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {

            //If the draw over permission is not available open the settings screen
            //to grant the permission.
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, CODE_DRAW_OVER_OTHER_APP_PERMISSION);
            return true;
        }
        return false;
    }

    private void initWindow() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        int baseSize = (width < height) ? width : height;
        getWindow().setLayout((int) (baseSize * 0.8), WRAP_CONTENT);
    }

    private void initAds() {
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial));
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("E795102A9C64A2862EF2257F549BB7A0")
                .addTestDevice("8D9BA6C70BBD1A1919720F807A4480AF")
                .build();
        mInterstitialAd.loadAd(adRequest);
    }

    /**
     * Set and initialize the view elements.
     */
    private void initViews() {
        findViewById(R.id.giphy).setVisibility(View.INVISIBLE);
        findViewById(R.id.btn).setVisibility(View.VISIBLE);
        TranslateAnimation fromLeft = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, -1f,
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0
        );
        fromLeft.setDuration(400);
        TranslateAnimation fromRight = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 1f,
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0
        );
        fromRight.setDuration(400);
        View btnAdd = findViewById(R.id.btnAdd);
        btnAdd.startAnimation(fromLeft);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!shouldInitPermission()) {
                    startService(new Intent(MainActivity.this, ShortcutService.class));
                    finish();
                }
            }
        });
        View btnClear = findViewById(R.id.btnClear);
        btnClear.startAnimation(fromRight);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopService(new Intent(MainActivity.this, ShortcutService.class));
                finish();
            }
        });
    }

    private void showInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }
}
