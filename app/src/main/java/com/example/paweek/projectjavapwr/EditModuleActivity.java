package com.example.paweek.projectjavapwr;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.paweek.projectjavapwr.Adapters.ModulesAdapter;
import com.example.paweek.projectjavapwr.Adapters.TermsAdapter;
import com.example.paweek.projectjavapwr.Models.Module;
import com.example.paweek.projectjavapwr.Models.Term;
import com.example.paweek.projectjavapwr.Services.ModulesService;

import java.util.ArrayList;

public class EditModuleActivity extends AppCompatActivity {

    public static final String KEY_ID = "key_id";
    public static final String KEY_EXIST = "key_exist";
    private int Id;
    private ModulesService _service;
    private ArrayList<Term> _terms;
    private Module _module;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_module);

        final ListView termsList = (ListView) findViewById(R.id.termsList);
        Button saveBtn = (Button) findViewById(R.id.saveModuleBtn);
        Button deletBtn = (Button) findViewById(R.id.deleteModuleBtn);
        final EditText moduleNameTxt = (EditText) findViewById(R.id.moduleName);

        _service = new ModulesService(this);
        Bundle bundle = getIntent().getExtras();
        Id = bundle.getInt(KEY_ID, 0);
        Boolean exist = bundle.getBoolean(KEY_EXIST, false);
        if (!exist)
            deletBtn.setVisibility(View.INVISIBLE);


        _terms = _service.getTerms(Id);
        _module = _service.getModule(Id);
        if (_module == null)
            _module = new Module(0, AppStrings.Token, "");
        ArrayList<Module> modules = _service.getModules(AppStrings.Token);

        final TermsAdapter adapter = new TermsAdapter(this, _terms);

        termsList.setAdapter(adapter);

        final EditText sentenceTxt = (EditText) findViewById(R.id.sentenceTxt);
        final EditText translationTxt = (EditText) findViewById(R.id.translationTxt);
        Button addTermBtn = (Button) findViewById(R.id.addTermBtn);
        moduleNameTxt.setText(String.valueOf(_module.Name));

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                _module.Name = moduleNameTxt.getText().toString();
                if (_module.Name.equals(""))
                {
                    showAlert("Modules cannot have empty name");
                    return;
                }

                if (_service.saveModule(_module, _terms))
                {
                    showAlert("Saved succed");
                    openModules();
                }
                else
                    showAlert("Something goes wrong");
            }
        });

        deletBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _service.deleteModule(_module.Id);
                openModules();
            }
        });

        addTermBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Term term = new Term(0,_module.Id, sentenceTxt.getText().toString(), translationTxt.getText().toString());
                _terms.add(term);
                adapter.notifyDataSetChanged();
                sentenceTxt.setText("");
                translationTxt.setText("");
            }
        });
    }

    private void showAlert(String message)
    {
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
        dlgAlert.setMessage(message);
        dlgAlert.show();
    }

    private void openModules()
    {
        Intent intent = new Intent(this, ModulesActivity.class);
        startActivity(intent);
    }
}
