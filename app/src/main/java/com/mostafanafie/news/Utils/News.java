package com.mostafanafie.news.Utils;

public class News {

    private String mTitle;
    private String mSection;
    private String mAuthor;
    private String mDate;
    private String mUrl;

    // Constructor
    public News(String title, String section, String author, String date, String url) {
        mTitle = title;
        mSection = section;
        mAuthor = author;
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

    // Return the author
    public String getAuthor() {
        return mAuthor;
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
