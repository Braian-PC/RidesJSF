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
import javax.faces.model.ListDataModel;

import org.omnifaces.cdi.ViewScoped;
import org.primefaces.event.SelectEvent;

import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import dataAccess.DataAccess;
import eredua.domeinua.Ride;
import nagusia.GertaerakSortu;

@ManagedBean
@ViewScoped
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
    private List<Ride> bidaiak;
    private Ride bidaiaR;
	private GertaerakSortu db = new GertaerakSortu();
	
	public QueryBean() {
	}

	@PostConstruct
    public void init() {
        motak = getMotak();

        if (motak != null && !motak.isEmpty()) {
            bidaiNondik = motak.get(0);
        }

        this.updateArrivalCities(null);
        motakBaldintzatua = getMotakBaldintzatua();
    }
	
	public List<String> getMotak() {
		
		List<String> depart = db.getAllFroms();
		return depart;
	}
	
	public void setMotak(List<String> motak) {
		this.motak = motak;
	}

	public void onDateSelect(SelectEvent event) {
		FacesContext.getCurrentInstance().addMessage(null,
		 new FacesMessage("Data aukeratua: "+event.getObject()));
	}
	public void updateArrivalCities(AjaxBehaviorEvent event) {
		motakBaldintzatua = db.getAllToByFrom(bidaiNondik);
		this.bidaiNora = motakBaldintzatua.get(0);
        System.out.println(bidaiNondik + " | " + bidaiNora);
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
		bidaiNondik = (String)event.getObject();
	}
	
    public List<Ride> getBidaiak() {
        if (bidaiNora == null || bidaiNondik == null || data == null) {
            return new ArrayList<>();
        }
        System.out.println("Aquiii" + bidaiak.toString());
        return bidaiak;
    }

	public void setBidaiak(List<Ride> bidaiak) {
		this.bidaiak = bidaiak;
	}
    
    public String bidaiakLortu() {
        
        System.out.println("Fecha seleccionada: " + data);
        System.out.println("Ciudades: De " + bidaiNondik + " a " + bidaiNora);
        
        bidaiak= db.getRideDetails(bidaiNondik, bidaiNora, data);
        System.out.println(bidaiak.toString());
        
        List<Ride> rideList = new ArrayList<>();
        for (Ride ride : bidaiak) {
            Ride t = new Ride();
            t.setDriver((String) ride.getDriver());
            t.setnPlaces((Integer) ride.getnPlaces());
            t.setPrice((Float) ride.getPrice());
            rideList.add(t);
        }
        
        if (bidaiak.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("No rides found for this date"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Rides found: " + bidaiak.size()));
        }
        return null;
    }
	
    public List<Ride> bidaiakByID(String driver) {
        
        System.out.println("Driver: " + driver);
        
        bidaiak= db.getRidesByUser(driver);
        System.out.println("Viajes:" + bidaiak.toString());
        
        return bidaiak;
    }
    
	public String getDepartCities() {
		BLFacade facadeBL; 
		facadeBL=new BLFacadeImplementation (new DataAccess());
		facadeBL=FacadeBean.getBusinessLogic();
		List<String> etxeak=facadeBL. getDepartCities();
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
