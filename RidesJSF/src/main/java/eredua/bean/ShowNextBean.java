package eredua.bean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import eredua.domeinua.Ride;
import nagusia.GertaerakSortu;

public class ShowNextBean {
	private Ride bidaiaR;
	private List<Ride> bidaiak= new ArrayList<>();
	private GertaerakSortu db = new GertaerakSortu();
	private String driver = db.getAllDrivers().get(0);
	
	public ShowNextBean() {
		
	}

	public Ride getBidaiaR() {
		return bidaiaR;
	}

	public void setBidaiaR(Ride bidaiaR) {
		this.bidaiaR = bidaiaR;
	}
	
	public List<Ride> getBidaiak() {
		return bidaiak;
	}

	public void setBidaiak(List<Ride> bidaiak) {
		this.bidaiak = bidaiak;
	}
	
    public String bidaiakLortu() {
        
        System.out.println("Driver: " + driver);
        
        // Obtener los viajes disponibles para la fecha seleccionada y las ciudades
        bidaiak= db.getRideDetailsByDriver(driver);
        System.out.println(bidaiak.toString());
        
        // Actualizar la lista de viajes en el Managed Bean
        List<Ride> rideList = new ArrayList<>();
        for (Ride ride : bidaiak) {
            Ride t = new Ride();
            t.setDriver((String) ride.getDriver());
            t.setnPlaces((Integer) ride.getnPlaces());
            t.setPrice((Float) ride.getPrice());
            rideList.add(t);
        }
        
        if (bidaiak.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("No rides found for this date"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Rides found: " + bidaiak.size()));
        }
        
        // Retornar null para no redirigir a otra página
        return null;
    }

}
