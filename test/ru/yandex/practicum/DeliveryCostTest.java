package ru.yandex.practicum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.delivery.FragileParcel;
import ru.yandex.practicum.delivery.ParcelBox;
import ru.yandex.practicum.delivery.PerishableParcel;
import ru.yandex.practicum.delivery.StandardParcel;

public class DeliveryCostTest {
    @Test
    void testStandardParcelCost() {
        StandardParcel parcel1 = new StandardParcel("Книги", 5, "ул. Книжная, 5", 15);
        Assertions.assertEquals(10, parcel1.calculateDeliveryCost());

        StandardParcel parcel2 = new StandardParcel("Одежда", 8, "ул. Кустова, 8", 12);
        Assertions.assertEquals(16, parcel2.calculateDeliveryCost());

        StandardParcel parcel3 = new StandardParcel("Письмо", 1, "ул. Гагарина, 3", 10);
        Assertions.assertEquals(2, parcel3.calculateDeliveryCost());
    }

    @Test
    void testFragileParcelCost() {
        FragileParcel parcel1 = new FragileParcel("Ваза", 3, "ул. Вазовая, 3", 20);
        Assertions.assertEquals(12, parcel1.calculateDeliveryCost());

        FragileParcel parcel2 = new FragileParcel("Зеркало", 7, "ул. Зеркальная, 7", 18);
        Assertions.assertEquals(28, parcel2.calculateDeliveryCost());

        FragileParcel parcel3 = new FragileParcel("Лампа", 1, "ул. Стеклянная, 3", 15);
        Assertions.assertEquals(4, parcel3.calculateDeliveryCost());
    }

    @Test
    void testPerishableParcelCost() {
        PerishableParcel parcel1 = new PerishableParcel("Молоко", 4, "ул. Молочная, 4", 16, 2);
        Assertions.assertEquals(12, parcel1.calculateDeliveryCost());

        PerishableParcel parcel2 = new PerishableParcel("Овощи", 6, "ул. Овощная, 6", 12, 5);
        Assertions.assertEquals(18, parcel2.calculateDeliveryCost());

        PerishableParcel parcel3 = new PerishableParcel("Фрукты", 1, "ул. Фруктовая, 3", 10, 1);
        Assertions.assertEquals(3, parcel3.calculateDeliveryCost());
    }

    @Test
    void testIsExpiredForPerishableParcel() {
        PerishableParcel parcel = new PerishableParcel("Продукты", 3, "ул. Продовольственная, 1", 10, 5);

        Assertions.assertFalse(parcel.isExpired(12));

        Assertions.assertTrue(parcel.isExpired(16));

        Assertions.assertFalse(parcel.isExpired(15));
    }

    @Test
    void testIsExpiredWithDifferentTTL() {
        PerishableParcel parcel = new PerishableParcel("Консервы", 5, "ул. Долгая, 7", 1, 30);

        Assertions.assertFalse(parcel.isExpired(15));

        Assertions.assertTrue(parcel.isExpired(40));

        Assertions.assertFalse(parcel.isExpired(31));
    }

    @Test
    void testAddParcelToBox() {
        ParcelBox<StandardParcel> box = new ParcelBox<>(20, "стандартных посылок");

        StandardParcel parcel1 = new StandardParcel("Книга 1", 5, "ул. А, 1", 10);
        Assertions.assertTrue(box.addParcel(parcel1));
        Assertions.assertEquals(1, box.getAllParcels().size());
        Assertions.assertEquals(5, box.getCurrentWeight());

        StandardParcel parcel2 = new StandardParcel("Книга 2", 10, "ул. Б, 2", 11);
        Assertions.assertTrue(box.addParcel(parcel2));
        Assertions.assertEquals(2, box.getAllParcels().size());
        Assertions.assertEquals(15, box.getCurrentWeight());

        StandardParcel parcel3 = new StandardParcel("Книга 3", 10, "ул. В, 3", 12);
        Assertions.assertFalse(box.addParcel(parcel3));
        Assertions.assertEquals(2, box.getAllParcels().size());
        Assertions.assertEquals(15, box.getCurrentWeight());
    }

    @Test
    void testAddParcelExactWeightLimit() {
        ParcelBox<FragileParcel> box = new ParcelBox<>(15, "хрупких посылок");

        FragileParcel parcel1 = new FragileParcel("Маленькая ваза", 5, "ул. А, 1", 10);
        Assertions.assertTrue(box.addParcel(parcel1));
        Assertions.assertEquals(1, box.getAllParcels().size());

        FragileParcel parcel2 = new FragileParcel("Средняя ваза", 5, "ул. Б, 2", 11);
        Assertions.assertTrue(box.addParcel(parcel2));
        Assertions.assertEquals(2, box.getAllParcels().size());

        FragileParcel parcel3 = new FragileParcel("Точная ваза", 5, "ул. В, 3", 12);
        Assertions.assertTrue(box.addParcel(parcel3));
        Assertions.assertEquals(3, box.getAllParcels().size());
        Assertions.assertEquals(15, box.getCurrentWeight());
    }

    @Test
    void testAddParcelOverWeightLimit() {
        ParcelBox<PerishableParcel> box = new ParcelBox<>(10, "скоропортящихся посылок");

        PerishableParcel parcel1 = new PerishableParcel("Молоко", 3, "ул. А, 1", 10, 2);
        Assertions.assertTrue(box.addParcel(parcel1));

        PerishableParcel parcel2 = new PerishableParcel("Йогурт", 4, "ул. Б, 2", 11, 3);
        Assertions.assertTrue(box.addParcel(parcel2));

        PerishableParcel parcel3 = new PerishableParcel("Сметана", 4, "ул. В, 3", 12, 5);
        Assertions.assertFalse(box.addParcel(parcel3));
        Assertions.assertEquals(2, box.getAllParcels().size());
        Assertions.assertEquals(7, box.getCurrentWeight());
    }
}
