package com.eamh.garmentreviews.ui.reviewdetail;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eamh.garmentreviews.R;
import com.eamh.garmentreviews.databinding.FragmentReviewDetailBinding;
import com.eamh.garmentreviews.model.Review;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ReviewDetailFragment extends Fragment {

    public interface FragmentInteractionListener{
        void onReviewLoaded(Review review);
    }

    public static final String ARG_REVIEW_ID = "ARG_REVIEW_ID";

    private FragmentInteractionListener fragmentInteractionListener;

    private FragmentReviewDetailBinding binding;
    private ReviewDetailViewModel viewModel;

    public ReviewDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_review_detail,
                container,
                false
        );

        binding.rvReviewers.setLayoutManager(
                new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false)
        );

        binding.rvReviewers.setItemAnimator(new DefaultItemAnimator());
        binding.setLifecycleOwner(this);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle arguments = getArguments();
        if (arguments != null && arguments.containsKey(ARG_REVIEW_ID)) {
            int reviewId = arguments.getInt(ARG_REVIEW_ID);
            initViewModel(reviewId);
        }
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

    private void initViewModel(int reviewId){
        ReviewDetailViewModelFactory factory = new ReviewDetailViewModelFactory(reviewId);
        viewModel = ViewModelProviders.of(this, factory).get(ReviewDetailViewModel.class);
        viewModel.getReview().observe(this, new Observer<Review>() {
            @Override
            public void onChanged(Review review) {
                if(review != null){
                    fragmentInteractionListener.onReviewLoaded(review);
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

    private void showInfoMessage(int resIdMessage) {
        Snackbar.make(binding.getRoot(), getString(resIdMessage), Snackbar.LENGTH_LONG)
                .setAction(R.string.retry, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewModel.loadReview();
                    }
                })
                .show();
    }
}
