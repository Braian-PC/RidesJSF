package eredua.bean;

import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import eredua.domeinua.LoginGertaera;
import nagusia.GertaerakSortu;

public class LoginBean {
	private String izena;
	private String pasahitza;
	private String pasahitza2;
	private Date data;
	private GertaerakSortu e = new GertaerakSortu();
	public LoginBean(){
	}
	public String getIzena() {
		return izena;
	}
	public void setIzena(String izena) {
		this.izena = izena;
	}
	public void setData(Date data) {
		this.data = data;
		}
	public String getPasahitza() {
		return pasahitza;
	}
	public void setPasahitza(String pasahitza) {
		this.pasahitza = pasahitza;
	}
	public String getPasahitza2() {
		return pasahitza2;
	}
	public void setPasahitza2(String pasahitza2) {
		this.pasahitza2 = pasahitza2;
	}
	public Date getData() {
		return data;
		}
	
	public String egiaztatu() {
	    if (e.userInDataBase(izena, pasahitza)) {
	        return "ok";
	    }
	    
	    FacesContext.getCurrentInstance().addMessage(null, 
	        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "User or password incorrects"));
	    return null;
	}
	
	public String createUser() {
		System.out.println(pasahitza + pasahitza2);
		if(pasahitza.equals(pasahitza2)) {
			if(e.createAndStoreErabiltzailea(izena, pasahitza)) {
				e.createAndStoreCurrentUser(izena, pasahitza);
				return "success";
			};
			FacesContext.getCurrentInstance().addMessage(null, 
			        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "User already exists"));
			return null;
		}
		FacesContext.getCurrentInstance().addMessage(null, 
		        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "The passwords are not the same"));
		return null;
	}	
}