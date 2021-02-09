package com.example.easyeat.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easyeat.R;

public class ResetActivity extends AppCompatActivity {
    TextView username;
    EditText pass;
    EditText repass;
    Button confirm;
    DataBaseHelper myDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);
        username=(TextView)findViewById(R.id.txtusername);
        pass=(EditText)findViewById(R.id.newpass);
        repass=(EditText) findViewById(R.id.retypepass);
        confirm=(Button) findViewById(R.id.btnconfirm);
        Intent intent=getIntent();
        username.setText(intent.getStringExtra("username"));
        myDB = new DataBaseHelper(this);


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user=username.getText().toString();
                String password=pass.getText().toString();
                String repassword=repass.getText().toString();
                if(password.equals(repassword)) {

                    Boolean checkpasswordupdate = myDB.updatePassword(user, password);
                    if (Boolean.TRUE.equals(checkpasswordupdate)) {
                       Intent intent = new Intent(getApplicationContext(), Login.class);
                        startActivity(intent);
                        Toast.makeText(ResetActivity.this, "Password Updated Successfully", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(ResetActivity.this, "Password Not  Updated ", Toast.LENGTH_SHORT).show();
                    }
                    }
                else {
                        Toast.makeText(ResetActivity.this, "Password Not Matching  ", Toast.LENGTH_SHORT).show();
                    }


            }
        });

    }
}