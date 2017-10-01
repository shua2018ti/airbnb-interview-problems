package sliding_puzzle;

/**
 * Sliding Puzzle
 * 这里我们假设空格为0，所以0周围的数字可以与其交换
 *
 * 最好的应该是A*算法，这里用BFS也是可以做的。最好不要DFS，可能会爆栈。
 * 面经里应该只需要判断是否能solve，其实打印出最短路径也是差不多的
 */

import java.util.*;

public class EightPuzzle {
    public static void main(String[] args) {
        int[][] matrix = {
                {3, 1, 4},
                {6, 2, 0},
                {7, 5, 8}
        };
        EightPuzzle ep = new EightPuzzle(matrix);
        boolean r1 = ep.isSolvable();
        List<String> r2 = ep.getSolution();

        return;
    }

    private static final int[][] DIRS = { { 1, 0 }, { 0, 1 }, { -1, 0 }, { 0, -1 } };
    private static final String[] PATH_WORDS = { "Down", "Right", "Up", "Left" };
    private static final String FINAL_STATE = "0,1,2,3,4,5,6,7,8,";

    /* The matrix */
    private int[][] matrix;
    private int m;
    private int n;

    /* Blank's initial position */
    private int originX;
    private int originY;

    /**
     * Constructor
     */
    public EightPuzzle(int[][] matrix) {
        this.matrix = matrix;
        this.m = matrix.length;
        this.n = matrix[0].length;
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                if (matrix[i][j] == 0) {
                    this.originX = i;
                    this.originY = j;
                }
    }

    /**
     * Check if the puzzle can be solved
     */
    public boolean isSolvable() {
        /* Maintain:
         *    two queues to record matrix and its blank's position;
         *    one map to record all the visited states.
         */
        Queue<String> matrixQueue = new LinkedList<>();
        Queue<int[]> blankQueue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        /* Add the initial position and string as seed */
        String stringMatrix = encodeIntoString(matrix.clone());
        blankQueue.offer(new int[] {originX, originY});
        matrixQueue.offer(stringMatrix);
        visited.add(stringMatrix);

        /* Perform a BFS */
        while (!blankQueue.isEmpty()) {
            /* Process only last layer's elements */
            int size = blankQueue.size();
            for (int i = 0; i < size; i++) {
                /* Return true if this is already the final state */
                String curMatrixString = matrixQueue.poll();
                if (curMatrixString.equals(FINAL_STATE))
                    return true;

                /* Retrieve the matrix and blank's position */
                int[][] curMatrix = decodeFromString(curMatrixString);
                int[] curElement = blankQueue.poll();
                int x = curElement[0], y = curElement[1];

                /* Search in four directions */
                for (int k = 0; k < DIRS.length; k++) {
                    /* Check if new blank's position is legal */
                    int xx = x + DIRS[k][0], yy = y + DIRS[k][1];
                    if (xx < 0 || xx >= m || yy < 0 || yy >= n)
                        continue;

                    /* Clone a new matrix and swap the elements on old and new blanks */
                    int[][] newMatrix = curMatrix.clone();
                    int temp = newMatrix[x][y];
                    newMatrix[x][y] = newMatrix[xx][yy];
                    newMatrix[xx][yy] = temp;

                    /* Hash the new state to see if this position visited */
                    String newMatrixString = encodeIntoString(newMatrix);
                    if (visited.contains(newMatrixString))
                        continue;

                    /* Add new state into queue and mark it visited */
                    matrixQueue.offer(newMatrixString);
                    blankQueue.offer(new int[] { xx, yy });
                    visited.add(newMatrixString);
                }
            }
        }

        return false;
    }

    /**
     * Print shortest path into a list of Strings
     */
    public List<String> getSolution() {

        /*
         * Maintain:
         *     two queues to record matrix and its blank's position;
         *     a Path queue to record the prefix of each state;
         *     one map to record all the visited states.
         */
        Queue<String> matrixQueue = new LinkedList<>();
        Queue<int[]> elementQueue = new LinkedList<>();
        Queue<List<String>> pathQueue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        /* Add the initial position and string as seed */
        String stringMatrix = encodeIntoString(matrix.clone());
        elementQueue.offer(new int[] { originX, originY });
        matrixQueue.offer(stringMatrix);
        pathQueue.offer(new ArrayList<>());
        visited.add(stringMatrix);

        /* Perform a BFS */
        while (!elementQueue.isEmpty()) {
            /* Process only last layer's elements */
            int size = elementQueue.size();
            for (int i = 0; i < size; i++) {
                /* Return true if this is already the final state */
                String curMatrixString = matrixQueue.poll();
                List<String> curPath = pathQueue.poll();
                if (curMatrixString.equals(FINAL_STATE))
                    return curPath;

                /* Retrieve blank's position */
                int[] curElement = elementQueue.poll();
                int x = curElement[0], y = curElement[1];

                /* Search towards four directions */
                for (int k = 0; k < DIRS.length; k++) {
                    /* Check if new blank's position is legal */
                    int xx = x + DIRS[k][0], yy = y + DIRS[k][1];
                    if (xx < 0 || xx >= m || yy < 0 || yy >= n)
                        continue;

                    /* Clone a new matrix and swap the elements on old and new blanks */
                    int[][] newMatrix = decodeFromString(curMatrixString).clone();
                    int temp = newMatrix[x][y];
                    newMatrix[x][y] = newMatrix[xx][yy];
                    newMatrix[xx][yy] = temp;

                    /* Hash the new state to see if this position visited */
                    String newMatrixString = encodeIntoString(newMatrix);
                    if (visited.contains(newMatrixString))
                        continue;

                    /* Inherit and update the path */
                    List<String> newPath = new ArrayList<>(curPath);
                    newPath.add(PATH_WORDS[k]);

                    /* Add new state into queue and mark it visited */
                    matrixQueue.offer(newMatrixString);
                    elementQueue.offer(new int[] { xx, yy });
                    pathQueue.offer(newPath);
                    visited.add(newMatrixString);
                }
            }
        }
        return null;
    }

    /**
     * Encode a matrix into String presentation,
     * which serves as its Hash value.
     */
    private String encodeIntoString(int[][] matrix) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                sb.append(matrix[i][j]).append(",");
            }
        }
        return sb.toString();
    }

    /*
     * Decode a matrix from its String presentation
     */
    private int[][] decodeFromString(String str) {
        String[] nums = str.split(",");
        int[][] res = new int[m][n];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                res[i][j] = Integer.parseInt(nums[i * n + j]);
        return res;
    }
}

