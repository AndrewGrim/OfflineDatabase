public class Monster {

    int id; // might need to be integer not int??
    String name, gender, species, generation, icon;

    public Monster(int id, String name, String gender, String species, String generation, String icon) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.species = species;
        this.generation = generation;
        this.icon = icon;

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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getGeneration() {
        return generation;
    }

    public void setGeneration(String generation) {
        this.generation = generation;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
