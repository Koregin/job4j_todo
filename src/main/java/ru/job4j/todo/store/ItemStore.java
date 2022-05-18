package ru.job4j.todo.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Item;

import java.util.List;

@Repository
public class ItemStore implements Store {
    private final SessionFactory sf;

    public ItemStore(SessionFactory sf) {
        this.sf = sf;
    }

    @Override
    public Item add(Item item) {
        final Session session = sf.openSession();
        try (session) {
            session.beginTransaction();
            session.save(item);
            session.getTransaction().commit();
            return item;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        }
    }

    @Override
    public List<Item> findAll() {
        final Session session = sf.openSession();
        try (session) {
            session.beginTransaction();
            List result = session.createQuery("from Item ").list();
            session.getTransaction().commit();
            return result;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        }
    }

    @Override
    public List<Item> findDoneItems() {
        final Session session = sf.openSession();
        try (session) {
            session.beginTransaction();
            List result = session.createQuery("from Item i where i.done = true ").list();
            session.getTransaction().commit();
            return result;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        }
    }

    @Override
    public List<Item> findNewItems() {
        final Session session = sf.openSession();
        try (session) {
            session.beginTransaction();
            List result = session.createQuery("from Item i where i.done = false").list();
            session.getTransaction().commit();
            return result;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        }
    }

    @Override
    public boolean replace(Item item) {
        final Session session = sf.openSession();
        try (session) {
            session.beginTransaction();
            session.update(item);
            session.getTransaction().commit();
            return true;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        }
    }

    @Override
    public boolean delete(int id) {
        final Session session = sf.openSession();
        try (session) {
            session.beginTransaction();
            Item item = new Item();
            item.setId(id);
            session.delete(item);
            session.getTransaction().commit();
            return true;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        }
    }

    @Override
    public Item findById(int id) {
        final Session session = sf.openSession();
        try (session) {
            session.beginTransaction();
            Item item = session.get(Item.class, id);
            session.getTransaction().commit();
            return item;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        }
    }

    @Override
    public void setDone(int id) {
        final Session session = sf.openSession();
        try (session) {
            session.beginTransaction();
            session.createQuery("update Item i set i.done = :newDone where i.id = :fId")
                    .setParameter("newDone", true)
                    .setParameter("fId", id)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        }
    }
}