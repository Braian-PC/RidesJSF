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
	
	@Override
	public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + kodea;
	return result; }
	
	@Override
	public boolean equals(Object obj) {
	if (this == obj)
	return true;
	if (obj == null)
	return false;
	if (getClass() != obj.getClass())
	return false;
	BidaiAukerak other = (BidaiAukerak) obj;
	if (kodea != other.kodea)
	return false;
	return true; }
}