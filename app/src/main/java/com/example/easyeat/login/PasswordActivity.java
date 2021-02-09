package com.example.easyeat.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.easyeat.R;

public class PasswordActivity extends AppCompatActivity {
    Button reset;
    EditText edtusername;
    DataBaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        edtusername = findViewById(R.id.username1);
        reset = findViewById(R.id.btnreset);
        myDB = new DataBaseHelper(this);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String user = edtusername.getText().toString();
                Boolean checkuser = myDB.checkUsername(user);
                if (Boolean.TRUE.equals(checkuser)) {
                    Intent intent = new Intent(getApplicationContext(), ResetActivity.class);
                    intent.putExtra("username", user);

                    startActivity(intent);
                } else {
                    Toast.makeText(PasswordActivity.this, "User does not exists", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }
}