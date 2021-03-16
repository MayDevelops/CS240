package Generators;
import Models.Person;
import java.util.*;
public class GenerateData {

  private ArrayList<Person> personsFamily;
  private GenerateNames nameGenerator = new GenerateNames();
  private GenerateLocations locationGenerator = new GenerateLocations();
  private GenerateEvent eventGenerator;
  private String username;
  private Random r = new Random();

  public GenerationStorage PopulateGenerations(Person p, int numGen) {
    username = p.getAssociatedUsername();
    eventGenerator = new GenerateEvent(username);

    PopulateFamilyTree(p, numGen);

    return new GenerationStorage(personsFamily, eventGenerator.GetEvents());
  }

  private void PopulateFamilyTree(Person root, int numGen) {
    int year = 2000;

    personsFamily = new ArrayList<Person>();
    personsFamily.add(root);

    eventGenerator.Birth(root,year);

    PopulateParents(root, numGen - 1, year);

  }

  private void PopulateParents(Person p, int currentGen, int year) {
    int ageGap = 40;
    year = year - ageGap - r.nextInt(15);

    Person father = PopulateFather(p);
    Person mother = PopulateMother(p);
    JoinFamily(p,father,mother);
    GenerateLifeEvents(father,mother,year);

    personsFamily.add(father);
    personsFamily.add(mother);

    if(currentGen != 0) {
      PopulateParents(father, currentGen - 1, year);
      PopulateParents(mother, currentGen - 1, year);
    }

  }

  private Person PopulateFather(Person p) {
    Person father = new Person();

    father.setAssociatedUsername(username);
    father.setFirstName(nameGenerator.MaleName());
    father.setLastName(p.getLastName());
    father.setGender("M");

    return father;
  }
  private Person PopulateMother(Person p) {
    Person mother = new Person();

    mother.setAssociatedUsername(username);
    mother.setFirstName(nameGenerator.FemaleName());
    mother.setLastName(p.getLastName());
    mother.setGender("F");

    return mother;
  }

  private void JoinFamily(Person c, Person f, Person m) {
    f.setSpouseID(m.getPersonID());
    m.setSpouseID(f.getPersonID());
    c.setFatherID(f.getPersonID());
    c.setMotherID(m.getPersonID());
  }

  private void GenerateLifeEvents(Person f, Person m, int year) {
    eventGenerator.Birth(f, year);
    eventGenerator.Birth(m,year);
    eventGenerator.Marriage(f,m,year);

    int randomEventChance = r.nextInt(4);

    if(year < 1901) {
      eventGenerator.Death(f,year);
      eventGenerator.Death(m,year);
    } else if (randomEventChance == 0) {
      eventGenerator.Death(f,year);
      eventGenerator.Random(m,year);
    } else if (randomEventChance == 1) {
      eventGenerator.Random(f,year);
      eventGenerator.Death(m,year);
    } else if (randomEventChance == 2) {
      eventGenerator.Random(f,year);
    } else {
      eventGenerator.Random(m,year);
    }
  }


}
