import java.util.ArrayList;

public class Main {
    public static void main(String[] args){
        Thread client = new Client();
        Thread node1 = new Node("123.456.789", "1");
        Thread node2 = new Node("432.156.789", "2");
        Thread node3 = new Node("111.111.111", "3");
        Thread node4 = new Node("223.456.789", "4");
        Thread node5 = new Node("323.456.789", "5");
        Thread node6 = new Node("125.556.789", "6");
        Thread node7 = new Node("143.436.789", "7");
        Thread node8 = new Node("123.426.789", "8");

        /*
        node1.setNodes(new Node[] {node2, node8});
        node2.setNodes(new Node[] {node1, node3});
        node3.setNodes(new Node[] {node2, node4});
        node4.setNodes(new Node[] {node3, node5});
        node5.setNodes(new Node[] {node4, node6});
        node6.setNodes(new Node[] {node5, node7});
        node7.setNodes(new Node[] {node6, node8});
        node8.setNodes(new Node[] {node7, node1});
*/

        node1.start();
        node2.start();
        node3.start();
        node4.start();
        node5.start();
        node6.start();
        node7.start();
        node8.start();
        client.start();
    }
}
