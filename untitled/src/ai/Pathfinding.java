package ai;

import character.NonPlayableCharacter;
import main.GamePanel;

import java.util.ArrayList;

public class Pathfinding {
    GamePanel gp;
    Node[][] nodes;
    ArrayList<Node> openList = new ArrayList<>();
    public ArrayList<Node> pathList = new ArrayList<>();
    Node startNode,goalNode,currentNode;
    boolean goalReached;
    int step = 0;

    public Pathfinding(GamePanel gp) {
        this.gp = gp;
        initNodes();
    }
    public void initNodes() {
        nodes = new Node[gp.maxScreenCol][gp.maxScreenRow];
        int col = 0;
        int row = 0;
        while(col < gp.maxScreenCol && row < gp.maxScreenRow) {
            nodes[col][row] = new Node(col, row);
            col++;
            if(col == gp.maxScreenCol) {
                col = 0;
                row++;
            }
        }
    }
    public void resetNodes() {
        int col = 0;
        int row = 0;
        while(col< gp.maxScreenCol && row<gp.maxScreenRow) {
            nodes[col][row].open = false;
            nodes[col][row].checked = false;
            nodes[col][row].solid = false;
            col++;
            if(col == gp.maxScreenCol) {
                col = 0;
                row++;
            }
        }
        //reset other settings
        openList.clear();
        pathList.clear();
        goalReached = false;
        step = 0;
    }
    public void setNodes(int startCol, int startRow, int goalCol, int goalRow, NonPlayableCharacter entity) {
        resetNodes();
        //setup

        startNode = nodes[startCol][startRow];
        currentNode = startNode;
        goalNode = nodes[goalCol][goalRow];
        openList.add(currentNode);

        int col = 0;
        int row = 0;

        while(col < gp.maxScreenCol && row < gp.maxScreenRow) {
            int titleNum = gp.tileM.mapTileNum[col][row]; //*
            if(gp.tileM.tile[titleNum].collision){
                nodes[col][row].solid = true;
            }

            //set cost
            getCost(nodes[col][row]);

            col++;
            if(col == gp.maxScreenCol){
                col = 0;
                row++;
            }
        }
    }
    public void getCost(Node node){
        //G cost
        int xDistance = Math.abs(node.col - startNode.col);
        int yDistance = Math.abs(node.row - startNode.row);
        node.gCost = xDistance + yDistance;
        //H cost
        xDistance = Math.abs(node.col - startNode.col);
        yDistance = Math.abs(node.row - startNode.row);
        node.hCost = xDistance + yDistance;
        //F cost
        node.fCost = node.gCost + node.hCost;
    }

    public boolean search(){
        while(!goalReached && step < 500){
            int col = currentNode.col;
            int row = currentNode.row;

            //check the current node
            currentNode.checked = true;
            openList.remove(currentNode);

            //open the up node
            if(row - 1 >= 0){
                openNode(nodes[col][row-1]);
            }
            //open the left node
            if(col - 1 >= 0){
                openNode(nodes[col-1][row]);
            }
            //open the down node
            if(row + 1 < gp.maxScreenRow){
                openNode(nodes[col][row+1]);
            }
            //open the right node
            if(col + 1 < gp.maxScreenCol){
                openNode(nodes[col+1][row]);
            }

            //find the best node
            int bestNodeIndex = 0;
            int bestNodefCost = 999;

            for(int i = 0;i<openList.size();i++){
                //check if this node's f cost is better
                if(openList.get(i).fCost < bestNodefCost){
                    bestNodeIndex = i;
                    bestNodefCost = openList.get(i).fCost;
                }
                //if f cost is equal, check the g cost
                else if (openList.get(i).fCost == bestNodefCost) {
                    if(openList.get(i).gCost < openList.get(bestNodeIndex).gCost){
                        bestNodeIndex = i;
                    }
                }
            }
            //if there is no node in the openlist, end the loop
            if(openList.size() == 0){
                break;
            }

            //after the loop, openlist[bestnodeindex] is the next (=currentnode)
            currentNode = openList.get(bestNodeIndex);

            if(currentNode == goalNode){
                goalReached = true;
                trackThePath();
            }
            step++;
        }
        return  goalReached;
    }

    public void openNode(Node node){
        if(!node.open && !node.checked && !node.solid){
            node.open = true;
            node.parent = currentNode;
            openList.add(node);
        }
    }

    public void trackThePath(){
        Node current = goalNode;
        while(current != startNode){
            pathList.add(0,current);
            current = current.parent;
        }
    }
}
