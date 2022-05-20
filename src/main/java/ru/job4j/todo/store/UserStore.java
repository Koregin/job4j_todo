package ru.job4j.todo.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;

import java.util.Optional;
import java.util.function.Function;

@Repository
public class UserStore {
    private final SessionFactory sf;

    public UserStore(SessionFactory sf) {
        this.sf = sf;
    }

    public Optional<User> create(User user) {
        Integer userId = (Integer) this.tx(session -> session.save(user));
        user.setId(userId);
        return userId != 0 ? Optional.of(user) : Optional.empty();
    }

    public Optional<User> findUserByEmailAndPwd(String email, String password) {
        return this.tx(session -> session.createQuery("from User u where u.email = :fEmail and u.password = :fPwd")
                .setParameter("fEmail", email)
                .setParameter("fPwd", password)
                .uniqueResultOptional());
    }

    private <T> T tx(final Function<Session, T> command) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }
}
