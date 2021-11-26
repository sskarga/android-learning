package com.sskarga.multiplicationtable.questions;

public class DivisionQuestion extends  Question implements nextQuestion {

    int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public DivisionQuestion() {
        this.template = "%d : %d = ?";
    }

    @Override
    public Question next() {
        this.valB = getRandomNumber(1, 10);
        this.ans = getRandomNumber(0, 10);
        this.valA = ans * valB;
        this.questionStr = String.format(this.template, this.valA, this.valB);
        return this;
    }
}
