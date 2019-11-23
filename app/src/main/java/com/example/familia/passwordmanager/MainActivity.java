package com.example.familia.passwordmanager;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int type;
    private boolean paswGenerated;

    private String user;
    private String web;
    private String key;
    private String pasw;
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private EditText ed1;
    private EditText ed2;
    private EditText ed3;
    private Spinner spinner;
    private CheckBox cb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        paswGenerated=false;

        spinner = findViewById(R.id.spinner);
        tv1 = findViewById(R.id.textView1);
        tv2 = findViewById(R.id.textView2);
        tv3 = findViewById(R.id.textView3);
        ed1 = findViewById(R.id.editText1);
        ed2 = findViewById(R.id.editText2);
        ed3 = findViewById(R.id.editText3);

        tv3.setTextIsSelectable(true);

        Button bt1=findViewById(R.id.b1);
        Button bt2=findViewById(R.id.b2);
        Button bt3=findViewById(R.id.b3);

        cb = findViewById(R.id.checkBox);

        String[] items = new String[]{"Choose Complexity:",
                                      "1: Short Pin",
                                      "2: Long Pin",
                                      "3: Keyword",
                                      "4: Simple\nPassword",
                                      "5: Average\nPassword",
                                      "6: Strong\nPassword"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type=position;
                displayInfo(type);
                paswGenerated=false;


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ed2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tv2.setText(Normalization.websiteStringStandardize(ed2.getText().toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ed1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tv1.setText(Normalization.userStringStandardize(ed1.getText().toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cb.isChecked()){
                    ed3.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }else{
                    ed3.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }
            }
        });

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ed1.setText("");
                ed2.setText("");
                ed3.setText("");
                tv1.setText("");
                tv2.setText("");
                tv3.setText("");
                Toast.makeText(getApplicationContext(), "fields cleared",Toast.LENGTH_SHORT).show();
            }
        });
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                displayPassword();

            }
        });

        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(paswGenerated){
                    Toast.makeText(getApplicationContext(),"Password copied to clipboard",Toast.LENGTH_SHORT).show();
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("",tv3.getText().toString());
                    clipboard.setPrimaryClip(clip);

                }else{
                    Toast.makeText(getApplicationContext(),"Please fill out all fields and click on \"GENERATE PASSWORD\"",Toast.LENGTH_LONG).show();
                }
                }
        });
    }

    private void setTextSize(int type){
        switch (type){
            case 0: tv3.setTextSize(20); break;
            case 1: tv3.setTextSize(80); break;
            case 2: tv3.setTextSize(60); break;
            case 3: tv3.setTextSize(50); break;
            case 4: tv3.setTextSize(40); break;
            case 5: tv3.setTextSize(35); break;
            case 6: tv3.setTextSize(20); break;

        }
    }

    private void displayPassword(){

        user=ed1.getText().toString();
        web=ed2.getText().toString();
        key=ed3.getText().toString();

        if (!(user.equals("") || web.equals("") || key.equals(""))){
            pasw = PasswordGeneration.generate(user,web,key,type);
            tv3.setText(pasw);
            setTextSize(type);
            paswGenerated=true;
        }else{
            tv3.setTextSize(20);
            tv3.setText("Please fill out all fields");
        }


    }

    private void displayInfo(int type){
        tv3.setTextSize(20);
        switch (type){
            case 0: tv3.setText("Select the complexity level of your password.");
                break;
            case 1: tv3.setText("Description: Four digits pin.");
                break;
            case 2: tv3.setText("Description: Eight digits pin.");
                break;
            case 3: tv3.setText("Description: Random short word.");
                break;
            case 4: tv3.setText("Description: Simple password, a capital letter the begining and numbers at the end, easy to remember.");
                break;
            case 5: tv3.setText("Description: Advance, longer password, contains numbers and letters mixed together in such a way it is readable.");
                break;
            case 6: tv3.setText("Description: Highly secure password, hard to remember, it has a wide variety of characters");
                break;


        }
    }

}






























