package com.eamh.garmentreviews.ui.reviews;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eamh.garmentreviews.R;
import com.eamh.garmentreviews.databinding.FragmentReviewsBinding;
import com.eamh.garmentreviews.model.Review;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class ReviewsFragment extends Fragment {

    public interface FragmentInteractionListener{
        void onReviewSelected(Review review);
    }

    private FragmentReviewsBinding binding;
    private ReviewsFragmentViewModel viewModel;
    private FragmentInteractionListener fragmentInteractionListener;

    public ReviewsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil
                .inflate(inflater,
                        R.layout.fragment_reviews,
                        container,
                        false);

        binding.rvReviews.setLayoutManager(
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        );
        binding.rvReviews.setItemAnimator(new DefaultItemAnimator());

        binding.setLifecycleOwner(this);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        viewModel =
                ViewModelProviders.of(this).get(ReviewsFragmentViewModel.class);

        viewModel.getSelectedReview().observe(this, new Observer<Review>() {
            @Override
            public void onChanged(Review review) {
                if (review != null) {
                    notifyReviewSelected(review);
                }
            }
        });

        viewModel.getShowInfoToUser().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer messageResId) {
                if (messageResId != null){
                    showInfoMessage(messageResId);
                }
            }
        });

        binding.setViewModel(viewModel);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentInteractionListener) {
            fragmentInteractionListener = (FragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement FragmentInteractionListener");
        }
    }

    private void notifyReviewSelected(Review review) {
        if (fragmentInteractionListener != null){
            fragmentInteractionListener.onReviewSelected(review);
        }
    }

    private void showInfoMessage(int resIdMessage) {
        Snackbar.make(binding.getRoot(), getString(resIdMessage), Snackbar.LENGTH_LONG)
                .setAction(R.string.retry, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewModel.loadReviews();
                    }
                })
                .show();
    }
}
