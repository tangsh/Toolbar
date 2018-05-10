package com.tsh.toolbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;


public class Toolbar extends android.support.v7.widget.Toolbar {

	private final static String FINAL_TITLE = "##*##";
	protected TextView mTitleTextView2;
	protected CharSequence mTitleText2;

	private boolean titleCenteredEnable;
	private float mElevation;
	private View leftView;

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
		mElevation = typedArray.getDimension(R.styleable.Toolbar_elevation, 5);
		typedArray.recycle();
		ViewCompat.setElevation(this, mElevation);
	}

	@Override
	public void setTitle(CharSequence title) {

		this.mTitleText2 = title;

		if (mTitleTextView2 == null) {
			super.setTitle(FINAL_TITLE);
			// find title view
			mTitleTextView2 = findTitleView();
		}
		//
		if (mTitleTextView2 != null) {
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

	@Override
	protected Parcelable onSaveInstanceState() {
		Bundle bundle = new Bundle();
		Parcelable parcelable = super.onSaveInstanceState();
		bundle.putParcelable("com_tsh_toolbar_super_data", parcelable);
		bundle.putBoolean("com_tsh_toolbar_state_title_centered_enable", titleCenteredEnable);
		bundle.putDouble("com_tsh_toolbar_state_elevation", mElevation);
		return bundle;
	}

	@Override
	protected void onRestoreInstanceState(Parcelable state) {
		if (state != null) {
			Bundle bundle = (Bundle) state;
			Parcelable superData = bundle.getParcelable("com_tsh_toolbar_super_data");
			titleCenteredEnable = bundle.getBoolean("com_tsh_toolbar_state_title_centered_enable", false);
			mElevation = (float) bundle.getDouble("com_tsh_toolbar_state_elevation", mElevation);
			ViewCompat.setElevation(this, mElevation);
			super.onRestoreInstanceState(superData);
		} else {
			super.onRestoreInstanceState(state);
		}
	}

	public TextView getTitleTextView() {
		return mTitleTextView2;
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {

		super.onLayout(changed, l, t, r, b);

		final boolean isRtl = ViewCompat.getLayoutDirection(this) == ViewCompat.LAYOUT_DIRECTION_RTL;

		if (titleCenteredEnable && shouldLayoutView(mTitleTextView2)) {
			int val;
			if (isRtl) {
				val = (getMeasuredWidth() + mTitleTextView2.getMeasuredWidth()) / 2;
				mTitleTextView2.layout(val - mTitleTextView2.getMeasuredWidth(), mTitleTextView2.getTop(),
						val, mTitleTextView2.getBottom());
			} else {
				val = (getMeasuredWidth() - mTitleTextView2.getMeasuredWidth()) / 2;
				mTitleTextView2.layout(val, mTitleTextView2.getTop(),
						mTitleTextView2.getMeasuredWidth() + val, mTitleTextView2.getBottom());
			}
		}

		if (leftView != null) {
			leftView.layout(0, leftView.getTop(), leftView.getRight() - leftView.getLeft(),
					leftView.getBottom());
			leftView.setVisibility(VISIBLE);
		}
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		leftView = findViewById(R.id.Toolbar_leftViewId);
	}

	protected boolean shouldLayoutView(View view) {
		return view != null && view.getParent() == this && view.getVisibility() != GONE;
	}
}