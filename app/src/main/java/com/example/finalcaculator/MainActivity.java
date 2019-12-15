package com.example.finalcaculator;

import androidx.appcompat.app.AppCompatActivity;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.BaseInputConnection;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edtNumber;
    private TextView tvResult;

    private Button btnNumber0;
    private Button btnNumber1;
    private Button btnNumber2;
    private Button btnNumber3;
    private Button btnNumber4;
    private Button btnNumber5;
    private Button btnNumber6;
    private Button btnNumber7;
    private Button btnNumber8;
    private Button btnNumber9;

    private Button btnAdd;
    private Button btnSub;
    private Button btnMul;
    private Button btnDiv;
    private Button btnComma;

    private Button btnSin;
    private Button btnCos;
    private Button btnTan;
    private Button btnCot;
    private Button btnLog;
    private Button btnNgoac;

    private Button btnClearAll;
    private Button btnClear;
    private Button btnResult;

    ArrayList<String> postFix;
    Stack stack;
    Stack stackResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWidget();
        setEventClickView();
        postFix = new ArrayList<String>();
        stack = new Stack();
        stackResult = new Stack();
    }

    public void initWidget() {
        edtNumber = findViewById(R.id.edit_input);
        tvResult = findViewById(R.id.tv_result);
        btnNumber0 = (Button) findViewById(R.id.btn_number0);
        btnNumber1 = (Button) findViewById(R.id.btn_number1);
        btnNumber2 = (Button) findViewById(R.id.btn_number2);
        btnNumber3 = (Button) findViewById(R.id.btn_number3);
        btnNumber4 = (Button) findViewById(R.id.btn_number4);
        btnNumber5 = (Button) findViewById(R.id.btn_number5);
        btnNumber6 = (Button) findViewById(R.id.btn_number6);
        btnNumber7 = (Button) findViewById(R.id.btn_number7);
        btnNumber8 = (Button) findViewById(R.id.btn_number8);
        btnNumber9 = (Button) findViewById(R.id.btn_number9);

        btnAdd = (Button) findViewById(R.id.btn_add);
        btnSub = (Button) findViewById(R.id.btn_sub);
        btnMul = (Button) findViewById(R.id.btn_mul);
        btnDiv = (Button) findViewById(R.id.btn_div);
        btnComma = (Button) findViewById(R.id.btn_comma);

        btnSin = findViewById(R.id.btnSin);
        btnTan = findViewById(R.id.btnTan);
        btnCos = findViewById(R.id.btnCos);
        btnCot = findViewById(R.id.btnCot);
        btnLog = findViewById(R.id.btnLog);
        btnNgoac = findViewById(R.id.btn_ngoac);

        btnClearAll = (Button) findViewById(R.id.btn_clearall);
        btnClear = (Button) findViewById(R.id.btn_clear);
        btnResult = (Button) findViewById(R.id.btn_result);
    }

    public void setEventClickView() {
        btnNumber0.setOnClickListener(this);
        btnNumber1.setOnClickListener(this);
        btnNumber2.setOnClickListener(this);
        btnNumber3.setOnClickListener(this);
        btnNumber4.setOnClickListener(this);
        btnNumber5.setOnClickListener(this);
        btnNumber6.setOnClickListener(this);
        btnNumber7.setOnClickListener(this);
        btnNumber8.setOnClickListener(this);
        btnNumber9.setOnClickListener(this);

        btnAdd.setOnClickListener(this);
        btnSub.setOnClickListener(this);
        btnMul.setOnClickListener(this);
        btnDiv.setOnClickListener(this);
        btnComma.setOnClickListener(this);

        btnSin.setOnClickListener(this);
        btnCos.setOnClickListener(this);
        btnTan.setOnClickListener(this);
        btnCot.setOnClickListener(this);
        btnLog.setOnClickListener(this);
        btnNgoac.setOnClickListener(this);

        btnClearAll.setOnClickListener(this);
        btnClear.setOnClickListener(this);
        btnResult.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_number0:
                edtNumber.append("0");
                break;
            case R.id.btn_number1:
                edtNumber.append("1");
                break;
            case R.id.btn_number2:
                edtNumber.append("2");
                break;
            case R.id.btn_number3:
                edtNumber.append("3");
                break;
            case R.id.btn_number4:
                edtNumber.append("4");
                break;
            case R.id.btn_number5:
                edtNumber.append("5");
                break;
            case R.id.btn_number6:
                edtNumber.append("6");
                break;
            case R.id.btn_number7:
                edtNumber.append("7");
                break;
            case R.id.btn_number8:
                edtNumber.append("8");
                break;
            case R.id.btn_number9:
                edtNumber.append("9");
                break;
            case R.id.btn_add:
                edtNumber.append("+");
                break;
            case R.id.btn_sub:
                edtNumber.append("-");
                break;
            case R.id.btn_mul:
                edtNumber.append("*");
                break;
            case R.id.btn_div:
                edtNumber.append("/");
                break;
            case R.id.btn_comma:
                edtNumber.append(",");
                break;
            case R.id.btn_clearall:
                edtNumber.setText("");
                break;
            case R.id.btnSin:
                edtNumber.append("sin(");
                break;
            case R.id.btnCos:
                edtNumber.append("cos(");
                break;
            case R.id.btnTan:
                edtNumber.append("tan(");
                break;
            case R.id.btnCot:
                edtNumber.append("cot(");
                break;
            case R.id.btnLog:
                edtNumber.append("log(");
                break;
            case R.id.btn_ngoac:
                edtNumber.append(")");
                break;
            case R.id.btn_clear:

                BaseInputConnection textFiledInputConnection = new BaseInputConnection(edtNumber, true);
                textFiledInputConnection.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));

                break;
            case R.id.btn_result:
                goThrough(edtNumber.getText().toString());
                tvResult.setText(result(postFix) + "");
                break;
        }
    }

    public boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public boolean hasPrecedence(String op1, String op2) {
        if (op2.equals("(") || op2.equals(")"))
            return false;
        if ((op1.equals("*") || op1.equals("/")) && (op2.equals("+") || op2.equals("-"))) {
            return false;
        }
        if (op1.equals("^") && (op2.equals("+") || op2.equals("-") || op2.equals("*") || op2.equals("/")))
            return false;
        else
            return true;
    }

    public boolean isTrigonometric(String s) {
        if (s.equals("sin") || s.equals("cos") || s.equals("tan") || s.equals("cot") || s.equals("log")) {
            return true;
        }
        return false;
    }

    public boolean isAlphabetic(char c) {
        if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))
            return true;
        else
            return false;
    }

    public void goThrough(String s) {
        String temp = "";
        String trigonometric = "";
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (isNumeric(String.valueOf(c)) == true) {
                temp += c;
            } else if (isAlphabetic(c) == true) {
                trigonometric += c;
            } else if (c == ')') {
                if (temp != "") {
                    postFix.add(temp);
                    temp = "";
                }
                String t = String.valueOf(stack.pop());
                while (t != "(") {
                    postFix.add(t);
                    t = String.valueOf(stack.pop());
                }
            } else {
                if (temp != "") {
                    postFix.add(temp);
                    temp = "";
                }
                if (c == '+' || c == '-' || c == '/' || c == '*' || c == '^') {
                    String a = "";
                    String b = "";
                    while (!stack.empty() && hasPrecedence(b = Character.toString(c), a = (String) stack.peek()) == true) {
                        postFix.add(String.valueOf(stack.pop()));
                        System.out.println(c + " - " + a);
                    }
                    stack.push(String.valueOf(c));
                } else {
                    if (isTrigonometric(trigonometric)) {
                        stack.push(trigonometric);
                        trigonometric = "";
                    }
                    stack.push("(");
                }
            }

            if (i == s.length() - 1) {
                if (temp != "") {
                    postFix.add(temp);
                }
                while (!stack.empty()) {
                    postFix.add(String.valueOf(stack.pop()));
                }
            }
        }
    }

    public double applyOp(String op, double a, double b) {
        switch (op) {
            case "+":
                return b + a;
            case "-":
                return b - a;
            case "*":
                return b * a;
            case "/":
                if (a == 0)
                    throw new
                            UnsupportedOperationException("Cannot divide by zero");
                return b / a;
            case "^":
                return Math.pow(b, a);
        }
        return 0;
    }

    public double result(ArrayList<String> p) {
        for (String s : p) {
            if (isNumeric(s)) {
                stackResult.push(s);
            } else {
                if (isTrigonometric(s)) {
                    double a = Double.parseDouble(stackResult.pop().toString());
                    switch (s) {
                        case "sin":
                            a = Math.PI / 180 * a;
                            stackResult.push(Math.sin(a));
                            break;
                        case "cos":
                            a = Math.PI / 180 * a;
                            stackResult.push(Math.cos(a));
                            break;
                        case "tan":
                            a = Math.PI / 180 * a;
                            stackResult.push(Math.tan(a));
                            break;
                        case "cot":
                            a = Math.PI / 180 * a;
                            stackResult.push(1 / (Math.tan(a)));
                            break;
                        case "log":
                            stackResult.push(Math.log(a));
                            break;
                    }

                } else {
                    double a = Double.parseDouble(stackResult.pop().toString());
                    double b = Double.parseDouble(stackResult.pop().toString());
                    double result = applyOp(s, a, b);
                    stackResult.push(String.valueOf(result));
                }
            }
        }
        return Double.parseDouble(stackResult.pop().toString());
    }
}


