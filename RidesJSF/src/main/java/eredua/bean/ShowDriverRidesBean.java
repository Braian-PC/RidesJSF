package eredua.bean;

import java.util.List;

import javax.annotation.PostConstruct;

import nagusia.GertaerakSortu;

public class ShowDriverRidesBean {
	private String driver;
	private List<String> driverList;
	private GertaerakSortu db = new GertaerakSortu();
	
	public ShowDriverRidesBean() {
		
	}
	
	@PostConstruct
    public void init() {
        driverList = getDriverList();

        // Establecer una selección inicial en depart si no está seleccionada
        if (driverList != null && !driverList.isEmpty()) {
            driver = driverList.get(0);
        }
    }
	
	public String getDriver() {
		return driver;
	}
	public void setDriver(String driver) {
		this.driver = driver;
	}
	
	public List<String> getDriverList() {
		List<String> users = db.getAllDrivers();
		return users;
	}
	
	public void setDriverList(List<String> driverList) {
		this.driverList = driverList;
	}
	
	public String saveDriver() {
		try {
		db.createAndStorageDriver(driver);
		} catch(Exception e) {
			return null;
		}
		
		return "search";
	}
}
