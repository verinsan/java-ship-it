package ru.yandex.practicum.delivery;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DeliveryApp {

    private static final Scanner scanner = new Scanner(System.in);
    private static List<Parcel> allParcels = new ArrayList<>();
    private static List<Trackable> trackableParcels = new ArrayList<>();

    private static ParcelBox<StandardParcel> standardBox;
    private static ParcelBox<FragileParcel> fragileBox;
    private static ParcelBox<PerishableParcel> perishableBox;

    public static void main(String[] args) {
        standardBox = new ParcelBox<>(50, "стандартных посылок");
        fragileBox = new ParcelBox<>(50, "хрупких посылок");
        perishableBox = new ParcelBox<>(50, "скоропортящихся посылок");

        boolean running = true;
        while (running) {
            showMenu();
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    addParcel();
                    break;
                case 2:
                    sendParcels();
                    break;
                case 3:
                    calculateCosts();
                    break;
                case 4:
                    trackParcels();
                    break;
                case 5:
                    showBoxContents();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Неверный выбор.");
            }
        }
    }

    private static void showMenu() {
        System.out.println("Выберите действие:");
        System.out.println("1 — Добавить посылку");
        System.out.println("2 — Отправить все посылки");
        System.out.println("3 — Посчитать стоимость доставки");
        System.out.println("4 — Обновить местоположение посылок");
        System.out.println("5 — Показать содержимое коробки");
        System.out.println("0 — Завершить");
    }

    // реализуйте методы ниже

    private static void addParcel() {
        // Подсказка: спросите тип посылки и необходимые поля, создайте объект и добавьте в allParcels
        System.out.println("\nВыберите тип посылки:");
        System.out.println("1 — Стандартная");
        System.out.println("2 — Хрупкая");
        System.out.println("3 — Скоропортящаяся");
        int type = Integer.parseInt(scanner.nextLine());

        System.out.print("Введите описание посылки: ");
        String description = scanner.nextLine();

        System.out.print("Введите вес посылки: ");
        int weight = Integer.parseInt(scanner.nextLine());

        System.out.print("Введите адрес назначения: ");
        String address = scanner.nextLine();

        System.out.print("Введите день отправки: ");
        int sendDay = Integer.parseInt(scanner.nextLine());

        Parcel parcel;

        switch (type) {
            case 1:
                StandardParcel standardParcel = new StandardParcel(description, weight, address, sendDay);
                parcel = standardParcel;
                allParcels.add(parcel);

                if (!standardBox.addParcel(standardParcel)) {
                    System.out.println("Посылка не поместилась в коробку.");
                }
                break;
            case 2:
                FragileParcel fragileParcel = new FragileParcel(description, weight, address, sendDay);
                parcel = fragileParcel;
                allParcels.add(parcel);
                trackableParcels.add(fragileParcel);

                if (!fragileBox.addParcel(fragileParcel)) {
                    System.out.println("Посылка не поместилась в коробку.");
                }
                break;
            case 3:
                System.out.print("Введите срок годности (дней): ");
                int timeToLive = Integer.parseInt(scanner.nextLine());

                PerishableParcel perishableParcel = new PerishableParcel(description, weight, address, sendDay, timeToLive);
                parcel = perishableParcel;
                allParcels.add(parcel);

                if (!perishableBox.addParcel(perishableParcel)) {
                    System.out.println("Посылка не поместилась в коробку.");
                }
                break;
            default:
                System.out.println("Неверный тип посылки.");
                return;
        }

        allParcels.add(parcel);
        System.out.println("Посылка добавлена");
    }

    private static void sendParcels() {
        // Пройти по allParcels, вызвать packageItem() и deliver()
        if (allParcels.isEmpty()) {
            System.out.println("Нет посылок для отправки.");
            return;
        }

        System.out.println("Содержимое коробок");
        standardBox.showContents();
        fragileBox.showContents();
        perishableBox.showContents();

        System.out.println("Отправка посылок:");
        for (Parcel parcel : allParcels) {
            parcel.packageItem();
            parcel.deliver();
            System.out.println(" ");
        }

        System.out.println("Проверка сроков годности скоропортящихся посылок:");
        System.out.print("Введите текущий день: ");
        int currentDay = Integer.parseInt(scanner.nextLine());

        for (Parcel parcel : allParcels) {
            if (parcel instanceof PerishableParcel) {
                PerishableParcel perishable = (PerishableParcel) parcel;
                boolean expired = perishable.isExpired(currentDay);
                String status = expired ? "испортилось" : "в норме";
                System.out.println(String.format("Посылка %s: %s", parcel.getDescription(), status));
            }
        }

    }

    private static void trackParcels() {
        if (trackableParcels.isEmpty()) {
            System.out.println("Нет посылок.");
            return;
        }

        System.out.print("Введите новое местоположение: ");
        String newLocation = scanner.nextLine().trim();

        if (newLocation.isEmpty()) {
            System.out.println("Местоположение не может быть пустым.");
            return;
        }

        for (Trackable trackable : trackableParcels) {
            trackable.reportStatus(newLocation);
        }
    }

    private static void calculateCosts() {
        // Посчитать общую стоимость всех доставок и вывести на экран
        if (allParcels.isEmpty()) {
            System.out.println("Нет посылок для расчета стоимости.");
            return;
        }

        int totalCost = 0;

        System.out.println("Расчет стоимости доставки:");
        for (Parcel parcel : allParcels) {
            int cost = parcel.calculateDeliveryCost();
            totalCost += cost;
        }
        System.out.println(String.format("Общая стоимость доставки: %d", totalCost));
    }

    private static void showBoxContents() {
        System.out.println("Выберите тип коробки:");
        System.out.println("1 — Стандартная коробка");
        System.out.println("2 — Хрупкая коробка");
        System.out.println("3 — Скоропортящаяся коробка");
        System.out.println("4 — Все коробки");

        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1:
                standardBox.showContents();
                break;
            case 2:
                fragileBox.showContents();
                break;
            case 3:
                perishableBox.showContents();
                break;
            case 4:
                standardBox.showContents();
                fragileBox.showContents();
                perishableBox.showContents();

        }
    }
}

