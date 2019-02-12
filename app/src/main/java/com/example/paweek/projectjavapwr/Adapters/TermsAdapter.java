package com.example.paweek.projectjavapwr.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.paweek.projectjavapwr.EditModuleActivity;
import com.example.paweek.projectjavapwr.Models.Term;
import com.example.paweek.projectjavapwr.R;

import java.util.ArrayList;
import java.util.List;

public class TermsAdapter extends ArrayAdapter<Term> {

    private Context context;
    private List<Term> terms;

    public TermsAdapter(Context context, ArrayList<Term> terms) {
        super(context, R.layout.layout_list_row);
        this.terms = terms;
        this.context = context;
    }

    @Override
    public int getCount() {
        return terms.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        TermsAdapter.TermHolder holder = null;

        if (row == null)
        {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(R.layout.layout_terms_list_row, null);

            holder = new TermsAdapter.TermHolder();

            holder.Sentence = (TextView) row.findViewById(R.id.sentenceTxt);
            holder.Translation = (TextView) row.findViewById(R.id.translationTxt);
            holder.DeleteBtn = (Button) row.findViewById(R.id.deleteTermBtn);

            row.setTag(holder);
        }
        else
            holder = (TermsAdapter.TermHolder) row.getTag();

        holder.Sentence.setText(terms.get(position).Sentence);
        holder.Translation.setText(terms.get(position).Translation);

        holder.DeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Term term = terms.get(position);
                terms.remove(term);
                notifyDataSetChanged();
            }
        });

        return row;
    }

    static class TermHolder
    {
        TextView Sentence;
        TextView Translation;
        Button DeleteBtn;
    }
}
