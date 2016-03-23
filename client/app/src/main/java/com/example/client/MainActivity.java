package com.example.client;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


public class MainActivity extends ActionBarActivity
        implements AdapterView.OnItemClickListener{

    static final String DB_NAME="BelongDB";
    static final String TB_NAME="belong";
    static final String[] FROM = new String[] {"name","number"};
    SQLiteDatabase db;
    Cursor cur;
    SimpleCursorAdapter adapter1;
    EditText etname,etnumber;
    Button btinsert;
    ListView lv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etname=(EditText)findViewById(R.id.etname);
        etnumber=(EditText)findViewById(R.id.etnumber);

        btinsert=(Button)findViewById(R.id.btinsert);
        // btdelete=(Button)findViewById(R.id.btdelete);

        db=openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE, null);

        String createTable="CREATE TABLE IF NOT EXISTS " + TB_NAME +
                "( _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name VARCHAR(32), " +
                "number  VARCHAR(16))";
        db.execSQL(createTable);

        cur=db.rawQuery("SELECT * FROM " + TB_NAME, null);

        adapter1= new SimpleCursorAdapter(this,R.layout.item,cur,
                FROM,
                new int[] {R.id.name},
                0);
        lv=(ListView)findViewById(R.id.lv);
        lv.setAdapter(adapter1);
        lv.setOnItemClickListener(this);
        this.registerForContextMenu(lv);
        lv.setOnCreateContextMenuListener(menuListener);

        requery();
    }


    private void addData(String name, String number){
        ContentValues cv=new ContentValues(2);
        cv.put(FROM[0],name);
        cv.put(FROM[1],number);

        db.insert(TB_NAME,null,cv);
    }

    private void requery(){
        cur=db.rawQuery("SELECT * FROM "+TB_NAME, null);
        adapter1.changeCursor(cur);
        btinsert.setEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    View.OnCreateContextMenuListener menuListener =
            new View.OnCreateContextMenuListener() {
                public void onCreateContextMenu(ContextMenu menu,View v, ContextMenu.ContextMenuInfo menuInfo) {
                    menu.add(0, 1, 1, "Information");
                    menu.add(0, 2, 2, "Delete");
                }
            };

    public boolean onContextItemSelected(MenuItem aItem) {
        switch (aItem.getItemId()) {
            case 1:
                Intent it = new Intent(this, SocketClient.class);
                String nameStr = cur.getString(1);
                //String numberStr = cur.getString(2);

                it.putExtra("send_name", nameStr);
                //it.putExtra("編號", numberStr);
                startActivity(it);
                break;

            case 2:
                db.delete(TB_NAME,"_id="+cur.getInt(0),null);
                requery();
                break;

        }
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        cur.moveToPosition(position);
        openContextMenu(v);
    }

    public void onInsert(View v){
        String nameStr=etname.getText().toString().trim();
        String numberStr=etnumber.getText().toString().trim();
        etname.setText("");
        etnumber.setText("");

        if(nameStr.length()==0 || numberStr.length()==0) return;

        if(v.getId()==R.id.btinsert)
            addData(nameStr,numberStr);

        requery();
    }










}
