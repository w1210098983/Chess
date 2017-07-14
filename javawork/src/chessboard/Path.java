package chessboard;

/**
 * Created by 吴和伟 on 2017/7/7.
 */
public class Path {

    public LinkQueue queue = new LinkQueue();
    private int step = -1;//步数
    private int value;//起始点序号
    private int degree;//起始点度数
    private int[][] map = new int[8][8];//棋盘地图
    private int[] record = new int[64];//路径记录
    private boolean f = false;//找到路径的标识,默认没找到

    int dir[][] = {{-2, -1}, {-2, 1}, {-1, -2}, {-1, 2}, {1, -2}, {1, 2}, {2, -1}, {2, 1}};//马可跳的方向

    public void setValue(int s) {//初始化
        value = s;
        reset();
        degree = getStep(value);
        f = false;
        step = -1;
        reset();
    }

    public void start(int s) {//求解路径并存储在队列中
        if (f) return;//如果找到,结束函数
        int x = s / 8;//起始点坐标x
        int y = s % 8;//起始点坐标y
        boolean degreedec = false;//起始点度数减小的标识,默认不变
        step += 1;//步数+1
        record[step] = s;//记录第step步的节点为s
        int[] children = new int[8];//新建一个该节点下一步的节点数组
        int temp = degreenumber(s);//求度数
        if (degree > temp) degreedec = true;//度数减小
        degree = temp;
        //如果改点不是第64个点,且使度数变为0,将导致马不能回到起点,结束函数并还原更改的全局变量
        if (degree == 0 && step < 63) {
            degree++;
            step--;
            return;
        }

        map[x][y] = step + 1;//再地图上标记改点已走过
        //如果改点为最后一个点,且可以回到起点,将数组中的路径转到队列中,使标识f为true并结束函数
        if (step == 63 && degree == 0) {
            for (int i = 0; i < 64; i++) {
                queue.enQueue(record[i]);
            }
            queue.enQueue(queue.getQueue());
            queue.deQueue();
            f = true;
            return;
        }
        next(x, y, children);
        //按顺序跳到节点数组中没有走过的点并使标识flag为false
        for (int i = 0; i < 8; i++) {
            if (children[i] < 64 && map[children[i] / 8][children[i] % 8] == 0) {
                start(children[i]);//递归调用start函数
                if (f) return;//如果找到路径,结束寻找
            }
        }
        //未找到路径,结束并还原更改
        if (!f) {
            map[x][y] = 0;
            step--;
            if (degreedec) degree++;
            return;
        }
    }

    public int degreenumber(int s) {//起始点的度数,当某一步走过可以回到起始点的节点走过时,度数-1
        boolean cor = false;
        //改点是否能回到起点
        for (int l = 0; l < 8; l++) {
            int m = value / 8 + dir[l][0];
            int n = value % 8 + dir[l][1];
            if (m >= 0 && m < 8 && n >= 0 && n < 8) {
                if (s == m * 8 + n) {
                    cor = true;
                    break;
                }
            }
        }
        if (cor) return degree - 1;//能,度数-1
        else return degree;//不能,度数不变
    }

    public void next(int x, int y, int children[]) {//求下一步的节点,按照j.c.Warnsdorff规则排序并存在数组中
        for (int i = 0; i < 8; i++) children[i] = 64;
        int size = 0;
        //寻找可以走的节点
        for (int i = 0; i < 8; i++) {
            int xi = x + dir[i][0];
            int yi = y + dir[i][1];
            if (xi >= 0 && xi < 8 && yi >= 0 && yi < 8 && map[xi][yi] == 0) {
                children[size] = xi * 8 + yi;
                //对数组中的节点按照j.c.Warnsdorff规则排序
                for (int j = size; j > 0; j--) {
                    if (getStep(children[j - 1]) <= getStep(children[j])) break;
                    int temp = children[j];
                    children[j] = children[j - 1];
                    children[j - 1] = temp;

                }
                size++;
            }
        }
    }

    public int getStep(int s) {//求点s的出度
        int xi, yi;//点(xi,yi)
        int count = 0;//出度数
        // 8个方向的下一步节点(xi,yi)
        for (int i = 0; i < 8; i++) {
            xi = s / 8 + dir[i][0];
            yi = s % 8 + dir[i][1];
            if (xi >= 0 && xi < 8 && yi >= 0 && yi < 8 && map[xi][yi] == 0) {//(xi,yi),在棋盘内,且没走过
                count++;//出度数+1
            }
        }
        return count;
    }

    public void reset() {//重置地图,是地图的每个点都未走过
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                map[i][j] = 0;
            }
        }
    }
}
