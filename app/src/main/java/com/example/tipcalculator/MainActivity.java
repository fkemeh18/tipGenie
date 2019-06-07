package com.example.tipcalculator;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.Random;

public class MainActivity extends AppCompatActivity
        implements TextWatcher,SeekBar.OnSeekBarChangeListener,EditText.OnEditorActionListener{


    private String[] magicWord = {"Abrakadabra!", "Hocus Pocus!", "Sha-zing!", "Presto!"};
    //declare your variables for the widgets
    private EditText eTBillAmnt;
    private TextView tVBillAmnt;
    private SeekBar  tipSeekBar;

    private TextView tvTipPercent;
    private TextView tVTipAmnt;
    private TextView tvTotalAmnt;

    private double bill = 0.00;
    private double percent = .15;
    private double tip;
    private double total;

    //formats the bill and tip percent with $ and %
    private static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
    private static final NumberFormat percentFormat = NumberFormat.getPercentInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //sets the following to the same values of the editText
        eTBillAmnt = (EditText)findViewById(R.id.et_enterBillAmnt);
        //deals with changing the text
        eTBillAmnt.addTextChangedListener((TextWatcher) this);
        eTBillAmnt.setOnEditorActionListener(this);

        //sets the following to the same values of the textView
        tVBillAmnt   = (TextView)findViewById(R.id.tv_enterBillTxt);
        tvTipPercent = (TextView)findViewById(R.id.tv_percentage);

        tipSeekBar = findViewById(R.id.seekBar);
        tipSeekBar.setOnSeekBarChangeListener( this);

        tVTipAmnt    = (TextView)findViewById(R.id.tv_tipAmount);
        tvTotalAmnt  = (TextView)findViewById(R.id.tv_totalAmount);
    }

    @Override
    public void beforeTextChanged(CharSequence cs, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence cs, int start, int before, int count) {
        try {
            bill = Double.parseDouble(cs.toString()) / 100;
            //Log.d("MainActivity", "Bill Amount = "+bill);

            //setText on the textView
            tVBillAmnt.setText(currencyFormat.format(bill));
        } catch (NumberFormatException n){
            tVBillAmnt.setText("");
            bill = 0.00;
        }


        //perform tip and total calculation and update UI by calling calculate
        //calculate();//uncomment this line
    }



    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        percent = progress / 100.0;
        tvTipPercent.setText(percentFormat.format(percent));

        calculate();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    private void calculate() {

        tip     = bill * percent;
        total   = bill + tip;

        tVTipAmnt.setText(currencyFormat.format(tip));
        tvTotalAmnt.setText(currencyFormat.format(total));
    }

    private String magicWordChooser(){
        Random r = new Random();
        String newWord = magicWord[r.nextInt(magicWord.length)];

        return newWord;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        Log.d("MainActivity","Key pressed!");
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            Log.d("MainActivity","Done pressed!");
            calculate();
            Toast toast = Toast.makeText(this, magicWordChooser(), Toast.LENGTH_SHORT);
            toast.show();
            InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            return true;
        }
        return false;
    }
}
