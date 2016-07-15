package sk.ness.jpa;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class Test {

	public static void main(String[] args) throws Exception {
		Student student = new Student();
		student.setMeno("Ferko");
		student.setPriezvisko("Hrasko");
		student.setVek(18);

		JpaHelper.beginTransaction();
		EntityManager em = JpaHelper.getEntityManager();
		em.persist(student);
		JpaHelper.commitTransaction();
//
//		// System.out.println(em.createQuery("SELECT s FROM Student s WHERE
//		// s.meno='Janko'").getResultList()); //jazyk JPQL, su tu triedy
//		// (Student je meno triedy nie tabulky), nie tabulky a stlpce
//		Query query = em.createQuery("SELECT s FROM Student s WHERE s.meno=:meno"); // ako
//																					// ?
//																					// v
//																					// Jave
//		
//		query.setParameter("meno", "Ferko");
//		query.setMaxResults(3);						// vyberie mi prve tri prvky z tabulky
//		System.out.println(query.getResultList());
//
//		//zretazenie
//		//System.out.println(em.createQuery("SELECT s FROM Student s WHERE s.meno=:meno").setParameter("meno", "Janko").setMaxResults(3).getResultList());
//		
//		JpaHelper.beginTransaction();
//		student = em.find(Student.class, 1); // hladat z triedy Student s id 25
//		System.out.println(student);
//		student.setVek(20);
//		// em.remove(student);
//		JpaHelper.commitTransaction();
//		System.out.println(student);
//		//JpaHelper.closeAll();
//		// JPAImpl
	}

}
