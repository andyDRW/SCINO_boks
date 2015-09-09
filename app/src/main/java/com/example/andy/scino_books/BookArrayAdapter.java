package com.example.andy.scino_books;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

/**
 * Created by andy on 07.09.15.
 */
public class BookArrayAdapter extends ArrayAdapter<Book> {

    private LayoutInflater inflater;

    public BookArrayAdapter( Context context, List<Book> planetList ) {
        super( context, R.layout.book_row, R.id.booksList, planetList );
        // Cache the LayoutInflate to avoid asking for a new one each time.
        inflater = LayoutInflater.from(context) ;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Planet to display
        Book book = (Book) this.getItem( position );

        // The child views in each row.
        CheckBox checkBox ;
        TextView textViewName ;
        TextView textViewAuthor;

        // Create a new row view
        if ( convertView == null ) {
            convertView = inflater.inflate(R.layout.book_row, null);

            // Find the child views.
            textViewName = (TextView) convertView.findViewById( R.id.BookName );
            checkBox = (CheckBox) convertView.findViewById( R.id.CheckBoxRead );
            textViewAuthor = (TextView) convertView.findViewById( R.id.BookAuthor );

            // Optimization: Tag the row with it's child views, so we don't have to
            // call findViewById() later when we reuse the row.
            convertView.setTag( new BookViewHolder(textViewName,checkBox,textViewAuthor) );

            // If CheckBox is toggled, update the planet it is tagged with.
            checkBox.setOnClickListener( new View.OnClickListener() {
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v ;
                    Book book = (Book) cb.getTag();
                    book.setRead( cb.isChecked() );
                }
            });
        }
        // Reuse existing row view
        else {
            // Because we use a ViewHolder, we avoid having to call findViewById().
            BookViewHolder viewHolder = (BookViewHolder) convertView.getTag();
            checkBox = viewHolder.getCheckBox() ;
            textViewName = viewHolder.getTextViewBookName() ;
            textViewAuthor=viewHolder.getTextViewAuthor();
        }

        // Tag the CheckBox with the Planet it is displaying, so that we can
        // access the planet in onClick() when the CheckBox is toggled.
        checkBox.setTag( book );

        // Display planet data
        checkBox.setChecked(book.getRead());
        textViewName.setText(book.getName());
        textViewAuthor.setText(book.getAuthor());

        return convertView;
    }
}
