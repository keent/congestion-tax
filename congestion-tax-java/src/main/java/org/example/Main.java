package org.example;

import congestion.calculator.Car;
import congestion.calculator.Vehicle;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;
import spark.routematch.RouteMatch;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static spark.Spark.*;

public class Main {
// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.

    private static final congestion.calculator.CongestionTaxCalculator taxCalculator = new congestion.calculator.CongestionTaxCalculator();

    public static void main(String[] args) {

        // Set port (optional, defaults to 4567)
        port(8080);

        // Define Spark routes
        post("/calculateTax", calculateTaxRoute);

        // Additional routes and configurations can be added here

        // Log defined routes
        logDefinedRoutes();

        // Stop the server gracefully when the program exits
        Runtime.getRuntime().addShutdownHook(new Thread(() ->
        {
            Spark.stop();
            System.out.println("Server stopped");
        }));
    }

    private static void logDefinedRoutes() {
        System.out.println("Defined Routes:");
        for (RouteMatch route : routes()) {
            System.out.println(route.getMatchUri());
        }
        System.out.println("--------------------");
    }

    private static final Route calculateTaxRoute = (Request request, Response response) -> {
        try {
            // Parse vehicle type and dates from the request
            String vehicleType = request.queryParams("vehicleType");
            String[] dateStrings = request.queryParams("dates").split(",");

            // Convert date strings to Date objects
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date[] dates = new Date[dateStrings.length];
            for (int i = 0; i < dateStrings.length; i++) {
                dates[i] = dateFormat.parse(dateStrings[i]);
            }

            // Create an instance of a class implementing the Vehicle interface based on the vehicle type
            Vehicle vehicle = null;
            boolean bTollFreeVehicle;
            switch (vehicleType) {
                case "Car": {
                    bTollFreeVehicle = false;
                    vehicle = new Car();
                    break;
                }
                case "Emergency":
                case "Busses":
                case "Diplomat":
                case "Motorcycle":
                case "Motorbike":
                case "Military":
                case "Foreign": {
                    bTollFreeVehicle = true;
                    break;
                }
                default: {
                    response.status(400);
                    return "Invalid vehicle type";
                }
            }

            // Calculate the tax
            int tax;
            if (bTollFreeVehicle) {
                tax = 0;
            }
            else {
                tax = taxCalculator.getTax(vehicle, dates);
            }

            // Return the result as JSON
            response.type("application/json");
            return "{\"congestionTax\": " + tax + "}";

        } catch (ParseException e) {
            response.status(500); // Bad Request
            return "Error date format";
        }
    };


}