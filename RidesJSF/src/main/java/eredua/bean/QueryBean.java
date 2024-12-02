package eredua.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;**/

public class QueryBean {
	private String izena;
	private String pasahitza;
	private Date data;
	private Bidaia mota;
	private static List<Bidaia> motak=new ArrayList<Bidaia>();
	
	public QueryBean() {
		 motak.add(new Bidaia(1,"Bilbo"));
		 motak.add(new Bidaia(2,"Donostia"));
		 motak.add(new Bidaia(3,"Eibar"));
		}
	
	public Bidaia getMota() {
		return mota;
	}
	
	public void setMota(Bidaia mota) {
		this.mota = mota;
		System.out.println("Erabiltzailearen mota: "+mota.getKodea()+"/"+mota.getBidaiNondik());
	}
	
	public List<Bidaia> getMotak() {
		return motak;
	}
	
	public void setMotak(List<Bidaia> motak) {
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
/**	
	public void onDateSelect(SelectEvent event) {
		FacesContext.getCurrentInstance().addMessage(null,
		 new FacesMessage("Data aukeratua: "+event.getObject()));
	}
	
	public static Bidaia getObject(String mota) {
		for (Bidaia m: motak){
		if (mota.equals(m.getBidaiNondik()))
		return m;}
		return null; 
	}**/
}
