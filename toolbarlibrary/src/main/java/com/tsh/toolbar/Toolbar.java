package com.tsh.toolbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

public class Toolbar extends android.support.v7.widget.Toolbar {

  private final static String FINAL_TITLE = "##*##";
  protected TextView mTitleTextView2;
  protected CharSequence mTitleText2;

  private Rect mRect = new Rect();
  private int[] outLocation = new int[2];
  private boolean titleCenteredEnable;

  public Toolbar(Context context) {
    super(context);
    init(context, null);
  }

  public Toolbar(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    init(context, attrs);
  }

  public Toolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context, attrs);
  }

  private void init(Context context, @Nullable AttributeSet attrs) {
    TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Toolbar);
    titleCenteredEnable = typedArray.getBoolean(R.styleable.Toolbar_titleCentered, false);
    typedArray.recycle();
  }

  @Override public void setTitle(CharSequence title) {

    this.mTitleText2 = title;

    if (mTitleTextView2 == null) {
      super.setTitle(FINAL_TITLE);
      // find title view
      mTitleTextView2 = findTitleView();
    }
    //
    if (mTitleTextView2 != null) {
      mTitleTextView2.setTranslationX(0);
      mTitleTextView2.setText(title);
    }
  }

  private TextView findTitleView() {
    View childView;
    TextView titleView = null, textView;
    for (int i = 0, count = getChildCount(); i < count; i++) {
      childView = getChildAt(i);
      if (childView instanceof TextView) {
        textView = (TextView) childView;
        if (FINAL_TITLE.equals(textView.getText().toString())) {
          titleView = textView;
        }
      }
    }
    return titleView;
  }

  @Override protected void onDraw(Canvas canvas) {
    if (titleCenteredEnable) {
      adjustTitlePosition();
    }
    super.onDraw(canvas);
  }

  /**
   * 调整标题居中
   */
  private void adjustTitlePosition() {
    if (mTitleTextView2 == null) return;

    mTitleTextView2.getLocalVisibleRect(mRect);
    if (mRect == null || mRect.width() == 0) return;

    String text = mTitleTextView2.getText().toString();
    if (mTitleText2 != null) {
      text = mTitleText2.toString();
    }

    float textWidth = mTitleTextView2.getPaint().measureText(text);

    if (textWidth <= mRect.width() && mTitleTextView2.getTranslationX() == 0) {

      mTitleTextView2.getLocationInWindow(outLocation);
      int left = outLocation[0];

      int translationX = (int) ((getMeasuredWidth() - textWidth) / 2 - left);

      mTitleTextView2.setTranslationX(translationX);
    }
  }

  @Override protected Parcelable onSaveInstanceState() {
    Bundle bundle = new Bundle();
    Parcelable parcelable = super.onSaveInstanceState();
    bundle.putParcelable("com_tsh_toolbar_super_data", parcelable);
    bundle.putBoolean("com_tsh_toolbar_state_title_centered_enable", titleCenteredEnable);
    return bundle;
  }

  @Override protected void onRestoreInstanceState(Parcelable state) {
    if (state != null) {
      Bundle bundle = (Bundle) state;
      Parcelable superData = bundle.getParcelable("com_tsh_toolbar_super_data");
      titleCenteredEnable = bundle.getBoolean("com_tsh_toolbar_state_title_centered_enable", false);
      super.onRestoreInstanceState(superData);
    } else {
      super.onRestoreInstanceState(state);
    }
  }
}