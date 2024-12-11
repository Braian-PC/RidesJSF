package nagusia;

import eredua.HibernateUtil;
import eredua.domeinua.Driver;
import eredua.domeinua.Erabiltzailea;
import eredua.domeinua.LoginGertaera;
import eredua.domeinua.Ride;  // Asegúrate de que esta línea apunte a la clase Ride correcta


import org.hibernate.Query;
import org.hibernate.Session;


import java.util.*;
public class GertaerakSortu {
	private String izena;
	private String pasahitza;
	
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
	System.out.println("Gertaeren zerrenda:");
	Session session = HibernateUtil.getSessionFactory().getCurrentSession();
	session.beginTransaction();
	List result = session.createQuery("from LoginGertaera").list();
	for (int i = 0; i < result.size(); i++) {
	LoginGertaera ev = (LoginGertaera) result.get(i);
	System.out.println("Id: " + ev.getId() + " Deskribapena: " +
	ev.getDeskribapena() + " Data: " + ev.getData()+ " Login: " + ev.isLogin());
	}
return result;
}

public void createAndStoreErabiltzailea(String izena, String pasahitza, String role) {
    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    session.beginTransaction();

    try {
        // Crear o cargar la entidad Erabiltzailea
        Erabiltzailea erabiltzailea = new Erabiltzailea();
        erabiltzailea.setIzena(izena);
        erabiltzailea.setPasahitza(pasahitza);
        erabiltzailea.setMota(role);

        // Usar merge en lugar de persist para manejar entidades detached
        session.merge(erabiltzailea);

        // Confirmar la transacción
        session.getTransaction().commit();
    } catch (Exception e) {
        System.out.println("Error al crear el usuario: " + e.getMessage());

        // Revertir la transacción en caso de error
        if (session.getTransaction().isActive()) {
            session.getTransaction().rollback();
        }
        throw e;
    }
}

public boolean userInDataBase(String izena, String pasahitza) {
    // Obtener la sesión de Hibernate y comenzar una transacción
    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    session.beginTransaction();

    try {
        // Crear la consulta HQL para buscar el usuario por nombre y contraseña
        Query query = session.createQuery("from Erabiltzailea where izena = :username and pasahitza = :password");
        query.setParameter("username", izena);
        query.setParameter("password", pasahitza);

        // Ejecutar la consulta y obtener el resultado
        List result = query.list();

        // Confirmar la transacción
        session.getTransaction().commit();

        // Verificar si hay un resultado
        return !result.isEmpty();
    } catch (Exception ex) {
        // Manejar errores
        System.out.println("Error al verificar las credenciales: " + ex.getMessage());

        // Revertir la transacción en caso de error
        if (session.getTransaction().isActive()) {
            session.getTransaction().rollback();
        }

        return false;
    }
}

public List<Object[]> getAllUsersAndPasswords() {
    // Obtener la sesión de Hibernate y comenzar una transacción
    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    session.beginTransaction();

    try {
        // Crear la consulta HQL para obtener todos los usuarios y contraseñas
        Query query = session.createQuery("select izena, pasahitza from Erabiltzailea");

        // Ejecutar la consulta y obtener los resultados
        List<Object[]> result = query.list();

        // Confirmar la transacción
        session.getTransaction().commit();

        // Mostrar la lista en System.out
        for (Object[] row : result) {
            System.out.println("Usuario: " + row[0] + ", Contraseña: " + row[1]);
        }

        return result;
    } catch (Exception ex) {
        // Manejar errores
        System.out.println("Error al obtener usuarios y contraseñas: " + ex.getMessage());

        // Revertir la transacción en caso de error
        if (session.getTransaction().isActive()) {
            session.getTransaction().rollback();
        }

        return new ArrayList<>();
    }
}

public void createAndStoreRide(String from, String to, Date date, int nPlaces, float price, String driver) {
    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    session.beginTransaction();
    
    Ride r = new Ride();
    r.setFrom(from);
    r.setTo(to);
    r.setDate(date);
    r.setPrice(price);
    r.setnPlaces(nPlaces);
    r.setDriver(driver);  // Pasamos el nombre del conductor como String
    
    session.persist(r);
    session.getTransaction().commit();
}

public List<Object[]> getRideDetails(String from, String to, Date date) {
    // Obtener la sesión de Hibernate y comenzar una transacción
    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    session.beginTransaction();

    try {
        // Crear la consulta HQL para obtener el driver, nPlaces y price del Ride
        String hql = "select r.driver, r.nPlaces, r.price from Ride r " +
                     "where r.from = :from and r.to = :to and r.date = :date";
        
        Query query = session.createQuery(hql);
        
        // Establecer los parámetros de la consulta
        query.setParameter("from", from);
        query.setParameter("to", to);
        query.setParameter("date", date);

        // Ejecutar la consulta y obtener los resultados
        List<Object[]> result = query.list();

        // Confirmar la transacción
        session.getTransaction().commit();

        // Mostrar los resultados en System.out
        for (Object[] row : result) {
            System.out.println("Driver: " + row[0] + ", Seats: " + row[1] + ", Price: " + row[2]);
        }

        return result;
    } catch (Exception ex) {
        // Manejar errores
        System.out.println("Error al obtener detalles del viaje: " + ex.getMessage());

        // Revertir la transacción en caso de error
        if (session.getTransaction().isActive()) {
            session.getTransaction().rollback();
        }

        return new ArrayList<>();
    }
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
e.createAndStoreErabiltzailea("Ane", "123", "driver");
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

public boolean compareUser(String izena, String pasahitza) {
	if(this.izena.equals(izena) && this.pasahitza.equals(pasahitza)) {
		return true;
	}
	return false;
}



public void printObjMemDB(String azalpena, Erabiltzailea e) {
System.out.print("\tMem:<"+e+"> DB:<"+GertaerakBerreskuratuJDBC.getErabiltzaileaJDBC(e)+"> =>");
System.out.println(azalpena); }
}