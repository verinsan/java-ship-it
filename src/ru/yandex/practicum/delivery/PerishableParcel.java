package ru.yandex.practicum.delivery;

public class PerishableParcel extends Parcel{
    private static final int BASE_UNIT_COST = 3;
    private int timeToLive;

    public PerishableParcel(String description, int weight, String deliveryAddress, int sendDay, int timeToLive) {
        super(description, weight, deliveryAddress, sendDay);
        this.timeToLive = timeToLive;
    }

    @Override
    protected int getBaseUnitCost() {
        return BASE_UNIT_COST;
    }

    public boolean isExpired(int currentDay) {
        return (sendDay + timeToLive) < currentDay;
    }

    public int getTimeToLive() {
        return timeToLive;
    }
}
