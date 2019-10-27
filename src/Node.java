
public class Node {
    public int NodeId;
    public int Node_X ,Node_Y; //Node Coordinates
    public int demand; //Node Demand if Customer
    public boolean IsRouted;
    private boolean IsDepot; //True if it Depot Node

    public Node(int depot_x,int depot_y) //Cunstructor for depot
    {
        this.NodeId = 0;
        this.Node_X = depot_x;
        this.Node_Y = depot_y;
        this.setIsDepot(true);
    }

    public Node(int id ,int x, int y, int demand) //Cunstructor for Customers
    {
        this.NodeId = id;
        this.Node_X = x;
        this.Node_Y = y;
        this.demand = demand;
        this.IsRouted = false;
        this.setIsDepot(false);
    }

	public boolean isIsDepot() {
		return IsDepot;
	}

	public void setIsDepot(boolean isDepot) {
		IsDepot = isDepot;
	}

}
