import entity.User;
import org.junit.Test;
import repository.UserRepository;

public class UserRepositoryTest {

    private final UserRepository repo = new UserRepository();

    @Test
    public void testCRUD() {

        System.out.println("=== CRUD TEST STARTED ===");

        // CREATE
        User user = new User("Alice", "alice@mail.com");
        repo.save(user);
        System.out.println("Создан пользователь: ID=" + user.getId());

        // READ
        User loaded = repo.findById(user.getId());
        System.out.println("Найден пользователь: " + loaded.getName());

        // UPDATE
        loaded.setName("Alice Updated");
        repo.update(loaded);
        System.out.println("Имя обновлено.");

        // DELETE
        repo.delete(loaded.getId());
        System.out.println("Пользователь удалён.");

        System.out.println("=== CRUD TEST FINISHED ===");
    }
}
