package com.cepel.dovizapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cepel.dovizapp.adapter.ToDoAdapter;
import com.cepel.dovizapp.model.DovizModel;
import com.cepel.dovizapp.R;
import com.cepel.dovizapp.model.ToDoModel;
import com.cepel.dovizapp.service.DovizAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.text.DecimalFormat;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;



public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static DecimalFormat df = new DecimalFormat("0.00");

    /* Currency Codes*/

    CardView cardView_usd, cardView_gbp, cardView_try, cardView_cad, cardView_chf, cardView_inr;
    TextView textView_usd, textView_gbp, textView_try, textView_cad, textView_chf, textView_inr;
    TextView textView_result;

    String d_usd, d_try;
    EditText editTextNumber;
    Button btn_convert;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        /* Currency Codes */

        /*
        Spinner spinner1 = findViewById(R.id.spinner1);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        spinner1.setOnItemSelectedListener(this);
        */

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.currencies, android.R.layout.simple_spinner_item);

        Spinner spinner2 = findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.currencies, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter);

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //spinner2.setOnItemSelectedListener(this);
        editTextNumber = findViewById(R.id.editTextNumber);
        textView_result = findViewById(R.id.txt_exchanged);
        btn_convert = findViewById(R.id.btn_convert);
        //textView_result.setText("600");

        btn_convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if( spinner2.getSelectedItem().toString().equals("USD"))  {
                    textView_result.setText( df.format(Double.parseDouble(textView_usd.getText().toString()) * Integer.parseInt(editTextNumber.getText().toString()))  + "" );
                }

                else if( spinner2.getSelectedItem().toString().equals("GBP"))  {
                    textView_result.setText( df.format(Double.parseDouble(textView_gbp.getText().toString()) * Integer.parseInt(editTextNumber.getText().toString()))  + "" );
                }

                else if( spinner2.getSelectedItem().toString().equals("CAD"))  {
                    textView_result.setText( df.format(Double.parseDouble(textView_cad.getText().toString()) * Integer.parseInt(editTextNumber.getText().toString()))  + "" );
                }

                else if( spinner2.getSelectedItem().toString().equals("TRY"))  {
                    textView_result.setText( df.format(Double.parseDouble(textView_try.getText().toString()) * Integer.parseInt(editTextNumber.getText().toString()))  + "" );
                }

                else if( spinner2.getSelectedItem().toString().equals("CHF"))  {
                    textView_result.setText( df.format(Double.parseDouble(textView_chf.getText().toString()) * Integer.parseInt(editTextNumber.getText().toString()))  + "" );
                }

                else if( spinner2.getSelectedItem().toString().equals("INR"))  {
                    textView_result.setText( df.format(Double.parseDouble(textView_inr.getText().toString()) * Integer.parseInt(editTextNumber.getText().toString()))  + "" );
                }
            }
        });



        System.out.println(spinner2.getSelectedItem().toString());




        cardView_usd = findViewById(R.id.card_usd);
        textView_usd = findViewById(R.id.text_usd);
        cardView_gbp = findViewById(R.id.card_euro);
        textView_gbp = findViewById(R.id.text_gbp);
        cardView_try = findViewById(R.id.card_turkishlira);
        textView_try = findViewById(R.id.text_try);
        cardView_cad = findViewById(R.id.card_cad);
        textView_cad = findViewById(R.id.text_cad);
        cardView_chf = findViewById(R.id.card_chf);
        textView_chf = findViewById(R.id.text_chf);
        cardView_inr = findViewById(R.id.card_inr);
        textView_inr = findViewById(R.id.text_inr);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://data.fixer.io/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DovizAPI dovizAPI = retrofit.create(DovizAPI.class);

        Call<DovizModel> call = dovizAPI.getData();

        call.enqueue(new Callback<DovizModel>() {
            @Override
            public void onResponse(Call<DovizModel> call, Response<DovizModel> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                }

                DovizModel dovizler = response.body();

                textView_usd.append(dovizler.getRates().getUSD().toString());
                textView_gbp.append(dovizler.getRates().getGBP().toString());
                textView_cad.append(dovizler.getRates().getCAD().toString());
                textView_try.append(dovizler.getRates().getTRY().toString());
                textView_chf.append(dovizler.getRates().getCHF().toString());
                textView_inr.append(dovizler.getRates().getINR().toString());

                d_usd = dovizler.getRates().getUSD().toString();
            }

            @Override
            public void onFailure(Call<DovizModel> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();

        ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
        ((TextView) parent.getChildAt(0)).setTextSize(16);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void nextActivity(View v) {
        Intent i = new Intent(this,  ToDoActivity.class);
        startActivity(i);
    }



}


