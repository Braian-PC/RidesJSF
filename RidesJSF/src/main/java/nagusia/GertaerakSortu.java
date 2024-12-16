package nagusia;


import eredua.HibernateUtil;
import eredua.domeinua.CurrentUser;
import eredua.domeinua.Erabiltzailea;
import eredua.domeinua.LoginGertaera;
import eredua.domeinua.Ride;  // Asegúrate de que esta línea apunte a la clase Ride correcta


import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

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

public void createAndStoreLoginGertaera(String erabil,boolean login,Date data) {
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
        Erabiltzailea erabiltzailea = new Erabiltzailea();
        erabiltzailea.setIzena(izena);
        erabiltzailea.setPasahitza(pasahitza);
        erabiltzailea.setMota(role);

        session.merge(erabiltzailea);

        session.getTransaction().commit();
    } catch (Exception e) {
        System.out.println("Error al crear el usuario: " + e.getMessage());

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
        String hql = "SELECT COUNT(e) FROM Erabiltzailea e WHERE e.izena = :izena";
        Long count = (Long) session.createQuery(hql)
            .setParameter("izena", izena)
            .uniqueResult();

        if (count > 0) {
            System.out.println("El usuario ya existe en la base de datos.");
            session.getTransaction().rollback();
            return false;
        }

        Erabiltzailea erabiltzailea = new Erabiltzailea();
        erabiltzailea.setIzena(izena);
        erabiltzailea.setPasahitza(pasahitza);

        session.persist(erabiltzailea);

        session.getTransaction().commit();
        System.out.println("Usuario creado correctamente.");
        return true;
    } catch (Exception e) {
        System.out.println("Error al crear el usuario: " + e.getMessage());

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
        String hql = "FROM Erabiltzailea WHERE izena = :izena AND pasahitza = :pasahitza";
        Erabiltzailea erabiltzailea = (Erabiltzailea) session.createQuery(hql)
            .setParameter("izena", izena)
            .setParameter("pasahitza", pasahitza)
            .uniqueResult();

        if (erabiltzailea != null) {
        	
            session.delete(erabiltzailea);

            session.getTransaction().commit();
            System.out.println("Usuario eliminado correctamente.");
            this.deleteRidesByDriver(izena);
            return true;
        } else {
            System.out.println("Usuario no encontrado con las credenciales proporcionadas.");
            session.getTransaction().rollback();
            return false;
        }
    } catch (Exception e) {
        System.out.println("Error al eliminar el usuario: " + e.getMessage());

        if (session.getTransaction().isActive()) {
            session.getTransaction().rollback();
        }
        throw e;
    }
}

public boolean userInDataBase(String izena, String pasahitza) {
	this.createAndStoreCurrentUser(izena, pasahitza);
    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    session.beginTransaction();

    try {
        Query query = session.createQuery("from Erabiltzailea where izena = :username and pasahitza = :password");
        query.setParameter("username", izena);
        query.setParameter("password", pasahitza);

        List result = query.list();

        session.getTransaction().commit();

        return !result.isEmpty();
    } catch (Exception ex) {
        System.out.println("Error al verificar las credenciales: " + ex.getMessage());

        if (session.getTransaction().isActive()) {
            session.getTransaction().rollback();
        }

        return false;
    }
}

public List<Object[]> getAllUsersAndPasswords() {
    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    session.beginTransaction();

    try {
        Query query = session.createQuery("select izena, pasahitza from Erabiltzailea");

        List<Object[]> result = query.list();

        session.getTransaction().commit();

        for (Object[] row : result) {
            System.out.println("Usuario: " + row[0] + ", Contraseña: " + row[1]);
        }

        return result;
    } catch (Exception ex) {
        System.out.println("Error al obtener usuarios y contraseñas: " + ex.getMessage());

        if (session.getTransaction().isActive()) {
            session.getTransaction().rollback();
        }

        return new ArrayList<>();
    }
}

public void createAndStoreRide(String from, String to, Date date, int nPlaces, float price) {
    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    session.beginTransaction();

    try {
        String hqlDriver = "SELECT c.izena FROM CurrentUser c";
        String driver = (String) session.createQuery(hqlDriver).uniqueResult();

        if (driver == null) {
            throw new IllegalStateException("No se encontró el conductor en CurrentUser.");
        }

        String hql = "SELECT COUNT(r) FROM Ride r WHERE r.from = :from AND r.to = :to AND r.date = :date";
        Long count = (Long) session.createQuery(hql)
            .setParameter("from", from)
            .setParameter("to", to)
            .setParameter("date", date)
            .uniqueResult();

        if (count > 0) {
            throw new IllegalArgumentException("A ride in the same date already exists.");
        }
        Ride r = new Ride();
        r.setFrom(from);
        r.setTo(to);
        r.setDate(date);
        r.setPrice(price);
        r.setnPlaces(nPlaces);
        r.setDriver(driver);

        session.persist(r);
        session.getTransaction().commit();
    } catch (Exception e) {
        if (session.getTransaction().isActive()) {
            session.getTransaction().rollback();
        }
        throw e;
    }
}


public void createAndStorageDriver(String from, String to, Date date, float price, Integer nPlaces) {
    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    session.beginTransaction();

    try {
        String hql = "SELECT COUNT(r) FROM Ride r WHERE r.from = :from AND r.to = :to AND r.date = :date";
        Long count = (Long) session.createQuery(hql)
            .setParameter("from", from)
            .setParameter("to", to)
            .setParameter("date", date)
            .uniqueResult();

        if (count > 0) {
            throw new IllegalArgumentException("A ride with the same parameters already exists.");
        }
        Ride r = new Ride();
        r.setFrom(from);
        r.setTo(to);
        r.setDate(date);
        r.setPrice(price);
        r.setnPlaces(nPlaces);

        session.persist(r);
        session.getTransaction().commit();
    } catch (Exception e) {
        if (session.getTransaction().isActive()) {
            session.getTransaction().rollback();
        }
        throw e;
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
	System.out.println(">> DataAccess: getRides=> from= "+from+" to= "+to+" date "+date);
	List<Ride> res = new ArrayList<>();	
    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    session.beginTransaction();

    try {
        
        Query query = session.createQuery("from Ride r where r.from = :from and r.to = :to and r.date = :date");
        
        query.setParameter("from", from);
        query.setParameter("to", to);
        query.setParameter("date", date);

        List<Ride> rides = query.list();

        session.getTransaction().commit();

        for (Ride ride:rides){
        	res.add(ride);
 		  }

        return res;
    } catch (Exception ex) {
        System.out.println("Error al obtener detalles del viaje: " + ex.getMessage());

        if (session.getTransaction().isActive()) {
            session.getTransaction().rollback();
        }

        return new ArrayList<>();
    }
}

public List<Ride> getRidesByUser(String user) {
    System.out.println(">> DataAccess: getRidesByUser => user= " + user);
    List<Ride> res = new ArrayList<>();
    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    session.beginTransaction();

    try {
        Query query = session.createQuery("from Ride r where r.driver = :driver");

        query.setParameter("driver", user);

        List<Ride> rides = query.list();

        session.getTransaction().commit();

        for (Ride ride : rides) {
            res.add(ride);
        }

        return res;
    } catch (Exception ex) {
        System.out.println("Error al obtener viajes por usuario: " + ex.getMessage());

        if (session.getTransaction().isActive()) {
            session.getTransaction().rollback();
        }

        return new ArrayList<>();
    }
}


public List<String> getAllFroms() {
    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    session.beginTransaction();

    try {
        String hql = "select distinct r.from from Ride r";
        
        Query query = session.createQuery(hql);

        List<String> result = query.list();

        session.getTransaction().commit();

        if (result.isEmpty()) {
            System.out.println("No se encontraron registros para 'from'.");
        } else {
            for (String fromValue : result) {
                System.out.println("From: " + fromValue);
            }
        }

        return result;
    } catch (Exception ex) {
        System.out.println("Error al obtener los valores 'from': " + ex.getMessage());

        if (session.getTransaction().isActive()) {
            session.getTransaction().rollback();
        }

        return new ArrayList<>();
    }
}

public List<String> getAllToByFrom(String from) {
    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    session.beginTransaction();

    try {
        String hql = "select r.to from Ride r where r.from = :from";

        // Ejecutar la consulta
        Query query = session.createQuery(hql);

        query.setParameter("from", from);

        List<String> result = query.list();

        session.getTransaction().commit();

        if (result.isEmpty()) {
            System.out.println("No se encontraron destinos ('to') para el valor 'from' proporcionado.");
        } else {
            for (String toValue : result) {
                System.out.println("To: " + toValue);
            }
        }

        return result;
    } catch (Exception ex) {
        System.out.println("Error al obtener los destinos 'to': " + ex.getMessage());

        if (session.getTransaction().isActive()) {
            session.getTransaction().rollback();
        }

        return new ArrayList<>();
    }
}

public boolean createAndStoreCurrentUser(String izena, String pasahitza) {
    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    session.beginTransaction();

    try {
        String deleteHql = "DELETE FROM CurrentUser";
        session.createQuery(deleteHql).executeUpdate();

        CurrentUser erabiltzailea = new CurrentUser();
        erabiltzailea.setIzena(izena);
        erabiltzailea.setPasahitza(pasahitza);

        session.persist(erabiltzailea);

        session.getTransaction().commit();
        System.out.println("Tabla limpiada y usuario creado correctamente.");
        return true;
    } catch (Exception e) {
        System.out.println("Error al crear el usuario: " + e.getMessage());

        if (session.getTransaction().isActive()) {
            session.getTransaction().rollback();
        }
        return false;
    }
}

public boolean updateUserAndRides(String newUsername, String newPassword) {
    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    session.beginTransaction();

    try {
        String hqlCurrentUser = "FROM CurrentUser c"; 
        CurrentUser currentUser = (CurrentUser) session.createQuery(hqlCurrentUser)
            .uniqueResult();

        if (currentUser == null) {
            System.out.println("No se encontró el usuario actual.");
            return false; 
        }

        String oldUsername = currentUser.getIzena();  

        String hqlUpdateRides = "UPDATE Ride r SET r.driver = :newUsername WHERE r.driver = :oldUsername";
        int updatedRides = session.createQuery(hqlUpdateRides)
            .setParameter("newUsername", newUsername)
            .setParameter("oldUsername", oldUsername)
            .executeUpdate();

        if (updatedRides > 0) {
            System.out.println(updatedRides + " rides actualizados.");
        } else {
            System.out.println("No se encontraron rides para actualizar.");
        }

        String hqlUpdateErabiltzailea = "UPDATE Erabiltzailea e SET e.izena = :newUsername, e.pasahitza = :newPassword WHERE e.izena = :oldUsername";
        int updatedErabiltzailea = session.createQuery(hqlUpdateErabiltzailea)
            .setParameter("newUsername", newUsername)
            .setParameter("newPassword", newPassword)
            .setParameter("oldUsername", oldUsername)
            .executeUpdate();

        if (updatedErabiltzailea > 0) {
            System.out.println(updatedErabiltzailea + " usuarios actualizados en Erabiltzailea.");
        } else {
            System.out.println("No se encontraron usuarios para actualizar en Erabiltzailea.");
        }

        session.delete(currentUser);

        CurrentUser newUser = new CurrentUser();
        newUser.setIzena(newUsername);
        newUser.setPasahitza(newPassword); 
        session.persist(newUser);

        session.getTransaction().commit();
        System.out.println("Usuario y rides actualizados correctamente.");
        return true;
    } catch (Exception e) {
        System.out.println("Error al actualizar el usuario y los rides: " + e.getMessage());

        if (session.getTransaction().isActive()) {
            session.getTransaction().rollback();
        }
        return false;
    }
}

public String getCurrentUserIzena() {
    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    session.beginTransaction(); 

    try {
        String hql = "SELECT c.izena FROM CurrentUser c";
        Query query = session.createQuery(hql);
        String izena = (String) query.uniqueResult();

        session.getTransaction().commit();

        return izena;
    } catch (Exception e) {
        System.out.println("Error al obtener el usuario actual: " + e.getMessage());
        if (session.getTransaction().isActive()) {
            session.getTransaction().rollback();
        }
        return null;
    }
}


public boolean deleteRidesByDriver(String driverName) {
    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    session.beginTransaction();

    try {
        String hql = "DELETE FROM Ride r WHERE r.driver = :driverName";
        int deletedRides = session.createQuery(hql)
            .setParameter("driverName", driverName)
            .executeUpdate();

        if (deletedRides > 0) {
            System.out.println(deletedRides + " ride(s) eliminado(s) para el conductor " + driverName);
        } else {
            System.out.println("No se encontraron rides para eliminar para el conductor " + driverName);
        }

        session.getTransaction().commit();
        return true;
    } catch (Exception e) {
        System.out.println("Error al eliminar los rides: " + e.getMessage());

        if (session.getTransaction().isActive()) {
            session.getTransaction().rollback();
        }
        return false;
    }
}

public boolean updateCurrentUserSearch(String newSearchValue) {
    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    Transaction transaction = session.beginTransaction();

    try {
        String hqlUpdate = "UPDATE CurrentUser c SET c.search = :search";
        int updatedCount = session.createQuery(hqlUpdate)
            .setParameter("search", newSearchValue)
            .executeUpdate();

        if (updatedCount > 0) {
            System.out.println("Search actualizado exitosamente.");
        } else {
            System.out.println("No se encontró un usuario para actualizar.");
        }

        transaction.commit();
        return true;
    } catch (Exception e) {
        System.out.println("Error al actualizar el search: " + e.getMessage());
        e.printStackTrace();

        if (transaction.isActive()) {
            transaction.rollback();
        }
        return false;
    }
}



    public String getCurrentUserSearch() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        try {
            String hql = "SELECT c.search FROM CurrentUser c";
            String searchValue = (String) session.createQuery(hql).uniqueResult();

            if (searchValue != null) {
                return searchValue;
            } else {
                System.out.println("No se encontró el valor de 'search' en CurrentUser");
                return null;
            }

        } catch (Exception e) {
            System.out.println("Error al obtener el valor de 'search': " + e.getMessage());
            
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            return null;
        } finally {
            session.getTransaction().commit();
        }
    }

    
public static void main(String[] args) {
	GertaerakSortu e = new GertaerakSortu();
	System.out.println("Gertaeren sorkuntza:");
	e.updateCurrentUserSearch(null);
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