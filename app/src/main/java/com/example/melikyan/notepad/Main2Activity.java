package com.example.melikyan.notepad;

import android.content.DialogInterface;
import android.content.Intent;
import android.preference.PreferenceActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class Main2Activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        File file =this.getFilesDir();
        final File[] listfiles=file.listFiles();
        String[] filesname=new String[listfiles.length];
        for(int i=0;i<filesname.length;i++)
            filesname[i]=listfiles[i].getName();
        ListView g=findViewById(R.id.listview);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, filesname);
        g.setAdapter(adapter);
        g.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
            {
                Intent intent=new Intent(Main2Activity.this,MainActivity.class);
                intent.putExtra("filename",listfiles[position].getName());
                startActivity(intent);
            }
        });
        g.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                listfiles[position].delete();
                Intent intent=new Intent(Main2Activity.this,Main2Activity.class);
                startActivity(intent);
                return true;
            }
        });

    }

    public void createFile(View view) throws IOException {
        final EditText input = new EditText(Main2Activity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        input.setHint("Введите имя файла");
        AlertDialog.Builder dialog=new AlertDialog.Builder(Main2Activity.this);
        dialog.setView(input);
        dialog.setTitle("Имя файла");
        dialog.setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                               String name=input.getText().toString();
                               File file=new File(getBaseContext().getFilesDir(),name+".txt");
                                try {
                                    file.createNewFile();
                                    dialog.dismiss();
                                    Intent intent = new Intent(Main2Activity.this,MainActivity.class);
                                    intent.putExtra("filename",file.getName());
                                    startActivity(intent);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        })
                .setNegativeButton("Отмена",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });
        dialog.show();

    }
}
