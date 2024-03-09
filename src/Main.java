import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {


        RouteFinder routeFinder = new RouteFinder();
        routeFinder.readCityData("G:/freelancing/SearchAlgo/RouteFinding/src/data/cities.csv");
        routeFinder.readAdjacencyData("G:/freelancing/SearchAlgo/RouteFinding/src/data/adjacency.txt");

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Enter starting city:");
            String start = scanner.nextLine();
            if (!routeFinder.cities.containsKey(start)) {
                System.out.println("Invalid city. Please enter a valid city.");
                continue;
            }

            System.out.println("Enter ending city:");
            String end = scanner.nextLine();
            if (!routeFinder.cities.containsKey(end)) {
                System.out.println("Invalid city. Please enter a valid city.");
                continue;
            }

            System.out.println("Select search method:");
            System.out.println("1. Breadth First Search");
            System.out.println("2. Depth First Search");
            System.out.println("3. Iterative Deepening DFS");
            System.out.println("4. Best First Search");
            System.out.println("5. A* Search");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            List<String> route = null;
            long startTime = System.currentTimeMillis();
            switch (choice) {
                case 1:
                    route = routeFinder.breadthFirstSearch(start, end);
                    break;
                case 2:
                    route = routeFinder.depthFirstSearch(start, end);
                    break;
                case 3:
                    route = routeFinder.iterativeDeepeningDFS(start, end);
                    break;
                case 4:
                    route = routeFinder.bestFirstSearch(start, end);
                    break;
                case 5:
                    route = routeFinder.aStarSearch(start, end);
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
            long endTime = System.currentTimeMillis();

            if (route != null) {
                System.out.println("Route found:");
                for (String city : route) {
                    System.out.print(city + " -> ");
                }
                System.out.println(end);
                System.out.println("Total time: " + (endTime - startTime) + " ms");
            } else {
                System.out.println("No route found.");
            }

            System.out.println("Do you want to find another route? (yes/no)");
            String again = scanner.nextLine();
            if (!again.equalsIgnoreCase("yes")) {
                break;
            }
        }

        scanner.close();
    }

}

