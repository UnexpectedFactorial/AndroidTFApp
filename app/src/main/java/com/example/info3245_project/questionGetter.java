package com.example.info3245_project;

public class questionGetter {
    private String question,yes,no,questionno;

    public questionGetter(){

    }

    public questionGetter(String question,String yes, String no,String questionno){
        this.question = question;
        this.yes = yes;
        this.no = no;
        this.questionno = questionno;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getYes() {
        return yes;
    }

    public void setYes(String yes) {
        this.yes = yes;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getQuestionno() {
        return questionno;
    }

    public void setQuestionnn(String questionno) {
        this.questionno = questionno;
    }

    public String toString(){


        return this.questionno + ". " + this.question + " | Yes:" +this.yes + " No:" + this.no;
    }
}

