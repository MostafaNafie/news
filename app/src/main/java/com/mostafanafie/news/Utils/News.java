package com.mostafanafie.news.Utils;

public class News {

    private String mTitle;
    private String mSection;
    private String mDate;
    private String mUrl;

    // Constructor
    public News(String title, String section, String date, String url) {
        mTitle = title;
        mSection = section;
        mDate = date;
        mUrl = url;
    }

    // Return the title
    public String getName() {
        return mTitle;
    }

    // Return the section
    public String getSection() {
        return mSection;
    }

    // Return the date
    public String getDate() {
        return mDate;
    }

    // Return the URL
    public String getUrl() {
        return mUrl;
    }
}
