package com.tg.starratingview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.tg.starratingview.R;

public class StarRatingView extends LinearLayout {

    // private static final String TAG = "RatingView";
    private View mRoot;
    private ImageView mFirst;
    private ImageView mSecond;
    private ImageView mThird;
    private ImageView mForth;
    private ImageView mFifth;

    private ImageView[] mStars;

    private int mStarOnRes = R.drawable.icon_star;
    private int mStarOffRes = R.drawable.icon_stargrey;

    private int mRating;

    private boolean mRateable = false;

    public StarRatingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.StarRatingView, 0, 0);
        final int N = a.getIndexCount();
        for (int i = 0; i < N; i++) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.StarRatingView_star_on) {
                mStarOnRes = a.getResourceId(attr, R.drawable.icon_star);
            } else if (attr == R.styleable.StarRatingView_star_off) {
                mStarOffRes = a.getResourceId(attr, R.drawable.icon_stargrey);
            } else if (attr == R.styleable.StarRatingView_rating) {
                mRating = a.getInteger(attr, 0);
            } else if (attr == R.styleable.StarRatingView_rateable) {
                mRateable = a.getBoolean(attr, false);
            }
        }

        a.recycle();

        LayoutInflater inflater = LayoutInflater.from(context);
        mRoot = inflater.inflate(R.layout.layout_rating, null);
        mFirst = (ImageView) mRoot.findViewById(R.id.first);
        mSecond = (ImageView) mRoot.findViewById(R.id.second);
        mThird = (ImageView) mRoot.findViewById(R.id.third);
        mForth = (ImageView) mRoot.findViewById(R.id.forth);
        mFifth = (ImageView) mRoot.findViewById(R.id.fifth);

        mStars = new ImageView[] { mFirst, mSecond, mThird, mForth, mFifth };
        for (View star : mStars) {
            star.setOnClickListener(mOnClickListener);
        }

        setRating(mRating);

        addView(mRoot);
    }

    public boolean setRating(int rating) {
        if (rating < 0 || rating > 5) {
            return false;
        }
        mRating = rating;
        for (int i = 0; i < rating; i++) {
            mStars[i].setImageResource(mStarOnRes);
        }
        for (int i = rating; i < 5; i++) {
            mStars[i].setImageResource(mStarOffRes);
        }
        return true;
    }

    public int getRating() {
        return mRating;
    }

    private OnClickListener mOnClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            if (!mRateable) {
                return;
            }
            int vId = v.getId();

            if (vId == R.id.first) {
                if (mRating == 1) {
                    mRating = 0;
                } else {
                    mRating = 1;
                }
            } else if (vId == R.id.second) {
                mRating = 2;
            } else if (vId == R.id.third) {
                mRating = 3;
            } else if (vId == R.id.forth) {
                mRating = 4;
            } else if (vId == R.id.fifth) {
                mRating = 5;
            }

            setRating(mRating);
        }

    };
    // public void setStarOnRes(int res) {
    // mStarOnRes = res;
    // }
    //
    // public void setStarOffRes(int res) {
    // mStarOffRes = res;
    // }
}
