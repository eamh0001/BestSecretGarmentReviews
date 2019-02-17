package com.eamh.garmentreviews.ui.reviews;

import android.content.Intent;
import android.os.Bundle;

import com.eamh.garmentreviews.R;
import com.eamh.garmentreviews.model.Review;
import com.eamh.garmentreviews.ui.reviewdetail.ReviewDetailActivity;
import com.eamh.garmentreviews.ui.reviewdetail.ReviewDetailFragment;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity
        extends AppCompatActivity
        implements FragmentReviewsList.FragmentInteractionListener,
        ReviewDetailFragment.FragmentInteractionListener {

    private boolean isTwoPanelsMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isTwoPanelsMode = getResources().getBoolean(R.bool.two_panels);
    }

    @Override
    public void onReviewSelected(Review review) {
        if (isTwoPanelsMode){
            setActivityTitle(review.getName());
            createReviewDetailFragment(review);
        }else {
            startActivityReviewDetail(review);
        }
    }

    @Override
    public void onReviewLoaded(Review review) {
        setActivityTitle(review.getName());
    }

    private void setActivityTitle(String title) {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null){
            supportActionBar.setTitle(title);
        }
    }

    private void createReviewDetailFragment(Review review) {
        Bundle arguments = new Bundle();
        arguments.putInt(ReviewDetailFragment.ARG_REVIEW_ID, review.getId());
        ReviewDetailFragment fragment = new ReviewDetailFragment();
        fragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.detailContainer, fragment)
                .commit();
    }

    private void startActivityReviewDetail(Review review) {
        int reviewId = review.getId();
        Intent intent = new Intent(this, ReviewDetailActivity.class);
        intent.putExtra(ReviewDetailActivity.ARG_REVIEW_ID, reviewId);
        startActivity(intent);
    }
}
