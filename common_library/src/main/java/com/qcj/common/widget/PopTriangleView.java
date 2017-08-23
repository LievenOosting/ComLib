package com.qcj.common.widget;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.qcj.common.R;
import com.qcj.common.util.UIUtils;


/**
 * Created by qiuchunjia on 2016/8/29.
 * 用于显示三角形的和轮框的view
 */
public class PopTriangleView extends View {
    private Paint mPaint;
    private int mColor = 0xffffffff;
    private int mRadius = 5;
    private float mTriangleDistance = 20;   //三角形对于左边的距离 px
    private int mTriangleHeight = 15;  //三角形的高度 15dp
    private int mTriangleWidth = 20;  //三角形的宽度  20dp
    private int mWidth = 1000;
    private int mHeight = 950;

    public enum TypeDirection {
        LEFT, RIGHT
    }

    private TypeDirection mCurrentType = TypeDirection.LEFT;

    /**************************/
    private RectF mRectF;
    private Path mTrianglePath;

    public PopTriangleView(Context context) {
        super(context);
    }

    public PopTriangleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData(context, attrs);
    }

    public PopTriangleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(mWidth, mHeight);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        drawRect(canvas);
        drawTriangle(canvas);
    }


    /**
     * 绘制矩形
     */
    private void drawRect(Canvas canvas) {
        if (canvas == null) return;
        mRectF.set(0, mTriangleHeight, mWidth, mHeight);
        canvas.drawRoundRect(mRectF, mRadius, mRadius, mPaint);
    }

    /**
     * 绘制三角形
     */
    private void drawTriangle(Canvas canvas) {
        if (canvas == null) return;
        mTrianglePath.moveTo(mTriangleDistance, mTriangleHeight);
        mTrianglePath.lineTo(mTriangleDistance + mTriangleWidth / 2f, 0);
        mTrianglePath.lineTo(mTriangleDistance + mTriangleWidth, mTriangleHeight);
        mTrianglePath.lineTo(mTriangleDistance, mTriangleHeight);
        canvas.drawPath(mTrianglePath, mPaint);
        mTrianglePath.reset();
    }

    private void initData(Context context, AttributeSet attrs) {
        mTriangleDistance = UIUtils.dip2px(getContext(), mTriangleDistance);
        mTriangleHeight = UIUtils.dip2px(getContext(), mTriangleHeight);
        mTriangleWidth = UIUtils.dip2px(getContext(), mTriangleWidth);
        mRadius = UIUtils.dip2px(getContext(), mRadius);
        mRectF = new RectF();
        mTrianglePath = new Path();
        // 获得自定义属性，tab的数量
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.PopTriangleView, 0, 0);
        if (a != null) {
            mColor = a.getColor(R.styleable.PopTriangleView_view_bg,
                    mColor);
            mRadius = (int) a.getDimension(R.styleable.PopTriangleView_view_radius,
                    mRadius);
            mTriangleDistance = (int) a.getDimension(R.styleable.PopTriangleView_view_triangle_distance,
                    mTriangleDistance);
            mTriangleHeight = (int) a.getDimension(R.styleable.PopTriangleView_view_triangle_height,
                    mTriangleHeight);
            mTriangleWidth = (int) a.getDimension(R.styleable.PopTriangleView_view_triangle_width,
                    mTriangleWidth);
            mWidth = (int) a.getDimension(R.styleable.PopTriangleView_view_width,
                    mWidth);
            mHeight = (int) a.getDimension(R.styleable.PopTriangleView_view_height,
                    mHeight);
            int type = a.getInt(R.styleable.PopTriangleView_type_direction, 0);
            if (type == 0) {
                mCurrentType = TypeDirection.LEFT;
            } else if (type == 1) {
                mCurrentType = TypeDirection.RIGHT;
                mTriangleDistance = mWidth - mTriangleDistance;
            }
            a.recycle();
        }
        mPaint = new Paint();
        mPaint.setColor(mColor);
        mPaint.setAntiAlias(true);
    }

    public void setTriangleDistance(float distance) {
        float dx = distance - mTriangleDistance;
        final float originDistance = mTriangleDistance;
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, "xxoo", 0, dx);
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                mTriangleDistance = originDistance + value;
                invalidate();
            }
        });
        objectAnimator.setDuration(1 * 500);
        objectAnimator.start();
    }
}
