package com.anton.sample.view;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anton.sample.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Anton on 1/29/16.
 */
public class ChipView extends FrameLayout implements View.OnClickListener {

    private ViewGroup container;
    private TextView name;
    private ImageView picture;
    private View deleteButton;
    private OnDeleteListener deleteListener;
    private ChipItem chipItem;

    public ChipView(Context context) {
        super(context);
        init(context);
    }

    public ChipView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ChipView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.chip_view, this, true);
        container = (ViewGroup) findViewById(R.id.container);
        container.setOnClickListener(this);
        name = (TextView) findViewById(R.id.name);
        picture = (ImageView) findViewById(R.id.picture);

        deleteButton = createDeleteButton();
        deleteButton.setOnClickListener(this);
    }

    public void setDeleteListener(OnDeleteListener deleteListener) {
        this.deleteListener = deleteListener;
    }

    public void setItem(ChipItem item) {
        chipItem = item;
        setName(item.getName());
        setPicture(item.getImageUrl());
    }

    public ChipItem getChipItem() {
        return chipItem;
    }

    private void setName(String name) {
        this.name.setText(name);
    }

    private void setPicture(String imageUrl) {
        Picasso.with(getContext())
                .load(imageUrl)
                .placeholder(R.mipmap.ic_launcher)
                .into(picture);
    }

    public void setTextColor(int textColor) {
        this.name.setTextColor(textColor);
    }

    public void setChipBackgroundColor(int color) {
        container.setBackground(createChipBackground(color));
    }

    private View createDeleteButton() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(dpToPx(24, getContext()), dpToPx(24, getContext()));
        layoutParams.rightMargin = dpToPx(8, getContext());

        ImageButton imageButton = new ImageButton(getContext());
        imageButton.setId(R.id.remove_chip);
        imageButton.setLayoutParams(layoutParams);
        imageButton.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        imageButton.setImageResource(android.R.drawable.ic_menu_close_clear_cancel);

        return imageButton;
    }

    private StateListDrawable createChipBackground(int normalColor) {
        StateListDrawable states = new StateListDrawable();
        GradientDrawable normalBackground = new GradientDrawable();
        normalBackground.setColor(getContext().getResources().getColor(normalColor));

        GradientDrawable activatedBackground = new GradientDrawable();
        activatedBackground.setColor(getContext().getResources().getColor(R.color.color_primary));

        states.addState(new int[]{android.R.attr.state_activated}, activatedBackground);
        states.addState(new int[]{}, normalBackground);
        return states;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.container:
                boolean isActivated = v.isActivated();
                if (isActivated) {
                    container.removeView(deleteButton);
                } else {
                    container.addView(deleteButton);
                }
                v.setActivated(!isActivated);
                break;
            case R.id.remove_chip:
                if (deleteListener != null) {
                    deleteListener.onDelete();
                }
                break;
        }
    }

    public static interface OnDeleteListener {
        void onDelete();
    }

    public static int dpToPx(int dp, Context ctx) {
        float density = ctx.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

    public static class ChipItem {
        private final String name;
        private final String imageUrl;

        public ChipItem(String name, String imageUrl) {
            this.name = name;
            this.imageUrl = imageUrl;
        }

        public String getName() {
            return name;
        }

        public String getImageUrl() {
            return imageUrl;
        }
    }

}
