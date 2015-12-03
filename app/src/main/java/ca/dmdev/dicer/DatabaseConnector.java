package ca.dmdev.dicer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mathe_000 on 2015-12-02.
 */
public class DatabaseConnector
{
   /* // database name
    private static final String DB_SEQUENCE_FAVOURITES = "sequenceFavourites";
    private static final String DB_SEQUENCE_ACTIONS = "sequenceActions";
    private SQLiteDatabase dbFav; // database object
    private SQLiteDatabase dbAct; // database object
    private DatabaseOpenHelper databaseHelperFavourites; // database helper
    private DatabaseOpenHelper databaseHelperActions; // database helper

    // public constructor for DatabaseConnector
    public DatabaseConnector(Context context)
    {
        // create a new DatabaseOpenHelper
        databaseHelperFavourites = new DatabaseOpenHelper(context, DB_SEQUENCE_FAVOURITES, null, 1);
        databaseHelperActions = new DatabaseOpenHelper(context, DB_SEQUENCE_ACTIONS, null, 1);
    } // end DatabaseConnector constructor

    // open the database connection
    public void open() throws SQLException
    {
        // create or open a database for reading/writing
        dbFav = databaseHelperFavourites.getWritableDatabase();
        dbAct = databaseHelperActions.getWritableDatabase();
    } // end method open

    // close the database connection
    public void close()
    {
        if (dbFav != null)
            dbFav.close(); // close the database connection\
        if (dbAct != null)
            dbAct.close(); // close the database connection
    } // end method close

    // inserts a new contact in the database
    public void insertSequence(FavouriteSequence sq)
    {
        ContentValues sequenceData = new ContentValues();
        sequenceData.put("title", sq.getTitle());
        sequenceData.put("total", sq.getTotal());
        sequenceData.put("sequenceData", sq.getSequenceData());
        sequenceData.put("actionsId", acitonsIndex);

        open(); // open the database
        database.insert("contacts", null, newContact);
        close(); // close the database
    } // end method insertContact

    // inserts a new contact in the database
    public void updateContact(long id, String name, String email,
                              String phone, String state, String city)
    {
        ContentValues editContact = new ContentValues();
        editContact.put("name", name);
        editContact.put("email", email);
        editContact.put("phone", phone);
        editContact.put("street", state);
        editContact.put("city", city);

        open(); // open the database
        database.update("contacts", editContact, "_id=" + id, null);
        close(); // close the database
    } // end method updateContact

    // return a Cursor with all contact information in the database
    public Cursor getAllContacts()
    {
        return database.query("contacts", new String[] {"_id", "name"},
                null, null, null, null, "name");
    } // end method getAllContacts

    // get a Cursor containing all information about the contact specified
    // by the given id
    public Cursor getOneContact(long id)
    {
        return database.query(
                "contacts", null, "_id=" + id, null, null, null, null);
    } // end method getOnContact

    // delete the contact specified by the given String name
    public void deleteContact(long id)
    {
        open(); // open the database
        database.delete("contacts", "_id=" + id, null);
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
            String createQuery = "CREATE TABLE contacts" +
                    "(id integer primary key autoincrement," +
                    "title TEXT, total INT, sequenceData TEXT," +
                    "actionId INT);";

            db.execSQL(createQuery); // execute the query
        } // end method onCreate

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion,
                              int newVersion)
        {
        } // end method onUpgrade
    } // end class DatabaseOpenHelper
    */
} // end class DatabaseConnector
