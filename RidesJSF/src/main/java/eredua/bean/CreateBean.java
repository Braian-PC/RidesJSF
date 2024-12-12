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
import eredua.domeinua.Ride;
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
		 new FacesMessage("Selected date: "+event.getObject()));
		}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
	
	public String creator() {
	    // Validación de campos obligatorios
	    if (bidaiNondik == null || bidaiNondik.trim().isEmpty() ||
	        bidaiNora == null || bidaiNora.trim().isEmpty() ||
	        eserlekuKop == 0 ||
	        prezioa == 0 ||
	        data == null) {

	        // Agregar mensaje de error global
	        FacesContext.getCurrentInstance().addMessage(null,
	            new FacesMessage(FacesMessage.SEVERITY_ERROR, 
	            "Error", "Mesdez, sartu datuak"));
	        return null; // Evita la navegación
	    }

	    try {
	        // Intentar crear y guardar el viaje en la base de datos
	        db.createAndStoreRide(bidaiNondik, bidaiNora, data, eserlekuKop, prezioa, "Braian");
	        
	        // Verificar si el viaje se guardó correctamente buscando los detalles
	        List<Ride> l = db.getRideDetails(bidaiNora, bidaiNondik, data);
	        System.out.println(l);
	        
	        // Si se llega aquí sin excepción, el viaje se guardó con éxito
	        FacesContext.getCurrentInstance().addMessage(null,
	            new FacesMessage(FacesMessage.SEVERITY_INFO, 
	            "Success", "Ride successfully saved"));
	        
	        // Opcional: limpiar los campos después de guardar
	        limpiarCampos();
	        
	        return "success"; // Puedes redirigir a otra página si lo deseas
	        
	    } catch (Exception e) {
	        // Manejar cualquier error durante el guardado
	        FacesContext.getCurrentInstance().addMessage(null,
	            new FacesMessage(FacesMessage.SEVERITY_ERROR, 
	            "Error", e.getMessage()));

	        System.out.println("Error: " + e.getMessage());
	        e.printStackTrace();
	        
	        return null;
	    }
	}

	// Método opcional para limpiar los campos después de guardar
	private void limpiarCampos() {
	    bidaiNondik = null;
	    bidaiNora = null;
	    eserlekuKop = 0;
	    prezioa = 0;
	    data = null;
	}
}
