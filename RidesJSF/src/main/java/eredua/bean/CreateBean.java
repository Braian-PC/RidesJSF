package eredua.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;

import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import dataAccess.DataAccess;
import domain.Ride;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;
import nagusia.GertaerakSortu;

public class CreateBean {
	private ArrayList<Bidaia> Bidaiak = new ArrayList<Bidaia>();
	private int kodea;
	private String bidaiNondik;
	private String bidaiNora;
	private int eserlekuKop;
	private int prezioa;
	private Date data;
	private GertaerakSortu db = new GertaerakSortu();
	
	public CreateBean() {
	}
	
	public ArrayList<Bidaia> getBidaiak(){
		return Bidaiak;
	}
	
	public String getBidaiNondik() {
		return bidaiNondik;
	}
	public void setBidaiNondik(String bidaiNondik) {
		this.bidaiNondik = bidaiNondik;
	}
	
	public String getBidaiNora() {
		return bidaiNora;
	}
	public void setBidaiNora(String bidaiNora) {
		this.bidaiNora = bidaiNora;
	}
	
	public int getEserlekuKop() {
		return eserlekuKop;
	}
	public void setEserlekuKop(int eserlekuKop) {
		this.eserlekuKop = eserlekuKop;
	}

	public int getPrezioa() {
		return prezioa;
	}
	public void setprezioa(int prezioa) {
		this.prezioa = prezioa;
	}
	
	public void onDateSelect(SelectEvent event) {
		FacesContext.getCurrentInstance().addMessage(null,
		 new FacesMessage("Data aukeratua: "+event.getObject()));
		}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
	
	public void creator() {
		try {
			System.out.println("Number of seats: " + eserlekuKop); 
			
	        db.createAndStoreRide(bidaiNondik, bidaiNora, data, eserlekuKop, prezioa, "Braian");
	        List<Object[]> l= db.getRideDetails(bidaiNora, bidaiNondik, data);
	        System.out.println(l);
		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
			   e.printStackTrace();
			}
	 }
}
