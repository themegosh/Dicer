package ca.dmdev.dicer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by mathe_000 on 2015-12-02.
 */
public class DatabaseConnector
{
    // database name
    private static final String DB_SEQUENCE_FAVOURITES = "sequenceFavourites";
    private static final String TABLE_SEQUENCE_FAVOURITES = "favourites";
    private static final String TABLE_SEQUENCE_ACTIONS = "actions";
    private SQLiteDatabase dbFav; // database object
    private DatabaseOpenHelper databaseHelperFavourites; // database helper

    // public constructor for DatabaseConnector
    public DatabaseConnector(Context context)
    {
        // create a new DatabaseOpenHelper
        databaseHelperFavourites = new DatabaseOpenHelper(context, DB_SEQUENCE_FAVOURITES, null, 1);
    } // end DatabaseConnector constructor

    // open the database connection
    public void open() throws SQLException
    {
        // create or open a database for reading/writing
        dbFav = databaseHelperFavourites.getWritableDatabase();
    } // end method open

    // close the database connection
    public void close()
    {
        if (dbFav != null)
            dbFav.close(); // close the database connection
    } // end method close

    // inserts a new contact in the database
    public void insertSequence(FavouriteSequence sq)
    {
        ContentValues sequenceData = new ContentValues();
        sequenceData.put("title", sq.getTitle());
        sequenceData.put("total", sq.getTotal());
        sequenceData.put("sequenceData", sq.getSequenceData());

        open(); // open the database
        long lastId = dbFav.insert(TABLE_SEQUENCE_FAVOURITES, null, sequenceData);
        sq.setId(lastId);
        //add each action to its own row in the DB
        for (int i = 0; i < sq.getSq().size(); i++) {
            ContentValues actions = new ContentValues();
            actions.put("sequenceId", lastId);
            actions.put("orders", i);
            actions.put("action", sq.getSq().get(i).toString());
            dbFav.insert(TABLE_SEQUENCE_ACTIONS, null, actions);
        }

        close(); // close the database
    } // end method insertContact

    // inserts a new contact in the database
    public void updateSequence(FavouriteSequence sq)
    {
        ContentValues sequenceData = new ContentValues();
        sequenceData.put("total", sq.getTotal());
        sequenceData.put("sequenceData", sq.getSequenceData());

        open(); // open the database
        dbFav.update(TABLE_SEQUENCE_FAVOURITES, sequenceData, "id=" + sq.getId(), null);

        close(); // close the database
    } // end method updateContact

    // return a Cursor with all contact information in the database
    public ArrayList<FavouriteSequence> getAllFavourites() {
        ArrayList<FavouriteSequence> afs = new ArrayList<>();
        FavouriteSequence fs =  new FavouriteSequence();

        open();
        try {
            Cursor c = dbFav.query(TABLE_SEQUENCE_FAVOURITES, new String[]{"id", "title", "total", "sequenceData"},
                    null, null, null, null, "id");
            // Move to first row
            if (!c.moveToFirst())
                return afs;
            do {
                fs.setId((int)c.getLong(0));
                fs.setTitle(c.getString(1));
                fs.setLastTotal(c.getInt(2));
                fs.setSequenceData(c.getString(3));

                //retrieve the actions table
                Cursor ca = dbFav.query(TABLE_SEQUENCE_ACTIONS, new String[]{"action"},
                        "sequenceId=" + fs.getId() , null, null, null, null);
                if (!ca.moveToFirst())
                    break;
                else {
                    do {
                        fs.addAction(ca.getString(0));
                    } while (ca.moveToNext());
                }

                afs.add(fs.clone());
            }  while(c.moveToNext());
            c.close();
            close();
            return afs;
        }
        finally {
            close();
        }
    } // end method getAllContacts

    // delete the contact specified by the given String name
    public void deleteContact(long id)
    {
        open(); // open the database
        dbFav.delete(TABLE_SEQUENCE_FAVOURITES, "id=" + id, null);
        dbFav.delete(TABLE_SEQUENCE_ACTIONS, "sequenceId=" + id, null);
        close(); // close the database
    } // end method deleteContact

    private class DatabaseOpenHelper extends SQLiteOpenHelper
    {
        // public constructor
        public DatabaseOpenHelper(Context context, String name,
                                  SQLiteDatabase.CursorFactory factory, int version)
        {
            super(context, name, factory, version);
        } // end DatabaseOpenHelper constructor

        // creates the contacts table when the database is created
        @Override
        public void onCreate(SQLiteDatabase db)
        {
            // query to create a new table named contacts
            String createSequenceFavourites = "CREATE TABLE "+ TABLE_SEQUENCE_FAVOURITES +
                    " (id integer primary key autoincrement," +
                    "title TEXT, total INT, sequenceData TEXT," +
                    "actionId INT);";

            db.execSQL(createSequenceFavourites); // execute the query

            String createSequenceActions = "CREATE TABLE "+ TABLE_SEQUENCE_ACTIONS +
                    " (sequenceId INT, orders INT, action TEXT);";
            db.execSQL(createSequenceActions); // execute the query
        } // end method onCreate

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion,
                              int newVersion)
        {
        } // end method onUpgrade
    } // end class DatabaseOpenHelper


} // end class DatabaseConnector