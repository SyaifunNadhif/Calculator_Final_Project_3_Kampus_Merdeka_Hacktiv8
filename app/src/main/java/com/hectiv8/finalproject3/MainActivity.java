package com.hectiv8.finalproject3;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;


import com.hectiv8.finalproject3.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private String operator, result;
    private  boolean dot_inserted, operator_inserted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        operator = "";
        result = "";
        dot_inserted = false;
        operator_inserted = false;

        binding.btn1.setOnClickListener(v -> {
            operator = operator + "1";
            displayOperator();
        });

        binding.btn2.setOnClickListener(v -> {
            operator = operator + "2";
            displayOperator();
        });

        binding.btn3.setOnClickListener(v -> {
            operator = operator + "3";
            displayOperator();
        });

        binding.btn4.setOnClickListener(v -> {
            operator = operator + "4";
            displayOperator();
        });

        binding.btn5.setOnClickListener(v -> {
            operator = operator + "5";
            displayOperator();
        });

        binding.btn6.setOnClickListener(v -> {
            operator = operator + "6";
            displayOperator();
        });

        binding.btn7.setOnClickListener(v -> {
            operator = operator + "7";
            displayOperator();
        });

        binding.btn8.setOnClickListener(v -> {
            operator = operator + "8";
            displayOperator();
        });

        binding.btn9.setOnClickListener(v -> {
            operator = operator + "9";
            displayOperator();
        });

        binding.btn0.setOnClickListener(v -> {
            operator = operator + "0";
            displayOperator();
        });


        binding.btnSamadengan.setOnClickListener(v -> {
            if(operator_inserted == true && !operator.substring(operator.length()-1, operator.length()).equals(" ")){
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