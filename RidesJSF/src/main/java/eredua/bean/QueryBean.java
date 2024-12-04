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
	private String izena;
	private String pasahitza;
	private Date data;
	private BidaiAukerak mota;
	private int kodeAukeratua = 3;
	private static List<BidaiAukerak> motak=new ArrayList<BidaiAukerak>();
	private static List<BidaiAukerak> motakBaldintzatua=new ArrayList<BidaiAukerak>();
	
	public QueryBean() {
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
		
	public String getIzena() {
		return izena;
	}
	public void setIzena(String izena) {
		this.izena = izena;
	}
	public void setData(Date data) {
		this.data = data;
		}
	public String getPasahitza() {
		return pasahitza;
	}
	public void setPasahitza(String pasahitza) {
		this. pasahitza = pasahitza;
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
		new FacesMessage("Erabiltzailearen mota (taula):"+mota.getKodea()+"/"+mota.getBidaiNondik()));}
}
