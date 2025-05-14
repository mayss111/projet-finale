package models;

public class Question {
    private int idq,idquiz;
    private String question ,prop1 ,prop2,propc;


    public Question(int idq, String question, String prop1, String prop2, String propc) {
        this.idq = idq;

        this.question = question;
        this.prop1 = this.prop1;
        this.prop2 = this.prop2;
        this.propc = this.propc;
    }
    public  Question(String question , String prop1 ,String prop2,String propc,int idquiz) {
        this.idquiz = idquiz;
        this.question = question;
        this.prop1 = prop1;
        this.prop2 = prop2;
        this.propc = propc;
    }

    public Question(int idq, int idquiz, String question, String prop1, String prop2, String propc) {
        this.idq = idq;
        this.idquiz = idquiz;
        this.question = question;
        this.prop1 = prop1;
        this.prop2 = prop2;
        this.propc = propc;

    }

    public int getIdq() {
        return idq;
    }

    public void setIdq(int idq) {
        this.idq = idq;
    }

    public int getIdquiz() {
        return idquiz;
    }

    public void setIdquiz(int idquiz) {
        this.idquiz = idquiz;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getProp1() {
        return prop1;
    }

    public void setProp1(String prop1) {
        this.prop1 = prop1;
    }

    public String getProp2() {
        return prop2;
    }

    public void setProp2(String prop2) {
        this.prop2 = prop2;
    }

    public String getPropc() {
        return propc;
    }

    public void setPropc(String propc) {
        this.propc = propc;
    }

    @Override
    public String toString() {
        return "Question{" +
                "idq=" + idq +
                ", idquiz=" + idquiz +
                ", question='" + question + '\'' +
                ", prop1='" + prop1 + '\'' +
                ", prop2='" + prop2 + '\'' +
                ", propc='" + propc + '\'' +
                '}';
    }
}
