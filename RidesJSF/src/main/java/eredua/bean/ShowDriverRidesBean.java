package eredua.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.ListDataModel;

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

        if (driverList != null && !driverList.isEmpty()) {
            driver = driverList.get(0);
            this.setDriver(driver);
        }
    }
	
	public String getDriver() {
		return driver;
	}
	
	public void setDriver(String driver) {
	    boolean updatedCurrentUser = db.updateCurrentUserSearch(driver);

	    if (updatedCurrentUser) {
	        this.driver = driver;

	        FacesContext.getCurrentInstance().addMessage(null, 
	            new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Search updated successfully in CurrentUser."));
	    } else {
	        FacesContext.getCurrentInstance().addMessage(null, 
	            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Failed to update search in CurrentUser."));
	    }
	}
	
	public void updateSetDriver(AjaxBehaviorEvent event) {
		this.setDriver(driver);
	}
	
	public List<String> getDriverList() {
		List<String> users = db.getAllDrivers();
		return users;
	}
	
	public void setDriverList(List<String> driverList) {
		this.driverList = driverList;
	}
}
