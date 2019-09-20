package com.durgesh.schoolassist.Adapters;

import com.parse.ParseFile;

public class QuestionData {
    private int qno;
    private ParseFile file;

    public QuestionData(int qno, ParseFile file) {
        this.qno = qno;
        this.file = file;
    }

    public int getQno() {
        return qno;
    }

    public void setQno(int qno) {
        this.qno = qno;
    }

    public ParseFile getFile() {
        return file;
    }

    public void setFile(ParseFile file) {
        this.file = file;
    }
}
