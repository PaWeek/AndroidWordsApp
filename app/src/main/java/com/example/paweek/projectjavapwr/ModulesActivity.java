package com.example.paweek.projectjavapwr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.paweek.projectjavapwr.Adapters.ModulesAdapter;
import com.example.paweek.projectjavapwr.Models.Module;
import com.example.paweek.projectjavapwr.Services.ModulesService;

import java.util.ArrayList;

public class ModulesActivity extends AppCompatActivity {

    private ModulesService _service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modules);

        _service = new ModulesService(this);

        final Button addBtn = (Button) findViewById(R.id.addBtn);
        ListView modulesListView = (ListView) findViewById(R.id.modulesListView);

        ArrayList<Module> modules = _service.getModules(AppStrings.Token);

        ModulesAdapter adapter = new ModulesAdapter(this, modules);

        modulesListView.setAdapter(adapter);

        addBtn.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                addModule();
            }
        });
    }

    private void addModule()
    {
        Intent intent = new Intent(this, EditModuleActivity.class);
        intent.putExtra(EditModuleActivity.KEY_ID, 0);
        intent.putExtra(EditModuleActivity.KEY_EXIST, false);
        startActivity(intent);
    }
}
