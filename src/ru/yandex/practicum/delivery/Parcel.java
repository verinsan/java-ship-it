package ru.yandex.practicum.delivery;
import java.util.Objects;

public abstract class Parcel {
    //добавьте реализацию и другие необходимые классы
    protected String description;
    protected int weight;
    protected String deliveryAddress;
    protected int sendDay;

    public Parcel(String description, int weight, String deliveryAddress, int sendDay) {
        this.description = Objects.requireNonNull(description, "Описание не может быть пустым");
        this.weight = weight;
        this.deliveryAddress = Objects.requireNonNull(deliveryAddress, "Адрес доставки не может быть пустым");
        this.sendDay = sendDay;
    }

    public void packageItem() {
        System.out.println(String.format("Посылка <<%s>> упакована", description));
    }

    public void deliver() {
        System.out.println(String.format("Посылка <<%s>> доставлена по адресу %s", description, deliveryAddress));
    }

    protected abstract int getBaseUnitCost();

    public int calculateDeliveryCost() {
        return weight * getBaseUnitCost();
    }

    public String getDescription() {
        return description;
    }

    public int getWeight() {
        return weight;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }
}
