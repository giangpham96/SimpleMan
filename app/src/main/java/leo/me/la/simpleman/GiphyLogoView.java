package leo.me.la.simpleman;

import android.content.Context;
import android.util.AttributeSet;

public class GiphyLogoView extends android.support.v7.widget.AppCompatImageView {
    public GiphyLogoView(Context context) {
        super(context);
    }

    public GiphyLogoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GiphyLogoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int w = getMeasuredWidth();
        setMeasuredDimension(w, w*225/640);
    }
}
