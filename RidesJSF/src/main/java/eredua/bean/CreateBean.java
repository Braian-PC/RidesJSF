package eredua.bean;

import java.util.ArrayList;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;

public class CreateBean {
	private ArrayList<Bidaia> Bidaiak = new ArrayList<Bidaia>();
	private int kodea;
	private String bidaiNondik;
	private String bidaiNora;
	private int eserlekuKop;
	private int prezioa;
	private Date data;
	
	public CreateBean() {
		Bidaiak.add(new Bidaia("Braian", "Donostia", "Gasteiz", 12, 12, new Date(05/12/24)));
		Bidaiak.add(new Bidaia("Bilbo", "Donostia", 13, 13, new Date(15/12/24)));
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
	    FacesContext facesContext = FacesContext.getCurrentInstance();
	    
	    // Realiza la validación manual
	    if (facesContext.getMaximumSeverity() == null || !facesContext.isValidationFailed()) {
	        // Si no hay errores, añade el objeto
	        Bidaiak.add(new Bidaia(bidaiNondik, bidaiNora, eserlekuKop, prezioa, data));
	        System.out.println(Bidaiak.toString());

	        // Añadir mensaje de éxito
	        facesContext.addMessage(null, new FacesMessage("Sortutako bidaia: " + Bidaiak.toString()));
	    } else {
	        // Si hay errores, no hacer nada y los mensajes de validación se mostrarán automáticamente
	        System.out.println("Validación fallida.");
	    }
	}
}
