package com.xwray.passwordview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v7.widget.AppCompatEditText;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * An EditText implementing the material design guidelines for password input:
 * https://www.google.com/design/spec/components/text-fields.html#text-fields-password-input
 * <p/>
 * It uses the right drawable for the visibility indicator.  If you try to use it yourself, you
 * will have a bad time.
 */
public class PasswordView extends AppCompatEditText {

    private Drawable eye;
    private Drawable eyeWithStrike;
    private final static int VISIBILITY_ENABLED = (int) (255 * .54f); // 54%
    private final static int VISIBLITY_DISABLED = (int) (255 * .38f); // 38%
    private boolean visible = false;
    private boolean useStrikeThrough = false;
    private Typeface typeface;

    public PasswordView(Context context) {
        super(context);
        init(null);
    }

    public PasswordView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public PasswordView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().getTheme().obtainStyledAttributes(
                    attrs,
                    R.styleable.PasswordView,
                    0, 0);
            try {
                useStrikeThrough = a.getBoolean(R.styleable.PasswordView_useStrikeThrough, false);
            } finally {
                a.recycle();
            }
        }

        // Make sure to mutate so that if there are multiple password fields, they can have
        // different visibilities.
        eye = getVectorDrawableWithIntrinsicBounds(getContext(), R.drawable.ic_eye).mutate();
        eyeWithStrike = getVectorDrawableWithIntrinsicBounds(getContext(), R.drawable.ic_eye_strike).mutate();
        eyeWithStrike.setAlpha(VISIBILITY_ENABLED);
        setup();
    }

    private Drawable getVectorDrawable(Context context, @DrawableRes int drawableResId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return context.getDrawable(drawableResId);
        } else {
            return VectorDrawableCompat.create(context.getResources(), drawableResId, context.getTheme());
        }
    }

    private Drawable getVectorDrawableWithIntrinsicBounds(Context context, @DrawableRes int drawableResId) {
        Drawable drawable = getVectorDrawable(context, drawableResId);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        return drawable;
    }

    protected void setup() {
        int start = getSelectionStart();
        int end = getSelectionEnd();
        setInputType(InputType.TYPE_CLASS_TEXT | (visible ? InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD : InputType.TYPE_TEXT_VARIATION_PASSWORD));
        setSelection(start, end);
        Drawable drawable = useStrikeThrough && !visible ? eyeWithStrike : eye;
        Drawable[] drawables = getCompoundDrawables();
        setCompoundDrawablesWithIntrinsicBounds(drawables[0], drawables[1], drawable, drawables[3]);
        eye.setAlpha(visible && !useStrikeThrough ? VISIBILITY_ENABLED : VISIBLITY_DISABLED);
    }

    @Override public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN
                && event.getX() >= (getRight() - getCompoundDrawables()[2].getBounds().width())) {
            visible = !visible;
            setup();
            invalidate();
            return true;
        }

        return super.onTouchEvent(event);
    }

    @Override public void setInputType(int type) {
        this.typeface = getTypeface();
        super.setInputType(type);
        setTypeface(typeface);
    }

    public void setUseStrikeThrough(boolean useStrikeThrough) {
        this.useStrikeThrough = useStrikeThrough;
    }
}
