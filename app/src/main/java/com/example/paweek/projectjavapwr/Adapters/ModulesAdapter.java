package com.example.paweek.projectjavapwr.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.paweek.projectjavapwr.EditModuleActivity;
import com.example.paweek.projectjavapwr.Models.Module;
import com.example.paweek.projectjavapwr.ModulesActivity;
import com.example.paweek.projectjavapwr.R;
import com.example.paweek.projectjavapwr.TestActivity;

import java.util.ArrayList;
import java.util.List;

public class ModulesAdapter extends ArrayAdapter<Module> {

    private Context context;
    private List<Module> modules;

    public ModulesAdapter(Context context, ArrayList<Module> modules) {
        super(context, R.layout.layout_list_row);
        this.modules = modules;
        this.context = context;
    }

    @Override
    public int getCount() {
        return modules.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ModuleHolder holder = null;

        if (row == null)
        {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(R.layout.layout_list_row, null);

            holder = new ModuleHolder();

            holder.Name = (TextView) row.findViewById(R.id.name);
            holder.EditBtn = (Button) row.findViewById(R.id.editBtn);
            holder.TestBtn = (Button) row.findViewById(R.id.testBtn);

            row.setTag(holder);
        }
        else
            holder = (ModuleHolder) row.getTag();

        holder.Name.setText(modules.get(position).Name);

        holder.EditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditModuleActivity.class);
                intent.putExtra(EditModuleActivity.KEY_ID, modules.get(position).Id);
                intent.putExtra(EditModuleActivity.KEY_EXIST, true);
                context.startActivity(intent);
            }
        });

        holder.TestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TestActivity.class);
                intent.putExtra(TestActivity.KEY_ID, modules.get(position).Id);
                context.startActivity(intent);
            }
        });

        return row;
    }

    static class ModuleHolder
    {
        TextView Name;
        Button EditBtn;
        Button TestBtn;
    }
}
