package com.futileposition.bookclubbuddy_drawer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.futileposition.bookclubbuddy_drawer.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import static android.R.id.list;
import static java.lang.Integer.parseInt;

/**
 * Created by MD on 6/3/2017.
 */

public class MainBookAdapter extends RecyclerView.Adapter<MainBookAdapter.MainBookViewHolder> {
    private Context mContext;
    private Book[] mDataset;

    public MainBookAdapter(Context context, Book[] books) {
        ArrayList<Book> tempList = new ArrayList<>();
        mContext = context;

        for (Book book : books) {
            if (!book.getIsFinished()) {
                tempList.add(book);
            }
        }
        mDataset = tempList.toArray(new Book[tempList.size()]);;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MainBookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_book_layout, parent, false);
        return new MainBookViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MainBookViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.bindMainBook(mDataset[position]);

    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MainBookViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public TextView mBookTitle;
        public TextView mAuthor;
        public TextView mDateStarted;
        public TextView mDateToFinish;
        public TextView mDaysLeft;

        public MainBookViewHolder(View itemView) {
            super(itemView);
            mBookTitle = (TextView) itemView.findViewById(R.id.bookTitle);
            mAuthor = (TextView) itemView.findViewById(R.id.author);
            mDateStarted = (TextView) itemView.findViewById(R.id.dateStarted);
            mDateToFinish = (TextView) itemView.findViewById(R.id.dateToFinish);
            mDaysLeft = (TextView) itemView.findViewById(R.id.daysLeft);

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {

        }

        public void bindMainBook(Book book) {
            mBookTitle.setText(book.getTitle());
            mAuthor.setText("By: " + book.getAuthor());
            mDateStarted.setText("Started On: " + book.getDateStartedAsString());
            mDateToFinish.setText("Finish By: " + book.getDateToFinishAsString());
            //int daysLeftAsInt = Integer.parseInt(book.getDaysToFinish().toString());
            int daysLeftAsInt = book.getDaysToFinish().getDays();
            int pagesPerDay = (book.getNumberofPages() - book.getPagesRead()) / daysLeftAsInt;
            String formattedString = String.format("%d pages for the next %d days.", pagesPerDay, daysLeftAsInt);
            mDaysLeft.setText(formattedString);
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}