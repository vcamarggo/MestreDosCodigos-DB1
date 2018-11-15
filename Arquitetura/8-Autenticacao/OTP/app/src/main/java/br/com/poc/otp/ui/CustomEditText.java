package br.com.poc.otp.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;

import br.com.poc.otp.R;

public class CustomEditText extends TextInputEditText {

    private static final int X_DEFAULT_RIGHT = 70;
    private static final int X_DEFAULT_BOTTOM = 70;
    private static final int X_DEFAULT_TOP = 0;
    private static final int X_DEFAULT_LEFT = 0;
    private static final int DRAWABLE_RIGHT_INDEX = 2;

    public CustomEditText(Context context) {
        super(context);
        initComponent();
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initComponent();
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initComponent();
    }

    private void initComponent() {
        setDrawable();
    }

    private void setDrawable() {
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_close_gray);
        drawable.setBounds(X_DEFAULT_LEFT, X_DEFAULT_TOP, X_DEFAULT_RIGHT, X_DEFAULT_BOTTOM);
        setCompoundDrawables(null, null, drawable, null);
        setOnTouchListener((v, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP && !getText().toString().isEmpty()) {
                if (motionEvent.getRawX() >= (getRight() - getCompoundDrawables()[DRAWABLE_RIGHT_INDEX].getBounds().width())) {
                    setText("");
                }
            }
            return false;
        });
    }


}