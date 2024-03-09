import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class RouteFinder {


    Map<String, List<String>> adjacencyList;
    Map<String, City> cities;

    public RouteFinder() {
        adjacencyList = new HashMap<>();
        cities = new HashMap<>();
    }

    public void readCityData(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            String name = parts[0];
            double latitude = Double.parseDouble(parts[1]);
            double longitude = Double.parseDouble(parts[2]);
            cities.put(name, new City(name, latitude, longitude));
        }
        reader.close();
    }

    public void readAdjacencyData(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(" ");
            String city1 = parts[0];
            String city2 = parts[1];
            addEdge(city1, city2);
        }
        reader.close();
    }

    public void addEdge(String city1, String city2) {
        adjacencyList.computeIfAbsent(city1, k -> new ArrayList<>()).add(city2);
        adjacencyList.computeIfAbsent(city2, k -> new ArrayList<>()).add(city1); // symmetric connection
    }

    public List<String> breadthFirstSearch(String start, String end) {
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        Map<String, String> parentMap = new HashMap<>();

        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            String current = queue.poll();
            if (current.equals(end)) {
                // Reconstruct the path from end to start
                List<String> path = new ArrayList<>();
                while (!current.equals(start)) {
                    path.add(0, current);
                    current = parentMap.get(current);
                }
                path.add(0, start);
                return path;
            }

            for (String neighbor : adjacencyList.getOrDefault(current, new ArrayList<>())) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    parentMap.put(neighbor, current);
                    queue.add(neighbor);
                }
            }
        }

        // If no path found
        return null;
    }


    public List<String> depthFirstSearch(String start, String end) {
        Set<String> visited = new HashSet<>();
        Stack<String> stack = new Stack<>();
        Map<String, String> parentMap = new HashMap<>();

        stack.push(start);
        visited.add(start);

        while (!stack.isEmpty()) {
            String current = stack.pop();
            if (current.equals(end)) {
                // Reconstruct the path from end to start
                List<String> path = new ArrayList<>();
                while (!current.equals(start)) {
                    path.add(0, current);
                    current = parentMap.get(current);
                }
                path.add(0, start);
                return path;
            }

            for (String neighbor : adjacencyList.getOrDefault(current, new ArrayList<>())) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    parentMap.put(neighbor, current);
                    stack.push(neighbor);
                }
            }
        }

        // If no path found
        return null;
    }


    public List<String> iterativeDeepeningDFS(String start, String end) {
        int maxDepth = 0;
        while (true) {
            List<String> result = depthLimitedDFS(start, end, maxDepth);
            if (result != null) {
                return result;
            }
            maxDepth++;
        }
    }

    private List<String> depthLimitedDFS(String start, String end, int maxDepth) {
        Set<String> visited = new HashSet<>();
        Deque<String> stack = new ArrayDeque<>();
        Map<String, String> parentMap = new HashMap<>();

        stack.push(start);
        visited.add(start);
        int depth = 0;

        while (!stack.isEmpty()) {
            String current = stack.pop();
            if (current.equals(end)) {
                // Reconstruct the path from end to start
                List<String> path = new ArrayList<>();
                while (!current.equals(start)) {
                    path.add(0, current);
                    current = parentMap.get(current);
                }
                path.add(0, start);
                return path;
            }

            if (depth < maxDepth) {
                for (String neighbor : adjacencyList.getOrDefault(current, new ArrayList<>())) {
                    if (!visited.contains(neighbor)) {
                        visited.add(neighbor);
                        parentMap.put(neighbor, current);
                        stack.push(neighbor);
                    }
                }
                depth++;
            }
        }
        return null; // If no path found within the depth limit
    }


    public List<String> bestFirstSearch(String start, String end) {
        Set<String> visited = new HashSet<>();
        PriorityQueue<String> queue = new PriorityQueue<>(Comparator.comparingDouble(city -> heuristic(city, end)));
        Map<String, String> parentMap = new HashMap<>();

        queue.offer(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            String current = queue.poll();
            if (current.equals(end)) {
                // Reconstruct the path from end to start
                List<String> path = new ArrayList<>();
                while (!current.equals(start)) {
                    path.add(0, current);
                    current = parentMap.get(current);
                }
                path.add(0, start);
                return path;
            }

            for (String neighbor : adjacencyList.getOrDefault(current, new ArrayList<>())) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    parentMap.put(neighbor, current);
                    queue.offer(neighbor);
                }
            }
        }
        return null; // If no path found
    }
    private double heuristic(String city, String end) {
        City city1 = cities.get(city);
        City city2 = cities.get(end);

        // Calculate Euclidean distance between the two cities' coordinates
        double deltaX = city1.latitude - city2.latitude;
        double deltaY = city1.longitude - city2.longitude;

        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }

    public List<String> aStarSearch(String start, String end) {
        Set<String> visited = new HashSet<>();
        Map<String, String> parentMap = new HashMap<>();
        Map<String, Double> gScore = new HashMap<>();
        Map<String, Double> fScore = new HashMap<>();

        PriorityQueue<String> openSet = new PriorityQueue<>(Comparator.comparingDouble(fScore::get));

        gScore.put(start, 0.0);
        fScore.put(start, heuristic(start, end));
        openSet.offer(start);

        while (!openSet.isEmpty()) {
            String current = openSet.poll();

            if (current.equals(end)) {
                // Reconstruct the path from end to start
                List<String> path = new ArrayList<>();
                while (!current.equals(start)) {
                    path.add(0, current);
                    current = parentMap.get(current);
                }
                path.add(0, start);
                return path;
            }

            visited.add(current);

            for (String neighbor : adjacencyList.getOrDefault(current, new ArrayList<>())) {
                if (visited.contains(neighbor)) {
                    continue; // Ignore the neighbor which is already evaluated
                }

                double tentativeGScore = gScore.getOrDefault(current, Double.POSITIVE_INFINITY) + 1; // Assuming uniform cost for simplicity

                if (!openSet.contains(neighbor) || tentativeGScore < gScore.getOrDefault(neighbor, Double.POSITIVE_INFINITY)) {
                    // This path is the best until now. Record it!
                    parentMap.put(neighbor, current);
                    gScore.put(neighbor, tentativeGScore);
                    fScore.put(neighbor, tentativeGScore + heuristic(neighbor, end));
                    openSet.offer(neighbor); // Discover a new node
                }
            }
        }

        return null; // If no path found
    }


}

