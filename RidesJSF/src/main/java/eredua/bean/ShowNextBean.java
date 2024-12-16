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
    private List<Ride> bidaiak = new ArrayList<>();
    private GertaerakSortu db = new GertaerakSortu();
    private String driver;

    public ShowNextBean() {
    }

    @PostConstruct
    public void init() {
        refreshDriver();

        if (driver == null) {
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Driver is null"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Driver inicializado", "Rides found"));
        }
    }

    private void refreshDriver() {
        driver = db.getCurrentUserSearch();
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

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }
}
