package eredua.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

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
	private static List<BidaiAukerak> motak=new ArrayList<BidaiAukerak>();
	
	public QueryBean() {
		 motak.add(new BidaiAukerak(1,"Bilbo"));
		 motak.add(new BidaiAukerak(2,"Donostia"));
		 motak.add(new BidaiAukerak(3,"Eibar"));
	}
	
	public BidaiAukerak getMota() {
		return mota;
	}
	
	public void setMota(BidaiAukerak mota) {
		this.mota = mota;
		System.out.println("Bidai aukera: "+mota.getKodea()+"/"+mota.getBidaiNondik());
	}
	
	public List<BidaiAukerak> getMotak() {
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
		if (mota.equals(m.getBidaiNondik()))
		return m;}
		return null; 
	}
}
