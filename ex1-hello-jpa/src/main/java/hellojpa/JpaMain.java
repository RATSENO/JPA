package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class JpaMain {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Member member = new Member();
            member.setUsername("user1");
            member.setHomeAddress(new Address("homecity", "street1", "zipcode"));

            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("족발");
            member.getFavoriteFoods().add("피자");

            member.getAddressHistory().add(new Address("old1","old1","old1"));
            member.getAddressHistory().add(new Address("old2","old2","old2"));

            em.persist(member);

            em.flush();
            em.clear();

            System.out.println("===================================");
            Member findMember = em.find(Member.class, member.getId());
            System.out.println("===================================");

            List<Address> addressHistory = findMember.getAddressHistory();
            for (Address address : addressHistory) {
                System.out.println(address.getCity());
            }

            Set<String> favoriteFoods = findMember.getFavoriteFoods();
            Iterator<String> iterator = favoriteFoods.iterator();
            while (iterator.hasNext()){
                System.out.println(iterator.next());
            }



            tx.commit();

        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
