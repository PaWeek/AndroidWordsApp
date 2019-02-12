package com.example.paweek.projectjavapwr;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.paweek.projectjavapwr.Models.Term;
import com.example.paweek.projectjavapwr.Services.ModulesService;

import java.util.ArrayList;

public class TestActivity extends AppCompatActivity {

    public static final String KEY_ID = "key_id";
    private int iterator;
    private Term _currentTerm;
    private ArrayList<Term> _terms;
    ModulesService _service;
    private TextView sentenceTxt;
    private TextView countTxt;
    private TextView resultTxt;
    private EditText translationTxt;
    private int listCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Bundle bundle = getIntent().getExtras();
        int id = bundle.getInt(KEY_ID, 0);
        _service = new ModulesService(this);
        _terms = _service.getTerms(id);
        listCount = _terms.size();
        iterator = 0;
        _currentTerm = _terms.get(iterator);

        Button checkAnswerBtn = (Button) findViewById(R.id.checkAnswerBtn);
        sentenceTxt = (TextView) findViewById(R.id.sentenceTxtT);
        countTxt = (TextView) findViewById(R.id.countTxtT);
        resultTxt = (TextView) findViewById(R.id.resultTxtT);
        translationTxt = (EditText) findViewById(R.id.translationTxtT);

        countTxt.setText((iterator+1)+"/"+listCount);
        sentenceTxt.setText(_currentTerm.Sentence);
        resultTxt.setVisibility(View.INVISIBLE);

        checkAnswerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkAnswer())
                {
                    resultTxt.setTextColor(Color.GREEN);
                    resultTxt.setText("Good!");
                    resultTxt.setVisibility(View.VISIBLE);
                }
                else
                {
                    resultTxt.setTextColor(Color.RED);
                    resultTxt.setText("Bad!");
                }

                translationTxt.setText("");

                if (!nextTerm())
                {
                    openModules();
                }
                else
                    countTxt.setText((iterator+1)+"/"+listCount);
            }
        });
    }

    private Boolean checkAnswer()
    {
        if (_currentTerm.Translation.equals(translationTxt.getText().toString()))
            return true;
        else
            return false;
    }

    private Boolean nextTerm()
    {
        iterator ++;
        if (iterator < listCount)
        {
            _currentTerm = _terms.get(iterator);
            sentenceTxt.setText(_currentTerm.Sentence);
            return true;
        }

        return false;
    }

    private void openModules()
    {
        Intent intent = new Intent(this, ModulesActivity.class);
        startActivity(intent);
    }

}
