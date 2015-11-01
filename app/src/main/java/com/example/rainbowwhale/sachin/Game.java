package com.example.rainbowwhale.sachin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class Game extends Activity {

    Integer round = 4;
    Integer match;
    Integer iR, iM;
    Integer cRound;
    User UL_Game;

    TextView tv_Right, tv_Left, tv_Round;
    Button bt_Next;


    RadioGroup radioGroup;
    RadioButton rb_Left, rb_Right;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent intentGame = getIntent();
        UL_Game = intentGame.getParcelableExtra("userList");
        if(UL_Game == null){
            Toast.makeText(this,"User List is Null",Toast.LENGTH_SHORT).show();
            return;
        }

        tv_Round = (TextView)findViewById(R.id.Game_textView_Round);
        tv_Left = (TextView)findViewById(R.id.Game_textView_Left);
        tv_Right = (TextView)findViewById(R.id.Game_textView_Right);
        radioGroup = (RadioGroup)findViewById(R.id.Game_selection);
        rb_Left = (RadioButton)findViewById(R.id.Game_radioButton_1);
        rb_Right = (RadioButton)findViewById(R.id.Game_radioButton_2);

        bt_Next = (Button)findViewById(R.id.Game_button_next);


        iM = 0;
        iR = 0;
        match = (int)Math.pow(2,(round-iR-1));//number of match of current round

        cRound = (int)Math.pow(2, (round - iR));//current round index
        tv_Round.setText(cRound.toString() + "강 [" + (iM+1) + "/" + (cRound/2) + "]");

        tv_Right.setText(UL_Game.getUser(iM).getDesc());
        tv_Left.setText(UL_Game.getUser(iM + 1).getDesc());


        bt_Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((iM<match-1)&&(iR<round-1)){
                    //every not final matches
                    if(rb_Left.isChecked())
                    {
                        UL_Game.DropOut(iM + 1);
                        iM += 1;
                    }
                    else if(rb_Right.isChecked())
                    {
                        UL_Game.DropOut(iM);
                        iM += 1;
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"반드시 한명을 선택해주세요", Toast.LENGTH_SHORT).show();
                    }
                    tv_Right.setText(UL_Game.getUser(iM).getDesc());
                    tv_Left.setText(UL_Game.getUser(iM + 1).getDesc());
                    tv_Round.setText(cRound.toString() + "강 [" + (iM+1) + "/" + (cRound/2) + "]");
                }
                else if((iM==(match-1))&&(iR<round-1)){
                    //final match of round
                    if(rb_Left.isChecked())
                    {
                        UL_Game.DropOut(iM+1);
                        iM = 0;
                        iR += 1;
                        cRound = (int)Math.pow(2,(round-iR));//current round index
                        //tv_Round.setText(cRound.toString()+"강");
                        tv_Round.setText(cRound.toString() + "강 [" + (iM+1) + "/" + (cRound/2) + "]");
                        match = (int)Math.pow(2,(round-iR-1));//number of match of current round
                        tv_Right.setText(UL_Game.getUser(iM).getDesc());
                        tv_Left.setText(UL_Game.getUser(iM + 1).getDesc());
                    }
                    else if(rb_Right.isChecked())
                    {
                        UL_Game.DropOut(iM);
                        iM = 0;
                        iR += 1;
                        cRound = (int)Math.pow(2,(4-iR));//current round index
                        //tv_Round.setText(cRound.toString()+"강");
                        tv_Round.setText(cRound.toString() + "강 [" + (iM+1) + "/" + (cRound/2) + "]");
                        match = (int)Math.pow(2,(round-iR-1));//number of match of current round
                        tv_Right.setText(UL_Game.getUser(iM).getDesc());
                        tv_Left.setText(UL_Game.getUser(iM + 1).getDesc());
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"반드시 한명을 선택해주세요", Toast.LENGTH_SHORT).show();
                    }
                }
                else if((iM==(match-1))&&(iR==(round-1))){
                    //final match of final round
                    if(rb_Left.isChecked())
                    {
                        UL_Game.DropOut(iM+1);
                        Intent intentGameL = new Intent(Game.this, ShowWinner.class);
                        intentGameL.putExtra("winner", UL_Game);
                        startActivity(intentGameL);
                        finish();
                    }
                    else if(rb_Right.isChecked())
                    {
                        UL_Game.DropOut(iM);
                        Intent intentGameL = new Intent(Game.this, ShowWinner.class);
                        intentGameL.putExtra("winner", UL_Game);
                        startActivity(intentGameL);
                        finish();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"반드시 한명을 선택해주세요", Toast.LENGTH_SHORT).show();
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
