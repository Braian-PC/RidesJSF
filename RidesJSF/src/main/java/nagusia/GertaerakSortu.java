package nagusia;

import eredua.HibernateUtil;
import eredua.domeinua.Erabiltzailea;
import eredua.domeinua.LoginGertaera;

import org.hibernate.Query;
import org.hibernate.Session;
import java.util.*;
public class GertaerakSortu {
public GertaerakSortu(){
}
private void createAndStoreLoginGertaera(Long id, String deskribapena, Date data) {
Session session = HibernateUtil.getSessionFactory().getCurrentSession();
session.beginTransaction();
LoginGertaera e = new LoginGertaera();
e.setId(id);
e.setDeskribapena(deskribapena);
e.setData(data);
session.persist(e);
session.getTransaction().commit();
}
private void createAndStoreErabiltzailea(String izena,String pasahitza,String mota) {
Session session = HibernateUtil.getSessionFactory().getCurrentSession();
session.beginTransaction();
Erabiltzailea u = new Erabiltzailea();
u.setIzena(izena);
u.setPasahitza(pasahitza);
u.setMota(mota);
session.persist(u);
session.getTransaction().commit();
}
private void createAndStoreLoginGertaera(String erabil,boolean login,Date data) {
Session session = HibernateUtil.getSessionFactory().getCurrentSession();
session.beginTransaction();
Query q= session.createQuery("from Erabiltzailea where izena= :erabiltzailea");
q.setParameter("erabiltzailea", erabil);
List result=q.list();
LoginGertaera e = new LoginGertaera();
try {
e.setErabiltzailea((Erabiltzailea)result.get(0));
}
catch (Exception ex)
{System.out.println("Errorea: erabiltzailea ez da existitzen"+ex.toString());}
e.setLogin(login);
e.setData(data);
session.persist(e);
session.getTransaction().commit();
}

private void createAndStoreLoginGertaera(String deskribapena, Date data) {
Session session = HibernateUtil.getSessionFactory().getCurrentSession();
session.beginTransaction();
LoginGertaera e = new LoginGertaera();
e.setDeskribapena(deskribapena);
e.setData(data);
session.persist(e);
session.getTransaction().commit();
}
private List gertaerakZerrendatu() {
Session session = HibernateUtil.getSessionFactory().getCurrentSession();
session.beginTransaction();
List result = session.createQuery("from LoginGertaera").list();
session.getTransaction().commit();
return result;
}
public static void main(String[] args) {
GertaerakSortu e = new GertaerakSortu();
System.out.println("Gertaeren sorkuntza:");
////e.createAndStoreLoginGertaera(1L,"Anek ondo egin du logina", new Date());
////e.createAndStoreLoginGertaera(2L,"Nerea saiatu da login egiten", new Date());
////e.createAndStoreLoginGertaera(3L,"Kepak ondo egin du logina", new Date());
//e.createAndStoreLoginGertaera("Anek ondo egin du logina", new Date());
//e.createAndStoreLoginGertaera("Nerea saiatu da login egiten", new Date());
//e.createAndStoreLoginGertaera("Kepak ondo egin du logina", new Date());
//
//List gertaerak = e.gertaerakZerrendatu();
//for (int i = 0; i < gertaerak.size(); i++) {
//LoginGertaera ev = (LoginGertaera) gertaerak.get(i);
//System.out.println("Id: " + ev.getId() + " Deskribapena: "
//+ ev.getDeskribapena() + " Data: " + ev.getData());
//}
//System.out.println("Gertaeren zerrenda:");}
e.createAndStoreErabiltzailea("Ane", "125", "ikaslea");
e.createAndStoreLoginGertaera("Ane",true, new Date());
e.createAndStoreLoginGertaera("Ane",false, new Date());
System.out.println("Gertaeren zerrenda:");
Session session = HibernateUtil.getSessionFactory().getCurrentSession();
session.beginTransaction();
List result = session.createQuery("from LoginGertaera").list();
for (int i = 0; i < result.size(); i++) {
LoginGertaera ev = (LoginGertaera) result.get(i);
System.out.println("Id: " + ev.getId() + " Deskribapena: " +
ev.getDeskribapena() + " Data: " + ev.getData()+ " Login: " + ev.isLogin());
}
session.getTransaction().commit();

}
}