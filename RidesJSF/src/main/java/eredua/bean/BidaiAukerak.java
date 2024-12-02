package eredua.bean;

public class BidaiAukerak {
	private String bidaiNondik;
	private int kodea;
	
	public BidaiAukerak(int kodea, String bidaiNondik) {
		 this.kodea=kodea;
		 this.bidaiNondik=bidaiNondik;
	}
	
	public String getBidaiNondik() {
		return bidaiNondik;
	}
	public void setBidaiNondik(String bidaiNondik) {
		this.bidaiNondik = bidaiNondik;
	}
	
	public int getKodea() {
		return kodea;
	}
	
	public void setKodea(int kodea) {
		this.kodea = kodea;
	}
	
	public String toString() {
		return bidaiNondik;
	}
}
