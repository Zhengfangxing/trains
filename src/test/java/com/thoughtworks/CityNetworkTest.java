package com.thoughtworks;

import org.junit.Test;

import static com.thoughtworks.City.*;

/**
 * @author William D X Zheng
 * @date 2019/3/11 23:56
 */
public class CityNetworkTest {

    /**
     * all case routes
     */
    private static final City[][] ROUTES = {
            // case1
            {A, B, C},
            // case2
            {A, D},
            // case3
            {A, D, C},
            // case4
            {A, E, B, C, D},
            // case5
            {A, E, D},
            // case6: maximum of 3 stops.
            {C, OPTIONAL, OPTIONAL, C},
            // case7: exactly 4 stops.
            {A, ANY, ANY, ANY, C},
            // case8
            {A, C},
            // case9
            {B, B},
            // case10
            {C, C}
    };

    /**
     * use to constructors. (adjacency matrix)
     */
    private static final int[][] CITY_NETWORK = {
            {0, 5, 0, 5, 7},
            {0, 0, 4, 0, 0},
            {0, 0, 0, 8, 2},
            {0, 0, 8, 0, 6},
            {0, 3, 0, 0, 0}
    };

    @Test
    public void homeWorkTest() {
        CityNetwork cityNetwork = new CityNetwork(CITY_NETWORK);

        int i = 0;
        // case1~5. The distance of route
        for (; i < 5; i++) {
            try {
                int distance = cityNetwork.routeDistance(ROUTES[i]);
                System.out.printf("Output #%d: %d\n", i + 1, distance);
            } catch (Exception e) {
                System.out.printf("Output #%d: NO SUCH ROUTE\n", i);
            }
        }

        // case6~7. The number of trips
        for (; i < 7; i++) {
            int number = cityNetwork.numberOfTrips(ROUTES[i]);
            System.out.printf("Output #%d: %d\n", i + 1, number);
        }

        // case8~9. The length of the shortest route
        for (; i < 9; i++) {
            int distance = cityNetwork.shortestRouteDistance(ROUTES[i][0], ROUTES[i][1]);
            System.out.printf("Output #%d: %d\n", i + 1, distance);
        }

        // case10. The number of different routes with a distance of less than 30
        int number = cityNetwork.numberOfRoutes(ROUTES[i][0], ROUTES[i][1], 30);
        System.out.printf("Output #%d: %d\n", ++i, number);
    }
}