package leo.me.la.simpleman;

import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.util.Random;

import leo.me.la.simpleman.retrofit.APIService;
import leo.me.la.simpleman.retrofit.Result;
import leo.me.la.simpleman.retrofit.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.MotionEvent.ACTION_DOWN;
import static java.lang.Math.abs;

public class ShortcutService extends Service {
    /**
     * The ratio between the vertical position of shortcut button
     * and the height of device's screen
     */
    private final float VERTICAL_POSITION_RATIO = 0.078125f;
    /**
     * The image view that shows the gif image
     */
    ImageView imageView;
    /**
     * The button that is used to load another gif
     */
    FloatingActionButton reload;
    /**
     * The ViewGroup which contains the {@link ShortcutService#gifView}
     * so that the {@link ShortcutService#gifView} can be animated
     */
    private FrameLayout gifContainer;
    /**
     * The view that shows gif and reload button, etc
     */
    private View gifView;
    /**
     * The height of the indicator inside {@link ShortcutService#gifView}
     */
    private int indicatorHeight;
    /**
     * Rotate the {@link ShortcutService#reload} button when the app is requesting for new gif
     */
    private Animation rotateAnimation;
    /**
     * Used to add, remove and position {@link ShortcutService#shortcutView},
     * {@link ShortcutService#gifContainer} and {@link ShortcutService#dimView}
     */
    private WindowManager windowManager;
    /**
     * The floating button
     */
    private View shortcutView;
    /**
     * The diameter of shortcut button
     */
    private int shortcutDiameter;
    /**
     * Used to update the position of {@link ShortcutService#shortcutView}
     * when the {@link ShortcutService#gifView} is shown or hidden
     */
    private Handler handler = new Handler();
    /**
     * The position which the shortcut button is located when clicked.
     * toX is equals to {@link ShortcutService#widthPixels},
     * and toY is equals to
     * {@link ShortcutService#heightPixels} * {@link ShortcutService#VERTICAL_POSITION_RATIO}
     */
    private int toX, toY;
    /**
     * The coordinates where the shortcut is located before being clicked
     */
    private int restoreX, restoreY;
    /**
     * The view that simulate the dim behind the {@link ShortcutService#gifView}
     * and the {@link ShortcutService#shortcutView}
     */
    private View dimView;
    /**
     * Params of {@link ShortcutService#shortcutView} and {@link ShortcutService#gifContainer}
     */
    private WindowManager.LayoutParams shortcutParams, gifContainerParams;
    /**
     * Restore the position of {@link ShortcutService#shortcutView} after
     * {@link ShortcutService#gifView} is hidden
     */
    private Runnable restoreShortcutPosition = new Runnable() {
        @Override
        public void run() {
            final int dX = restoreX - shortcutParams.x;
            final int dY = restoreY - shortcutParams.y;
            int baseYChange, baseXChange;
            int basePixelChange = 50;

            if (dX == 0 && dY == 0) {
                return;
            } else if (dY == 0) {
                baseYChange = 0;
                baseXChange = basePixelChange * dX / abs(dX);
            } else if (dX == 0) {
                baseXChange = 0;
                baseYChange = basePixelChange * dY / abs(dY);
            } else {
                baseXChange = (abs(dX) > abs(dY))
                        ? basePixelChange * dX / abs(dY)
                        : basePixelChange * dX / abs(dX);
                baseYChange = (abs(dY) > abs(dX))
                        ? basePixelChange * dY / abs(dX)
                        : basePixelChange * dY / abs(dY);
            }
            shortcutParams.x += baseXChange;
            shortcutParams.y += baseYChange;
            if ((shortcutParams.x > restoreX && dX > 0) ||
                    (shortcutParams.x < restoreX && dX < 0))
                shortcutParams.x = restoreX;
            if ((dY > 0 && shortcutParams.y > restoreY) ||
                    (dY < 0 && shortcutParams.y < restoreY))
                shortcutParams.y = restoreY;
            getWindowManager().updateViewLayout(shortcutView, shortcutParams);
            handler.postDelayed(this, 1L);
        }
    };
    /**
     * The width and height of screen in pixel
     */
    private int widthPixels, heightPixels;
    /**
     * Glide request to load Gif
     */
    private RequestBuilder<GifDrawable> request;
    /**
     * Move the {@link ShortcutService#shortcutView}
     * to {@link ShortcutService#toX} and {@link ShortcutService#toY}
     * before {@link ShortcutService#gifView} is shown
     */
    private Runnable updateShortcutPosition = new Runnable() {
        @Override
        public void run() {
            final int dX = toX - shortcutParams.x;
            final int dY = toY - shortcutParams.y;
            int baseYChange, baseXChange;
            int basePixelChange = 50;

            if (dX == 0 && dY == 0) {
                createGifView();
                initGifView();
                initGlideRequest();
                requestImage();
                return;
            } else if (dY == 0) {
                baseYChange = 0;
                baseXChange = basePixelChange * dX / abs(dX);
            } else if (dX == 0) {
                baseXChange = 0;
                baseYChange = basePixelChange * dY / abs(dY);
            } else {
                baseXChange = (abs(dX) > abs(dY))
                        ? basePixelChange * dX / abs(dY)
                        : basePixelChange * dX / abs(dX);
                baseYChange = (abs(dY) > abs(dX))
                        ? basePixelChange * dY / abs(dX)
                        : basePixelChange * dY / abs(dY);
            }
            shortcutParams.x += baseXChange;
            shortcutParams.y += baseYChange;
            if (shortcutParams.x > toX)
                shortcutParams.x = toX;
            if ((dY > 0 && shortcutParams.y > toY) ||
                    (dY < 0 && shortcutParams.y < toY))
                shortcutParams.y = toY;
            getWindowManager().updateViewLayout(shortcutView, shortcutParams);
            handler.postDelayed(this, 1L);
        }
    };
    /**
     * Touch listener of {@link ShortcutService#shortcutView}
     */
    View.OnTouchListener shortcutTouchListener = new View.OnTouchListener() {
        private static final int MAX_CLICK_DURATION = 200;
        private long startClickTime;
        private int initialX;
        private int initialY;
        private float initialTouchX;
        private float initialTouchY;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case ACTION_DOWN:
                    startClickTime = System.currentTimeMillis();
                    //remember the initial position.
                    initialX = shortcutParams.x;
                    initialY = shortcutParams.y;
                    shortcutView.findViewById(R.id.shortcut).setAlpha(0.85f);
                    //get the touch location
                    initialTouchX = event.getRawX();
                    initialTouchY = event.getRawY();

                    return true;
                case MotionEvent.ACTION_UP:
                    //As we implemented on touch listener with ACTION_MOVE,
                    //we have to check if the previous action was ACTION_DOWN
                    //to identify if the user clicked the dimView or not.
                    shortcutView.findViewById(R.id.shortcut).setAlpha(0.6f);
                    if (System.currentTimeMillis() - startClickTime < MAX_CLICK_DURATION) {
                        if (removeGifView()) {
                            handler.post(restoreShortcutPosition);
                            handler.removeCallbacks(updateShortcutPosition);
                        } else {
                            handler.post(updateShortcutPosition);
                            handler.removeCallbacks(restoreShortcutPosition);
                        }
                    } else {
                        if (removeGifView()) {
                            shortcutParams.x = toX;
                            shortcutParams.y = toY;
                            getWindowManager().updateViewLayout(shortcutView, shortcutParams);
                        } else {
                            shortcutParams.x = (shortcutParams.x > widthPixels / 2) ? widthPixels : 0;
                            getWindowManager().updateViewLayout(shortcutView, shortcutParams);
                            restoreX = shortcutParams.x;
                            restoreY = shortcutParams.y;
                        }
                    }
                    return true;
                case MotionEvent.ACTION_MOVE:
                    if (gifView != null && System.currentTimeMillis() - startClickTime > MAX_CLICK_DURATION)
                        removeGifView();
                    //Calculate the X and Y coordinates of the dimView.
                    shortcutParams.x = initialX + (int) (event.getRawX() - initialTouchX);
                    shortcutParams.y = initialY + (int) (event.getRawY() - initialTouchY);

                    //Update the layout with new X & Y coordinate
                    windowManager.updateViewLayout(shortcutView, shortcutParams);
                    return true;
            }
            return false;
        }
    };

    public ShortcutService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        createDimBackground();
        createShortcutView();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        resetSize();
        if (gifView != null) {
            shortcutParams.y = (int) (heightPixels * VERTICAL_POSITION_RATIO);
            gifContainerParams.width = getGifViewWidth();
            gifContainerParams.height = getGifViewHeight();
            gifContainerParams.x = widthPixels;
            gifContainerParams.y = shortcutParams.y + shortcutDiameter;
            windowManager.updateViewLayout(gifContainer, gifContainerParams);
        }
        toX = (toX > widthPixels / 2) ? widthPixels : 0;
        shortcutParams.x = (shortcutParams.x > widthPixels / 2) ? widthPixels : 0;
        getWindowManager().updateViewLayout(shortcutView, shortcutParams);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(updateShortcutPosition);
        handler.removeCallbacks(restoreShortcutPosition);
        if (shortcutView != null) {
            shortcutView.setOnTouchListener(null);
            getWindowManager().removeView(shortcutView);
        }
        removeGifView();
        if (dimView != null) {
            getWindowManager().removeView(dimView);
        }
    }

    private void createDimBackground() {
        dimView = LayoutInflater.from(this).inflate(R.layout.dim_background, null);
        dimView.setVisibility(View.GONE);
        int type = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                ? WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
                : WindowManager.LayoutParams.TYPE_PHONE;
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                type,
                WindowManager.LayoutParams.FLAG_IGNORE_CHEEK_PRESSES,
                PixelFormat.TRANSLUCENT);
        getWindowManager().addView(dimView, params);
        dimView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeGifView();
                handler.post(restoreShortcutPosition);
                handler.removeCallbacks(updateShortcutPosition);
            }
        });
        dimView.findViewById(R.id.dim).setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                    removeGifView();
                    handler.post(restoreShortcutPosition);
                    return true;
                }
                return false;
            }
        });
    }

    private void createShortcutView() {
        shortcutView = LayoutInflater.from(this).inflate(R.layout.shortcut_floating_button, null);
        int type = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                ? WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
                : WindowManager.LayoutParams.TYPE_PHONE;
        shortcutParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                type,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
        );

        shortcutParams.gravity = Gravity.TOP | Gravity.START;
        resetSize();
        shortcutParams.x = widthPixels;
        shortcutParams.y = (int) (heightPixels * VERTICAL_POSITION_RATIO);
        restoreX = widthPixels;
        restoreY = (int) (heightPixels * VERTICAL_POSITION_RATIO);
        getWindowManager().addView(shortcutView, shortcutParams);

        shortcutView.findViewById(R.id.shortcut).setOnTouchListener(shortcutTouchListener);
    }

    private void createGifView() {
        gifContainer = new FrameLayout(this);
        gifContainer.setFocusableInTouchMode(true);
        gifContainer.setFocusable(true);
        gifContainer.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        gifView = LayoutInflater.from(this).inflate(R.layout.boobs_view, null);
        resetSize();
        int width = getGifViewWidth();
        int height = getGifViewHeight();
        int type = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                ? WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
                : WindowManager.LayoutParams.TYPE_PHONE;
        gifContainerParams = new WindowManager.LayoutParams(
                width,
                height,
                type,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
        );

        gifContainerParams.gravity = Gravity.TOP | Gravity.START;
        gifContainerParams.x = widthPixels;
        gifContainerParams.y = toY + shortcutDiameter;
        getWindowManager().addView(gifContainer, gifContainerParams);
        gifContainer.addView(gifView);
        ScaleAnimation animation = new ScaleAnimation(
                0f, 1f, 0f, 1f,
                Animation.ABSOLUTE, width,
                Animation.ABSOLUTE, 0);
        animation.setDuration(150);
        gifView.findViewById(R.id.root).startAnimation(animation);
        dimView.setVisibility(View.VISIBLE);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0f, 1f);
        alphaAnimation.setDuration(150);
        dimView.findViewById(R.id.dim).startAnimation(alphaAnimation);
    }

    private int getGifViewWidth() {
        return (widthPixels < heightPixels - shortcutParams.y - shortcutDiameter)
                ? widthPixels * 8 / 10
                : (heightPixels - shortcutParams.y - shortcutDiameter) * 8 / 10 - indicatorHeight;
    }

    private int getGifViewHeight() {
        return (widthPixels < heightPixels - shortcutParams.y - shortcutDiameter)
                ? widthPixels * 8 / 10 + indicatorHeight
                : (heightPixels - shortcutParams.y - shortcutDiameter) * 8 / 10;
    }

    private void initGifView() {
        if (gifView != null) {
            imageView = gifView.findViewById(R.id.img);
            reload = gifView.findViewById(R.id.tap);
            reload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    requestImage();
                }
            });
            rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate);
        }
    }

    private boolean removeGifView() {
        //hide dim view
        AlphaAnimation alphaAnimation = new AlphaAnimation(1f, 0f);
        alphaAnimation.setDuration(150);
        dimView.findViewById(R.id.dim).startAnimation(alphaAnimation);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                dimView.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        if (gifContainer != null) {
            if (gifView != null) {
                //remove gif view
                gifContainer.removeView(gifView);
                gifView = null;
            }
            //remove container view
            getWindowManager().removeView(gifContainer);
            gifContainer = null;
            return true;
        }
        return false;
    }

    private WindowManager getWindowManager() {
        if (windowManager == null)
            windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        return windowManager;
    }

    private void resetSize() {
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);
        widthPixels = displayMetrics.widthPixels;
        toX = widthPixels;
        heightPixels = displayMetrics.heightPixels;
        toY = (int) (heightPixels * VERTICAL_POSITION_RATIO);
        indicatorHeight = getResources().getDimensionPixelSize(R.dimen.indicator_height);
        shortcutDiameter = getResources().getDimensionPixelSize(R.dimen.shortcut_size);
    }

    private void initGlideRequest() {
        if (request == null) {
            RequestOptions options = new RequestOptions();
            options.diskCacheStrategy(DiskCacheStrategy.NONE);
            options.skipMemoryCache(true);
            request = Glide.with(ShortcutService.this).asGif().apply(options)
                    .listener(new RequestListener<GifDrawable>() {

                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                    Target<GifDrawable> target, boolean isFirstResource) {
                            Toast.makeText(ShortcutService.this, R.string.fail_load_image, Toast.LENGTH_SHORT).show();
                            removeGifView();
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GifDrawable resource, Object model,
                                                       Target<GifDrawable> target, DataSource dataSource, boolean isFirstResource) {
                            reload.clearAnimation();
                            reload.setEnabled(true);
                            if (gifView != null)
                                gifView.findViewById(R.id.search).setVisibility(View.GONE);
                            return false;
                        }
                    });
        }
    }

    private void requestImage() {
        gifView.findViewById(R.id.search).setVisibility(View.VISIBLE);
        imageView.setVisibility(View.GONE);
        reload.startAnimation(rotateAnimation);
        reload.setEnabled(false);
        APIService apiService = RetrofitClient.getClient().create(APIService.class);
        apiService.getImage(getRandomId(), "2a1b3bd2aed440d2b7b96e8aef9320b2")
                .enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {
                        if (response.code() == 200) {
                            String url = response.body().getData().getImages().getOriginal().getUrl();
                            Log.e("url", url);
                            imageView.setVisibility(View.VISIBLE);
                            request.load(url).into(imageView);
                        }
                    }

                    @Override
                    public void onFailure(Call<Result> call, Throwable t) {
                        t.printStackTrace();
                        removeGifView();
                        Toast.makeText(ShortcutService.this, R.string.error_connection, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private String getRandomId() {
        String[] ids = getResources().getStringArray(R.array.ids);
        Random rand = new Random();
        int index = rand.nextInt(ids.length);
        return ids[index];
    }
}
