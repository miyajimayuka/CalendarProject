package jp.ac.shohoku.calendarproject;

public class Memo {
    protected int id;
    protected String note;
    protected String lastupdate;

    public Memo(int id, String note, String lastupdate){
        this.id = id;
        this.note = note;
        this.lastupdate = lastupdate;
    }

    public String getNote(){
        return note;
    }

    public String getLastupdate(){
        return lastupdate;
    }

    public int getId(){
        return id;
    }

}