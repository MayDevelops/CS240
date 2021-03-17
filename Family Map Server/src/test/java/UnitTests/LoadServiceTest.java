package UnitTests;

import DataAccessObjects.DataAccessException;
import Models.*;
import Service.Requests.LoadRequest;
import Service.Results.LoadResult;
import Service.Services.ClearService;
import Service.Services.LoadService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoadServiceTest {

  @Test
  public void LoadPass() throws DataAccessException {
    ClearService clearService = new ClearService();

    Event eOne = new Event("123", "OptimusPrime", "Alpha1", "Merica", "NY", "Dead", 100f, 900f, 6969);
    Event eTwo = new Event("345", "OptimusPrime", "Alpha2", "Taiwan", "Brown", "Shoppping", 100f, 900f, 1900);
    Event eThree = new Event("456", "Megatron", "Alpha3", "England", "Yellow", "Kickin", 100f, 900f, 6969);
    Event eFour = new Event("678", "Megatron", "Alpha4", "Russia", "Green", "Alive", 100f, 900f, 6969);

    Person personOne = new Person("1", "OptimusPrime", "Bob", "Saggit", "F", "123abc", "abc123", null);
    Person personTwo = new Person("1-2", "OptimusPrime", "Bill", "Saggy", "F", null, null, null);
    Person personThree = new Person("1-2-3", "Megatron", "Boop", "Saggin", "M", null, "abc1234", null);
    Person personFour = new Person("1-2-3-4", "Megatron", "Bop", "Sagger", "M", "123abcd", null, null);

    User userOne = new User("OptimusPrime", "autobotsrox", "stars@gmail.com", "Op", "Prime", "R", "Op123");
    User userTwo = new User("Megatron", "killoptimus", "bumblebeestinks@yahoo.com", "Mega", "Tron", "R", "Me123");

    Event[] eventArray = new Event[]{eOne, eTwo, eThree, eFour};
    Person[] personArray = new Person[]{personOne, personTwo, personThree, personFour};
    User[] userArray = new User[]{userOne, userTwo};

    LoadRequest loadRequest = new LoadRequest(userArray, personArray, eventArray);
    LoadService loadService = new LoadService();
    LoadResult loadResult = loadService.Load(loadRequest);

    assertEquals("Successfully added 2 Users, 4 Persons, and 4 Events.", loadResult.getMessage());

    clearService.ClearDatabase();
  }

  @Test
  public void LoadFail() throws DataAccessException {
    ClearService clearService = new ClearService();

    Event eOne = new Event("123", "OptimusPrime", "Alpha1", "Merica", "NY", "Dead", 100f, 900f, 6969);
    Event eTwo = new Event("345", "OptimusPrime", "Alpha2", "Taiwan", "Brown", "Shoppping", 100f, 900f, 1900);
    Event eThree = new Event("456", "Megatron", "Alpha3", "England", "Yellow", "Kickin", 100f, 900f, 6969);
    Event eFour = new Event("678", "Megatron", "Alpha4", "Russia", "Green", "Alive", 100f, 900f, 6969);
    Event eBad = new Event();

    Person personOne = new Person("1", "OptimusPrime", "Bob", "Saggit", "F", "123abc", "abc123", null);
    Person personTwo = new Person("1-2", "OptimusPrime", "Bill", "Saggy", "F", null, null, null);
    Person personThree = new Person("1-2-3", "Megatron", "Boop", "Saggin", "M", null, "abc1234", null);
    Person personFour = new Person("1-2-3-4", "Megatron", "Bop", "Sagger", "M", "123abcd", null, null);

    User userOne = new User("OptimusPrime", "autobotsrox", "stars@gmail.com", "Op", "Prime", "R", "Op123");
    User userTwo = new User("Megatron", "killoptimus", "bumblebeestinks@yahoo.com", "Mega", "Tron", "R", "Me123");

    Event[] eventArray = new Event[]{eOne, eTwo, eThree, eFour, eBad};
    Person[] personArray = new Person[]{personOne, personTwo, personThree, personFour};
    User[] userArray = new User[]{userOne, userTwo};

    LoadRequest loadRequest = new LoadRequest(userArray, personArray, eventArray);
    LoadService loadService = new LoadService();
    LoadResult loadResult = loadService.Load(loadRequest);

    assertEquals("Error: Invalid input.", loadResult.getMessage());

    clearService.ClearDatabase();
  }
}
