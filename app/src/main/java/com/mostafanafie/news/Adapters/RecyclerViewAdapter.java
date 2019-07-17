package com.mostafanafie.news.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mostafanafie.news.R;
import com.mostafanafie.news.Utils.News;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context mContext;
    // Initialize the ArrayList
    private ArrayList<News> mNewsList;

    // Constructor
    public RecyclerViewAdapter(ArrayList<News> newsList, Context context) {
        mNewsList = newsList;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Bind the news to the view holder
        News news = mNewsList.get(position);
        holder.titleTextView.setText(news.getName());
        holder.sectionTextView.setText(news.getSection());
        holder.authorTextView.setText(news.getAuthor());

        // Display the date
        String dateStr = news.getDate();
        SimpleDateFormat curFormater = new SimpleDateFormat("yyyy-MM-dd");
        Date dateObj = null;
        try {
            dateObj = curFormater.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String newDateStr = curFormater.format(dateObj);
        holder.dateTextView.setText(newDateStr);

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri uri = Uri.parse(news.getUrl());
                // Create a new intent to view the URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, uri);
                if (websiteIntent.resolveActivity(mContext.getPackageManager()) != null) {
                    // Send the intent to launch a new activity
                    mContext.startActivity(websiteIntent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // Initialize the views of the list item
        @BindView(R.id.parent_layout)
        CardView parentLayout;
        @BindView(R.id.textView_title)
        TextView titleTextView;
        @BindView(R.id.textView_section)
        TextView sectionTextView;
        @BindView(R.id.textView_author)
        TextView authorTextView;
        @BindView(R.id.textView_date)
        TextView dateTextView;

        // Constructor
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize ButterKnife
            ButterKnife.bind(this, itemView);
        }
    }


}
