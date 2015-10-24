package com.example.rainbowwhale.sachin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Result extends Activity {

    TextView tv_Name, tv_Nick, tv_Desc;
    Button bt_End;
    User UL_winner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intentResult = getIntent();
        UL_winner = (User)intentResult.getParcelableExtra("winner");
        if(UL_winner == null){
            Toast.makeText(this, "User List is Null", Toast.LENGTH_SHORT).show();
            return;
        }


        tv_Name = (TextView)findViewById(R.id.Result_textView_Name);
        tv_Nick = (TextView)findViewById(R.id.Result_textView_Nick);
        tv_Desc = (TextView)findViewById(R.id.Result_textView_desc);

        tv_Name.setText((CharSequence) UL_winner.getName());
        tv_Nick.setText((CharSequence) UL_winner.getNick());
        tv_Desc.setText((CharSequence) UL_winner.getDesc());

        bt_End.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
