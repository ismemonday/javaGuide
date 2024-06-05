package org.mgd.pathFind;

import java.util.*;

/**
 * @Author maoguidong
 * @Date 2024/5/13
 * @Des
 */
public class Dijkstra {
    private static final int INF = Integer.MAX_VALUE;

    public static void main(String[] args) {
        int[][] graph = {
                {0, 10, INF, INF, INF, 3},
                {INF, 0, 7, 5, INF, 3},
                {INF, INF, 0, INF, INF, INF},
                {3, INF, 4, 0, 7, INF},
                {INF, INF, INF, INF, 0, INF},
                {INF, 2, INF, 6, 1, 0},
        };
        int[][] graph2 = {
                {0, 12, INF, INF, INF, 16, 14},
                {12, 0, 10, INF, INF, 7, INF},
                {INF, 10, 0, 3, 5, 6, INF},
                {INF, INF, 3, 0, 4, INF, INF},
                {INF, INF, 5, 4, 0, 2, 8},
                {16, 7, 6, INF, 2, 0, 9},
                {14, INF, INF, INF, 8, 9, 0},
        };
        //基于优先队列
        //forProirityQueue(graph, 0);
        //基于集合与排序的实现
        forArraySort(graph2, 3);
        forArraySortOptimizing(graph2, 3);
    }

    /**
     * 基于集合与排序的优化
     * costTable
     * 【
     * 0{1，1，1，1，1}，//各点位标识
     * 1{2，2，2，2，2}，//到各点的最小路径
     * 2{0，0，0，0，0}，//标识是否已经找到最短路径
     * 3{1，1，1，1，1} //上点位标识
     * 】
     *
     * @param graph2
     * @param i
     */
    private static void forArraySortOptimizing(int[][] graph2, int i) {
        //初始化cstTable
        int[][] costTable = new int[4][graph2.length];
        for (int i1 = 0; i1 < graph2[i].length; i1++) {
            costTable[0][i1] = i1;
            costTable[1][i1] = graph2[i][i1];
            costTable[2][i1] = 0;
            costTable[3][i1] = i;
        }
        //找出起点到所有点的路径消耗
        //最近确认的点的标识
        int lastActIndex = i;
        while (!allPassed(costTable)) {
            //更新经过最近确认的路径中的点的代价值
            for (int i1 = 0; i1 < costTable[1].length; i1++) {
                if (costTable[2][i1] == 0) {
                    //原点到经过点的cost+经过点到当前点的cost<原点到当前点的cost
                    if (costTable[1][i1] == 0 || (costTable[1][lastActIndex] + graph2[lastActIndex][i1] >= 0 && costTable[1][lastActIndex] + graph2[lastActIndex][i1] < costTable[1][i1])) {
                        costTable[1][i1] = costTable[1][lastActIndex] + graph2[lastActIndex][i1];
                        costTable[3][i1] = lastActIndex;
                    }
                }
            }
            //从未确认的路径中找出最短的路径
            int unPassedMinIndex = -1;
            int unPassedMin = INF;
            for (int i1 = 0; i1 < costTable[1].length; i1++) {
                if (costTable[2][i1] == 0) {
                    //从还没确认最短路径中找出最短路径
                    if (costTable[1][i1] < unPassedMin) {
                        unPassedMin = costTable[1][i1];
                        unPassedMinIndex = i1;
                    }
                }
            }
            //标记最短路径
            costTable[2][unPassedMinIndex] = 1;
            lastActIndex = unPassedMinIndex;
        }
        //逆推出起点到指定点的路径
        System.out.println("逆推出起点到指定点的路径");
        for (int i1 : costTable[0]) {
            System.out.println("点" + i + "到点" + i1 + "的最短距离消耗:" + costTable[1][i1] + "具体路径为:" + getPath(costTable, i1, i));
        }

    }

    private static String getPath(int[][] costTable, int curIndex, int start) {
        ArrayList<Integer> integers = new ArrayList<>();
        int index = curIndex;
        while (costTable[3][index] != start) {
            integers.add(index);
            index = costTable[3][index];
        }
        integers.add(index);
        integers.add(start);
        StringBuilder result = new StringBuilder();
        for (int i = integers.size() - 1; i >= 0; i--) {
            result.append(integers.get(i));
            if (i != 0) {
                result.append("=>");
            }
        }
        return result.toString();
    }

    private static boolean allPassed(int[][] costTable) {
        for (int i : costTable[2]) {
            if (i == 0) {
                return false;
            }
        }
        return true;
    }

    private static void forArraySort(int[][] graph, int startIndex) {

        List<Integer> unPassedIndex = new ArrayList<>();
        List<Integer> passedIndex = new ArrayList<>();
        passedIndex.add(startIndex);
        for (int i = 0; i < graph.length; i++) {
            if (startIndex == i) {
                continue;
            }
            unPassedIndex.add(i);
        }
        int[][] cost = new int[graph.length][graph.length];
        for (int i = 0; i < graph.length; i++) {
            cost[0][i] = graph[startIndex][i];
            cost[1][i] = i;
            cost[graph.length - 1][i] = startIndex;
        }

        while (!unPassedIndex.isEmpty()) {
            //更新cost表
            Integer lastPasseIndex = passedIndex.get(passedIndex.size() - 1);
            int lastCostMin = cost[0][lastPasseIndex];
            for (Integer index : unPassedIndex) {
                if (lastCostMin + graph[lastPasseIndex][index] > 0 && cost[0][index] > lastCostMin + graph[lastPasseIndex][index]) {
                    cost[0][index] = lastCostMin + graph[lastPasseIndex][index];
                    //看lastPasseIndex是否有前置点

                    int startNextIndex = 2;
                    cost[startNextIndex][index] = lastPasseIndex;
                    while (cost[startNextIndex][lastPasseIndex] != 0) {
                        cost[startNextIndex + 1][index] = cost[startNextIndex][lastPasseIndex];
                        startNextIndex++;
                    }
                    if (cost[2][cost[startNextIndex][index]] != 0) {

                    }

                }
            }
            //找出未过的节点中，cost最小的节点并放置到已经经过的队列中
            int costMin = INF;
            int costMinIndex = 0;
            for (Integer index : unPassedIndex) {
                if (cost[0][index] < costMin) {
                    costMin = cost[0][index];
                    costMinIndex = index;
                }
            }
            boolean removed = unPassedIndex.remove(Integer.valueOf(costMinIndex));
            passedIndex.add(costMinIndex);
            cost[0][costMinIndex] = costMin;
        }

        for (int i = 0; i < cost.length; i++) {
            System.out.println("点：" + (startIndex) + "到点：" + (i) + "的最短路径为:" + cost[0][i] + "最短路径系列为：" + parseShort(cost, i));
        }
    }

    private static String parseShort(int[][] cost, int i) {

        StringBuilder builder = new StringBuilder();
        for (int j = cost[0].length - 1; j > 1; j--) {
            if (cost[j][i] != 0) {
                builder.append(cost[j][i]);
                builder.append(",");
            }
        }
        builder.append(cost[1][i]);
        return builder.toString();
    }
}
