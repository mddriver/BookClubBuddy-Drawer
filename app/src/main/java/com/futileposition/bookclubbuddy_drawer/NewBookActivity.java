package com.futileposition.bookclubbuddy_drawer;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.futileposition.bookclubbuddy_drawer.MainActivity;
import com.futileposition.bookclubbuddy_drawer.R;

import org.joda.time.DateTime;

import java.io.IOError;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.R.attr.duration;
import static android.R.id.edit;
import static com.futileposition.bookclubbuddy_drawer.SqlBookDatabase.TABLE_NAME;

public class NewBookActivity extends AppCompatActivity {

    @BindView(R.id.submitNewBook) Button mSubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_book);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Temporarily set to take me back to main activity.
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewBookActivity.this, MainActivity.class);
                startActivity(intent);
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show(); */
            }
        });

        mSubmitButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                EditText authorText = (EditText) findViewById(R.id.bookAuthorText);
                String author = authorText.getText().toString();
                EditText titleText = (EditText) findViewById(R.id.bookTitleText);
                String title = titleText.getText().toString();
                EditText pagesText = (EditText) findViewById(R.id.bookTotalPagesText);
                int pages = 0;
                try {
                    pages = Integer.parseInt(pagesText.getText().toString());
                } catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(), "Pages must be a number", Toast.LENGTH_LONG).show();
                    return;
                }
                if (author.length() == 0 || title.length() == 0 || pages <= 0) {
                    Toast.makeText(getApplicationContext(), "Please fill in all fields. Pages must be greater than 0!", Toast.LENGTH_LONG).show();
                    return;
                }
                SimpleDateFormat dateFormat = new SimpleDateFormat(
                        "yyyy-MM-dd");
                Date date = new Date();
                String start_date = dateFormat.format(date);
                DatePicker goalDatePicker = (DatePicker) findViewById(R.id.goalDatePicker);
                Date goalAsDate = new Date(goalDatePicker.getYear() - 1900, goalDatePicker.getMonth(), goalDatePicker.getDayOfMonth());


                String end_date = dateFormat.format(goalAsDate);
                if (goalAsDate.before(date) || goalAsDate.equals(date)) {
                    Toast.makeText(getApplicationContext(), "Surely you at least want to give yourself until tomorrow, right?", Toast.LENGTH_LONG).show();
                    return;
                }
                //Create a new SQL Entry with Title, Author, Pages, and Date Finished.
                com.futileposition.bookclubbuddy_drawer.SqlBookDatabase.createBook(getApplicationContext(), title, author, pages, start_date, end_date);
            }
        });
    };
}
