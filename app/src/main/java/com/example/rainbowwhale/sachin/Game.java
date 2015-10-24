package com.example.rainbowwhale.sachin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class Game extends Activity {

    Integer round = 3;
    Integer match;
    Integer iR, iM;
    Integer cRound;
    String sel_UL;
    User UL_Game;

    TextView tv_Right, tv_Left, tv_Round;
    Button bt_Next;


    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent intentGame = getIntent();
        UL_Game = (User)intentGame.getParcelableExtra("userList");
        if(UL_Game == null){
            Toast.makeText(this,"User List is Null",Toast.LENGTH_SHORT).show();
            return;
        }

        cRound = (int)Math.pow(2,4);
        tv_Round = (TextView)findViewById(R.id.Game_textView_Round);
        tv_Round.setText(cRound.toString()+"강");
        tv_Right = (TextView)findViewById(R.id.textView_Right);
        tv_Left = (TextView)findViewById(R.id.textView_Left);
        radioGroup = (RadioGroup)findViewById(R.id.Game_selection);
        iM = 0;
        iR = 0;
        tv_Right.setText((CharSequence) UL_Game.getUser(iM).getDesc());
        tv_Left.setText((CharSequence) UL_Game.getUser(iM+1).getDesc());
        bt_Next = (Button)findViewById(R.id.Game_button_next);
        bt_Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                match = (int)Math.pow(2,(3-iR));
                cRound = (int)Math.pow(2,(4-iR));
                tv_Round.setText(cRound.toString()+"강");
                if(iM<match){
                    RadioButton rb_Sel = (RadioButton)findViewById(radioGroup.getCheckedRadioButtonId());
                    tv_Right.setText((CharSequence) UL_Game.getUser(iM).getDesc());
                    tv_Left.setText((CharSequence) UL_Game.getUser(iM+1).getDesc());
                    sel_UL = rb_Sel.getText().toString();
                    if(sel_UL.equalsIgnoreCase("Left"))
                    {
                        UL_Game.DropOut(iM+1);
                        iM += 1;
                    }
                    else if(sel_UL.equalsIgnoreCase("Right"))
                    {
                        UL_Game.DropOut(iM);
                        iM += 1;
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"반드시 한명을 선택해주세요", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    if(iR<round){
                        iM = 0;
                        iR = iR + 1;
                    }
                    else{
                        //
                        Intent intentGameL = new Intent(Game.this, Result.class);
                        intentGameL.putExtra("winner", UL_Game);
                        startActivity(intentGameL);
                    }
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game, menu);
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
