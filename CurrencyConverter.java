import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class CurrencyConverter {

    // Your API Key
    private static final String API_KEY = "2e19cf20764987b4592a5201";
    private static final String API_URL = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Allow the user to choose the base currency and the target currency
        System.out.print("Enter base currency (e.g., USD): ");
        String baseCurrency = scanner.nextLine().toUpperCase();

        System.out.print("Enter target currency (e.g., INR): ");
        String targetCurrency = scanner.nextLine().toUpperCase();

        // Take input from the user for the amount they want to convert
        System.out.print("Enter amount to convert: ");
        double amount = scanner.nextDouble();

        // Fetch real-time exchange rates from a reliable API
        double exchangeRate = getExchangeRate(baseCurrency, targetCurrency);

        // Convert the input amount from the base currency to the target currency
        double convertedAmount = amount * exchangeRate;

        // Show the converted amount and the target currency symbol to the user
        System.out.printf("%.2f %s is equal to %.2f %s%n", amount, baseCurrency, convertedAmount, targetCurrency);
    }

    // Method to fetch the exchange rate from the API
    private static double getExchangeRate(String baseCurrency, String targetCurrency) {
        try {
            URL url = new URL(API_URL + baseCurrency);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Parsing the response manually without any additional libraries
            String responseString = response.toString();
            String targetRateString = responseString.split("\"" + targetCurrency + "\":")[1].split(",")[0];
            return Double.parseDouble(targetRateString);

        } catch (Exception e) {
            e.printStackTrace();
            return 0.0;
        }
    }
}
