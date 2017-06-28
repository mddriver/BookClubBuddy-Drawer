package com.futileposition.bookclubbuddy_drawer;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.futileposition.bookclubbuddy_drawer.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import static android.R.id.list;
import static com.futileposition.bookclubbuddy_drawer.R.id.current_books_recycler_view;
import static com.futileposition.bookclubbuddy_drawer.SqlBookDatabase.setPages;
import static java.lang.Integer.parseInt;

/**
 * Created by MD on 6/3/2017.
 */

public class MainBookAdapter extends RecyclerView.Adapter<MainBookAdapter.MainBookViewHolder> {

    private static Context mContext;
    private static Book[] mDataset;
    private static View mView;
    private static RecyclerView mRecyclerView;
    private EditText mPagesEditText;

    public MainBookAdapter(Context context, Book[] books) {
        mContext = context;
        ArrayList<Book> tempList = new ArrayList<>();
        mContext = context;

        for (Book book : books) {
            if (!book.getIsFinished()) {
                tempList.add(book);
            }
        }
        mDataset = tempList.toArray(new Book[tempList.size()]);
        ;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MainBookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_book_layout, parent, false);
        mRecyclerView = (RecyclerView) parent.findViewById(R.id.current_books_recycler_view);
        mPagesEditText = (EditText) parent.findViewById(R.id.pagesEditText);

        return new MainBookViewHolder(view);
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MainBookViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.bindMainBook(mDataset[position]);

    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MainBookViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        // each data item is just a string in this case
        private EditText mPagesEditText;
        public TextView mBookTitle;
        public TextView mAuthor;
        public TextView mDateStarted;
        public TextView mDateToFinish;
        public TextView mDaysLeft;
        private TextView mPagesRead;
        public Button mEditBookButton;
        public Button mAddPagesButton;

        public MainBookViewHolder(View itemView) {
            super(itemView);
            mBookTitle = (TextView) itemView.findViewById(R.id.bookTitle);
            mAuthor = (TextView) itemView.findViewById(R.id.author);
            mDateStarted = (TextView) itemView.findViewById(R.id.dateStarted);
            mDateToFinish = (TextView) itemView.findViewById(R.id.dateToFinish);
            mDaysLeft = (TextView) itemView.findViewById(R.id.daysLeft);
            mPagesRead = (TextView) itemView.findViewById(R.id.pagesRead);
            mEditBookButton = (Button) itemView.findViewById(R.id.editBookButton);
            mAddPagesButton = (Button) itemView.findViewById(R.id.addPagesButton);
            mPagesEditText = (EditText) itemView.findViewById(R.id.pagesEditText);
            mEditBookButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, v.getId() + "", Toast.LENGTH_LONG).show();
                }
            });

            mAddPagesButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    int pages;
                    Book book = mDataset[getAdapterPosition()];
                    try {
                        pages = Integer.parseInt(mPagesEditText.getText().toString());
                    } catch (NumberFormatException e) {
                        Toast.makeText(mContext, "Pages must be a number", Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (pages > book.getNumberofPages()) {
                        Toast.makeText(mContext, "You can't really read more pages than the book has, can you?", Toast.LENGTH_LONG).show();
                    } else if (pages < 0) {
                        Toast.makeText(mContext, "I don't understand how you have read fewer than zero pages.", Toast.LENGTH_LONG).show();
                    } else {
                        setPages(mContext, pages, book.getAppIndex());
                        book.setPagesRead(pages);
                        Toast.makeText(mContext, "Pages read so far set to: " + pages, Toast.LENGTH_LONG).show();

                        int daysLeftAsInt = book.getDaysToFinish().getDays();
                        int pagesPerDay = (book.getNumberofPages() - book.getPagesRead()) / daysLeftAsInt;
                        String readSoFar = String.format("You have read %d pages so far.", book.getPagesRead());
                        mPagesRead.setText(readSoFar);
                        if(pagesPerDay == 0 && pages < book.getNumberofPages()) {
                            String formattedString = String.format("You only have to read %d pages over the next %d days.", book.getNumberofPages()-pages, daysLeftAsInt);
                            mDaysLeft.setText(formattedString);
                        } else {
                            String pagesString;
                            if (pagesPerDay > 1) {
                                pagesString = "pages";
                            } else {
                                pagesString = "page";
                            }
                            String formattedString = String.format("You need to read %d %s for the next %d days.", pagesPerDay, pagesString, daysLeftAsInt);
                            mDaysLeft.setText(formattedString);
                        }
                    }
                }
            });


            itemView.setOnClickListener(this);

        }


        public void bindMainBook(final Book book) {
            mBookTitle.setText(book.getTitle());
            mAuthor.setText("By: " + book.getAuthor());
            mDateStarted.setText("Started On: " + book.getDateStartedAsString());
            mDateToFinish.setText("Finish By: " + book.getDateToFinishAsString());
            //int daysLeftAsInt = Integer.parseInt(book.getDaysToFinish().toString());
            int daysLeftAsInt = book.getDaysToFinish().getDays();
            int pagesPerDay = (book.getNumberofPages() - book.getPagesRead()) / daysLeftAsInt;
            String readSoFar = String.format("You have read %d pages so far.", book.getPagesRead());
            mPagesRead.setText(readSoFar);
            if(pagesPerDay == 0 && book.getPagesRead() < book.getNumberofPages()) {
                String formattedString = String.format("You only have to read %d pages over the next %d days.", book.getNumberofPages()-book.getPagesRead(), daysLeftAsInt);
                mDaysLeft.setText(formattedString);
            } else {
                String pagesString;
                if (pagesPerDay > 1) {
                    pagesString = "pages";
                } else {
                    pagesString = "page";
                }
                String formattedString = String.format("You need to read %d %s for the next %d days.", pagesPerDay, pagesString, daysLeftAsInt);
                mDaysLeft.setText(formattedString);
            }

        }

        @Override
        public void onClick(View v) {

        }
    }
}