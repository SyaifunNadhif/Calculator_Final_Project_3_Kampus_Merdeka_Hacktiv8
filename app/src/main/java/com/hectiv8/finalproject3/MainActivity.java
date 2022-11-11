package com.hectiv8.finalproject3;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.hectiv8.finalproject3.databinding.ActivityMainBinding;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private ActivityMainBinding binding;
    private Calculation calculation;
    private ArrayList<Calculation> calculationList = new ArrayList<Calculation>();
    private boolean finalized = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Button btn0 = binding.btn0;
        Button btn1 = binding.btn1;
        Button btn2 = binding.btn2;
        Button btn3 = binding.btn3;
        Button btn4 = binding.btn4;
        Button btn5 = binding.btn5;
        Button btn6 = binding.btn6;
        Button btn7 = binding.btn7;
        Button btn8 = binding.btn8;
        Button btn9 = binding.btn9;
        Button btn000 = binding.btn000;
        Button btnBagi = binding.btnBagi;
        Button btnKali = binding.btnKali;
        Button btnTmbh = binding.btnTambah;
        Button btnKrng = binding.btnKurang;

        // btn angka
        btn0.setOnClickListener(v -> {
            if(!binding.operatorTv.getText().equals("0"))
                onNumberPressed(((Button) v).getText().toString());
            count();
        });

        btn000.setOnClickListener(v -> {
            if(!binding.operatorTv.getText().equals("0"))
                onNumberPressed(((Button) v).getText().toString());
            count();
        });

        btn1.setOnClickListener(v -> {
            onNumberPressed(((Button) v).getText().toString());
            count();
        });

        btn2.setOnClickListener(v -> {
            onNumberPressed(((Button) v).getText().toString());
            count();
        });

        btn3.setOnClickListener(v -> {
            onNumberPressed(((Button) v).getText().toString());
            count();
        });

        btn4.setOnClickListener(v -> {
            onNumberPressed(((Button) v).getText().toString());
            count();
        });

        btn5.setOnClickListener(v -> {
            onNumberPressed(((Button) v).getText().toString());
            count();
        });

        btn6.setOnClickListener(v -> {
            onNumberPressed(((Button) v).getText().toString());
            count();
        });

        btn7.setOnClickListener(v -> {
            onNumberPressed(((Button) v).getText().toString());
            count();
        });

        btn8.setOnClickListener(v -> {
            onNumberPressed(((Button) v).getText().toString());
            count();
        });

        btn9.setOnClickListener(v -> {
            onNumberPressed(((Button) v).getText().toString());
            count();
        });

        // btn operator
        btnBagi.setOnClickListener(v -> {
            onOperatorPressed(((Button) v).getText().toString());
            count();
        });

        btnKali.setOnClickListener(v -> {
            onOperatorPressed(((Button) v).getText().toString());
            count();
        });

        btnKrng.setOnClickListener(v -> {
            onOperatorPressed(((Button) v).getText().toString());
            count();
        });

        btnTmbh.setOnClickListener(v -> {
            onOperatorPressed(((Button) v).getText().toString());
            count();
        });

        binding.btnClear.setOnClickListener(v -> {
             if(!binding.operatorTv.getText().equals("0")) {
                 calculation.clear();
             }
             setBtnClear();
             count();
             finalized = false;


        });

        binding.btnBackspace.setOnClickListener(v -> {
            if(!finalized){
                if(!binding.operatorTv.getText().equals("0")){
                    calculation.backspace();
                    if(calculation.getCalculationStr().equals("")){
                        calculation.clear();
                        setBtnClear();
                    }else {
                        showCurrentCalc();
                    }
                }
            }
        });

        binding.btnDot.setOnClickListener(v -> {
            if(finalized) {
                calculationList.add(calculation);
                calculation = new Calculation(".");
                showCurrentCalc();
                count();
                finalized = false;
            }else{
                if(binding.operatorTv.getText().equals("0")){
                    calculation = new Calculation(".");
                    binding.resultTv.setVisibility(View.VISIBLE);
                    binding.btnClear.setText("C");
                }else{
                    calculation.addDot();
                }
                showCurrentCalc();
            }
        });

        binding.btnPersen.setOnClickListener(v -> {
            if(finalized) {
                calculationList.add(calculation);
                String prevResult = binding.resultTv.getText().toString().substring(2);
                Double persen = Double.parseDouble(prevResult) * 0.01;
                calculation = new Calculation(String.valueOf(persen));
                showCurrentCalc();
                count();
                finalized = false;
            }else {
                if(!binding.operatorTv.getText().equals("0")){
                    calculation.onPersenPressed();
                    showCurrentCalc();
                    count();
                    finalized = false;
                }else {
                    if(!binding.operatorTv.getText().equals("0")){
                        calculation.onPersenPressed();
                        showCurrentCalc();
                    }
                }
            }
        });

        binding.btnEqual.setOnClickListener(v -> {
            setFinalized();
            finalized = true;
        });




    }

    // operator
    private void onOperatorPressed(String operator) {
        if(finalized) {
            String prevResult = binding.resultTv.getText().toString().substring(2);
            calculationList.add(calculation);
            calculation = new Calculation(prevResult.concat(operator));
            showCurrentCalc();
            setFinalized();
            finalized = false;
        } else {
            if(binding.operatorTv.getText().equals("0")) {
                calculation = new Calculation(operator);
                binding.resultTv.setVisibility(View.VISIBLE);
                binding.btnClear.setText("C");
            }else {
                calculation.addOperator(operator);
            }
            showCurrentCalc();
        }
    }

    // angka
    void onNumberPressed(String number){
        if(finalized){
            calculationList.add(calculation);
            calculation = new Calculation(number);
            showCurrentCalc();
            setFinalized();
            finalized = false;
        }else {
            if(binding.operatorTv.getText().equals("0")){
                calculation = new Calculation(number);
                binding.resultTv.setVisibility(View.VISIBLE);
                binding.btnClear.setText("C");
            }else {
                calculation.addNumber(number);
            }
            showCurrentCalc();
        }
    }

//  Menampilkan Hasil
    void showCurrentCalc() {
        binding.operatorTv.setText(calculation.getCalculationStr());
        Double result = calculation.getResult();
        if(result%1 == 0)
            binding.resultTv.setText("= ".concat(String.valueOf(result)));
        else {
            DecimalFormat df = new DecimalFormat("#");
            df.setMaximumFractionDigits(8);
            binding.resultTv.setText("= ".concat(df.format(result)));
        }
    }

//  Mengubah warna result dan operator
    void setFinalized(){
        binding.operatorTv.setTextSize(30);
        binding.resultTv.setTextSize(40);
        binding.operatorTv.setTextColor(getResources().getColor(R.color.white));
        binding.resultTv.setTextColor(getResources().getColor(R.color.white));
    }

    void count(){
        binding.operatorTv.setTextSize(40);
        binding.resultTv.setTextSize(30);
        binding.operatorTv.setTextColor(getResources().getColor(R.color.athenGray));
        binding.resultTv.setTextColor(getResources().getColor(R.color.athenGray));
    }

    void setBtnClear(){
        binding.btnClear.setText("AC");
        binding.operatorTv.setText("0");
        binding.resultTv.setVisibility(View.INVISIBLE);
    }

}