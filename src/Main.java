import java.io.*;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner inputScanner = new Scanner(System.in);

        System.out.println("Hello and welcome!");
        System.out.println("Select mode:");
        System.out.println("1 - Input data into file");
        System.out.println("2 - Process file data");
        System.out.println("0 (default) - exit");

        int[] availableModes = {0, 1, 2};
        int selectedMode = 0;

        // Select mode
        if (inputScanner.hasNextInt()) {
            int modeFromInput = inputScanner.nextInt();
            if (Arrays.stream(availableModes).noneMatch(value -> value == modeFromInput)) {
                throw new RuntimeException("Selected wrong mode: " + modeFromInput);
            }
            selectedMode = modeFromInput;
        }

        if (selectedMode == 0) {
            System.out.println("Exit");
            System.exit(0);
        }

        if (selectedMode == 1) {
            System.out.println("Input mode");

            processInputMode(inputScanner);

            System.exit(0);
        }

        if (selectedMode == 2) {
            System.out.println("File process mode");

            processFileProcessingMode(inputScanner);

            System.exit(0);
        }

        throw new RuntimeException("FATAL: Incorrect mode selected. Unable to execute any code for given mode.");
    }

    private static void processInputMode (Scanner inputScanner) {
        List<Offer> offers = new ArrayList<>();

        int id;
        String productName;
        String vendorCode;
        String categoryCode;
        float pricePerUnit;
        int quantity;

        boolean shouldStopInput = false;

        do {
            System.out.println("Start data input of Offer #" + (offers.size() + 1));
            System.out.print("Enter ID: ");
            id = inputScanner.nextInt();
            System.out.print("Enter product name: ");
            productName = inputScanner.next();
            System.out.print("Enter product's vendor code: ");
            vendorCode = inputScanner.next();
            System.out.print("Enter product's category code: ");
            categoryCode = inputScanner.next();
            System.out.print("Enter price (per product's unit): ");
            pricePerUnit = inputScanner.nextFloat();
            System.out.print("Enter quantity: ");
            quantity = inputScanner.nextInt();

            Offer offer = new Offer(
                    id,
                    productName,
                    vendorCode,
                    categoryCode,
                    pricePerUnit,
                    quantity
            );

            offers.add(offer);

            System.out.print("Add another one offer? (yes/no): ");
            String answer = inputScanner.next();
            if (!Objects.equals(answer, "yes")) {
                shouldStopInput = true;
            }
        } while (!shouldStopInput);

        System.out.println("Saving data to file...");
        String filePath = Paths.get("file_out_mode_1.txt").toAbsolutePath().toString();
        File file = new File(filePath);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException("Unable to create file", e);
            }
        }

        if (!file.canWrite()) {
            throw new RuntimeException("File is not writable");
        }

        List<String> offerValuesList = new ArrayList<>();
        for (int i = 0; i < offers.size(); i++) {
            offerValuesList.add(offers.get(i).toValuesString());
        }
        String content = String.join("\n", offerValuesList);

        try {
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);

            // Write in file
            bw.write(content);

            // Close connection
            bw.close();
        } catch (IOException e) {
            throw new RuntimeException("Error while writing to file", e);
        }

        System.out.println("File saved: " + filePath);
    }

    private static void processFileProcessingMode (Scanner inputScanner) {
        System.out.print("Enter file absolute path: ");
//        String filePath = inputScanner.next();
        String filePath;
        if (inputScanner.hasNext()) {
            filePath = inputScanner.next() + inputScanner.nextLine(); // Workaround for reading paths with spaces
        } else {
            throw new RuntimeException("WTF");
        }

        File file = new File(filePath);

        if (!file.exists()) {
            throw new RuntimeException("File does not exist in path " + filePath);
        }

        if (!file.canRead()) {
            throw new RuntimeException("File is not readable");
        }

        List<Offer> offers = new ArrayList<>();

        // Read file
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Read and validate line (values line)
                if (!line.matches("^\\d+,.+,.+,[\\w\\d_-]+,\\d+(.\\d+)?,\\d+$")) {
                    System.out.println("Values line is not correct: " + line);
                    continue;
                }

                System.out.println("Read values line: " + line);

                // Convert values line into Offer
                Offer offer = Offer.fromValuesString(line);
                offers.add(offer);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Process data (offers sorting by quantity) and output result
        offers.sort(Comparator.comparing(Offer::getQuantity));

        System.out.println("Data processed (sorted by quantity). Result:");

        for (int i = 0; i < offers.size(); i++) {
            System.out.println(offers.get(i).toValuesString());
        }
    }
}