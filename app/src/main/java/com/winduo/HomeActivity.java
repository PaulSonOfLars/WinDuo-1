package com.winduo;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.io.Console;

public class HomeActivity extends AppCompatActivity {
    public static final String TAG = HomeActivity.class.getSimpleName();
    public static final String CHATNAME = "CHATNAME";

    private ListView listView;
    private Cursor chatlistCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_chat_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        this.listView = (ListView) findViewById(R.id.groupchat_listview);


        this.setUpListView();
        this.setUpListener();

    }


    private void setUpListView() {
        Cursor c = createCursor(); //give me a cursor.
        this.chatlistCursor = c;
        String[] from = new String[] {"groupchat_name"};
        int[] to = new int[] {R.id.groupchat_name};

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.groupchat_listview_item, c, from, to, BIND_IMPORTANT);
        listView.setAdapter(adapter);
    }

    private void setUpListener() {
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {


                Intent intent = new Intent(HomeActivity.this, ChatActivity.class);


                chatlistCursor.moveToPosition(position);
                String chatname = chatlistCursor.getString(chatlistCursor.getColumnIndex("groupchat_name"));

                Log.d(TAG, "Clicked " + String.valueOf(position) + ". " + chatname);

                intent.putExtra(CHATNAME, chatname);
                startActivity(intent);

            }

        });
    }

    private MatrixCursor createCursor() {
        String[] columns = new String[] { "_id", "groupchat_name" };

        MatrixCursor matrixCursor = new MatrixCursor(columns);
        startManagingCursor(matrixCursor);

        matrixCursor.addRow(new Object[] { 1, "Goat" });
        matrixCursor.addRow(new Object[] { 2, "Girafe debate" });
        matrixCursor.addRow(new Object[] { 3, "Hippo discussion" });
        matrixCursor.addRow(new Object[] { 5, "Shark chat" });
        matrixCursor.addRow(new Object[] { 6, "Squirrel talk" });
        matrixCursor.addRow(new Object[] { 7, "Pig parlay" });
        matrixCursor.addRow(new Object[] { 8, "Animal farm" });
        matrixCursor.addRow(new Object[] { 9, "Tortoise..." });
        matrixCursor.addRow(new Object[] { 10, "Snake chat" });

        return matrixCursor;
    }
}
