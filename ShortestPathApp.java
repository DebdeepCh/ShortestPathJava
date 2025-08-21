import java.util.*;

class Graph {
    private int numVertices;
    private LinkedList<int[]>[] adjacencyList;

    // Constructor
    @SuppressWarnings("unchecked")
    Graph(int vertices) {
        this.numVertices = vertices;
        adjacencyList = new LinkedList[vertices];
        for (int i = 0; i < vertices; i++) {
            adjacencyList[i] = new LinkedList<>();
        }
    }

    // Add an edge to the graph
    void addEdge(int from, int to, int weight) {
        adjacencyList[from].add(new int[]{to, weight});
        adjacencyList[to].add(new int[]{from, weight}); // Remove this line for directed graph
    }

    // Dijkstra's algorithm to find shortest paths from source
    void findShortestPaths(int source) {
        int[] distances = new int[numVertices]; // distance from source
        int[] parents = new int[numVertices];   // store paths
        boolean[] visited = new boolean[numVertices];

        Arrays.fill(distances, Integer.MAX_VALUE);
        Arrays.fill(parents, -1);
        distances[source] = 0;

        // Priority queue for selecting vertex with minimum distance
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        pq.add(new int[]{source, 0});

        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int u = current[0];

            if (visited[u]) continue;
            visited[u] = true;

            for (int[] neighbor : adjacencyList[u]) {
                int v = neighbor[0];
                int weight = neighbor[1];

                if (!visited[v] && distances[u] + weight < distances[v]) {
                    distances[v] = distances[u] + weight;
                    parents[v] = u;
                    pq.add(new int[]{v, distances[v]});
                }
            }
        }

        // Print results
        System.out.println("\nVertex\tDistance from Source\tPath");
        for (int i = 0; i < numVertices; i++) {
            if (i != source) {
                System.out.print(i + "\t\t" + distances[i] + "\t\t");
                printPath(i, parents);
                System.out.println();
            }
        }
    }

    // Recursive function to print path from source to target
    void printPath(int vertex, int[] parents) {
        if (parents[vertex] == -1) {
            System.out.print(vertex);
            return;
        }
        printPath(parents[vertex], parents);
        System.out.print(" -> " + vertex);
    }
}

public class ShortestPathApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Shortest Path Finder using Dijkstra's Algorithm ===");

        System.out.print("Enter number of vertices in the graph: ");
        int vertices = scanner.nextInt();
        Graph graph = new Graph(vertices);

        System.out.print("Enter number of edges: ");
        int edges = scanner.nextInt();

        System.out.println("Enter each edge in the format: from to weight");
        for (int i = 0; i < edges; i++) {
            System.out.print("Edge " + (i + 1) + ": ");
            int from = scanner.nextInt();
            int to = scanner.nextInt();
            int weight = scanner.nextInt();
            graph.addEdge(from, to, weight);
        }

        System.out.print("Enter the source vertex: ");
        int source = scanner.nextInt();

        System.out.println("\nCalculating shortest paths...");
        graph.findShortestPaths(source);

        scanner.close();
    }
}
