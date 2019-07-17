package com.mostafanafie.news;

import android.app.LoaderManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mostafanafie.news.Adapters.RecyclerViewAdapter;
import com.mostafanafie.news.Utils.News;
import com.mostafanafie.news.Utils.NewsLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements android.app.LoaderManager.LoaderCallbacks<List<News>> {

    private Context mContext = this;
    // Tag for log messages
    private String TAG = MainActivity.class.getSimpleName();

    // URL for news data from the Guardian
    private static final String GUARDIAN_REQUEST_URL = "https://content.guardianapis.com/search";
    // Constant value for the news loader ID
    private static final int NEWS_LOADER_ID = 1;

    // Initialize the RecyclerView
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    // Initialize the TextView that is displayed when the list is empty
    @BindView(R.id.empty_view)
    TextView emptyStateTextView;
    // Initialize the Loading Indicator
    @BindView(R.id.loading_indicator)
    View loadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize ButterKnife
        ButterKnife.bind(this);

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Initialize the LoaderManager
            LoaderManager loaderManager = getLoaderManager();
            // Initialize the loader
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);
        } else {
            // Update empty state with no connection error message
            loadingIndicator.setVisibility(View.GONE);
            emptyStateTextView.setText(R.string.no_internet_connection);
        }

    }

    @NonNull
    @Override
    public android.content.Loader<List<News>> onCreateLoader(int id, @Nullable Bundle args) {
        Uri baseUri = Uri.parse(GUARDIAN_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("q", "google");
        uriBuilder.appendQueryParameter("show-tags", "contributor");
        uriBuilder.appendQueryParameter("api-key", "test");

        return new NewsLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(android.content.Loader<List<News>> loader, List<News> news) {
        // Hide loading indicator because the data has been loaded
        loadingIndicator.setVisibility(View.GONE);

        // If there is a valid list, then add them to the adapter's data set
        if (news != null && !news.isEmpty()) {
            // Initialize the ArrayList
            ArrayList<News> newsList = new ArrayList<>(news);
            // Setup the recycler view
            setupRecyclerView(newsList);
        } else {
            // Set empty state text to display "No news found."
            emptyStateTextView.setText(R.string.no_news);
        }
    }

    @Override
    public void onLoaderReset(android.content.Loader<List<News>> loader) {

    }

    private void setupRecyclerView(ArrayList<News> newsList) {
        // Initialize the adapter, and pass the ArrayList to it
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(newsList, this);
        // Attach the adapter to the recycler view
        recyclerView.setAdapter(adapter);
        // Attach the layout manager to the recycler view
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
    }
}
