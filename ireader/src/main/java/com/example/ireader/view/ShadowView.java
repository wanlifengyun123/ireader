package com.example.ireader.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.example.ireader.R;
import com.example.ireader.utils.ViewUtil;

/**
 * Created by yajun on 2016/12/12.
 *
 */
public class ShadowView extends View {

    private int resColor;
    private int resShadowColor;
    private String resText;

    private Paint paint;
    private Paint paintText;
    private int mWidth, mHeight;
    private RectF rectF;

    private boolean isSelected;
    private float ex = 0f,ey = 0f;

    private ShadowClickListener shadowClickListener;

    public void setShadowClickListener(ShadowClickListener shadowClickListener) {
        this.shadowClickListener = shadowClickListener;
    }

    public ShadowView(Context context) {
        super(context);
        initView(context);
    }

    public ShadowView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context){
        resColor = context.getResources().getColor(R.color.white);
        resShadowColor = context.getResources().getColor(R.color.loginPressBgColor);
        resText = context.getResources().getString(R.string.addBook);

        setLayerType( LAYER_TYPE_SOFTWARE , null);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(resColor);

        paintText = new Paint();
        paintText.setAntiAlias(true);
        paintText.setColor(context.getResources().getColor(R.color.loginBgColor));
        paintText.setTextSize(ViewUtil.sp2px(context,14));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        rectF = new RectF(0, 0, mWidth, mHeight - 8);
        if(isSelected){
            // 设定阴影(柔边, X 轴位移, Y 轴位移, 阴影颜色)
            paint.setShadowLayer(1, 0, 8, resShadowColor);
        }else {
            paint.setShadowLayer(0,0,0,resShadowColor);
        }
        canvas.drawRoundRect(rectF, 150, 150, paint);//第二个参数是x半径，第三个参数是y半径

        Paint.FontMetricsInt fontMetrics = paintText.getFontMetricsInt();
        int baseline = (int) ((rectF.bottom + rectF.top - fontMetrics.bottom - fontMetrics.top) / 2);
        // 下面这行是实现水平居中，drawText对应改为传入targetRect.centerX()
        paintText.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(resText, rectF.centerX(), baseline, paintText);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                ex = event.getX();
                ey = event.getY();
                isSelected = true;
                break;
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if(Math.abs(ex - event.getX()) <= 0 && Math.abs(ey - event.getY()) <= 0){
                   if(shadowClickListener != null){
                       shadowClickListener.onClick(this);
                   }
                }
                isSelected = false;
                break;
        }
        invalidate();
        return true;
    }

    public interface ShadowClickListener {
        public void onClick(View v);
    }
}
