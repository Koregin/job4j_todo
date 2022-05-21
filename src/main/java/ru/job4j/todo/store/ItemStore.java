package ru.job4j.todo.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.model.User;

import java.util.List;
import java.util.function.Function;

@Repository
public class ItemStore implements Store {
    private final SessionFactory sf;

    public ItemStore(SessionFactory sf) {
        this.sf = sf;
    }

    @Override
    public Item add(Item item) {
        Integer id = (Integer) this.tx(session -> session.save(item));
        item.setId(id);
        return item;
    }

    @Override
    public List<Item> findAll(User user) {
        return this.tx(session -> session.createQuery("from Item i where i.user = :fUser")
                .setParameter("fUser", user).list());
    }

    @Override
    public List<Item> findDoneItems(User user) {
        return this.tx(session -> session.createQuery("from Item i where i.user = :fUser and i.done = true")
                .setParameter("fUser", user).list());
    }

    @Override
    public List<Item> findNewItems(User user) {
        return this.tx(session -> session.createQuery("from Item i where i.user = :fUser and i.done = false")
                .setParameter("fUser", user).list());
    }

    @Override
    public boolean replace(Item item) {
        Integer rsl = this.tx(session -> session.createQuery("update Item i set i.description = :newDesc where i.id = :fId")
                .setParameter("newDesc", item.getDescription())
                .setParameter("fId", item.getId())
                .executeUpdate());
        return rsl == 1;
    }

    @Override
    public boolean delete(int id) {
        Integer rsl = this.tx(session -> session.createQuery("delete from Item i where i.id = :fId")
                .setParameter("fId", id)
                .executeUpdate());
        return rsl == 1;
    }

    @Override
    public Item findById(int id) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            Item item = (Item) session.createQuery("select i from Item i left join fetch i.categories where i.id = :fId")
                            .setParameter("fId", id)
                            .uniqueResult();
            tx.commit();
            return item;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean setDone(int id) {
        Integer rsl = this.tx(session -> session.createQuery("update Item i set i.done = :newDone where i.id = :fId")
                .setParameter("newDone", true)
                .setParameter("fId", id)
                .executeUpdate());
        return rsl == 1;
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