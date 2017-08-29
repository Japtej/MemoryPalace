package com.japtej.memorypalace;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PalaceDetailActivity extends AppCompatActivity {

    Toolbar toolbar;
    int listItemLocation;
    EditText editText;
    DBHandler dbHandler;
    Intent returnIntent = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_palace_detail);



        toolbar = (Toolbar)findViewById(R.id.toolbar2);

        final Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            toolbar.setTitle(bundle.getString("PalaceName"));
            listItemLocation = bundle.getInt("PalaceLocation");
        }
        setSupportActionBar(toolbar);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_palace, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int res_id= item.getItemId();
        dbHandler = new DBHandler(this, null, null, 1);

        if(res_id==R.id.action_edit){



            AlertDialog.Builder mBuilder = new AlertDialog.Builder(PalaceDetailActivity.this);
            mBuilder.setNegativeButton("Cancel", null);
            View mView = getLayoutInflater().inflate(R.layout.edit_palace, null);
            editText = (EditText)mView.findViewById(R.id.editText);
            Button mButton = (Button)mView.findViewById(R.id.editConfirmButton);
            mBuilder.setView(mView);
            final AlertDialog dialog = mBuilder.create();
            dialog.show();



            mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!editText.getText().toString().isEmpty()){
                        String editPalace = editText.getText().toString();
//                        int id=dbHandler.upgradePalace(new Palace(listItemLocation+1,editPalace));
                        dialog.dismiss();
                        toolbar.setTitle(editPalace);
                        String location = String.valueOf(listItemLocation);
                        returnIntent.putExtra("editPalace", editPalace);
                        returnIntent.putExtra("itemLocation", location);
                        setResult(Activity.RESULT_OK, returnIntent);
                        Toast.makeText(getApplicationContext(), "Edited Successfully",Toast.LENGTH_SHORT).show();
                    }

                    else{
                        Toast.makeText(getApplicationContext(), "Please fill name",Toast.LENGTH_SHORT).show();
                    }

                }
            });

            }
         else{
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(PalaceDetailActivity.this);
            mBuilder.setMessage("Are you sure you want to DELETE?")
                    .setCancelable(false)
                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Bundle bundle = getIntent().getExtras();
                            String name = bundle.getString("PalaceName");
                            //Toast.makeText(getApplicationContext(), "The name of the deleted palace will be "+name, Toast.LENGTH_SHORT).show();
                            dbHandler.deletePalace(new Palace(name));
                            returnIntent.putExtra("deletePalace", name);
                            setResult(Activity.RESULT_OK,returnIntent);
                            dialogInterface.dismiss();
                            finish();
                        }
                    })
                    .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
            AlertDialog alert = mBuilder.create();
            alert.show();
        }

        return super.onOptionsItemSelected(item);
    }
}
