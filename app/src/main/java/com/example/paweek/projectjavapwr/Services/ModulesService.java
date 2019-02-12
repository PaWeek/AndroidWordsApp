package com.example.paweek.projectjavapwr.Services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.paweek.projectjavapwr.AppStrings;
import com.example.paweek.projectjavapwr.DataBase.DbHelper;
import com.example.paweek.projectjavapwr.DataBase.ModulesTable;
import com.example.paweek.projectjavapwr.DataBase.TermsTable;
import com.example.paweek.projectjavapwr.DataBase.UsersTable;
import com.example.paweek.projectjavapwr.Models.Module;
import com.example.paweek.projectjavapwr.Models.Term;

import java.util.ArrayList;

public class ModulesService {

    private DbHelper dbHelper;


    public ModulesService(Context context)
    {
        dbHelper = new DbHelper(context);
    }

    public Boolean saveModule(Module module, ArrayList<Term> terms)
    {
        if (module == null)
            return false;

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(ModulesTable.NAME, module.Name);
        cv.put(ModulesTable.USER_ID, module.UserId);

        if(module.Id == 0)
            module.Id = (int) db.insert(ModulesTable.TABLE_NAME, null, cv);
        else
        {
            db.update(ModulesTable.TABLE_NAME, cv, "_id="+module.Id, null);
            db.delete(TermsTable.TABLE_NAME, TermsTable.MODULES_ID+"="+module.Id, null);
        }

        for (int i = 0; i < terms.size(); i++)
        {
            Term term = terms.get(i);
            ContentValues cv2 = new ContentValues();
            cv2.put(TermsTable.SENTENCE, term.Sentence);
            cv2.put(TermsTable.TRANSLATION, term.Translation);
            cv2.put(TermsTable.MODULES_ID, module.Id);

            db.insert(TermsTable.TABLE_NAME, null, cv2);
        }

        return true;
    }

    public void deleteModule(int id)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete(TermsTable.TABLE_NAME, TermsTable.MODULES_ID+"="+id, null);
        db.delete(ModulesTable.TABLE_NAME, ModulesTable._ID+"="+id, null);
    }

    public Module getModule(int id)
    {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.query(ModulesTable.TABLE_NAME, new String[]{ModulesTable._ID, ModulesTable.NAME},ModulesTable._ID+"="+id,
                null, null, null, null);

        while (c.moveToNext())
            return new Module(c.getInt(c.getColumnIndex(ModulesTable._ID)),AppStrings.Token,c.getString(c.getColumnIndex(ModulesTable.NAME)));

        return null;
    }

    public ArrayList<Module> getModules(int userId)
    {
        ArrayList<Module> modules = new ArrayList<Module>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.query(ModulesTable.TABLE_NAME, new String[]{ModulesTable._ID, ModulesTable.NAME},ModulesTable.USER_ID+"="+userId,
                null, null, null, null);

        while (c.moveToNext())
        {
            Module module = new Module(c.getInt(c.getColumnIndex(ModulesTable._ID)),AppStrings.Token,c.getString(c.getColumnIndex(ModulesTable.NAME)));
            modules.add(module);
        }

        return modules;
    }

    public ArrayList<Term> getTerms(int moduleId)
    {
        if (moduleId == 0)
            return new ArrayList<Term>();

        ArrayList<Term> terms = new ArrayList<Term>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.query(TermsTable.TABLE_NAME, new String[]{TermsTable._ID, TermsTable.MODULES_ID, TermsTable.SENTENCE, TermsTable.TRANSLATION},TermsTable.MODULES_ID+"="+moduleId,
                null, null, null, null);

        while (c.moveToNext())
        {
            Term term = new Term(c.getInt(c.getColumnIndex(TermsTable._ID)),moduleId,c.getString(c.getColumnIndex(TermsTable.SENTENCE)),c.getString(c.getColumnIndex(TermsTable.TRANSLATION)));
            terms.add(term);
        }

        return  terms;
    }
}
