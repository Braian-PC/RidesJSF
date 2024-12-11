package eredua.bean;

import java.util.Date;

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
		this. pasahitza = pasahitza;
	}
	public String getPasahitza2() {
		return pasahitza2;
	}
	public void setPasahitza2(String pasahitza2) {
		this. pasahitza2 = pasahitza2;
	}
	public Date getData() {
		return data;
		}
	public String egiaztatu() {
		if(e.userInDataBase(izena, pasahitza)) {
			return "ok";
		}
		return null;
	}
	public String createUser() {
		if(pasahitza.equals(pasahitza2)) {
			e.createAndStoreErabiltzailea(izena, pasahitza, "driver");
			return "success";
		}
		return "newuser";
	}	
}