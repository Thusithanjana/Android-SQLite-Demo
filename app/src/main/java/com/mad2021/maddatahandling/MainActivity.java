package com.mad2021.maddatahandling;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mad2021.maddatahandling.database.DBHelper;

public class MainActivity extends AppCompatActivity {

    EditText edtName, edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtName = findViewById(R.id.edtName);
        edtPassword = findViewById(R.id.edtPassword);

    }

    public void saveUser(View view){
        String name = edtName.getText().toString();
        String password = edtPassword.getText().toString();
        DBHelper dbHelper = new DBHelper(this);

        if(name.isEmpty()||password.isEmpty()){
            Toast.makeText(this, "Enter values", Toast.LENGTH_SHORT).show();
        }else{
            long inserted = dbHelper.addInfo(name,password);

            if(inserted>0){
                Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Something went wrong ;(", Toast.LENGTH_SHORT).show();
            }




        }


    }
}