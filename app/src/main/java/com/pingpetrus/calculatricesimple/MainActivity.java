package com.pingpetrus.calculatricesimple;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Déclaration des variables membres
    private TextView screen;
    private double firstValue = 0;
    private double secondValue = 0;
    private Operator operator = Operator.NONE;
    private boolean isOperatorPressed = false;
    private boolean isEqualsPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialisation de l'écran de la calculatrice
        screen = findViewById(R.id.display);

        // Configuration des écouteurs pour les boutons numériques
        setNumericButtonListeners();

        // Configuration des écouteurs pour les boutons d'opérateurs
        setOperatorButtonListeners();

        // Configuration de l'écouteur pour le bouton de réinitialisation
        setResetButtonListener();
    }

    // Méthode pour configurer les écouteurs des boutons numériques
    private void setNumericButtonListeners() {
        int[] numericButtonIds = {
                R.id.button1, R.id.button2, R.id.button3,
                R.id.button4, R.id.button5, R.id.button6,
                R.id.button7, R.id.button8, R.id.button9,
                R.id.button0
        };

        // Définition d'un écouteur commun pour les boutons numériques
        View.OnClickListener listener = v -> {
            Button button = (Button) v;
            String currentText = screen.getText().toString();
            // Vérification si le texte actuel est "0" ou si un opérateur a été pressé
            if (currentText.equals("0") || currentText.equals("Error: Division by zero") || isOperatorPressed || isEqualsPressed) {
                screen.setText(button.getText().toString());
                isOperatorPressed = false;
                isEqualsPressed = false;
            } else {
                screen.append(button.getText().toString());
            }
        };

        // Attribution de l'écouteur à chaque bouton numérique
        for (int id : numericButtonIds) {
            findViewById(id).setOnClickListener(listener);
        }
    }

    // Méthode pour configurer les écouteurs des boutons d'opérateurs
    private void setOperatorButtonListeners() {
        // Attribution d'un écouteur à chaque bouton d'opérateur
        findViewById(R.id.buttonAdd).setOnClickListener(v -> setOperator(Operator.ADD));
        findViewById(R.id.buttonSubtract).setOnClickListener(v -> setOperator(Operator.SUBTRACT));
        findViewById(R.id.buttonMultiply).setOnClickListener(v -> setOperator(Operator.MULTIPLY));
        findViewById(R.id.buttonDivide).setOnClickListener(v -> setOperator(Operator.DIVIDE));
        findViewById(R.id.buttonEqual).setOnClickListener(v -> calculateResult());
    }

    // Méthode pour configurer l'écouteur du bouton de réinitialisation
    private void setResetButtonListener(){
        findViewById(R.id.buttonC).setOnClickListener(v -> {
            // Réinitialisation de l'écran et des valeurs
            screen.setText("0");
            firstValue = 0;
            secondValue = 0;
            isOperatorPressed = false;
        });
    }

    // Méthode pour définir l'opérateur sélectionné
    private void setOperator(Operator operator) {
        this.operator = operator;
        firstValue = Double.parseDouble(screen.getText().toString());
        isOperatorPressed = true;
    }

    // Méthode pour effectuer le calcul en fonction de l'opérateur
    private void calculateResult() {
        try {
            secondValue = Double.parseDouble(screen.getText().toString());
            // Vérification de la division par zéro
            if (operator == Operator.DIVIDE && secondValue == 0) {
                screen.setText("Error: Division by zero");
                return; // Arrête la méthode si la division par zéro est détectée
            }
            double result = performOperation(firstValue, secondValue, operator);
            screen.setText(String.valueOf(result));
            operator = Operator.NONE;
            isOperatorPressed = false;
            isEqualsPressed = true;
        } catch (NumberFormatException e) {
            screen.setText("Error: Invalid input");
        }
    }

    // Méthode pour effectuer l'opération mathématique
    private double performOperation(double a, double b, Operator operator) {
        switch (operator) {
            case ADD:
                return a + b;
            case SUBTRACT:
                return a - b;
            case MULTIPLY:
                return a * b;
            case DIVIDE:
                return a / b;
            default:
                return 0;
        }
    }

    // Énumération des opérateurs possibles
    private enum Operator {
        NONE, ADD, SUBTRACT, MULTIPLY, DIVIDE
    }
}
