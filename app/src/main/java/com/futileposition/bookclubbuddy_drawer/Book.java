package com.futileposition.bookclubbuddy_drawer;

import org.joda.time.DateTime;
import org.joda.time.Days;


/**
 * Created by MD on 6/3/2017.
 */

class Book {


    private String mTitle;
    private String mAuthor;
    private DateTime mDateStarted;
    private DateTime mDateToFinish;
    private int mPagesPerDay;
    private String[] mNotes;
    private boolean mIsFinished;
    private Days mDaysToFinish;
    private int mAppIndex;
    private int mNumberOfPages;
    private int mPagesRead;




    /**
     *  Getters and Setters Below Here.
     */

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String mAuthor) {
        this.mAuthor = mAuthor;
    }

    public DateTime getDateStarted() {
        return mDateStarted;
    }

    public String getDateStartedAsString() {
        return mDateStarted.toString("MM/dd/yyyy");
    }

    public void setDateStarted(DateTime mDateStarted) {
        this.mDateStarted = mDateStarted;
    }

    public DateTime getDateToFinish() {
        return mDateToFinish;
    }

    public String getDateToFinishAsString() {
        return mDateToFinish.toString("MM/dd/yyyy");
    }

    public void setDateToFinish(DateTime mDateToFinish) {
        this.mDateToFinish = mDateToFinish;
    }

    public int getPagesPerDay() {
        return mPagesPerDay;
    }

    public void setPagesPerDay(int mPagesPerDay) {
        this.mPagesPerDay = mPagesPerDay;
    }

    public String[] getNotes() {
        return mNotes;
    }

    public void setNotes(String[] mNotes) {
        this.mNotes = mNotes;
    }


    public boolean getIsFinished() {
        return mIsFinished;
    }

    public void setIsFinished(boolean finished) {
        mIsFinished = finished;
    }

    public Days getDaysToFinish() {
        Days days = Days.daysBetween(new DateTime(), mDateToFinish);
        return days;
    }

    public int getAppIndex() {
        return mAppIndex;
    }

    public void setAppIndex(int mAppIndex) {
        this.mAppIndex = mAppIndex;
    }

    public int getNumberofPages() {
        return mNumberOfPages;
    }

    public void setNumberofPages(int mNumberofPages) {
        this.mNumberOfPages = mNumberofPages;
    }

    public int getPagesRead() {
        return mPagesRead;
    }

    public void setPagesRead(int mPagesRead) {
        this.mPagesRead = mPagesRead;
    }

}