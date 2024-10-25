import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Car {
    private String carId;
    private String brand;
    private String model;
    private double basePricePerDay;
    private boolean isAvailable;

    public Car(String carId, String brand, String model, double basePricePerDay) {
        this.carId = carId;
        this.brand = brand;
        this.model = model;
        this.basePricePerDay = basePricePerDay;
        this.isAvailable = true;
    }

    public String getCarId() {
        return carId;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public double priceCalculate(int totalRentalDays) {
        return basePricePerDay * totalRentalDays;
    }

    public boolean getAvailability() {
        return isAvailable;
    }

    public void rent() {
        isAvailable = false;
    }

    public void returnCar() { // Fixed method name
        isAvailable = true;
    }
}

class Customer {
    private String customerId;
    private String name;

    public Customer(String customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }
}

class Rental {
    private Car car;
    private Customer customer;
    private int totalDays;

    public Rental(Car car, Customer customer, int totalDays) {
        this.car = car;
        this.customer = customer;
        this.totalDays = totalDays;
    }

    public Car getCar() {
        return car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getTotalDays() {
        return totalDays;
    }
}

class MCarRentalSystem {
    private List<Car> cars;
    private List<Customer> customers;
    private List<Rental> rentals;

    public MCarRentalSystem() {
        cars = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
    }

    public void addCar(Car car) {
        cars.add(car);
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public void rentCar(Car car, Customer customer, int totalDays) {
        if (car.getAvailability()) {
            car.rent();
            rentals.add(new Rental(car, customer, totalDays));
        } else {
            System.out.println("Car is not available for rent");
        }
    }

    public void returningCar(Car car) {
        Rental rentalToRemove = null;
        for (Rental rental : rentals) {
            if (rental.getCar().equals(car)) { // Use .equals() instead of ==
                rentalToRemove = rental;
                break;
            }
        }

        if (rentalToRemove != null) {
            rentals.remove(rentalToRemove);
            System.out.println("Car returned successfully.");
            car.returnCar(); // Fixed method name
        } else {
            System.out.println("This car was not rented.");
        }
    }

    public void mainMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("[-----xxxxx-------CAR-RENTAL-SYSTEM------xxxxx-------]");
            System.out.println("1. Rent a Car");
            System.out.println("2. Return a Car");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            if (choice == 1) {
                System.out.println("\n[=====------Renting a Car-------======]");
                System.out.print("Enter your name: ");
                String customerName = scanner.nextLine();

                System.out.println("\nAvailable Cars:");
                for (Car car : cars) {
                    if (car.getAvailability()) {
                        System.out.println(car.getCarId() + " - " + car.getBrand() + " - " + car.getModel());
                    }
                }

                System.out.print("\nEnter the car ID that you want to rent: ");
                String carID = scanner.nextLine();

                System.out.print("Enter the number of days for rental: ");
                int totalRentalDays;
                try {
                    totalRentalDays = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid number of days. Please try again.");
                    continue;
                }

                Customer newCustomer = new Customer("CUST" + (customers.size() + 1), customerName);
                addCustomer(newCustomer);

                Car selectedCar = null;
                for (Car car : cars) {
                    if (car.getCarId().equals(carID) && car.getAvailability()) {
                        selectedCar = car;
                        break;
                    }
                }

                if (selectedCar != null) {
                    double totalPrice = selectedCar.priceCalculate(totalRentalDays);
                    System.out.println("\n-----Rental Information-----");
                    System.out.println("Customer ID: " + newCustomer.getCustomerId());
                    System.out.println("Customer Name: " + newCustomer.getName());
                    System.out.println("Car: " + selectedCar.getBrand() + " " + selectedCar.getModel());
                    System.out.println("Rental Days: " + totalRentalDays);
                    System.out.printf("Total Price: $%.2f%n", totalPrice);

                    System.out.print("\nConfirm Rental (Yes/No): ");
                    String confirm = scanner.nextLine();

                    if (confirm.equalsIgnoreCase("Yes")) {
                        rentCar(selectedCar, newCustomer, totalRentalDays);
                        System.out.println("\nCar rented successfully");
                    } else {
                        System.out.println("\nRental canceled");
                    }
                } else {
                    System.out.println("\nInvalid car selection or car not available for rent.");
                }
            } else if (choice == 2) {
                System.out.println("\n== Return a Car ==");
                System.out.print("Enter the car ID you want to return: ");
                String carId = scanner.nextLine();

                Car carToReturn = null;
                for (Car car : cars) {
                    if (car.getCarId().equals(carId) && !car.getAvailability()) {
                        carToReturn = car;
                        break;
                    }
                }

                if (carToReturn != null) {
                    returningCar(carToReturn);
                } else {
                    System.out.println("Invalid car ID or car is not rented.");
                }
            } else if (choice == 3) {
                break;
            } else {
                System.out.println("Invalid choice. Please enter a valid option.");
            }
        }

        System.out.println("\nThank you for using the Car Rental System!");
        scanner.close();
    }
}

public class MainCarRentalSystem {
    public static void main(String[] args) {
        MCarRentalSystem rentalSystem = new MCarRentalSystem();

        Car car1 = new Car("C001", "Toyota", "Camry", 60.0);
        Car car2 = new Car("C002", "Honda", "Accord", 70.0);
        Car car3 = new Car("C003", "Mahindra", "Thar", 150.0);
        rentalSystem.addCar(car1);
        rentalSystem.addCar(car2);
        rentalSystem.addCar(car3);

        rentalSystem.mainMenu();
    }
}
