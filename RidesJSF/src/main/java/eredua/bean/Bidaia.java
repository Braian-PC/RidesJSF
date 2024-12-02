package eredua.bean;

import java.util.Date;

public class Bidaia {
	private String bidaiNondik;
	private String bidaiNora;
	private int eserlekuKop;
	private int prezioa;
	private int kodea;
	private Date data;
	
	public Bidaia(String bidaiNondik, String bidaiNora, int eserlekuKop, int prezioa, Date data) {
		this.bidaiNondik=bidaiNondik;
		this.bidaiNora=bidaiNora;
		this.eserlekuKop=eserlekuKop;
		this.prezioa=prezioa;
		this.data=data;
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
	
	public int getKodea() {
		return kodea;
	}
	public void setKodea(int kodea) {
		this.kodea = kodea;
	}
	
	public String toString(){
		return bidaiNondik + ", " + bidaiNora + ", " + eserlekuKop + ", " + prezioa + ", " + data;
	}
}
