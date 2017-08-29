package com.japtej.memorypalace;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView list;
    Toolbar toolbar1;
    DrawerLayout drawerLayout;
    ArrayList<String> mp_list;
    FloatingActionButton fab;
    DBHandler dbHandler;
    String result;
    ArrayAdapter<String> adapter;
    int location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHandler = new DBHandler(this, null, null, 1);

        list = (ListView)findViewById(R.id.list);
        toolbar1 = (Toolbar)findViewById(R.id.toolbar1);
        toolbar1.setTitle(getResources().getString(R.string.app_name));
        setSupportActionBar(toolbar1);

        mp_list= new ArrayList<>();
        List<Palace> palaces = dbHandler.getAllPalaces();
        for(Palace p: palaces){
            String name = p.get_name();
            mp_list.add(name);
        }

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,mp_list);
        list.setAdapter(adapter);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.edit_palace, null);
                final EditText editText = (EditText)mView.findViewById(R.id.editText);
                final TextView textView = (TextView)mView.findViewById(R.id.editTitle);
                textView.setText("Add Palace");
                Button mButton = (Button)mView.findViewById(R.id.editConfirmButton);
                mButton.setText("Add Palace");
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();
                mButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String newPalace= editText.getText().toString();
                        dbHandler.addPalace(new Palace(newPalace));
                        mp_list.add(newPalace);
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(),"Added",Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, PalaceDetailActivity.class);
                intent.putExtra("PalaceName",list.getItemAtPosition(i).toString());
                intent.putExtra("PalaceLocation",i);
                startActivityForResult(intent,1);
            }
        });

        drawerLayout = (DrawerLayout)findViewById(R.id.drawerLayout);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int res_id = item.getItemId();
        switch (res_id){
            case R.id.action_settings:
                Toast.makeText(getApplicationContext(),"You selected settings", Toast.LENGTH_SHORT).show();
            break;
            case R.id.action_share:
                Toast.makeText(getApplicationContext(),"You selected share", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_search:
                Toast.makeText(getApplicationContext(),"You selected search", Toast.LENGTH_SHORT).show();
                break;
            default: break;

        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            if(resultCode== Activity.RESULT_OK) {
                if(data.getStringExtra("deletePalace")==null) {

                    result = data.getStringExtra("editPalace");
                    location = Integer.parseInt(data.getStringExtra("itemLocation"));
                    mp_list.set(location, result);
                    adapter.notifyDataSetChanged();
                }
                else {
                    result = data.getStringExtra("deletePalace");
                    mp_list.remove(result);
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }
}
