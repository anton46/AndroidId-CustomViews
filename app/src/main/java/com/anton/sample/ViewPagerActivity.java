package com.anton.sample;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.anton.sample.view.CircleIndicator;
import com.nineoldandroids.view.ViewHelper;

/**
 * Created by anton on 12/9/15.
 */
public class ViewPagerActivity extends AppCompatActivity {
    private ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
        bindViews();
        initImagesTransition();
    }

    private void bindViews() {
        OnboardingAdapter adapter = new OnboardingAdapter(getSupportFragmentManager());

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setPageTransformer(true, new CrossfadePageTransformer());
        viewPager.setAdapter(adapter);

        CircleIndicator circleIndicator = (CircleIndicator) findViewById(R.id.circle_indicator);
        circleIndicator.setViewPager(viewPager);
    }

    private void initImagesTransition() {
    }


    private static class OnboardingAdapter extends FragmentPagerAdapter {
        final int[] TITLES = {R.string.onboarding_title_1, R.string.onboarding_title_2, R.string.onboarding_title_3};
        final int[] DESCRIPTIONS = {R.string.lorem, R.string.lorem, R.string.lorem};
        final int[] IMAGES = {R.drawable.image1, R.drawable.image2, R.drawable.image3};
        final int[] BASE_COLORS = {R.color.color_primary, R.color.color_4, R.color.color_3};

        public OnboardingAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            OnBoardingOptions onBoardingOptions = new OnBoardingOptions(TITLES[position], DESCRIPTIONS[position], IMAGES[position], BASE_COLORS[position]);
            return ViewPagerFragment.getInstance(onBoardingOptions);
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }
    }

    public class CrossfadePageTransformer implements ViewPager.PageTransformer {

        @Override
        public void transformPage(View page, float position) {
            int pageWidth = page.getWidth();

            View image = page.findViewById(R.id.image);
            View title = page.findViewById(R.id.title);
            View description = page.findViewById(R.id.description);
            View overlay = page.findViewById(R.id.overlay);
            View buttonContainer = page.findViewById(R.id.button_container);

            if (0 <= position && position < 1) {
                ViewHelper.setTranslationX(page, pageWidth * -position);
            }
            if (-1 < position && position < 0) {
                ViewHelper.setTranslationX(page, pageWidth * -position);
            }

            if (position <= -1.0f || position >= 1.0f) {
            } else if (position == 0.0f) {
            } else {
                if (image != null) {
                    ViewHelper.setAlpha(image, 1.0f - Math.abs(position));
                }

                if (buttonContainer != null) {
                    ViewHelper.setAlpha(buttonContainer, 1.0f - Math.abs(position));
                    if (1.0f - Math.abs(position) < 0.2f) {
                        buttonContainer.setVisibility(View.GONE);
                    } else {
                        buttonContainer.setVisibility(View.VISIBLE);
                    }
                }

                if (title != null) {
                    ViewHelper.setTranslationX(title, pageWidth / 2 * position);
                    ViewHelper.setAlpha(title, 1.0f - Math.abs(position));
                    if (1.0f - Math.abs(position) < 0.2f) {
                        title.setVisibility(View.GONE);
                    } else {
                        title.setVisibility(View.VISIBLE);
                    }
                }

                if (description != null) {
                    ViewHelper.setTranslationX(description, (float) (pageWidth / 1.5 * position));
                    ViewHelper.setAlpha(description, 1.0f - Math.abs(position));
                    if (1.0f - Math.abs(position) < 0.2f) {
                        description.setVisibility(View.GONE);
                    } else {
                        description.setVisibility(View.VISIBLE);
                    }
                }

                if (overlay != null) {
                    ViewHelper.setAlpha(overlay, (1.0f - Math.abs(position)) * 0.5f);
                    if (1.0f - Math.abs(position) < 0.2f) {
                        overlay.setVisibility(View.GONE);
                    } else {
                        overlay.setVisibility(View.VISIBLE);
                    }
                }

            }
        }
    }

    public static class OnBoardingOptions implements Parcelable {
        private final int title;
        private final int description;
        private final int image;
        private final int baseColor;

        public OnBoardingOptions(int title, int description, int image, int baseColor) {
            this.title = title;
            this.description = description;
            this.image = image;
            this.baseColor = baseColor;
        }

        public int getTitle() {
            return title;
        }

        public int getDescription() {
            return description;
        }

        public int getImage() {
            return image;
        }

        public int getBaseColor() {
            return baseColor;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.title);
            dest.writeInt(this.description);
            dest.writeInt(this.image);
            dest.writeInt(this.baseColor);
        }

        protected OnBoardingOptions(Parcel in) {
            this.title = in.readInt();
            this.description = in.readInt();
            this.image = in.readInt();
            this.baseColor = in.readInt();
        }

        public static final Creator<OnBoardingOptions> CREATOR = new Creator<OnBoardingOptions>() {
            public OnBoardingOptions createFromParcel(Parcel source) {
                return new OnBoardingOptions(source);
            }

            public OnBoardingOptions[] newArray(int size) {
                return new OnBoardingOptions[size];
            }
        };
    }
}
