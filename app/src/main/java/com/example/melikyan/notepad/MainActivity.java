package com.example.melikyan.notepad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    File file;
    EditText tt;
    String newfilename;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tt=findViewById(R.id.edittext);
        Intent intent=getIntent();
        newfilename =intent.getStringExtra("filename");
        file=new File(getBaseContext().getFilesDir(),newfilename);
        try {
            InputStream inputStream = openFileInput(newfilename);
            if (inputStream != null) {
                InputStreamReader isr = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(isr);
                String line=reader.readLine();
                StringBuilder builder = new StringBuilder();

                while (line  != null) {
                    builder.append(line + "\n");
                    line=reader.readLine();
                }

                inputStream.close();
                tt.setText(builder.toString());
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void savefile(View view) {
        try {
            OutputStream outputStream = openFileOutput(newfilename, 0);
            OutputStreamWriter osw = new OutputStreamWriter(outputStream);
            osw.write(tt.getText().toString());
            osw.close();
            Intent intent=new Intent(MainActivity.this,Main2Activity.class);
            startActivity(intent);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
