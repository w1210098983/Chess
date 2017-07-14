package chessboard;

/**
 * Created by 吴和伟 on 2017/7/7.
 */
public class LinkQueue {//队列类

    private class Node {//节点类
        private Object data;
        private Node next;

        public Node() {
            data = null;
            next = null;
        }

        public Node(Object data, Node next) {
            this.data = data;
            this.next = next;
        }
    }

    private Node rear;//队末
    private Node front;//队头
    private int size = 0;//队列长度

    public LinkQueue() {//初始化
        Node node = new Node();
        front = rear = node;//指向头结点
    }

    public void enQueue(Object value) {//进队
        Node node = new Node(value, null);
        rear.next = node;
        rear = node;
        size++;
    }

    public boolean deQueue() {//出队
        if (rear == front) {
            System.out.println("队列空,无法出队;");
            return false;
        } else {
            Node temp = front.next;
            front.next = temp.next;
            if (temp.next == null) {
                front = rear;
            }
            size--;
            return true;
        }
    }

    public int getSize() {//得到队列长度
        return size;
    }

    public boolean isEmpty() {//判断是否为空
        return rear == front;
    }

    public Object getQueue() {//得到队首元素但不出队
        if (front == rear) {
            System.out.println("队列空,无法取队头!");
            return null;
        }
        return front.next.data;
    }
    public void clear(){
        while (!isEmpty()){
            deQueue();
        }
    }
}
