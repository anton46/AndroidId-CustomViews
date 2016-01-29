package com.anton.sample;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by anton on 12/9/15.
 */
public class ViewPagerFragment extends Fragment {
    private static final String ARG_OPTIONS = "options";

    public static ViewPagerFragment getInstance(ViewPagerActivity.OnBoardingOptions options) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_OPTIONS, options);

        ViewPagerFragment fragment = new ViewPagerFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_onboarding, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewPagerActivity.OnBoardingOptions options = getArguments().getParcelable(ARG_OPTIONS);

        if (options != null) {
            TextView textView = (TextView) view.findViewById(R.id.title);
            textView.setText(getString(options.getTitle()));

            textView = (TextView) view.findViewById(R.id.description);
            textView.setText(getString(options.getDescription()));

            ImageView imageView = (ImageView) view.findViewById(R.id.image);
            imageView.setImageDrawable(getDrawable(getActivity(), options.getImage()));

            View buttonContainer = view.findViewById(R.id.button_container);
            buttonContainer.setBackgroundColor(getResources().getColor(options.getBaseColor()));
        }
    }

    public Drawable getDrawable(Context context, int res) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return context.getResources().getDrawable(res, null);
        } else {
            return context.getResources().getDrawable(res);
        }
    }
}
