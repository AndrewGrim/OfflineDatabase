public class Monster {

    int id; // might need to be integer not int??
    String name, sex, genus;

    public Monster(int id, String name, String sex, String genus) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.genus = genus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getGenus() {
        return genus;
    }

    public void setGenus(String genus) {
        this.genus = genus;
    }
}
