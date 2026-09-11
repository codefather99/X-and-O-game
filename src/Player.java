public class Player {
    private String name;
    private final String mark;

    public Player(String name, String mark){
        this.name = name;
        this.mark = mark;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        if (name != null && !name.trim().isEmpty()){
            this.name = name.trim();
        }
    }

    public String getMark(){
        return mark;
    }
}
