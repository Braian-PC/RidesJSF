package eredua.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.omnifaces.cdi.ViewScoped;
import org.primefaces.event.SelectEvent;

import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import dataAccess.DataAccess;
import domain.Ride;
/**
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;**/

public class QueryBean {
	private String driver;
	private String bidaiNondik;
	private String bidaiNora;
	private int eserlekuKop;
	private int prezioa;
	private Date data;
	private int kodeAukeratua = 0;
	private Bidaia bidaia;
	private static List<String> motak=new ArrayList<String>();
	private static List<String> motakBaldintzatua=new ArrayList<String>();
	private String city;
	CreateBean lortuBidaia = new CreateBean();
    private List<Ride> bidaiak; // Lista de viajes
    private Ride bidaiaR;        // Viaje seleccionado
	
	public QueryBean() {
	}

	@PostConstruct
    public void init() {
        // Carga inicial de las opciones de depart
        motak = getMotak();

        // Establecer una selección inicial en depart si no está seleccionada
        if (motak != null && !motak.isEmpty()) {
            bidaiNondik = motak.get(0);
        }

        // Cargar opciones iniciales para arrival en base a la opción inicial de depart
        this.updateArrivalCities(null);
        motakBaldintzatua = getMotakBaldintzatua();
    }
	
	public List<String> getMotak() {
		BLFacade facadeBL=FacadeBean.getBusinessLogic();// Negozioaren logika sortu
		List<String> depart=facadeBL.getDepartCities(); //Neg. logikara deitu
		
		return depart;
	}
	
	public void setMotak(List<String> motak) {
		this.motak = motak;
	}
	


	public void onDateSelect(SelectEvent event) {
		FacesContext.getCurrentInstance().addMessage(null,
		 new FacesMessage("Data aukeratua: "+event.getObject()));
	}
	
	/**public static BidaiAukerak getObject(String mota) {
		for (BidaiAukerak m: motak){
			if (mota.equals(m.getBidaiNondik())) return m;
		}
		return null; 
	}**/
	public void updateArrivalCities(AjaxBehaviorEvent event) {
        this.setBidaiNondik(bidaiNondik);
        BLFacade facadeBL = FacadeBean.getBusinessLogic();
        motakBaldintzatua = facadeBL.getDestinationCities(bidaiNondik);
        System.out.println(bidaiNondik + bidaiNora);
	}
	
	public void updateSetBidaiNora(AjaxBehaviorEvent event) {
		this.setBidaiNora(bidaiNora);
	}
	
    public List<String> getMotakBaldintzatua() {
    	return motakBaldintzatua;
    }
	
	
	public void setMotakBaldintzatua(List<String> motakBaldintzatua) {
		this.motakBaldintzatua = motakBaldintzatua;
	}
	
	public void onEventSelect(SelectEvent event) {
		//this.mota=(String)event.getObject();
		
		//FacesContext.getCurrentInstance().addMessage("nireForm:mezuak",
		//new FacesMessage("Erabiltzailearen mota (taula):"+mota.getKodea()+"/"+mota.getBidaiNondik()));
		bidaiNondik = (String)event.getObject();
	}
	
    public List<Ride> getBidaiak() {
        if (bidaiNora == null || bidaiNondik == null || data == null) {
        	System.out.println("Nada por aquí");
            return new ArrayList<>(); // Si faltan datos, retornar lista vacía
        }

        BLFacade facadeBL = FacadeBean.getBusinessLogic();
        bidaiak = facadeBL.getRides(bidaiNora, bidaiNondik, data);

        // Si no hay resultados, agregar un mensaje
        if (bidaiak.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage("mezuak",
                new FacesMessage(FacesMessage.SEVERITY_INFO, "No Rides", null));
        }
        return bidaiak;
    }

	
	public String bidaiakLortu() {
	    BLFacade facadeBL = FacadeBean.getBusinessLogic();
	    
	    // Normaliza la fecha antes de la consulta
	    Date normalizedDate = normalizeDate(data);
	    
	    System.out.println("Fecha seleccionada: " + data);
	    System.out.println("Fecha normalizada: " + normalizedDate);
	    System.out.println("Ciudades: De " + bidaiNondik + " a " + bidaiNora);
	    
	    List<Ride> rides = facadeBL.getRides(bidaiNora, bidaiNondik, data);
	    
	    if (rides.isEmpty()) {
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("No hay viajes disponibles para esta fecha."));
	    } else {
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Viajes encontrados: " + rides.size()));
	    }

	    return null;
	}
	
	private Date normalizeDate(Date date) {
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    cal.set(Calendar.HOUR_OF_DAY, 0);
	    cal.set(Calendar.MINUTE, 0);
	    cal.set(Calendar.SECOND, 0);
	    cal.set(Calendar.MILLISECOND, 0);
	    return cal.getTime();
	}
	
	public String getDepartCities() {
		BLFacade facadeBL; // Negozioaren logika gordetzen du
		facadeBL=new BLFacadeImplementation (new DataAccess());
		facadeBL=FacadeBean.getBusinessLogic();// Negozioaren logika sortu
		List<String> etxeak=facadeBL. getDepartCities(); //Neg. logikara deitu
		return etxeak.toString();
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

	public void setPrezioa(int prezioa) {
		this.prezioa = prezioa;
	}
	
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	
	public Bidaia getBidaia() {
		return bidaia;
	}

	public void setBidaia(Bidaia b) {
		this.bidaia = b;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public Ride getBidaiaR() {
		return bidaiaR;
	}

	public void setBidaiaR(Ride bidaiaR) {
		this.bidaiaR = bidaiaR;
	}
}
