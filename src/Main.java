import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by diling on 8/13/22.
 */
public class  Main {
    private static final int BOARD_WIDTH = 4;
    private static final int BOARD_HEIGHT = 5;


    public static class Block {
        public int left;
        public int top;
        public int width;
        public int height;
        public int type;
        public Block(int left, int top, int width, int height, int type) {
            this.left = left;
            this.top = top;
            this.width = width;
            this.height = height;
            this.type = type;
        }
        public Block(Block other) {
            this(other.left, other.top, other.width, other.height, other.type);
        }
    }

    public static int klotskiBfs() {
        Queue<Block[]> queue = new LinkedList<>();
        Queue<Integer> depthQueue = new LinkedList<>();

        List<int[][]> visited = new ArrayList<>();

        queue.add(new Block[] {
                new Block(1, 0, 2, 2, 1),
                new Block(0, 0, 1, 2, 2),
                new Block(3, 0, 1, 2, 2),
                new Block(0, 2, 1, 2, 2),
                new Block(3, 2, 1, 2, 2),
                new Block(1, 2, 2, 1, 3),
                new Block(1, 3, 1, 1, 4),
                new Block(2, 3, 1, 1, 4),
                new Block(1, 4, 1, 1, 4),
                new Block(2, 4, 1, 1, 4),
        });
        depthQueue.add(0);


        visited.add(new int[][]{
                {2, 1, 1, 2},
                {2, 1, 1, 2},
                {2, 3, 3, 2},
                {2, 4, 4, 2},
                {0, 4, 4, 0}
        });

        // List<int[]{left, top, width, height, type}>

        while (!queue.isEmpty()) {
            Block[] node = queue.remove();
            int depth = depthQueue.remove();
            System.out.println(String.format("queue size %d, depth = %d", queue.size(), depth));
            if (node[0].left == 1 && node[0].top == 3) {
                return depth;
            }
            int[][] state = new int[BOARD_HEIGHT][BOARD_WIDTH];
            for (Block block : node) {
                for (int i = 0; i < block.height; i++) {
                    for (int j = 0; j < block.width; j++) {
                        if (state[block.top + i][block.left + j] != 0) {
                            System.out.println("Something is wrong");
                        }
                        state[block.top + i][block.left + j] = block.type;
                    }
                }
            }

            // find all neighbors of node, for each neighbor, if it is not
            // visited yet, add it to the queue
            for (int block_index = 0; block_index < node.length; block_index++) {
                Block block = node[block_index];
                // check if block can move left/right/up/down
                if (block.left > 0 && state[block.top][block.left - 1] == 0
                        && (block.height == 1 || state[block.top + 1][block.left - 1] == 0)) {
                    int[][] newState = makeStateCopy(state);
                    for (int i = 0; i < block.height; i++) {
                        newState[i + block.top][block.left - 1] = newState[i + block.top][block.left + block.width - 1];
                        newState[i + block.top][block.left + block.width - 1] = 0;
                    }
                    // go through all visited state, check if newState is already in visited;
                    // it has not been visited yet, put the new blocks in queue, and put
                    // newState in visited.
                    boolean found = isVisited(visited, newState);
                    if (!found) {
                        // add to queue
                        Block[] neighbor = makeBlocksCopy(node);
                        neighbor[block_index].left--;
                        queue.add(neighbor);
                        depthQueue.add(depth+1);
                        visited.add(newState);
                    }

                }

                if (block.top > 0 && state[block.top - 1][block.left] == 0
                        && (block.width == 1 || state[block.top - 1][block.left + 1] == 0)) {
                    int[][] newState = makeStateCopy(state);
                    for (int i = 0; i < block.width; i++) {
                        newState[block.top - 1][i + block.left] = newState[block.top + block.height -1][i + block.left];
                        newState[block.top + block.height - 1][i + block.left] = 0;
                    }
                    // go through all visited state, check if newState is already in visited;
                    // it has not been visited yet, put the new blocks in queue, and put
                    // newState in visited.
                    boolean found = isVisited(visited, newState);
                    if (!found) {
                        // add to queue
                        Block[] neighbor = makeBlocksCopy(node);
                        neighbor[block_index].top--;
                        queue.add(neighbor);
                        depthQueue.add(depth+1);
                        visited.add(newState);
                    }

                }

                if (block.left + block.width < BOARD_WIDTH && state[block.top][block.left + block.width] == 0
                        && (block.height == 1 || state[block.top + 1][block.left + block.width] == 0)) {
                    int[][] newState = makeStateCopy(state);
                    for (int i = 0; i < block.height; i++) {
                        newState[i + block.top][block.left + block.width] = newState[i + block.top][block.left];
                        newState[i + block.top][block.left] = 0;
                    }
                    // go through all visited state, check if newState is already in visited;
                    // it has not been visited yet, put the new blocks in queue, and put
                    // newState in visited.
                    boolean found = isVisited(visited, newState);
                    if (!found) {
                        // add to queue
                        Block[] neighbor = makeBlocksCopy(node);
                        neighbor[block_index].left++;
                        queue.add(neighbor);
                        depthQueue.add(depth+1);
                        visited.add(newState);
                    }
                }


                if (block.top + block.height < BOARD_HEIGHT && state[block.top + block.height][block.left] == 0
                        && (block.width == 1 || state[block.top + block.height][block.left + 1] == 0)) {
                    int[][] newState = makeStateCopy(state);
                    for (int i = 0; i < block.width; i++) {
                        newState[block.top + block.height][i + block.left] = newState[block.top][i + block.left];
                        newState[block.top][i + block.left] = 0;
                    }
                    // go through all visited state, check if newState is already in visited;
                    // it has not been visited yet, put the new blocks in queue, and put
                    // newState in visited.
                    boolean found = isVisited(visited, newState);
                    if (!found) {
                        // add to queue
                        Block[] neighbor = makeBlocksCopy(node);
                        neighbor[block_index].top++;
                        queue.add(neighbor);
                        depthQueue.add(depth+1);
                        visited.add(newState);
                    }
                }
            }
        }

        return Integer.MAX_VALUE;

    }



    private static boolean isVisited(List<int[][]> visited, int[][] newState) {
        for (int[][] visitedState : visited) {
            boolean isSame = true;
            for (int i=0; i<visitedState.length; i++) {
                for (int j = 0; j < visitedState[0].length; j++) {
                    if (visitedState[i][j] != newState[i][j]){
                        isSame = false;
                        break;
                    }
                }
                if (!isSame) {
                    break;
                }
            }
            if (isSame) {
                return true;
            }
        }
        return false;
    }

    public static int[][] makeStateCopy(int[][] a) {
        int[][] b = new int[a.length][a[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                b[i][j] = a[i][j];
            }
        }
        return b;
    }

    public static Block[] makeBlocksCopy(Block[] blocks) {
        Block[] newBlocks = new Block[blocks.length];
        for (int i = 0; i < blocks.length; i++) {
            newBlocks[i] = new Block(blocks[i]);
        }
        return newBlocks;
    }

    public static void main(String[] args) {
        System.out.println("Hello World");
        System.out.println(klotskiBfs());
    }
}
