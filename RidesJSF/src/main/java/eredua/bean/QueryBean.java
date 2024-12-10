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

@ManagedBean
@ViewScoped
public class QueryBean {
	private String driver;
	private String bidaiNondik;
	private String bidaiNora;
	private int eserlekuKop;
	private int prezioa;
	private Date data;
	private BidaiAukerak mota;
	private int kodeAukeratua = 0;
	private Bidaia bidaia;
	private static List<BidaiAukerak> motak=new ArrayList<BidaiAukerak>();
	private static List<BidaiAukerak> motakBaldintzatua=new ArrayList<BidaiAukerak>();
	private String city;
	CreateBean lortuBidaia = new CreateBean();
    private List<Ride> bidaiak; // Lista de viajes
    private Ride bidaiaR;        // Viaje seleccionado
	
	public QueryBean() {
	}
	
	public BidaiAukerak getMota() {
		return mota;
	}
	
	public void setMota(BidaiAukerak mota) {
		this.mota = mota;
		bidaiNondik = mota.getBidaiNondik();
		System.out.println("Bidai aukera: "+mota.getKodea()+"/"+bidaiNondik);
	}
	
	@PostConstruct
    public void init() {
        // Carga inicial de las opciones de depart
        motak = getMotak();

        // Establecer una selección inicial en depart si no está seleccionada
        if (motak != null && !motak.isEmpty()) {
            mota = motak.get(0); // Primera opción por defecto
            city = mota.getBidaiNondik();
        }

        // Cargar opciones iniciales para arrival en base a la opción inicial de depart
        motakBaldintzatua = getMotakBaldintzatua();
    }
	
	public List<BidaiAukerak> getMotak() {
		BLFacade facadeBL=FacadeBean.getBusinessLogic();// Negozioaren logika sortu
		List<String> depart=facadeBL.getDepartCities(); //Neg. logikara deitu
		motak = new ArrayList<BidaiAukerak>();
		for (int i=0; i<depart.size(); i++) {
			motak.add(new BidaiAukerak(i, depart.get(i)));
		}
		return motak;
	}
	
	public void setMotak(List<BidaiAukerak> motak) {
		this.motak = motak;
	}
	


	public void onDateSelect(SelectEvent event) {
		FacesContext.getCurrentInstance().addMessage(null,
		 new FacesMessage("Data aukeratua: "+event.getObject()));
	}
	
	public static BidaiAukerak getObject(String mota) {
		for (BidaiAukerak m: motak){
			if (mota.equals(m.getBidaiNondik())) return m;
		}
		return null; 
	}

    public List<BidaiAukerak> getMotakBaldintzatua() {
        if (city == null || city.isEmpty()) {
            return new ArrayList<>(); // Retornar lista vacía si no hay ciudad seleccionada
        }

        BLFacade facadeBL = FacadeBean.getBusinessLogic();
        List<String> destiny = facadeBL.getDestinationCities(city);
        motakBaldintzatua = new ArrayList<>();
        for (int i = 0; i < destiny.size(); i++) {
            motakBaldintzatua.add(new BidaiAukerak(i, destiny.get(i)));
        }
        return motakBaldintzatua;
    }
	
	
	public void setMotakBaldintzatua(List<BidaiAukerak> motakBaldintzatua) {
		this.motakBaldintzatua = motakBaldintzatua;
	}
	
    public void listener(AjaxBehaviorEvent event) {
        // Manejar cambio en depart
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage("Erabiltzailearen mota:" + mota.getKodea() + "/" + mota.getBidaiNondik()));
        city = mota.getBidaiNondik(); // Actualizar ciudad seleccionada
        motakBaldintzatua = getMotakBaldintzatua(); // Actualizar llegada
    }
	
	public void onEventSelect(SelectEvent event) {
		this.mota=(BidaiAukerak)event.getObject();
		
		FacesContext.getCurrentInstance().addMessage("nireForm:mezuak",
		new FacesMessage("Erabiltzailearen mota (taula):"+mota.getKodea()+"/"+mota.getBidaiNondik()));
		city = mota.getBidaiNondik();
		System.out.println(mota.getKodea());
	}
	
    public List<Ride> getBidaiak() {
        if (bidaiNora == null || bidaiNondik == null || data == null) {
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
	    
	    List<Ride> rides = facadeBL.getRides(bidaiNora, bidaiNondik, normalizedDate);
	    
	    if (rides.isEmpty()) {
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("No hay viajes disponibles para esta fecha."));
	    } else {
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Viajes encontrados: " + rides.size()));
	    }

	    return null; // Mantén la misma página
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
