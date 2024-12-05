package eredua.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.event.SelectEvent;
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
	private BidaiAukerak mota;
	private int kodeAukeratua = 3;
	private Bidaia bidaia;
	private static List<BidaiAukerak> motak=new ArrayList<BidaiAukerak>();
	private static List<BidaiAukerak> motakBaldintzatua=new ArrayList<BidaiAukerak>();
	CreateBean lortuBidaia;
	
	public QueryBean() {
		lortuBidaia = new CreateBean();
	}
	
	public BidaiAukerak getMota() {
		return mota;
	}
	
	public void setMota(BidaiAukerak mota) {
		this.mota = mota;
		System.out.println("Bidai aukera: "+mota.getKodea()+"/"+mota.getBidaiNondik());
	}
	
	public List<BidaiAukerak> getMotak() {
		motak = new ArrayList<BidaiAukerak>();
		motak.add(new BidaiAukerak(1, "Bilbo"));
		motak.add(new BidaiAukerak(2, "Donosti"));
		motak.add(new BidaiAukerak(3,"Eibar"));
		return motak;
	}
	
	public void setMotak(List<BidaiAukerak> motak) {
		this.motak = motak;
	}
	
	public Date getData() {
		return data;
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
		motakBaldintzatua = new ArrayList<BidaiAukerak>();
		
		if(kodeAukeratua == 1) {
			motakBaldintzatua.add(new BidaiAukerak(2,"Donostia"));
		}
		else if(kodeAukeratua == 2) {
			motakBaldintzatua.add(new BidaiAukerak(1,"Bilbo"));
			motakBaldintzatua.add(new BidaiAukerak(4,"Gasteiz"));
			motakBaldintzatua.add(new BidaiAukerak(5,"Iruña"));
		}
		else {
			motakBaldintzatua.add(new BidaiAukerak(4,"Gasteiz"));
		}
		return motakBaldintzatua;
	}

	public void setMotakBaldintzatua(List<BidaiAukerak> motakBaldintzatua) {
		this.motakBaldintzatua = motakBaldintzatua;
	}
	
	public void listener(AjaxBehaviorEvent event) {
		FacesContext.getCurrentInstance().addMessage(null,
		new FacesMessage("Erabiltzailearen mota:"+mota.getKodea()+"/"+mota.getBidaiNondik()));
		kodeAukeratua = mota.getKodea();
		}
	
	public void onEventSelect(SelectEvent event) {
		this.mota=(BidaiAukerak)event.getObject();
		// Egia esan, selection="#{login.mota}" atributuarekin ere lortzen da
		FacesContext.getCurrentInstance().addMessage("nireForm:mezuak",
		new FacesMessage("Erabiltzailearen mota (taula):"+mota.getKodea()+"/"+mota.getBidaiNondik()));
		}
	
	public ArrayList<Bidaia> getBidaiak(){
		return lortuBidaia.getBidaiak();
	}
	
	public String toStringBidaiList() {
		String emaitza = lortuBidaia.toString();
		return emaitza;
	}
	
	public void agregarMensaje() {
        FacesMessage message = new FacesMessage("Mensaje del Bean: ", toStringBidaiList());
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
	/**
	 *     public String toStringList() {
        // Convierte la lista a una cadena
        StringBuilder sb = new StringBuilder();
        for (String dato : getListaDatos()) {
            sb.append(dato).append("\n"); // Cada dato en una nueva línea
        }
        return sb.toString();
    }
	 */

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
}
