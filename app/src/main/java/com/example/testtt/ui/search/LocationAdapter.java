package com.example.testtt.ui.search;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testtt.R;
import com.example.testtt.domain.model.LocationItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder> {

    private final Context context;
    private final List<LocationItem> items = new ArrayList<>();
    private String currentQuery = "";

    public LocationAdapter(Context context) {
        this.context = context;
    }

    public void submitList(List<LocationItem> newItems, String query) {
        items.clear();
        if (newItems != null) {
            items.addAll(newItems);
        }
        currentQuery = query != null ? query : "";
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_location, parent, false);
        return new LocationViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {
        LocationItem item = items.get(position);

        // Highlight query trong title
        holder.tvTitle.setText(highlight(item.getTitle(), currentQuery));
        holder.tvAddress.setText(item.getAddress());

        // Click mở Google Maps
        holder.itemView.setOnClickListener(v -> {
            String uri = String.format(Locale.US, "geo:%f,%f?q=%f,%f(%s)",
                    item.getLat(), item.getLng(),
                    item.getLat(), item.getLng(),
                    Uri.encode(item.getTitle()));
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            intent.setPackage("com.google.android.apps.maps");
            try {
                context.startActivity(intent);
            } catch (Exception e) {
                // fallback: mở bất kỳ app map nào
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(uri)));
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    // --- Highlight helper ---
    private Spannable highlight(String text, String keyword) {
        if (text == null || keyword == null) return new SpannableString(text);
        String lowerText = text.toLowerCase(Locale.US);
        String lowerKey = keyword.toLowerCase(Locale.US);
        int start = lowerText.indexOf(lowerKey);
        if (start < 0) {
            return new SpannableString(text);
        } else {
            SpannableString ss = new SpannableString(text);
            ss.setSpan(new StyleSpan(android.graphics.Typeface.BOLD),
                    start, start + keyword.length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return ss;
        }
    }

    static class LocationViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvAddress;
        public LocationViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvAddress = itemView.findViewById(R.id.tvAddress);
        }
    }
}
