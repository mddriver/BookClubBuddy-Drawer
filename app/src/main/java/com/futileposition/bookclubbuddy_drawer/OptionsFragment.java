package com.futileposition.bookclubbuddy_drawer;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.Toast;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.futileposition.bookclubbuddy_drawer.SqlBookDatabase.TABLE_NAME;

/**
 * Created by MD on 6/21/2017.
 */

public class OptionsFragment extends Fragment  {
    public static final String ARG_OPTION_NUMBER = "option_number";
    private MainBookAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    private Button mSubmitButton;



    public interface OnBookSelectedInterface {
        void onBookRecipeSelected(int index);
    }

    public OptionsFragment() {
        // Empty constructor required for fragment subclasses
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        int i = getArguments().getInt(ARG_OPTION_NUMBER);
        View rootView = null;
        if(i == 0) {
            //TO FIX: SET THIS TO SHOW CURRENT BOOKS
            rootView = inflater.inflate(R.layout.view_current_book, container, false);

            getActivity().setTitle("Book Club Buddy");

            RecyclerView mBooksReadingRecyclerView = (RecyclerView) rootView.findViewById(R.id.current_books_recycler_view);
            SqlBookDatabase mDbHelper = new SqlBookDatabase(container.getContext());
            SQLiteDatabase db = mDbHelper.getReadableDatabase();

            //Create a cursor and get all of the information from the SQLiteDatabase
            Cursor cursor = db.query(
                    TABLE_NAME,                     // The table to query
                    null,                               // The columns to return, null reads all
                    null,                                // The columns for the WHERE clause
                    null,                            // The values for the WHERE clause
                    null,                                     // don't group the rows
                    null,                                     // don't filter by row groups
                    null                                 // The sort order
            );
            ArrayList<Book> books = new ArrayList<Book>();
            while(cursor.moveToNext()) {
                Book tempBook = new Book();
                tempBook.setAppIndex(cursor.getInt(0));
                tempBook.setTitle(cursor.getString(1));
                tempBook.setAuthor(cursor.getString(2));
                tempBook.setNumberofPages(cursor.getInt(3));
                DateTime firstDate = new DateTime();
                DateTimeFormatter timeFormat = DateTimeFormat.forPattern("yyyy-MM-dd");
                tempBook.setDateStarted(timeFormat.parseDateTime(cursor.getString(4)));
                tempBook.setDateToFinish(timeFormat.parseDateTime(cursor.getString(5)));
                tempBook.setPagesRead(cursor.getInt(6));
                books.add(tempBook);
            }
            cursor.close();
            Book[] myDataset = books.toArray(new Book[books.size()]);


            mAdapter = new MainBookAdapter(container.getContext(), myDataset);
            mBooksReadingRecyclerView.setAdapter(mAdapter);
            mLayoutManager = new LinearLayoutManager(container.getContext());
            mBooksReadingRecyclerView.setLayoutManager(mLayoutManager);

        } else if (i == 1) {
            //TO FIX: Set this to show book add interface.
            rootView = inflater.inflate(R.layout.content_new_book, container, false);
            mSubmitButton = (Button) rootView.findViewById(R.id.submitNewBook);
            final View finalRootView = rootView;

            mSubmitButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    EditText authorText = (EditText) finalRootView.findViewById(R.id.bookAuthorText);
                    String author = authorText.getText().toString();
                    EditText titleText = (EditText) finalRootView.findViewById(R.id.bookTitleText);
                    String title = titleText.getText().toString();
                    EditText pagesText = (EditText) finalRootView.findViewById(R.id.bookTotalPagesText);
                    int pages = 0;
                    try {
                        pages = Integer.parseInt(pagesText.getText().toString());
                    } catch (NumberFormatException e) {
                        Toast.makeText(getContext(), "Pages must be a number", Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (author.length() == 0 || title.length() == 0 || pages <= 0) {
                        Toast.makeText(getContext(), "Please fill in all fields. Pages must be greater than 0!", Toast.LENGTH_LONG).show();
                        return;
                    }
                    SimpleDateFormat dateFormat = new SimpleDateFormat(
                            "yyyy-MM-dd");
                    Date date = new Date();
                    String start_date = dateFormat.format(date);
                    DatePicker goalDatePicker = (DatePicker) finalRootView.findViewById(R.id.goalDatePicker);
                    Date goalAsDate = new Date(goalDatePicker.getYear() - 1900, goalDatePicker.getMonth(), goalDatePicker.getDayOfMonth());


                    String end_date = dateFormat.format(goalAsDate);
                    if (goalAsDate.before(date) || goalAsDate.equals(date)) {
                        Toast.makeText(getContext(), "Surely you at least want to give yourself until tomorrow, right?", Toast.LENGTH_LONG).show();
                        return;
                    }
                    //Create a new SQL Entry with Title, Author, Pages, and Date Finished.
                    com.futileposition.bookclubbuddy_drawer.SqlBookDatabase.createBook(getContext(), title, author, pages, start_date, end_date);
                }
            });
            getActivity().setTitle("Add A New Book");
        }
        return rootView;
    }

}