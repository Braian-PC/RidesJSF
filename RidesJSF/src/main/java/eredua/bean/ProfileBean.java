package eredua.bean;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import nagusia.GertaerakSortu;

public class ProfileBean {
	private String izena = "Izaro";
	private String pasahitza;
	private String zergatia;
	private GertaerakSortu db= new GertaerakSortu();
	
	public ProfileBean() {
		
	}
	
	public String deleteUser() {
	    if (db.deleteErabiltzailea(izena, pasahitza)) {
	        return "bye";
	    }
	    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "User not found or password incorrect"));
	    return null;
	}

	public String getPasahitza() {
		return pasahitza;
	}

	public void setPasahitza(String pasahitza) {
		this.pasahitza = pasahitza;
	}	
	
	public String getIzena() {
		return izena;
	}

	public void setIzena(String izena) {
		this.izena = izena;
	}	
	
	public String getZergatia() {
		return zergatia;
	}

	public void setZergatia(String zergatia) {
		this.zergatia = zergatia;
	}	
}
