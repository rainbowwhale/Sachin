package com.example.rainbowwhale.sachin;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;


public class GenderSelect extends Activity {

    //위젯 객체
    Button bt_SelectMale, bt_SelectFemale, bt_SelectMix;
    String pName, pNick;
    //유저 리스트 객체
    User UL, UL_male, UL_female, UL_game;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gender_select);

        //플레이어의 이름과 별명
        pName = getIntent().getStringExtra("playerName");
        pNick = getIntent().getStringExtra("playerNick");

        //유저 리스트 파일
        AssetManager assetMgr = getAssets();
        try {InputStream is = assetMgr.open("sachin_user.txt");
            if (is != null) {
                BufferedReader bfReader = new BufferedReader(new InputStreamReader(is,"euc-kr"));
                if (bfReader != null) {
                    //System.out.println("Sheet opened!");
                    String csvLine;
                    String[] tmp;
                    int gender, profileNumber;
                    csvLine=bfReader.readLine();// skip first line
                    csvLine=bfReader.readLine();
                    tmp = csvLine.split("\t");// 유저리스트 파일이 tap spaced 포멧이어야 함

                    //리스트의 이름이 플레이어의 이름과 같으면 넘김
                    if(tmp[1].equalsIgnoreCase(pName)){
                        csvLine=bfReader.readLine();
                        tmp = csvLine.split("\t");
                    }

                    if(tmp[3].equalsIgnoreCase("male")){
                        gender = 0;
                    }
                    else if(tmp[3].equalsIgnoreCase("female")){
                        gender = 1;
                    }
                    else{//여성도 남성도 아닐 경우
                        gender = 2;
                    }
                    profileNumber = Integer.parseInt(tmp[5].split("_")[1]);
                    UL = new User(tmp[1], tmp[2], tmp[4], gender, profileNumber);

                    csvLine=bfReader.readLine();
                    while (csvLine!=null)
                    {
                        tmp = csvLine.split("\t");
                        if(!tmp[1].equalsIgnoreCase(pName)) {
                            if (tmp[3].equalsIgnoreCase("male")) {
                                gender = 0;
                            } else if (tmp[3].equalsIgnoreCase("female")) {
                                gender = 1;
                            } else {
                                gender = 2;
                            }
                            profileNumber = Integer.parseInt(tmp[5].split("_")[1]);
                            UL.append(tmp[1], tmp[2], tmp[4], gender, profileNumber);
                        }
                        csvLine=bfReader.readLine();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        for(int idx=0;idx<UL.lengthUser(0);idx++){
            if(UL.getUser(idx).getGender()==0){
                if(UL_male==null){
                    UL_male = new User(UL.getUser(idx));
                }
                else{
                    UL_male.append(UL.getUser(idx));
                }
            }
            else{
                if(UL_female==null){
                    UL_female = new User(UL.getUser(idx));
                }
                else {
                    UL_female.append(UL.getUser(idx));
                }
            }
        }

        bt_SelectMale=(Button)findViewById(R.id.button_male);
        bt_SelectMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(UL_male.lengthUser(0)<16){
                    Toast.makeText(getApplicationContext(),"인원이 모자랍니다.", Toast.LENGTH_SHORT).show();
                }
                else {
                    Random r = new Random();
                    int i1;
                    for(int idx = 0;idx<16;idx++){
                        i1 = r.nextInt(UL_male.lengthUser(0)-idx);
                        if(UL_game==null){
                            UL_game = new User(UL_male.getUser(i1));
                            UL_male.DropOut(i1);
                        }
                        else{
                            UL_game.append(UL_male.getUser(i1));
                            UL_male.DropOut(i1);
                        }
                    }
                }
                Intent intentGenderSelect = new Intent(GenderSelect.this, Game.class);
                intentGenderSelect.putExtra("userList", UL_game);
                startActivity(intentGenderSelect);
            }
        });

        bt_SelectFemale=(Button)findViewById(R.id.button_female);
        bt_SelectFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(UL_female.lengthUser(0)<16){
                Toast.makeText(getApplicationContext(),"인원이 모자랍니다.", Toast.LENGTH_SHORT).show();
            }
            else {
                Random r = new Random();
                int i1;
                for(int idx = 0;idx<16;idx++){
                    i1 = r.nextInt(UL_female.lengthUser(0)-idx);
                    if(UL_game==null){
                        UL_game = new User(UL_female.getUser(i1));
                        UL_female.DropOut(i1);
                    }
                    else{
                        UL_game.append(UL_female.getUser(i1));
                        UL_female.DropOut(i1);
                    }
                }
            }
                Intent intentGenderSelect = new Intent(GenderSelect.this, Game.class);
                intentGenderSelect.putExtra("userList", UL_game);
                startActivity(intentGenderSelect);
            }
        });

        bt_SelectMix=(Button)findViewById(R.id.button_mix);
        bt_SelectMix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(UL_male.lengthUser(0)<16){
                    Toast.makeText(getApplicationContext(),"인원이 모자랍니다.", Toast.LENGTH_SHORT).show();
                }
                else {
                    Random r = new Random();
                    int i1;
                    for(int idx = 0;idx<16;idx++){
                        i1 = r.nextInt(UL.lengthUser(0)-idx);
                        if(UL_game==null){
                            UL_game = new User(UL.getUser(i1));
                            UL.DropOut(i1);
                        } else{
                            UL_game.append(UL.getUser(i1));
                            UL.DropOut(i1);
                        }
                    }
                }
                Intent intentGenderSelect = new Intent(GenderSelect.this, Game.class);
                intentGenderSelect.putExtra("userList", UL_game);
                startActivity(intentGenderSelect);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_gender_select, menu);
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
