package com.example.appstyle.decorator;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    private final int spacing;

    public SpaceItemDecoration(int spacing) {
        this.spacing = spacing;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        int itemCount = state.getItemCount();

        if (position == 0) {
            outRect.top = spacing / 2;
        }
        if (position == itemCount - 1) {
            outRect.bottom = spacing / 2;
        } else {
            outRect.bottom = spacing;
        }
    }
}
