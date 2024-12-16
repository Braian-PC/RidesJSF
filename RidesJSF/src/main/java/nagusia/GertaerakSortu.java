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

public boolean createAndStoreErabiltzailea(String izena, String pasahitza) {
    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    session.beginTransaction();

    try {
        // Verificar si el nombre de usuario ya existe
        String hql = "SELECT COUNT(e) FROM Erabiltzailea e WHERE e.izena = :izena";
        Long count = (Long) session.createQuery(hql)
            .setParameter("izena", izena)
            .uniqueResult();

        if (count > 0) {
            // Usuario ya existe, revertir la transacción
            System.out.println("El usuario ya existe en la base de datos.");
            session.getTransaction().rollback();
            return false;
        }

        // Crear la nueva entidad Erabiltzailea
        Erabiltzailea erabiltzailea = new Erabiltzailea();
        erabiltzailea.setIzena(izena);
        erabiltzailea.setPasahitza(pasahitza);

        // Guardar la entidad
        session.persist(erabiltzailea);

        // Confirmar la transacción
        session.getTransaction().commit();
        System.out.println("Usuario creado correctamente.");
        return true;
    } catch (Exception e) {
        System.out.println("Error al crear el usuario: " + e.getMessage());

        // Revertir la transacción en caso de error
        if (session.getTransaction().isActive()) {
            session.getTransaction().rollback();
        }
        return false;
    }
}

public boolean deleteErabiltzailea(String izena, String pasahitza) {
    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    session.beginTransaction();

    try {
        // Consultar el usuario basado en el nombre y contraseña
        String hql = "FROM Erabiltzailea WHERE izena = :izena AND pasahitza = :pasahitza";
        Erabiltzailea erabiltzailea = (Erabiltzailea) session.createQuery(hql)
            .setParameter("izena", izena)
            .setParameter("pasahitza", pasahitza)
            .uniqueResult();

        if (erabiltzailea != null) {
            // Eliminar el usuario
            session.delete(erabiltzailea);

            // Confirmar la transacción
            session.getTransaction().commit();
            System.out.println("Usuario eliminado correctamente.");
            return true;
        } else {
            System.out.println("Usuario no encontrado con las credenciales proporcionadas.");
            session.getTransaction().rollback();
            return false;
        }
    } catch (Exception e) {
        System.out.println("Error al eliminar el usuario: " + e.getMessage());

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

    try {
        // Verificar si ya existe un Ride con el mismo from, to y date
        String hql = "SELECT COUNT(r) FROM Ride r WHERE r.from = :from AND r.to = :to AND r.date = :date";
        Long count = (Long) session.createQuery(hql)
            .setParameter("from", from)
            .setParameter("to", to)
            .setParameter("date", date)
            .uniqueResult();

        if (count > 0) {
            throw new IllegalArgumentException("A ride in the same date already exists.");
        }

        // Crear y guardar el nuevo Ride
        Ride r = new Ride();
        r.setFrom(from);
        r.setTo(to);
        r.setDate(date);
        r.setPrice(price);
        r.setnPlaces(nPlaces);
        r.setDriver(driver);  // Pasamos el nombre del conductor como String

        session.persist(r);
        session.getTransaction().commit();
    } catch (Exception e) {
        if (session.getTransaction().isActive()) {
            session.getTransaction().rollback();
        }
        throw e; // Repropagar la excepción para que sea manejada por el llamador
    }
}

public void createAndStorageDriver(String from, String to, Date date, float price, Integer nPlaces) {
    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    session.beginTransaction();

    try {
        // Verificar si ya existe un Ride con el mismo from, to y date
        String hql = "SELECT COUNT(r) FROM Ride r WHERE r.from = :from AND r.to = :to AND r.date = :date";
        Long count = (Long) session.createQuery(hql)
            .setParameter("from", from)
            .setParameter("to", to)
            .setParameter("date", date)
            .uniqueResult();

        // Si ya existe un viaje con los mismos parámetros, lanzamos una excepción
        if (count > 0) {
            throw new IllegalArgumentException("A ride with the same parameters already exists.");
        }

        // Crear y guardar el nuevo Ride
        Ride r = new Ride();
        r.setFrom(from);
        r.setTo(to);
        r.setDate(date);
        r.setPrice(price);
        r.setnPlaces(nPlaces);

        session.persist(r);  // Guardamos el Ride en la base de datos
        session.getTransaction().commit();
    } catch (Exception e) {
        // En caso de error, revertimos la transacción
        if (session.getTransaction().isActive()) {
            session.getTransaction().rollback();
        }
        throw e; // Repropagamos la excepción para que sea manejada por el llamador
    }
}



public List<String> getAllDrivers() {
	List<String> list = new ArrayList<>();
	Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    session.beginTransaction();
	
    try {
    	
        Query query = session.createQuery("select distinct r.driver from Ride r");

        List<String> result = query.list();
        
        session.getTransaction().commit();

        if (result.isEmpty()) {
            System.out.println("No drivers found");
        } else {
            for (String fromValue : result) {
                System.out.println("Driver " + fromValue);
            }
        
        }
        return result;
    } catch(Exception e) {
    	System.out.println("Error al obtener detalles del viaje: " + e.getMessage());
    }
    return null;
}

public List<Ride> getRideDetails(String from, String to, Date date) {
    // Obtener la sesión de Hibernate y comenzar una transacción
	System.out.println(">> DataAccess: getRides=> from= "+from+" to= "+to+" date "+date);
	List<Ride> res = new ArrayList<>();	
    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    session.beginTransaction();

    try {
        
        Query query = session.createQuery("from Ride r where r.from = :from and r.to = :to and r.date = :date");
        
        // Establecer los parámetros de la consulta
        query.setParameter("from", from);
        query.setParameter("to", to);
        query.setParameter("date", date);

        // Ejecutar la consulta y obtener los resultados
        List<Ride> rides = query.list();

        // Confirmar la transacción
        session.getTransaction().commit();

        // Mostrar los resultados en System.out
        for (Ride ride:rides){
        	res.add(ride);
 		  }

        return res;
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

public List<Ride> getRidesByUser(String user) {
    // Obtener la sesión de Hibernate y comenzar una transacción
    System.out.println(">> DataAccess: getRidesByUser => user= " + user);
    List<Ride> res = new ArrayList<>();
    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    session.beginTransaction();

    try {
        // Crear la consulta HQL para buscar viajes asociados al usuario
        Query query = session.createQuery("from Ride r where r.driver = :driver");

        // Establecer el parámetro de usuario
        query.setParameter("driver", user);

        // Ejecutar la consulta y obtener los resultados
        List<Ride> rides = query.list();

        // Confirmar la transacción
        session.getTransaction().commit();

        // Mostrar los resultados en System.out
        for (Ride ride : rides) {
            res.add(ride);
        }

        return res;
    } catch (Exception ex) {
        // Manejar errores
        System.out.println("Error al obtener viajes por usuario: " + ex.getMessage());

        // Revertir la transacción en caso de error
        if (session.getTransaction().isActive()) {
            session.getTransaction().rollback();
        }

        return new ArrayList<>();
    }
}


public List<String> getAllFroms() {
    // Obtener la sesión de Hibernate y comenzar una transacción
    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    session.beginTransaction();

    try {
        // Crear la consulta HQL para obtener todos los valores de "from" en la tabla Ride
        String hql = "select distinct r.from from Ride r";
        
        // Ejecutar la consulta
        Query query = session.createQuery(hql);

        // Obtener los resultados como una lista de cadenas
        List<String> result = query.list();

        // Confirmar la transacción
        session.getTransaction().commit();

        // Comprobar si hay resultados y evitar el acceso a un índice vacío
        if (result.isEmpty()) {
            System.out.println("No se encontraron registros para 'from'.");
        } else {
            for (String fromValue : result) {
                System.out.println("From: " + fromValue);
            }
        }

        return result;
    } catch (Exception ex) {
        // Manejar errores
        System.out.println("Error al obtener los valores 'from': " + ex.getMessage());

        // Revertir la transacción en caso de error
        if (session.getTransaction().isActive()) {
            session.getTransaction().rollback();
        }

        return new ArrayList<>();
    }
}

public List<String> getAllToByFrom(String from) {
    // Obtener la sesión de Hibernate y comenzar una transacción
    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    session.beginTransaction();

    try {
        // Crear la consulta HQL para obtener todos los valores "to" donde "from" sea igual al parámetro
        String hql = "select r.to from Ride r where r.from = :from";

        // Ejecutar la consulta
        Query query = session.createQuery(hql);

        // Establecer el parámetro "from" en la consulta
        query.setParameter("from", from);

        // Obtener los resultados como una lista de cadenas
        List<String> result = query.list();

        // Confirmar la transacción
        session.getTransaction().commit();

        // Comprobar si hay resultados y manejar el caso de lista vacía
        if (result.isEmpty()) {
            System.out.println("No se encontraron destinos ('to') para el valor 'from' proporcionado.");
        } else {
            for (String toValue : result) {
                System.out.println("To: " + toValue);
            }
        }

        return result;
    } catch (Exception ex) {
        // Manejar errores
        System.out.println("Error al obtener los destinos 'to': " + ex.getMessage());

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