package com.sskarga.multiplicationtable.questions;

public class MultiplicationQuestion extends Question implements nextQuestion {

    int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public MultiplicationQuestion() {
        this.template = "%d × %d = ?";
    }

    @Override
    public Question next() {
        this.valA = getRandomNumber(0, 10);
        this.valB = getRandomNumber(0, 10);
        this.ans = this.valA * this.valB;
        this.questionStr = String.format(this.template, this.valA, this.valB);
        return this;
    }
}
