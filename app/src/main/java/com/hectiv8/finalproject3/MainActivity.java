package com.hectiv8.finalproject3;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;


import com.hectiv8.finalproject3.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private String operator, result;
    private  boolean dot_inserted = false, operator_inserted = false, equals = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        operator = "";
        result = "";

        binding.btn1.setOnClickListener(v -> {
            operator = operator + "1";
            operator_inserted = false;
            displayOperator();
        });

        binding.btn2.setOnClickListener(v -> {
            operator = operator + "2";
            operator_inserted = false;
            displayOperator();
        });

        binding.btn3.setOnClickListener(v -> {
            operator = operator + "3";
            operator_inserted = false;
            displayOperator();
        });

        binding.btn4.setOnClickListener(v -> {
            operator = operator + "4";
            operator_inserted = false;
            displayOperator();
        });

        binding.btn5.setOnClickListener(v -> {
            operator = operator + "5";
            operator_inserted = false;
            displayOperator();
        });

        binding.btn6.setOnClickListener(v -> {
            operator = operator + "6";
            operator_inserted = false;
            displayOperator();
        });

        binding.btn7.setOnClickListener(v -> {
            operator = operator + "7";
            operator_inserted = false;
            displayOperator();
        });

        binding.btn8.setOnClickListener(v -> {
            operator = operator + "8";
            operator_inserted = false;
            displayOperator();
        });

        binding.btn9.setOnClickListener(v -> {
            operator = operator + "9";
            operator_inserted = false;
            displayOperator();
        });

        binding.btn0.setOnClickListener(v -> {
            operator = operator + "0";
            operator_inserted = false;
            displayOperator();
        });


        binding.btnSamadengan.setOnClickListener(v -> {
            if(equals == true && !operator.substring(operator.length()-1, operator.length()).equals(" ")){
                String [] tokens = operator.split(" ");
                switch (tokens[1].charAt(0)){
                    case '+':
                        result = Double.toString(Double.parseDouble(tokens[0]) + Double.parseDouble(tokens[2]));
                        break;
                    case '-':
                        result = Double.toString(Double.parseDouble(tokens[0]) - Double.parseDouble(tokens[2]));
                        break;
                    case 'x':

                        break;
                    case '/':

                        break;
                }
                displayResult();
            }
        });

        binding.btnTambah.setOnClickListener(v -> {
            dot_inserted = false;
            if(!operator.isEmpty()){
                if(operator.substring(operator.length()-1, operator.length()).equals(".")){
                    backspace();
                }
            }

            if(operator_inserted == false){
                operator = operator + " + ";
                operator_inserted = true;
                equals = true;
            }

            displayOperator();
        });

        binding.btnKurang.setOnClickListener(v -> {
            dot_inserted = false;
            if(!operator.isEmpty()){
                if(operator.substring(operator.length()-1, operator.length()).equals(".")){
                    backspace();
                }
            }

            if(operator_inserted == false){
                operator = operator + " - ";
                operator_inserted = true;
                equals = true;
            }
            displayOperator();
        });

        binding.btnKali.setOnClickListener(v -> {

        });

        binding.btnBagi.setOnClickListener(v -> {

        });


        binding.btnTitik.setOnClickListener(v -> {
            if(operator.isEmpty()){
                operator = "0.";
                dot_inserted = true;
            }


            if(dot_inserted == false && operator_inserted == true){
                operator = operator + "0.";
                dot_inserted = true;
            }

            if(dot_inserted == false){
                operator = operator + ".";
                dot_inserted = true;
            }

            displayOperator();
        });

        binding.btnClear.setOnClickListener(v -> {
            clear();
            displayOperator();
            displayResult();
        });

        binding.btnBackspace.setOnClickListener(v -> {
            backspace();
            displayOperator();
        });



    }

    public void displayOperator(){
        binding.operatorTv.setText(operator);
    }

    public void  displayResult(){
        binding.resultTv.setText(result);
    }

    public void clear(){
        operator = "";
        result = "";
        dot_inserted = false;
        operator_inserted = false;
    }

    public void backspace(){
        if(!operator.isEmpty()){
            if(operator.substring(operator.length()-1, operator.length()).equals(".")){
                dot_inserted = false;
                operator_inserted = true;
            }

            if(operator.substring(operator.length()-1, operator.length()).equals(" ")){
                operator = operator.substring(0, operator.length()-3);
                operator_inserted = false;
            }else {
                operator = operator.substring(0, operator.length()-1);
            }

        }
    }
}