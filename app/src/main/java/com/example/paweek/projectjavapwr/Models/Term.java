package com.example.paweek.projectjavapwr.Models;

public class Term {
    public int Id;
    public int ModuleId;
    public String Sentence;
    public String Translation;

    public Term(int id, int moduleId, String sentence, String translation)
    {
        Id = id;
        ModuleId = moduleId;
        Sentence = sentence;
        Translation = translation;
    }
}
