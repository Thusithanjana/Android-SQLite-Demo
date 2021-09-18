package com.mad2021.maddatahandling;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.mad2021.maddatahandling.database.DBHelper;

import java.util.ArrayList;
import java.util.List;

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

    public void viewAll(View view){
        DBHelper dbHelper = new DBHelper(this);

        List info = dbHelper.readAllInfo();
        List items = new ArrayList<Integer>();

        String[] data = (String[]) info.toArray(new String[0]);

        //Toast.makeText(this, info.toString(), Toast.LENGTH_SHORT).show();

//        Snackbar snackbar = Snackbar.make(view, info.toString(),Snackbar.LENGTH_LONG);
//        snackbar.setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE);
//        snackbar.show();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Users");
        builder.setItems(data, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String userName = data[i].split(":")[0];
                edtName.setText(userName);
                edtPassword.setText("*************************");

            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    public void deleteUser(View view){
        DBHelper dbHelper = new DBHelper(this);

        String userName = edtName.getText().toString();

        if(userName.isEmpty()){
            Toast.makeText(this, "Please select user to Delete", Toast.LENGTH_SHORT).show();
        }else{
            dbHelper.deleteInfo(userName);
            Toast.makeText(this, "User deleted!", Toast.LENGTH_SHORT).show();

            edtName.setText("");
            edtPassword.setText("");
        }

    }

    public void updateUser(View view){
        DBHelper dbHelper = new DBHelper(this);
        String userName = edtName.getText().toString();
        String password = edtPassword.getText().toString();

        if(userName.isEmpty()||password.isEmpty()){

        }else{
            dbHelper.updateInfo(view,userName,password);
        }

    }



}