package eredua.bean;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.annotation.PostConstruct;

import nagusia.GertaerakSortu;

public class ProfileBean {
    private String izena;
    private String pasahitza;
    private String zergatia;
    private GertaerakSortu db = new GertaerakSortu();

    public ProfileBean() {
    }

    @PostConstruct
    public void init() {
        String currentUserIzena = db.getCurrentUserIzena();

        if (currentUserIzena != null) {
            this.izena = currentUserIzena;
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Current user not found"));
        }
    }

    public String deleteUser() {
        if (db.deleteErabiltzailea(izena, pasahitza)) {
            return "bye";
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Current user not found"));
        return null;
    }

    public String editUser() {
        if (!db.updateUserAndRides(izena, pasahitza)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "An exception occurred"));
        }
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Success", "Successfully modified"));
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
