package com.pingpetrus.calculatricesimple;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView display;
    private double firstValue = 0;
    private double secondValue = 0;
    private Operator operator = Operator.NONE;
    private boolean isOperatorPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = findViewById(R.id.display);
        setNumericButtonListeners();
        setOperatorButtonListeners();
    }

    private void setNumericButtonListeners() {
        int[] numericButtonIds = {
                R.id.button1, R.id.button2, R.id.button3,
                R.id.button4, R.id.button5, R.id.button6,
                R.id.button7, R.id.button8, R.id.button9,
                R.id.button0

        };

        View.OnClickListener listener = v -> {
            Button button = (Button) v;
            String currentText = display.getText().toString();
            if (currentText.equals("0") || isOperatorPressed) {
                display.setText(button.getText().toString());
                isOperatorPressed = false;
            } else {
                display.append(button.getText().toString());
            }
        };

        for (int id : numericButtonIds) {
            findViewById(id).setOnClickListener(listener);
        }
    }

    private void setOperatorButtonListeners() {
        findViewById(R.id.buttonAdd).setOnClickListener(v -> setOperator(Operator.ADD));
        findViewById(R.id.buttonSubtract).setOnClickListener(v -> setOperator(Operator.SUBTRACT));
        findViewById(R.id.buttonMultiply).setOnClickListener(v -> setOperator(Operator.MULTIPLY));
        findViewById(R.id.buttonDivide).setOnClickListener(v -> setOperator(Operator.DIVIDE));
        findViewById(R.id.buttonEqual).setOnClickListener(v -> calculateResult());
    }

    private void setOperator(Operator operator) {
        this.operator = operator;
        firstValue = Double.parseDouble(display.getText().toString());
        isOperatorPressed = true;
    }

    private void calculateResult() {
        try {
            secondValue = Double.parseDouble(display.getText().toString());
            double result = performOperation(firstValue, secondValue, operator);
            display.setText(String.valueOf(result));
            operator = Operator.NONE;
            isOperatorPressed = false;
        } catch (NumberFormatException e) {
            display.setText("Error");
        }
    }

    private double performOperation(double a, double b, Operator operator) {
        switch (operator) {
            case ADD:
                return a + b;
            case SUBTRACT:
                return a - b;
            case MULTIPLY:
                return a * b;
            case DIVIDE:
                if (b == 0) {
                    throw new IllegalArgumentException("Division by zero");
                }
                return a / b;
            default:
                return 0;
        }
    }

    private enum Operator {
        NONE, ADD, SUBTRACT, MULTIPLY, DIVIDE
    }
}
