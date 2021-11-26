package com.sskarga.multiplicationtable.questions;

public class Question {
    String template;
    public int valA;
    public int valB;
    public int ans;
    public String questionStr;
}

interface nextQuestion {
    Question next();
}