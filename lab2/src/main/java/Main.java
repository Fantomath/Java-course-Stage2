import entity.User;
import repository.UserRepository;

public class Main {
    public static void main(String[] args) {

        UserRepository repo = new UserRepository();

        User u = new User("Mark", "mark@mail.com");
        repo.save(u);

        System.out.println("User saved!");

        User loaded = repo.findById(1L);
        System.out.println("Loaded user: " + loaded.getName());
    }
}
