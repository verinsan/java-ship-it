package ru.yandex.practicum.delivery;
import java.util.ArrayList;
import java.util.List;

public class ParcelBox<T extends Parcel> {
    private List<T> parcels;
    private int maxWeight;
    private int currentWeight;
    private String boxType;

    public ParcelBox(int maxWeight, String boxType) {
        this.parcels = new ArrayList<>();
        this.maxWeight = maxWeight;
        this.currentWeight = 0;
        this.boxType = boxType;
    }

    public boolean addParcel(T parcel) {
        if (parcel == null) {
            System.out.println("Нельзя добавить пустую посылку");
            return false;
        }

        int newWeight = currentWeight + parcel.getWeight();

        if (newWeight > maxWeight) {
            System.out.println(String.format("Нельзя добавить посылку <<%s>>", parcel.getDescription()));
            System.out.println("Превышен максимальный вес коробки.");
            return false;
        }

        parcels.add(parcel);
        currentWeight = newWeight;
        System.out.println(String.format("Посылка <<%s>> добавлена в коробку для %s", parcel.getDescription(), boxType));
        return true;
    }

    public List<T> getAllParcels() {
        return parcels;
    }

    public void showContents() {
        System.out.println("Коробка " + boxType);
        System.out.println(String.format("Максимальный вес: %d кг", maxWeight));
        System.out.println(String.format("Текущий вес: %d кг", currentWeight));
        System.out.println(String.format("Свободно: %d кг", maxWeight - currentWeight));
        System.out.println(String.format("Количество посылок: %d", parcels.size()));

        if (parcels.isEmpty()) {
            System.out.println("Коробка пуста");
        } else {
            for (int i = 0; i < parcels.size(); i++) {
                T parcel = parcels.get(i);
                System.out.println((i + 1) + ". " + parcel.getDescription());
                System.out.println("Вес: " + parcel.getWeight() + " кг");
                System.out.println("Адрес: " + parcel.getDeliveryAddress());

                if (parcel instanceof FragileParcel) {
                    System.out.println("Тип: Хрупкая посылка");
                } else if (parcel instanceof PerishableParcel) {
                    System.out.println("Тип: Скоропортящаяся посылка");
                    System.out.println("Срок годности: " + ((PerishableParcel) parcel).getTimeToLive() + " дней");
                } else {
                    System.out.println("Тип: Стандартная посылка");
                }
                System.out.println();
            }
        }
    }

    // Геттеры
    public double getMaxWeight() {
        return maxWeight;
    }

    public double getCurrentWeight() {
        return currentWeight;
    }

    public String getBoxType() {
        return boxType;
    }

    public int getParcelCount() {
        return parcels.size();
    }
}
