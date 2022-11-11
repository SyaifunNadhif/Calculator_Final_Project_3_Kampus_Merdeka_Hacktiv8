package com.hectiv8.finalproject3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Calculation {
    private String calculationStr;
    private String lastDigit = "0";
    public double result;

    public void setCalculationStr(String calculationStr) {
        this.calculationStr = calculationStr;
    }

    public String getLastDigit() {
        return lastDigit;
    }

    public void setLastDigit(String lastDigit) {
        this.lastDigit = lastDigit;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }


    public Calculation() {
        super();
    }


    public String getCalculationStr() {
        return calculationStr;
    }


    public Calculation(String text) {
        if (text.length() == 1) {
            if (isOperator(text) || text.equals(".")) {
                this.calculationStr = "0".concat(text);
            } else {
                this.calculationStr = text;
                this.result = Double.parseDouble(text);
            }
            this.lastDigit = text;
        } else {
            this.calculationStr = text;
            this.lastDigit = String.valueOf(calculationStr.charAt(calculationStr.length() - 1));
//            calculateResult();
        }
    }

    public Calculation(String calculationStr, String lastDigit, double result) {
        this.calculationStr = calculationStr;
        this.lastDigit = lastDigit;
        this.result = result;
    }

    void addNumber(String number) {
        this.calculationStr = calculationStr.concat(number);
        this.lastDigit = number;
        calculateResult();
    }

    boolean isOperator(String key) {
        String[] operators = {"÷", "×", "-", "+"};
        return Arrays.asList(operators).contains(key);
    }


    public void addOperator(String operator) {
        if (isOperator(lastDigit) && !lastDigit.equals(".")) {
            this.calculationStr = calculationStr.substring(0, calculationStr.length() - 1).concat(operator);
        } else {
            this.calculationStr = calculationStr.concat(operator);
        }
        this.lastDigit = operator;
    }

    private void calculateResult() {
        ArrayList<String> nums = getNumbers(this.calculationStr);

        Map<ArrayList<String>, Map<String, ArrayList<Integer>>> opDetails = getOperatorDetails(calculationStr);
        ArrayList<String> opSorted = new ArrayList<String>();
        Map<String, ArrayList<Integer>> opLocation = new HashMap<String, ArrayList<Integer>>();
        for (Map.Entry<ArrayList<String>, Map<String,ArrayList<Integer>>> entry : opDetails.entrySet()){
            opSorted = entry.getKey();
            opLocation = entry.getValue();
        }

        if(!opSorted.isEmpty()){
            this.result =getResultVal(nums, opSorted, opLocation);
        }else {
            this.result = Double.parseDouble(nums.get(0));
        }
    }

    public void clear() {
        this.calculationStr = "0";
        this.result = 0;
        this.lastDigit = "0";
    }

    public void backspace() {
        this.calculationStr = calculationStr.substring(0, calculationStr.length() - 1);
        if (!calculationStr.equals("")) {
            this.lastDigit = calculationStr.substring(calculationStr.length() - 1);
            calculateResult();
        }
    }

    public void addDot() {
        if (!lastDigit.equals(".")) {
            int i = calculationStr.length() - 1;
            if (i == 0) {
                this.calculationStr = calculationStr.concat(".");
                this.lastDigit = ".";
                calculateResult();
            } else {
                while (i > 0) {
                    if (isOperator(String.valueOf(calculationStr.charAt(i))) || (i == 1 && !String.valueOf(calculationStr.charAt(1)).equals("."))) {
                        boolean noDot = true;
                        String lastNumber = calculationStr.substring(i + 1);
                        if (lastNumber.contains(".")) {
                            noDot = false;
                            break;
                        }
                        if (noDot) {
                            this.calculationStr = calculationStr.concat(".");
                            this.lastDigit = ".";
                            calculateResult();
                        }
                        break;
                    }
                    i--;
                }
            }
        }
    }

    public void onPersenPressed() {
        boolean noOperator = true;
        for (int i = 0; i < calculationStr.length(); i++) {
            if (isOperator(String.valueOf(calculationStr.charAt(i)))) {
                noOperator = false;
            }
        }

        if (noOperator) {
            this.result = Double.parseDouble(calculationStr) * 0.01;
            this.calculationStr = String.valueOf(result);
        } else if (!isOperator(lastDigit)) {
            ArrayList<String> numbers = getNumbers(calculationStr);
            Map<ArrayList<String>, Map<String, ArrayList<Integer>>> opDetails = getOperatorDetails(calculationStr);
            ArrayList<String> opSorted = new ArrayList<String>();
            Map<String, ArrayList<Integer>> opLocation = new HashMap<>();
            for (Map.Entry<ArrayList<String>, Map<String, ArrayList<Integer>>> entry : opDetails.entrySet()) {
                opSorted = entry.getKey();
                opLocation = entry.getValue();
            }

            String lastOp = checkLastOperator(opSorted, opLocation);
            String lastNum = numbers.get(numbers.size()-1);
            String lastNumPercentage = String.valueOf(Double.parseDouble(lastNum) * 0.01);
            if(lastOp.equals("×") || lastOp.equals("÷")){
                numbers.set(numbers.size()-1, lastNumPercentage);
                this.calculationStr = calculationStr.substring(0, calculationStr.length() - lastNum.length()).concat(lastNumPercentage);
            }else {
                ArrayList<String> prevNums = new ArrayList<>(numbers);
                prevNums.remove(prevNums.size()-1);
                ArrayList<String> tempOpSorted = new ArrayList<>(opSorted);
                tempOpSorted.remove(tempOpSorted.size()-1);

                // duplicate opLocation arraylist
                Map<String, ArrayList<Integer>> prevCalcOpLoc = new HashMap<String, ArrayList<Integer>>();
                for(Map.Entry<String,ArrayList<Integer>> entry : opLocation.entrySet()){
                    String key = entry.getKey();
                    ArrayList<Integer> value = new ArrayList<>(entry.getValue());
                    prevCalcOpLoc.put(key, value);
                }

                double prevCalc = getResultVal(prevNums, tempOpSorted, prevCalcOpLoc);
                String percentageVal = String.valueOf(prevCalc * Double.parseDouble(lastNumPercentage));
                numbers.set(numbers.size()-1, percentageVal);
                this.calculationStr = calculationStr.substring(0, calculationStr.length() - lastNum.length()).concat(percentageVal);
                this.lastDigit = String.valueOf(calculationStr.charAt(calculationStr.length()-1));
            }
            this.result = getResultVal(numbers, opSorted, opLocation);
        }

    }


    public ArrayList<String> getNumbers(String calculationStr) {
        String[] operators = {"\\×", "÷", "\\+", "-"};

        ArrayList<String> split = new ArrayList<String>();
        ArrayList<String> numbers = new ArrayList<String>();
        for (String operator : operators) {
            if (split.size() == 0) {
                split.add(calculationStr);
            } else {
                split.clear();
                split.addAll(numbers);
                numbers.clear();
            }
            for (String splitted : split) {
                String[] temp = splitted.split(operator, 0);
                for (String tempSplitted : temp) {
                    numbers.add(tempSplitted);
                }
            }
        }

        for (int i = 0; i < numbers.size(); i++) {
            if (numbers.get(i).equals(".")) {
                numbers.set(i, "0");
            }
        }

        return numbers;
    }

    public Map<ArrayList<String>, Map<String, ArrayList<Integer>>> getOperatorDetails(String calculationStr) {
        Map<String , ArrayList<Integer>> opLocation = new HashMap<String, ArrayList<Integer>>();
        opLocation.put("×", new ArrayList<Integer>());
        opLocation.put("÷", new ArrayList<Integer>());
        opLocation.put("+", new ArrayList<Integer>());
        opLocation.put("-", new ArrayList<Integer>());

        String[] calcSplitted = calculationStr.split("");
        int opOrder = 0;
        ArrayList<String> opSorted = new ArrayList<String>();
        int iterateLimit = calcSplitted.length;

        if(isOperator(lastDigit)){
            iterateLimit--;
        }

        for(int i=0; i<iterateLimit; i++){
            if(calcSplitted[i].equals("×")
                    || calcSplitted[i].equals("÷")
                    || calcSplitted[i].equals("+")
                    || calcSplitted[i].equals("-")) {
                opLocation.get(calcSplitted[i]).add(opOrder);
                if(opSorted.isEmpty()){
                    opSorted.add(calcSplitted[i]);
                }else{
                    for(int j =opSorted.size()-1; j>= 0; j--){
                        if(getOperatorVal(calcSplitted[i]) > getOperatorVal(opSorted.get(j))){
                            if(j == 0){
                                opSorted.add(0, calcSplitted[i]);
                            }
                            continue;
                        }else {
                            opSorted.add(j+1, calcSplitted[i]);
                            break;
                        }
                    }
                }
                opOrder++;
            }
        }
        Map<ArrayList<String>, Map<String, ArrayList<Integer>>> opDetails = new HashMap<ArrayList<String>, Map<String, ArrayList<Integer>>>();
        opDetails.put(opSorted, opLocation);

        return opDetails;

    }

    public int getOperatorVal(String op) {
        if(op.equals("×") || op.equals("÷")){
            return 1;
        } else {
            return 0;
        }
    }

    public String checkLastOperator(ArrayList<String> opSorted, Map<String, ArrayList<Integer>> opLocation) {
        String lastOperator = "";
        for (Map.Entry<String, ArrayList<Integer>> entry : opLocation.entrySet()) {
            for (int location : entry.getValue()) {
                if (location == opSorted.size() - 1)
                    lastOperator = entry.getKey();
            }
        }
        return lastOperator;
    }

    public double getResultVal(ArrayList<String> numbers, ArrayList<String> opSorted, Map<String, ArrayList<Integer>> opLocation) {
        ArrayList<Integer> calculated = new ArrayList<Integer>();

        for(String operator : opSorted){
            int opPos = opLocation.get(operator).get(0);
            int smallerOpPos = 0;
            if(calculated.isEmpty()){
                calculated.add(opPos);
            }else {
                for (int pos : calculated){
                    if(pos < opPos)
                        smallerOpPos++;
                }
                calculated.add(smallerOpPos, opPos);
            }
            opPos -= smallerOpPos;
            numbers.set(opPos, String.valueOf(calculate(operator, numbers.get(opPos), numbers.get(opPos+1))));
            numbers.remove(opPos+1);

            opLocation.get(operator).remove(0);
        }
        return Double.parseDouble(numbers.get(0));
    }

    public static double calculate(String operator, String num1, String num2) {
        double dNum1 = Double.parseDouble(num1);
        double dNum2 = Double.parseDouble(num2);
        double result = 0;
        switch (operator) {
            case "×":
                result = dNum1*dNum2;
                break;
            case "÷":
                result = dNum1/dNum2;
                break;
            case "+":
                result = dNum1+dNum2;
                break;
            case "-":
                result = dNum1-dNum2;
                break;
        }
        return result;
    }
}




