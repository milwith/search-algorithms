# search-algorithms

# route-find
This project focuses on implementing various algorithms for route finding tasks. The goal is to find the optimal path between two cities given a dataset containing city names, longitude, and latitude information.
Sure, here's the README file written using Markdown syntax, suitable for GitHub:

# Route Finding Task README

## Overview

This project focuses on implementing various algorithms for route finding tasks. The goal is to find the optimal path between two cities given a dataset containing city names, longitude, and latitude information. The algorithms are categorized into two main approaches: undirected (blind) brute-force approaches and heuristic approaches. The former includes breadth-first search, depth-first search, and iterative deepening depth-first search (ID-DFS), while the latter comprises best-first search and A* search.

## Implementation

### Data Input

The city data is provided in a CSV format containing city names along with their longitude and latitude coordinates. This information is used to construct the graph representation of the cities and their connections.

### Graph Representation

The city connections are represented as an adjacency list or matrix. Since the adjacency is symmetric (if city A is adjacent to city B, then city B is adjacent to city A), bidirectional connections are implicitly assumed.

### Algorithms

#### Undirected (Blind) Brute-Force Approaches

1. **Breadth-First Search (BFS):** Explores all the neighbor nodes at the present depth prior to moving on to the nodes at the next depth level.
   
2. **Depth-First Search (DFS):** Traverses a path all the way to the deepest level before backtracking and trying a different path.
   
3. **Iterative Deepening Depth-First Search (ID-DFS):** A hybrid approach that combines the benefits of BFS and DFS, gradually increasing the depth limit until the target node is found.

#### Heuristic Approaches

1. **Best-First Search:** Expands the most promising node according to some heuristic, which estimates the cost of reaching the goal.

2. **A* Search:** Evaluates nodes by combining the cost to reach the node and the cost to reach the goal, using an admissible heuristic function.

### Output

For each algorithm, the output includes the optimal route from the start city to the end city along with the total time taken to find the route.

## Instructions

1. Select the start city and end city from the provided dataset.
   
2. Choose one of the algorithms mentioned above based on your preference or requirements.
   
3. Run the selected algorithm with the chosen cities as input.
   
4. Review the output to obtain the optimal route and the total time taken.

## Conclusion

This project provides a comprehensive exploration of various route finding algorithms, offering insights into their performance and suitability for different scenarios. By leveraging both brute-force and heuristic approaches, users can find the most efficient routes between cities, facilitating navigation and logistical planning.
