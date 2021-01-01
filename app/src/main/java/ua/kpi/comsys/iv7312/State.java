package ua.kpi.comsys.iv7312;

public class State {

    private String title; // назва
    private String year;  // рік
    private String type;  // тип
    private int poster; // ресурс флага
    private int index; // индекс
    private boolean isInfo; // проверка существует ли info для даного постера

    public State(String title, String year, String type, int poster, boolean isInfo, int index){

        this.title=title;
        this.year=year;
        this.type=type;
        this.poster=poster;
        this.index=index;
        this.isInfo=isInfo;
    }

    public String getTitle() {
        return this.title;
    }

    public String getYear() {
        return this.year;
    }

    public String getType() { return this.type; }

    public int getPoster() {
        return this.poster;
    }

    public Integer getIndex() { return this.index; }

    public Boolean getIsInfo() { return this.isInfo; }

}