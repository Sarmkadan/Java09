package applications.editablelistview;

import android.content.ContentQueryMap;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class ViewListContents extends AppCompatActivity {

    private static  int DELETE;
    DatabaseHelper myDB;
    SimpleCursorAdapter scAdapter;
    static public String my = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewlistcontents_layout);

       final ListView listView = (ListView) findViewById(R.id.listView);
        myDB = new DatabaseHelper(this);

        //populate an ArrayList<String> from the database and then view it
        ArrayList<String> theList = new ArrayList<>();
        Cursor data = myDB.getListContents();
        if(data.getCount() == 0){
            Toast.makeText(this, "There are no contents in this list!",Toast.LENGTH_LONG).show();
        }else{
            while(data.moveToNext()){
                theList.add(data.getString(1));
                ListAdapter listAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,theList);
                listView.setAdapter(listAdapter);
            }
        }
     /*   registerForContextMenu(listView);*/
       // listView.setAdapter(scAdapter);
        registerForContextMenu(listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> myAdapter, View myView, int pos, long mylng) {
                Toast.makeText(ViewListContents.this, (listView.getItemAtPosition(pos).toString()), Toast.LENGTH_SHORT).show();
                my =(listView.getItemAtPosition(pos).toString());

                // this is your selected item
            }

            });}
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, DELETE, 0, "delete");
    }
   public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == DELETE) {
            // получаем из пункта контекстного меню данные по пункту списка
            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            // извлекаем id записи и удаляем соответствующую запись в БД
            Toast.makeText(this, my, Toast.LENGTH_SHORT).show();
            myDB.delRec(acmi.id,my);
            // обновляем курсор

            setContentView(R.layout.viewlistcontents_layout);

            final ListView listView = (ListView) findViewById(R.id.listView);
            myDB = new DatabaseHelper(this);

            //populate an ArrayList<String> from the database and then view it
            ArrayList<String> theList = new ArrayList<>();
            Cursor data = myDB.getListContents();
            if(data.getCount() == 0){
                Toast.makeText(this, "There are no contents in this list!",Toast.LENGTH_LONG).show();
            }else{
                while(data.moveToNext()){
                    theList.add(data.getString(1));
                    ListAdapter listAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,theList);
                    listView.setAdapter(listAdapter);
                }
            }
     /*   registerForContextMenu(listView);*/
            // listView.setAdapter(scAdapter);
            registerForContextMenu(listView);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> myAdapter, View myView, int pos, long mylng) {
                    Toast.makeText(ViewListContents.this, (listView.getItemAtPosition(pos).toString()), Toast.LENGTH_SHORT).show();
                    my =(listView.getItemAtPosition(pos).toString());

                    // this is your selected item
                }

            });

            return true;
        }
        return super.onContextItemSelected(item);
    }

}
