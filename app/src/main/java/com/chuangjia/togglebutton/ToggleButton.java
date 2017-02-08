package com.chuangjia.togglebutton;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Melvin on 2017/2/8.
 */

public class ToggleButton extends View {

    private Bitmap toggleIdBitmap;
    private ToggleState mToggleState;
    private Bitmap bitmapToggle;
    //new中使用
    private  int currentX;
    private boolean isSliding=false;
    public ToggleButton(Context context) {
        super(context);
    }
    //在布局文件中使用
    public ToggleButton(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public void setState(ToggleState state) {
        mToggleState = state;
    }

    public enum ToggleState{
        Open,Close
    }
    public void setBackground(int toggleId) {
        toggleIdBitmap = BitmapFactory.decodeResource(getResources(),toggleId);
    }

    public void setMoveBackground(int toggle) {
        bitmapToggle = BitmapFactory.decodeResource(getResources(),toggle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(toggleIdBitmap.getWidth(),toggleIdBitmap.getHeight());
    }
    //android 中图形都是通过canvas绘制
    //位置：
        //left和right图片左上角点的位置

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(toggleIdBitmap,0,0,null);
        if(isSliding){
            int left=currentX-bitmapToggle.getWidth()/2;
            if(left<0) {left = 0;}
            if(left>toggleIdBitmap.getWidth()){left=toggleIdBitmap.getWidth();}
            canvas.drawBitmap(bitmapToggle,left,0,null);
        }else {
            if (mToggleState == ToggleState.Close) {
                canvas.drawBitmap(bitmapToggle, toggleIdBitmap.getWidth() - bitmapToggle.getWidth(), 0, null);
            } else {
                canvas.drawBitmap(bitmapToggle, 0, 0, null);
            }
        }
    }
    //当调用滑动的方法的时候会引起重绘，调用onDraw(Canvas canvas)方法。
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        currentX= (int) event.getX();//这个是图片本身相对坐标系。
        //currentX= (int) event.getRawX();这个是原生坐标系作为参考
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                isSliding=true;
                break;
            case MotionEvent.ACTION_MOVE:
                isSliding=true;
                break;
            case MotionEvent.ACTION_UP:
                isSliding=false;
                //在这逻辑就是离开自己判定位置
                ToggleState preState=mToggleState;
                int centerX=toggleIdBitmap.getWidth()/2-bitmapToggle.getWidth()/2;
                if(currentX>centerX){
                    //open
                   // currentX=toggleIdBitmap.getWidth()-bitmapToggle.getWidth();
                    mToggleState=ToggleState.Open;

                }else{
                    //close
                   // currentX=0;
                    mToggleState=ToggleState.Close;
                }
                if(mToggleState!=preState) {
                    if (listener != null) {
                        listener.onToggleChangeListener(mToggleState);
                    }
                }
                break;

        }
        //invalidate the whole view .if the view is visible,onDraw(Canvas canvas) will be called.
        //this API must be in UI_thread. To call by non_uiThread ,postInvalidate();
        invalidate();
        return true;
    }
    private OnToggleChangeListener listener;
    public void setOnToggleChangeListener(OnToggleChangeListener listener){
        this.listener=listener;
    }
    public interface OnToggleChangeListener{
        void onToggleChangeListener(ToggleState state);
    }
}
