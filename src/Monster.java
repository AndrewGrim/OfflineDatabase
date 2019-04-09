import java.sql.Blob;

public class Monster {

    int id; // might need to be integer not int??
    String name, gender, species, generation;

    public Monster(int id, String name, String gender, String species, String generation) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.species = species;
        this.generation = generation;
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
}
