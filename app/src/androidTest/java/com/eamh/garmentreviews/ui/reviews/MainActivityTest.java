package com.eamh.garmentreviews.ui.reviews;

import com.eamh.garmentreviews.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void reviews_loaded() {
        RecyclerView recyclerView =
                mainActivityActivityTestRule.getActivity().findViewById(R.id.rvReviews);
        Espresso.registerIdlingResources(mainActivityActivityTestRule.getActivity().idlingResource);
        assertThat(recyclerView.getAdapter().getItemCount(), greaterThan(0));
    }

}