package eredua.domeinua;

import java.io.*;
import java.util.Date;
import javax.persistence.*;

@Entity
public class Ride implements Serializable {

    @Id 
    @GeneratedValue
    private Integer rideNumber;

    @Column(name = "departure")
    private String from;

    @Column(name = "destination")
    private String to;

    private int nPlaces;
    private Date date;
    private float price;
    
    private String driver;

    public Ride() {
        super();
    }

    public Ride(String from, String to, Date date, int nPlaces, float price, String driver) {
        super();
        this.from = from;
        this.to = to;
        this.nPlaces = nPlaces;
        this.date = date;
        this.price = price;
        this.driver = driver;
    }

    public Integer getRideNumber() {
        return rideNumber;
    }

    public void setRideNumber(Integer rideNumber) {
        this.rideNumber = rideNumber;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public int getnPlaces() {
        return nPlaces;
    }

    public void setnPlaces(int nPlaces) {
        this.nPlaces = nPlaces;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    @Override
    public String toString() {
        return rideNumber + ";" + from + ";" + to + ";" + date;
    }
}
