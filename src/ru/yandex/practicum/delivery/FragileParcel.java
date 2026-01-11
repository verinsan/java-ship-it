package ru.yandex.practicum.delivery;

public class FragileParcel extends Parcel implements Trackable{
    private static final int BASE_UNIT_COST = 4;

    public FragileParcel(String description, int weight, String deliveryAddress, int sendDay) {
        super(description, weight, deliveryAddress, sendDay);
    }

    @Override
    public void packageItem() {
        System.out.println(String.format("Посылка <<%s>> обёрнута в защитную плёнку", description));
        super.packageItem();
    }

    @Override
    protected int getBaseUnitCost(){
        return BASE_UNIT_COST;
    }

    @Override
    public void reportStatus(String newLocation) {
        System.out.println(String.format(
                "Хрупкая посылка <<%s>> изменила местоположение на %s", description, newLocation));
    }
}
