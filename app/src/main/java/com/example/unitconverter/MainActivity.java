package com.example.unitconverter;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Spinner spinnerFrom, spinnerTo;
    private EditText inputValue;
    private TextView resultView;
    private Button convertButton;

    private String[] lengthUnits = {"Inch", "Foot", "Yard", "Mile"};
    private String[] weightUnits = {"Pound", "Ounce", "Ton"};
    private String[] temperatureUnits = {"Celsius", "Fahrenheit", "Kelvin"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinnerFrom = findViewById(R.id.spinnerFrom);
        spinnerTo = findViewById(R.id.spinnerTo);
        inputValue = findViewById(R.id.inputValue);
        resultView = findViewById(R.id.resultView);
        convertButton = findViewById(R.id.convertButton);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.units, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerFrom.setAdapter(adapter);
        spinnerTo.setAdapter(adapter);

        convertButton.setOnClickListener(v -> convertUnit());
    }

    private void convertUnit() {
        String fromUnit = spinnerFrom.getSelectedItem().toString();
        String toUnit = spinnerTo.getSelectedItem().toString();
        String inputText = inputValue.getText().toString();

        if (inputText.isEmpty()) {
            Toast.makeText(this, "Please enter a value", Toast.LENGTH_SHORT).show();
            return;
        }

        double value = Double.parseDouble(inputText);

        // Validate category before conversion
        if (!isValidConversion(fromUnit, toUnit)) {
            Toast.makeText(this, "Invalid conversion: Please select units from the same category", Toast.LENGTH_LONG).show();
            return;
        }

        double result = performConversion(fromUnit, toUnit, value);
        resultView.setText(String.format("Result: %.4f %s", result, toUnit));
    }

    private boolean isValidConversion(String from, String to) {
        return (isInArray(from, lengthUnits) && isInArray(to, lengthUnits)) ||
                (isInArray(from, weightUnits) && isInArray(to, weightUnits)) ||
                (isInArray(from, temperatureUnits) && isInArray(to, temperatureUnits));
    }

    private boolean isInArray(String item, String[] array) {
        for (String element : array) {
            if (element.equals(item)) return true;
        }
        return false;
    }

    private double performConversion(String from, String to, double value) {
        // Length Conversions
        if (from.equals("Inch") && to.equals("Foot")) return value / 12;
        if (from.equals("Inch") && to.equals("Yard")) return value / 36;
        if (from.equals("Inch") && to.equals("Mile")) return value / 63360;
        if (from.equals("Foot") && to.equals("Inch")) return value * 12;
        if (from.equals("Foot") && to.equals("Yard")) return value / 3;
        if (from.equals("Foot") && to.equals("Mile")) return value / 5280;
        if (from.equals("Yard") && to.equals("Inch")) return value * 36;
        if (from.equals("Yard") && to.equals("Foot")) return value * 3;
        if (from.equals("Yard") && to.equals("Mile")) return value / 1760;
        if (from.equals("Mile") && to.equals("Inch")) return value * 63360;
        if (from.equals("Mile") && to.equals("Foot")) return value * 5280;
        if (from.equals("Mile") && to.equals("Yard")) return value * 1760;

        // Weight Conversions
        if (from.equals("Pound") && to.equals("Ounce")) return value * 16;
        if (from.equals("Pound") && to.equals("Ton")) return value / 2000;
        if (from.equals("Ounce") && to.equals("Pound")) return value / 16;
        if (from.equals("Ounce") && to.equals("Ton")) return value / 32000;
        if (from.equals("Ton") && to.equals("Pound")) return value * 2000;
        if (from.equals("Ton") && to.equals("Ounce")) return value * 32000;

        // Temperature Conversions
        if (from.equals("Celsius") && to.equals("Fahrenheit")) return (value * 1.8) + 32;
        if (from.equals("Celsius") && to.equals("Kelvin")) return value + 273.15;
        if (from.equals("Fahrenheit") && to.equals("Celsius")) return (value - 32) / 1.8;
        if (from.equals("Fahrenheit") && to.equals("Kelvin")) return ((value - 32) / 1.8) + 273.15;
        if (from.equals("Kelvin") && to.equals("Celsius")) return value - 273.15;
        if (from.equals("Kelvin") && to.equals("Fahrenheit")) return ((value - 273.15) * 1.8) + 32;

        return value;
    }
}
