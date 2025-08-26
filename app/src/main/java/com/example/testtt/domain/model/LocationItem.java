package com.example.testtt.domain.model;

/**
 * Domain model đại diện cho 1 kết quả tìm kiếm địa điểm
 */
public class LocationItem {

    private final String title;
    private final String address;
    private final double lat;
    private final double lng;

    public LocationItem(String title, String address, double lat, double lng) {
        this.title = title;
        this.address = address;
        this.lat = lat;
        this.lng = lng;
    }

    // --- Getter ---
    public String getTitle() {
        return title;
    }

    public String getAddress() {
        return address;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }
}
