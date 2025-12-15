package edu.univ.erp.service;

import edu.univ.erp.data.SettingsTable;

public class MaintenanceService {
    private SettingsTable settingsTable=new SettingsTable();

    public boolean isMaintenanceEnabled(){
        return settingsTable.getMaintenanceEnabled();
    }
    public String getMaintenanceMessage(){
        return settingsTable.getMaintenanceMessage();
    }
    public void updateMaintenanceSettings(boolean enabled, String message){
        settingsTable.setMaintenanceEnabled(enabled);
        settingsTable.setMaintenanceMessage(message);
    }
}
