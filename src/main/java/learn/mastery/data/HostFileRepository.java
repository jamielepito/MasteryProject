package learn.mastery.data;

public class HostFileRepository implements HostRepository{

    //set/get id
    //set/get lastName
    //set/get email
    //set/get phone
    //set/get address
    //set/get city
    //set/get state
    //set/get postal
    //set/get standardRate
    //set/get weekendRate

    private final String filePath;

    public HostFileRepository(String filePath){
        this.filePath = filePath;
    }
}
