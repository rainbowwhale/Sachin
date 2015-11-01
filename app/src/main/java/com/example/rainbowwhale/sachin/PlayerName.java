package com.example.rainbowwhale.sachin;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PlayerName extends Activity {
    Button bt_Next;
    EditText et_Name;
    EditText et_Nick;
    String playerName;
    String playerNick;

    SharedPreferences mPref;
    SharedPreferences.Editor mPrefEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_name);

        et_Name = (EditText)findViewById(R.id.editText_name);
        et_Nick = (EditText)findViewById(R.id.editText_nickname);

        mPref = getSharedPreferences("PrefPlayer", 0);
        playerName = mPref.getString("pName", "");
        playerNick = mPref.getString("pNick", "");

        et_Name.setText(playerName);
        et_Nick.setText(playerNick);

        bt_Next = (Button)findViewById(R.id.button_next);
        bt_Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPref = getSharedPreferences("PrefPlayer", 0);
                mPrefEdit = mPref.edit();
                playerName = et_Name.getText().toString();
                playerNick = et_Nick.getText().toString();
                mPrefEdit.putString("pName", playerName);
                mPrefEdit.putString("pNick", playerNick);
                if(playerName.length()<1)
                    Toast.makeText(getApplicationContext(),"이름이 너무 짧습니다.", Toast.LENGTH_SHORT).show();
                else{
                    mPrefEdit.commit();
                    Intent intentPlayerName = new Intent(PlayerName.this, GenderSelect.class);
                    intentPlayerName.putExtra("playerName", playerName);
                    intentPlayerName.putExtra("playerNick", playerNick);
                    startActivity(intentPlayerName);
                    finish();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_name, menu);
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
}
