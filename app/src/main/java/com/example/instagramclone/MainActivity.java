package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnSave;
    private EditText edtName, edtPunchSpeed, edtPunchPower,edtKickSpeed,edtKickPower;
    private TextView txtGetData;
    private Button btnGetAllData;
    private String allKickBoxers;
    private TextView txtGetAllData;
    private Button btnTransition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(MainActivity.this);

        edtName = findViewById(R.id.edtName);
        edtPunchSpeed = findViewById(R.id.edtPunchSpeed);
        edtPunchPower = findViewById(R.id.edtPunchPower);
        edtKickSpeed = findViewById(R.id.edtKickSpeed);
        edtKickPower = findViewById(R.id.edtKickPower);
        btnGetAllData = findViewById(R.id.btnGetAllData);

        txtGetData = findViewById(R.id.txtGetData);
        txtGetAllData = findViewById(R.id.txtGetAllData);

        btnTransition = findViewById(R.id.btnNextActivity);

        txtGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("KickBoxer");
                parseQuery.getInBackground("wnHElx6otL", new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        if(object != null && e == null){
                            txtGetData.setText(object.get("name") + " - " + "Punch Power : "
                            + object.get("punchPower") + " Punch Speed : " + object.get("punchSpeed"));
                        }
                    }
                });
            }
        });

        btnGetAllData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                allKickBoxers = "";
                ParseQuery<ParseObject> queryAll = ParseQuery.getQuery("KickBoxer");
                queryAll.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {

                        if ( e == null){
                            if (objects.size() > 0){
                                for (ParseObject kickBoxer : objects){
                                    allKickBoxers = allKickBoxers + kickBoxer.get("name")
                                            + " PP "+ kickBoxer.get("punchPower")
                                    + " PS "+ kickBoxer.get("punchSpeed")
                                    + " KP "+ kickBoxer.get("kickPower")
                                    + " KS "+ kickBoxer.get("kickSpeed") + "\n";
                                    txtGetAllData.setText(allKickBoxers);
                                }
                                FancyToast.makeText(MainActivity.this,"Success",
                                        FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();
                            }else{
                                FancyToast.makeText(MainActivity.this,e.getMessage(),
                                        FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
                            }
                        }
                    }
                });
            }
        });

        btnTransition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            // 从这里转去SignUp的Activity
                Intent intent = new Intent(MainActivity.this,
                        SignUpLoginActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View view) {
        // 用try来找错误，程序运行不会关闭
        try{
        final ParseObject kickBoxer = new ParseObject("KickBoxer");
        kickBoxer.put("name",edtName.getText().toString());
        //把String变成int的过程，就是加上Integer.parseInt
        kickBoxer.put("punchSpeed", Integer.parseInt(edtPunchSpeed.getText().toString()));
        kickBoxer.put("punchPower", Integer.parseInt(edtPunchPower.getText().toString()));
        kickBoxer.put("kickSpeed",Integer.parseInt(edtKickSpeed.getText().toString()));
        kickBoxer.put("kickPower",Integer.parseInt(edtKickPower.getText().toString()));
        kickBoxer.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    FancyToast.makeText(MainActivity.this,kickBoxer.get("name") + " is saved on server",
                            FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();

                }else{
                    FancyToast.makeText(MainActivity.this,e.getMessage(),
                            FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();

                }
            }
        });
    } catch (Exception e){
            FancyToast.makeText(MainActivity.this,e.getMessage(),
                    FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
        }
    }
}
