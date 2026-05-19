package com.transportgame.core;

import com.transportgame.ui.panels.GamePanel.MapTileType;

import java.awt.Point;
import java.util.*;

public class Pathfinder {

    public static boolean isRoadTile(MapTileType type) {
        return type == MapTileType.ROAD || type == MapTileType.BRIDGE;
    }

    public static Point findNearestRoad(MapTileType[][] tiles, int rows, int cols, int row, int col) {
        if (isRoadTile(tiles[row][col])) return new Point(row, col);
        for (int radius = 1; radius <= 3; radius++) {
            for (int dr = -radius; dr <= radius; dr++) {
                for (int dc = -radius; dc <= radius; dc++) {
                    int r = row + dr, c = col + dc;
                    if (r >= 0 && r < rows && c >= 0 && c < cols && isRoadTile(tiles[r][c])) {
                        return new Point(r, c);
                    }
                }
            }
        }
        return null;
    }

    public static List<Point> findPath(MapTileType[][] tiles, int rows, int cols,
                                       int startRow, int startCol, int endRow, int endCol) {
        Point startRoad = findNearestRoad(tiles, rows, cols, startRow, startCol);
        Point endRoad   = findNearestRoad(tiles, rows, cols, endRow, endCol);
        if (startRoad == null || endRoad == null) return null;

        Queue<Point>      queue   = new LinkedList<>();
        Map<Point, Point> parent  = new HashMap<>();
        Set<Point>        visited = new HashSet<>();

        queue.add(startRoad);
        visited.add(startRoad);
        parent.put(startRoad, null);

        int[][] dirs = {{0,1},{1,0},{0,-1},{-1,0}};

        while (!queue.isEmpty()) {
            Point current = queue.poll();
            if (current.x == endRoad.x && current.y == endRoad.y) {
                List<Point> path = new ArrayList<>();
                Point p = current;
                while (p != null) { path.add(0, p); p = parent.get(p); }
                return path;
            }
            for (int[] dir : dirs) {
                int nr = current.x + dir[0], nc = current.y + dir[1];
                if (nr < 0 || nr >= rows || nc < 0 || nc >= cols) continue;
                Point next = new Point(nr, nc);
                if (visited.contains(next) || !isRoadTile(tiles[nr][nc])) continue;
                visited.add(next);
                parent.put(next, current);
                queue.add(next);
            }
        }
        return null;
    }
}
