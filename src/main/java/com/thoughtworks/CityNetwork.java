package com.thoughtworks;

import static com.thoughtworks.City.OPTIONAL;

/**
 * @author William D X Zheng
 * @date 2019/3/11 23:00
 */
public class CityNetwork {
    private int[][] network;

    public CityNetwork(int[][] network) {
        this.network = network;
    }

    /**
     * Return total distance of the given route. Throw an Exception if no path.
     *
     * @param route the given route.
     * @return total distance of the route.
     * @throws Exception if no path.
     */
    public int routeDistance(City[] route) throws Exception {
        int distance = 0;

        // get the 1st city's index
        int idx1 = route[0].getIndex();
        int idx2;
        for (int i = 1; i < route.length; i++) {
            // get next city's index
            idx2 = route[i].getIndex();
            // if there is no route between 2 cities, then throw an exception
            if (network[idx1][idx2] == 0) {
                throw new Exception();
            }
            // add distance of 2 cities to total distance
            distance += network[idx1][idx2];
            // change index-1 to index-2 and go forward to next city
            idx1 = idx2;
        }

        return distance;
    }

    /**
     * Return the number of trips of the given route.
     *
     * @param route the given route.
     * @return the number of trips of the route.
     */
    public int numberOfTrips(City[] route) {
        return numberOfTrips(route, 0);
    }

    /**
     * Return the distance of the shortest route between the given cities.
     *
     * @param origin the origin city (start city).
     * @param dest   the destination city.
     * @return the distance of the shortest route between origin and destination.
     */
    public int shortestRouteDistance(City origin, City dest) {
        boolean[][] flags = new boolean[network.length][network.length];
        return shortestRouteDistance(origin.getIndex(), dest.getIndex(), flags, 0, 0, origin == dest);
    }

    /**
     * Return the number of routes between the given cities which distance is less than maxDistance.
     *
     * @param origin      the origin city.
     * @param dest        the destination city.
     * @param maxDistance the max distance of the found routes.
     * @return the number of routes.
     */
    public int numberOfRoutes(City origin, City dest, int maxDistance) {
        return numberOfRoutes(origin.getIndex(), dest.getIndex(), maxDistance, 0, origin == dest);
    }

    private int numberOfTrips(City[] route, int offset) {
        // we are at the last city on the route.
        if (offset == route.length - 1) {
            return 1;
        }

        // try to go forward to next city.
        City start = route[offset];
        City next = route[offset + 1];
        int number = 0;
        int idx1;
        int idx2;
        if (start.getIndex() < 0) {
            // if start is a special city (Any or Optional), try every city on the network graph.
            for (City city : City.values()) {
                if (city.getIndex() >= 0) {
                    route[offset] = city;
                    number += numberOfTrips(route, offset);
                }
            }
            // Optional city can be skipped
            if (start == OPTIONAL) {
                number += numberOfTrips(route, offset + 1);
            }
            route[offset] = start;
        } else {
            // start is a normal city
            idx1 = start.getIndex();
            // check whether next is a special city or a normal city.
            if (next.getIndex() < 0) {
                for (City city : City.values()) {
                    if (city.getIndex() >= 0) {
                        route[offset + 1] = city;
                        number += numberOfTrips(route, offset);
                    }
                }
                // Optional city can be skipped
                if (next == OPTIONAL) {
                    route[offset + 1] = start;
                    number += numberOfTrips(route, offset + 1);
                }
                route[offset + 1] = next;
            } else {
                idx2 = next.getIndex();
                number = network[idx1][idx2] == 0 ? 0 :
                        numberOfTrips(route, offset + 1);
            }
        }

        return number;
    }

    /**
     * a conventional DFS algorithm
     * <p>
     * I think Dijkstra can be improved. That's is a better idea, but i didn't realize it, I'll continue to learn.
     *
     * @param origin
     * @param dest
     * @param flags
     * @param shortest
     * @param distance
     * @param sameCity
     * @return
     */
    private int shortestRouteDistance(int origin, int dest, boolean[][] flags, int shortest, int distance, boolean sameCity) {
        if (!sameCity && origin == dest) {
            return distance;
        }

        int currentDistance;
        for (int next = 0; next < network.length; next++) {
            if (!flags[origin][next] && network[origin][next] != 0) {
                flags[origin][next] = true;
                currentDistance = shortestRouteDistance(next, dest, flags, 0, distance + network[origin][next], false);
                boolean flag1 = currentDistance != 0 && (shortest == 0 || currentDistance < shortest);
                if (flag1) {
                    shortest = currentDistance;
                }
                flags[origin][next] = false;
            }
        }

        return shortest;
    }

    /**
     * @param origin
     * @param dest
     * @param maxDistance
     * @param distance
     * @param sameCity
     * @return
     */
    private int numberOfRoutes(int origin, int dest, final int maxDistance, int distance, boolean sameCity) {
        if (distance >= maxDistance) {
            return 0;
        }

        int number = 0;
        if (!sameCity && origin == dest) {
            number++;
        }
        for (int next = 0; next < network.length; next++) {
            if (network[origin][next] != 0) {
                number += numberOfRoutes(next, dest, maxDistance, distance + network[origin][next], false);
            }
        }

        return number;
    }
}
