package ca.dmdev.dicer;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class SaveFavourite extends ActionBarActivity {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */

    // UI references.
    private TextView txtTitle;
    private TextView txtSequence;
    private View mLoginFormView;
    private Sequence sq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_favourite);

        // Set up the login form.
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtSequence = (TextView) findViewById(R.id.txtSequence);

        sq = (Sequence) getIntent().getSerializableExtra("Sequence");
        txtSequence.setText(sq.toString());

        Button btnSaveRoll = (Button) findViewById(R.id.favourites_save_button);
        btnSaveRoll.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                saveFav();
            }
        });

        mLoginFormView = findViewById(R.id.save_favouite_form);
    }

    private void saveFav(){
        //verify input
        if (txtTitle.getText().toString().length() <= 0){
            txtTitle.setError(getString(R.string.error_field_required));
            txtTitle.requestFocus();
        }
        else { //valid input
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            intent.putExtra("Sequence", sq);
            intent.putExtra("Title", txtTitle.getText().toString());
            finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:

                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    finish();
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    //No button clicked
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage(getText(R.string.confirm_discard_unsaved)).setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();

                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }


}

