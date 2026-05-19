package com.transportgame.core;

import com.transportgame.ui.panels.GamePanel.MapTileType;
import org.junit.jupiter.api.Test;

import java.awt.Point;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PathfinderTest {

    private static final int SIZE = 20;

    private MapTileType[][] emptyGrid() {
        MapTileType[][] t = new MapTileType[SIZE][SIZE];
        for (int r = 0; r < SIZE; r++)
            for (int c = 0; c < SIZE; c++)
                t[r][c] = MapTileType.EMPTY;
        return t;
    }

    @Test
    void roadTileIsTraversable() {
        assertTrue(Pathfinder.isRoadTile(MapTileType.ROAD));
    }

    @Test
    void bridgeTileIsTraversable() {
        assertTrue(Pathfinder.isRoadTile(MapTileType.BRIDGE));
    }

    @Test
    void emptyTileIsNotTraversable() {
        assertFalse(Pathfinder.isRoadTile(MapTileType.EMPTY));
    }

    @Test
    void houseTileIsNotTraversable() {
        assertFalse(Pathfinder.isRoadTile(MapTileType.HOUSE));
    }

    @Test
    void nearestRoadReturnsSelfWhenOnRoad() {
        MapTileType[][] t = emptyGrid();
        t[5][5] = MapTileType.ROAD;

        Point p = Pathfinder.findNearestRoad(t, SIZE, SIZE, 5, 5);
        assertNotNull(p);
        assertEquals(5, p.x);
        assertEquals(5, p.y);
    }

    @Test
    void nearestRoadFindsAdjacentRoad() {
        MapTileType[][] t = emptyGrid();
        t[5][6] = MapTileType.ROAD;

        Point p = Pathfinder.findNearestRoad(t, SIZE, SIZE, 5, 5);
        assertNotNull(p);
        assertEquals(5, p.x);
        assertEquals(6, p.y);
    }

    @Test
    void nearestRoadFindsRoadWithinThreeTiles() {
        MapTileType[][] t = emptyGrid();
        t[5][8] = MapTileType.ROAD;

        Point p = Pathfinder.findNearestRoad(t, SIZE, SIZE, 5, 5);
        assertNotNull(p);
    }

    @Test
    void nearestRoadReturnsNullWhenTooFar() {
        MapTileType[][] t = emptyGrid();
        t[5][9] = MapTileType.ROAD;

        Point p = Pathfinder.findNearestRoad(t, SIZE, SIZE, 5, 5);
        assertNull(p);
    }

    @Test
    void straightHorizontalPath() {
        MapTileType[][] t = emptyGrid();
        for (int c = 3; c <= 8; c++) t[5][c] = MapTileType.ROAD;

        List<Point> path = Pathfinder.findPath(t, SIZE, SIZE, 5, 3, 5, 8);

        assertNotNull(path);
        assertFalse(path.isEmpty());

        assertEquals(new Point(5, 3), path.get(0));
        assertEquals(new Point(5, 8), path.get(path.size() - 1));

        assertEquals(6, path.size());
    }

    @Test
    void lShapedPath() {
        MapTileType[][] t = emptyGrid();

        for (int c = 3; c <= 7; c++) t[5][c] = MapTileType.ROAD;

        for (int r = 5; r <= 9; r++) t[r][7] = MapTileType.ROAD;

        List<Point> path = Pathfinder.findPath(t, SIZE, SIZE, 5, 3, 9, 7);

        assertNotNull(path);
        assertEquals(new Point(5, 3), path.get(0));
        assertEquals(new Point(9, 7), path.get(path.size() - 1));

        assertEquals(9, path.size());
    }

    @Test
    void returnsNullWhenNoRoadAtAll() {
        MapTileType[][] t = emptyGrid();
        List<Point> path = Pathfinder.findPath(t, SIZE, SIZE, 2, 2, 8, 8);
        assertNull(path);
    }

    @Test
    void returnsNullWhenRoadIsBlocked() {
        MapTileType[][] t = emptyGrid();

        for (int c = 2; c <= 4; c++) t[5][c] = MapTileType.ROAD;
        for (int c = 6; c <= 8; c++) t[5][c] = MapTileType.ROAD;

        List<Point> path = Pathfinder.findPath(t, SIZE, SIZE, 5, 2, 5, 8);
        assertNull(path);
    }

    @Test
    void pathBlockedAfterRoadRemoved() {
        MapTileType[][] t = emptyGrid();
        for (int c = 2; c <= 8; c++) t[5][c] = MapTileType.ROAD;

        assertNotNull(Pathfinder.findPath(t, SIZE, SIZE, 5, 2, 5, 8));

        t[5][5] = MapTileType.EMPTY;

        assertNull(Pathfinder.findPath(t, SIZE, SIZE, 5, 2, 5, 8));
    }

    @Test
    void bridgeTilesAreTraversed() {
        MapTileType[][] t = emptyGrid();
        t[5][3] = MapTileType.ROAD;
        t[5][4] = MapTileType.BRIDGE;
        t[5][5] = MapTileType.ROAD;

        List<Point> path = Pathfinder.findPath(t, SIZE, SIZE, 5, 3, 5, 5);
        assertNotNull(path);
        assertEquals(3, path.size());
    }

    @Test
    void multiStopRouteConnects() {

        MapTileType[][] t = emptyGrid();
        for (int c = 2; c <= 12; c++) t[5][c] = MapTileType.ROAD;

        List<Point> ab = Pathfinder.findPath(t, SIZE, SIZE, 5, 2, 5, 7);
        List<Point> bc = Pathfinder.findPath(t, SIZE, SIZE, 5, 7, 5, 12);

        assertNotNull(ab);
        assertNotNull(bc);

        assertEquals(ab.get(ab.size() - 1), bc.get(0));
    }
}
