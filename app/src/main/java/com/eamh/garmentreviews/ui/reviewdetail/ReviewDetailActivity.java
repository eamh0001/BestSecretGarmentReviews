package com.eamh.garmentreviews.ui.reviewdetail;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MenuItem;

import com.eamh.garmentreviews.R;
import com.eamh.garmentreviews.model.Review;
import com.eamh.garmentreviews.ui.reviews.MainActivity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class ReviewDetailActivity
        extends AppCompatActivity
        implements ReviewDetailFragment.FragmentInteractionListener{

    public static final String ARG_REVIEW_ID = "ARG_REVIEW_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_detail);

        boolean isTabletMode = getResources().getBoolean(R.bool.tablet_mode);
        if(!isTabletMode){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.

            Intent intent = getIntent();

            if (intent != null && intent.hasExtra(ARG_REVIEW_ID)) {
                Bundle extras = intent.getExtras();
                int reviewId = extras.getInt(ARG_REVIEW_ID);
                Bundle arguments = new Bundle();
                arguments.putInt(ReviewDetailFragment.ARG_REVIEW_ID, reviewId);
                ReviewDetailFragment fragment = new ReviewDetailFragment();
                fragment.setArguments(arguments);
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.item_detail_container, fragment)
                        .commit();
            }
        }
    }

    private void setActivityTitle(String title) {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null){
            supportActionBar.setTitle(title);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            navigateUpTo(new Intent(this, MainActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onReviewLoaded(Review review) {
        setActivityTitle(review.getName());
    }
}
