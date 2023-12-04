package com.example.yosrijemai_lsi3_mesure_glycemie.view;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yosrijemai_lsi3_mesure_glycemie.R;
import com.example.yosrijemai_lsi3_mesure_glycemie.controller.Controller;
public class MainActivity extends AppCompatActivity {
    private TextView TVAge;
    private SeekBar sbAge;
    private RadioButton BtnOui;
    private RadioButton BtnNon;
    private EditText ETValMes;
    private Button BtnConsulter;
    private TextView text;
    private RadioGroup radio;
    private boolean jeuner;
    @SuppressLint("WrongViewCast")
    private void init() {
        radio = findViewById(R.id.radioB);
        TVAge = findViewById(R.id.agetext);
        sbAge = findViewById(R.id.sbAge);
        BtnOui = findViewById(R.id.rbtOui);
        BtnNon = findViewById(R.id.rbtNon);
        ETValMes = findViewById(R.id.etValeur);
        BtnConsulter = findViewById(R.id.consulterBtn);
        text = findViewById(R.id.result);
        sbAge.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.i("Information", "onProgressChanged " + progress);
                // affichage dans le Log de Android studio pour voir les changements détectés sur le SeekBar ..
                TVAge.setText("Votre âge : " + progress);
                // Mise à jour du TextView par la valeur: progress à chaque changement.
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton selectedRadioButton = findViewById(checkedId);
                String selectedValue = selectedRadioButton.getText().toString();
                jeuner = selectedValue.equals("Oui");
            }
        });
        BtnConsulter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int age = sbAge.getProgress();
                System.out.println("age =" + age);
                String valmesure = (ETValMes.getText().toString());
                System.out.println("val =" + valmesure);
                Controller controller = new Controller();
                int inputIsValid = controller.createPatient(age,valmesure,jeuner);
                if (inputIsValid == 1 ) {
                    Toast.makeText(getApplicationContext(), "L'âge et la valeur de mesure sont invalides", Toast.LENGTH_SHORT).show();
                } else if (inputIsValid == -1) {
                    Toast.makeText(getApplicationContext(), "L'âge est invalide", Toast.LENGTH_SHORT).show();
                } else if (inputIsValid ==-2) {
                    Toast.makeText(getApplicationContext(), "la valeur de mesure est invalide", Toast.LENGTH_SHORT).show();
                } else {
                    controller.patient.calcule();
                    text.setText(controller.getReponse());
                    ETValMes.setText("");
                    sbAge.setProgress(0);
                }
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }
}