package com.example.rainbowwhale.sachin;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ShowWinner extends Activity {

    TextView tv_Title, tv_Name, tv_Nick, tv_Desc;
    Button bt_Finish;
    User Winner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_winner);

        Intent intentWinner = getIntent();
        Winner = intentWinner.getParcelableExtra("userList");
        if(Winner == null){
            Toast.makeText(this, "User List is Null", Toast.LENGTH_SHORT).show();
            return;
        }
        tv_Name = (TextView)findViewById(R.id.Result_textView_Name);
        tv_Nick = (TextView)findViewById(R.id.Result_textView_Nick);
        tv_Desc = (TextView)findViewById(R.id.Result_textView_Desc);
        tv_Name.setText(Winner.getName());
        tv_Nick.setText(Winner.getNick());
        tv_Desc.setText(Winner.getDesc());

        bt_Finish = (Button)findViewById(R.id.Result_button_Finish);
        bt_Finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}
