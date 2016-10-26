package com.winduo;

import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.graphics.Matrix;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class ChatActivity extends AppCompatActivity {
    public static String TAG = ChatActivity.class.getSimpleName();

    ListView mainchatListView;
    ListView subchatListView;
    ListView subchatSelectionListView;
    EditText editText;

    Cursor subchatSelectionCursor;

    private String subchatSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatscreen);

        this.mainchatListView = (ListView) findViewById(R.id.mainchat_area);
        this.subchatListView = (ListView) findViewById(R.id.subchat_area);
        this.subchatSelectionListView = (ListView) findViewById(R.id.subchat_list);
        this.editText = (EditText) findViewById(R.id.new_message_edittext) ;

        Intent intent = getIntent();
        String chatname = intent.getStringExtra(HomeActivity.CHATNAME);

        TextView title = (TextView) findViewById(R.id.chat_title);
        title.setText(chatname);

        setMainChatView(chatname);
        setSubChatView("");
        setSubchatSelectionListView();
        setUpMainChatListener();
        setUpSubchatSelectionListener();

    }


    private void setMainChatView(String chatname) {
        String[] from = new String[] {"user", "message", "time"};
        int[] to = new int[] {R.id.message_user, R.id.chat_message, R.id.message_date};
        Cursor c = createCursor("main");

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.my_message_listview_item, c, from, to, BIND_IMPORTANT);
        mainchatListView.setAdapter(adapter);
    }

    private void setSubChatView(String subchatname) {
        if (subchatname.isEmpty()) {
            this.setSubchatInvisible();
            return;
        }

        this.setSubchatVisible();

        String[] from = new String[] {"user", "message", "time"};
        int[] to = new int[] {R.id.message_user, R.id.chat_message, R.id.message_date};
        Cursor c = createCursor("sub");

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.my_message_listview_item, c, from, to, BIND_IMPORTANT);
        subchatListView.setAdapter(adapter);
    }

    private void setSubchatSelectionListView() {
        String[] from = new String[] {"user", "first_message"};
        int[] to = new int[] {R.id.subchat_creator_text, R.id.subchat_heading_text};
        Cursor c = createCursor2();
        this.subchatSelectionCursor = c;


        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.subchat_listview_item, c, from, to, BIND_IMPORTANT);
        subchatSelectionListView.setAdapter(adapter);
    }

    private void setUpMainChatListener() {
        this.mainchatListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "MainChat OnClick");
                setSubchatInvisible();
            }
        });
    }

    private void setUpSubchatSelectionListener() {
        this.subchatSelectionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                subchatSelectionCursor.moveToPosition(position);
                String subchatname = subchatSelectionCursor.getString(subchatSelectionCursor.getColumnIndex("subchat_name"));
                setSubChatView(subchatname);
            }
        });
    }

    public void sendMessage(View v) {
        Log.d(TAG, "sendMessage: " + editText.getText());
    }

    public void createSubChat(View v) {
        Log.d(TAG, "create new sub chat");
    }

    private void setSubchatInvisible() {
        this.subchatListView.setVisibility(View.GONE);
    }

    private void setSubchatVisible() {
        this.subchatListView.setVisibility(View.VISIBLE);
    }

    private MatrixCursor createCursor(String append) {
        String[] columns = new String[] { "_id", "user", "message", "time", "chatname" };

        MatrixCursor matrixCursor = new MatrixCursor(columns);
        startManagingCursor(matrixCursor);

        matrixCursor.addRow(new Object[] { 1, "user1" + append, "I think they're amazing", "20:30pm", "chatname1" });
        matrixCursor.addRow(new Object[] { 1, "user2" + append, "No way, what are you even talking about", "20:30pm", "chatname1" });
        matrixCursor.addRow(new Object[] { 1, "user3" + append, "I agree", "20:30pm", "chatname1" });
        matrixCursor.addRow(new Object[] { 1, "user4" + append, "They're awesome", "20:30pm", "chatname1" });
        matrixCursor.addRow(new Object[] { 1, "user5" + append, "Trash", "20:30pm", "chatname1" });
        return matrixCursor;
    }

    private MatrixCursor createCursor2() {
        String[] columns = new String[] { "_id", "user", "first_message", "subchat_name" };

        MatrixCursor matrixCursor = new MatrixCursor(columns);
        startManagingCursor(matrixCursor);

        matrixCursor.addRow(new Object[] { 1, "Jean", "So when are we meeting up?", "shark" });
        matrixCursor.addRow(new Object[] { 1, "Alex", "Do I owe anyone money?", "shark" });
        matrixCursor.addRow(new Object[] { 1, "Maria", "About last night...", "shark" });
        matrixCursor.addRow(new Object[] { 1, "Josh", "Anyone coming out tonight?", "girafe" });
        matrixCursor.addRow(new Object[] { 1, "Trevor", "Lol, check this out", "girafe" });


        return matrixCursor;
    }
}
