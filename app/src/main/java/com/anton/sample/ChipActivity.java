package com.anton.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.anton.sample.view.ChipView;

import org.apmem.tools.layouts.FlowLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Anton on 1/29/16.
 */
public class ChipActivity extends AppCompatActivity {

    private int[] colors = new int[]{R.color.color_1, R.color.color_2, R.color.color_3, R.color.color_4};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chip);

        final Random random = new Random();

        final FlowLayout flowLayout = (FlowLayout) findViewById(R.id.chips_container);
        final List<ChipView.ChipItem> items = buildItems();
        for (int i = 0; i < items.size(); i++) {
            ChipView.ChipItem chipItem = items.get(i);
            final ChipView chipView = createChipView(flowLayout, chipItem, colors[random.nextInt(items.size())]);
            flowLayout.addView(chipView);
        }

        Button addButton =  (Button) findViewById(R.id.add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ChipView chipView = createChipView(flowLayout, items.get(random.nextInt(items.size())), colors[random.nextInt(colors.length)]);
                flowLayout.addView(chipView);
            }
        });
    }

    private List<ChipView.ChipItem> buildItems() {
        List<ChipView.ChipItem> items = new ArrayList<>();
        items.add(new ChipView.ChipItem("Larry Page", "http://a.abcnews.com/images/Technology/gty_larry_page_google_tk_130514_ms.jpg"));
        items.add(new ChipView.ChipItem("Mark Zuckerberg", "http://a4.files.biography.com/image/upload/c_fill,cs_srgb,dpr_1.0,g_face,h_300,q_80,w_300/MTIwNjA4NjMzNjg3ODAzNDA0.jpg"));
        items.add(new ChipView.ChipItem("Jack Dorsey", "http://cdni.wired.co.uk/1240x826/d_f/dorsey1.jpg"));
        items.add(new ChipView.ChipItem("Bill Gates", "http://images.boomsbeat.com/data/images/full/595/bill-gates-jpg.jpg"));

        return items;
    }

    private ChipView createChipView(final ViewGroup parent, ChipView.ChipItem chipItem, int backgroundColor) {
        final ChipView chipView = new ChipView(this);
        chipView.setChipBackgroundColor(backgroundColor);
        chipView.setTag(String.valueOf(System.currentTimeMillis()));
        chipView.setItem(chipItem);
        chipView.setDeleteListener(new ChipView.OnDeleteListener() {
            @Override
            public void onDelete() {
                parent.removeView(parent.findViewWithTag(chipView.getTag()));
            }
        });

        return chipView;
    }
}
