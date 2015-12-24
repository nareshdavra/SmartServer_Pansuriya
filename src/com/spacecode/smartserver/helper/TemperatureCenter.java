//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.spacecode.smartserver.helper;

import com.spacecode.sdk.device.event.TemperatureEventHandler;
import com.spacecode.smartserver.database.DbManager;
import com.spacecode.smartserver.database.dao.DaoTemperatureMeasurement;
import com.spacecode.smartserver.database.entity.TemperatureMeasurementEntity;
import com.spacecode.smartserver.helper.ConfManager;
import com.spacecode.smartserver.helper.DeviceHandler;
import com.spacecode.smartserver.helper.SmartLogger;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TemperatureCenter {
    private static double _lastMeasureValue = 777.0D;
    private static Date _lastMeasureTime;
    private static Timer _measurementTimer;
    private static final long DELAY_MS_FORCE_MEASURE = 600000L;

    private TemperatureCenter() {
    }

    public static void initialize() {
        if(ConfManager.isDevTemperature()) {
            DeviceHandler.getDevice().addListener(new TemperatureCenter.TemperatureMeasureHandler(null));
            _measurementTimer = new Timer();
            _measurementTimer.scheduleAtFixedRate(new TimerTask() {
                public void run() {
                    if(DeviceHandler.isAvailable()) {
                        double currentTemp = DeviceHandler.getDevice().getCurrentTemperature();
                        if(currentTemp != 777.0D) {
                            Date dateNow = new Date();
                            if(TemperatureCenter._lastMeasureTime == null || dateNow.getTime() - TemperatureCenter._lastMeasureTime.getTime() < 600000L) {
                                TemperatureCenter.recordNewMeasure(currentTemp);
                            }

                        }
                    }
                }
            }, 0L, 600000L);
        }
    }

    public static void stop() {
        if(ConfManager.isDevTemperature()) {
            if(_measurementTimer != null) {
                _measurementTimer.cancel();
            }

        }
    }

    private static void recordNewMeasure(double valueFromProbe) {
        double roundedValue = (double)Math.round(valueFromProbe * 10.0D) / 10.0D;
        _lastMeasureValue = roundedValue;
        DaoTemperatureMeasurement daoTempMeasurement = (DaoTemperatureMeasurement)DbManager.getDao(TemperatureMeasurementEntity.class);
        if(!daoTempMeasurement.insert(new TemperatureMeasurementEntity(roundedValue))) {
            SmartLogger.getLogger().severe("Unable to insert new temperature measure (" + _lastMeasureValue + ").");
        } else {
            _lastMeasureTime = new Date();
        }
    }

    private static class TemperatureMeasureHandler implements TemperatureEventHandler {
        private TemperatureMeasureHandler(Object o) {
        }

        public void temperatureMeasure(double value) {
            if(value == 777.0D) {
                SmartLogger.getLogger().warning("ERROR_VALUE sent with the Temperature Probe event!");
            } else {
                TemperatureCenter.recordNewMeasure(value);
            }
        }
    }
}
