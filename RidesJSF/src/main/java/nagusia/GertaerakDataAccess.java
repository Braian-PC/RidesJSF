package nagusia;
import eredua.HibernateUtil;
import eredua.domeinua.Erabiltzailea;
import eredua.domeinua.LoginGertaera;
import org.hibernate.Session;
import java.util.*;
public class GertaerakDataAccess {
public GertaerakDataAccess (){}
public Erabiltzailea createAndStoreErabiltzailea(String izena, String pasahitza,
String mota) {
Session session = HibernateUtil.getSessionFactory().getCurrentSession();
session.beginTransaction();
Erabiltzailea e = new Erabiltzailea();
e.setIzena(izena);
e.setPasahitza(pasahitza);
e.setMota(mota);
session.persist(e);
session.getTransaction().commit();
return e; }
public LoginGertaera createAndStoreLoginGertaera(String erabiltzailea, boolean login,
Date data) {
Session session = HibernateUtil.getSessionFactory().getCurrentSession();
session.beginTransaction();
LoginGertaera e = new LoginGertaera();
try {
e.setErabiltzailea((Erabiltzailea)session.get(Erabiltzailea.class, erabiltzailea));
e.setLogin(login);
e.setData(data);
session.persist(e);
session.getTransaction().commit();
}
catch (Exception ex)
{System.out.println("Errorea: erabiltzailea ez da existitzen: "+ex.toString());
e=null;}
return e;
}
public static void main(String[] args) {
GertaerakDataAccess e = new GertaerakDataAccess ();
System.out.println("Gertaeren sorkuntza:"); //
e.createAndStoreErabiltzailea("Ane", "125", "ikaslea");
e.createAndStoreLoginGertaera("Ane",true, new Date());
e.createAndStoreLoginGertaera("Ane",false, new Date());
e.createAndStoreErabiltzailea("Kepa", "126", "ikaslea");
e.createAndStoreLoginGertaera("Kepa",true, new Date());
e.createAndStoreLoginGertaera("Kepa",false, new Date());
}
}