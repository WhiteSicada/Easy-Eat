package com.example.easyeat.menuactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easyeat.MainActivity;
import com.example.easyeat.R;
import com.example.easyeat.login.DataBaseHelper;
import com.example.easyeat.model.User;

public class ProfileActivity extends AppCompatActivity {

    private EditText profileEmail;
    private TextView userprofile;
    private Button updateProfile;
    private Button cancelProfile;
    private User user;
    private DataBaseHelper myDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        myDb = new DataBaseHelper(this);
        // link butoon ,edit text , text view
        associateVarWithItems();

        // fill Edit Text and text view
        fillInfo(user);

        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = profileEmail.getText().toString();
                Boolean updateStatus = myDb.updateUser(email,String.valueOf(user.getId()));
                if (Boolean.TRUE.equals(updateStatus)){
                    User userHolder = new User();
                    userHolder.setId(user.getId());
                    userHolder.setUsernname(user.getUsernname());
                    userHolder.setEmail(email);
                    userHolder.setPassword(user.getPassword());
                    Toast.makeText(ProfileActivity.this, R.string.update_successfully, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ProfileActivity.this , MainActivity.class);
                    intent.putExtra("user",userHolder);
                    startActivity(intent);
                }else{
                    Toast.makeText(ProfileActivity.this, R.string.update_failed, Toast.LENGTH_SHORT).show();
                }
            }
        });


        cancelProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this , MainActivity.class);
                intent.putExtra("user",user);
                startActivity(intent);
            }
        });
    }

    public void associateVarWithItems(){
        userprofile = (TextView) findViewById(R.id.userprofile);
        profileEmail = (EditText) findViewById(R.id.profileEmail);
        updateProfile = (Button) findViewById(R.id.updateProfile);
        cancelProfile = (Button) findViewById(R.id.cancelProfile);
    }

    public void fillInfo(User user){
        userprofile.setText(user.getUsernname());
        profileEmail.setText(user.getEmail());
    }


}