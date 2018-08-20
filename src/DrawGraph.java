import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import javax.swing.*;
import java.util.Random;
import java.util.Scanner;



public class DrawGraph {


	private int Vr ; // number of rows
	private int Vc ; // number of columns
	Scanner cin = new Scanner (System.in);

	public void setVr (int n){ this.Vr = n; }
	public void setVc (int n){ this.Vc = n; }

	public int getVr (){ return this.Vr ; }
	public int getVc (){ return this.Vc ; }


	void writeArray (int graph[][],int Vr,int Vc){

		for (int i = 0 ; i < Vr ; i++){
			for (int j = 0 ; j<Vc;j++){
				System.out.print("Enter item [" +  i + "][" + j + "]: " );
				graph[i][j]=cin.nextInt();
			}
		}
	}


	// print the vertices matrix
	void readArray (int graph[][]){
		for (int i = 0 ; i < Vr ; i++){
			System.out.println();
			for (int j = 0 ; j<Vc;j++){
				System.out.print(graph[i][j] + "\t");
			}
		}
		System.out.println("\n");
	}


	// generate the vertices matrix randomly
	void writeRandomArray (int graph[][],int startRange, int endRange){

		Random addEdge = new Random();	// this specify whether add edge or not
		for (int i = 0 ; i < Vr ; i++){
			for (int j = i ; j<Vc;j++){
				// check if loop edge or there is no edge, then add the weight 0
				if (i==j  || Math.round(addEdge.nextDouble()) == 0)
					graph[i][j]= 0;
				else{
					int weight 	= showRandomInteger(startRange,endRange); // get random integer
					graph[i][j]	= weight;
					graph[j][i] = weight;
				}
			}
		}
	}


	// function return a random integer
	private static int showRandomInteger(int aStart, int aEnd ){
		if (aStart > aEnd) {
			throw new IllegalArgumentException("Start cannot exceed End.");
		}
		Random random = new Random();	// this specify the weight of edge if there is

		//get the range, casting to long to avoid overflow problems
		long range = (long)aEnd - (long)aStart + 1;
		// compute a fraction of the range, 0 <= frac < range
		long fraction = (long)(range * random.nextDouble());
		int randomNumber =  (int)(fraction + aStart);
		return randomNumber;
	}


	int minKey(int key[], boolean mstArr[], int numVertices)
	{

		int min = Integer.MAX_VALUE, min_index=-1;   // INT_MAX means the biggest number in integer

		for (int i = 0; i < numVertices ; i++){
			if (mstArr[i] == false && key[i] < min){
				min = key[i];
				min_index = i;
			}
		}
		return min_index;
	}


	void print(int parent[],  int key[])
	{
		int costMST = 0 ;
		System.out.println("Source"+ "\t\t" + "destination" + "\t\t" + "Weight");
		for (int i = 1; i < Vc; i++){
			System.out.println(parent[i]+ "     \t\t" + i +"           \t\t" +/*graph[i][parent[i]]*/ key[i]);
			costMST+= key[i];
		}
		System.out.println("the cost of spanning tree is : " + costMST );
	}


	void primsAlgorithm (int graph [][],int parent [] , int key [] )
	{
		// this array represents parent every vertext or we can say it will represent our spanning tree
		boolean mstArr[]= new boolean [Vc];  // if the value true that means the vertex was choosen but if it is false that means the vertex was not choosen

		for (int i = 0 ; i < Vc ; i++)  // to set infinite value to all kays (vertices)
		{
			key[i]= Integer.MAX_VALUE;
			mstArr[i]= false ;
		}

		key[0]=0;       // this is a start node
		parent[0]=-1;  // the start node should be the root

		for (int i = 0 ; i < Vc-1 ; i++ ){

			int min = minKey(key,mstArr,Vc);
			mstArr[min] = true ; //means the vertex is chosen

			for (int ver = 0 ; ver < Vc ; ver++){

				if (graph[min][ver] != 0 && mstArr[ver]== false && graph [min][ver] < key[ver] ){
					parent[ver]  = min;
					key[ver] = graph[min][ver];
				}
				//graph[min][ver] != 0  means this is an edge between the vertices
				//mstArr[ver]== false   means the vertex was not choosen
				// graph [min][ver] < key[ver]  means
			}
		}
	}


	// function display window that draw the graph
	// it take the graph vertices matrix and number of vertices
	public static void Draw_Graph(int[][] ver_matrix , int numV)
	{
		JFrame frame = new JFrame();

		// create object of graph, used to store all information of graph inside it
		mxGraph graph = new mxGraph();
		Object parent = graph.getDefaultParent();

		// store objects of all vertices
		Object[] vertices = new Object[numV];

		// information used to specify a set of points in the circumference, each vertex in one point
		int r = numV * 20; 					// the radius

		// start updating the graph
		graph.getModel().beginUpdate();

		try
		{
			// for each vertex, gather the information and insert it into the graph
			for(int i = 0 ; i < numV ; i++){
				double x = r + Math.cos( 2 * Math.PI / numV * i ) * r; // calculate the x-axes
				double y = r + Math.sin( 2 * Math.PI / numV * i ) * r; // calculate the y-axes
				// insert vertex in graph:
				// vertex name: "V" + i
				// vertex point: (x , y)
				// vertex size: (40,40) height and width
				// vertex style: ellipse
				vertices[i] = graph.insertVertex(parent, null, "v" + i , x , y, 40, 40 , "shape=ellipse");
			}

			// in vertices matrix if there is edge, insert it into the graph
			for(int i = 0 ; i < numV ; i++)
				for(int j = i ; j < numV ; j++)
					if(ver_matrix[i][j] > 0)
						// edge weight: ver_matrix[i][j]
						// incident vertices: ( vertices[i], vertices[j] )
						// undirected edge: endArrow=none
						graph.insertEdge(parent, null, ver_matrix[i][j] , vertices[i], vertices[j] , "endArrow=none");

		}

		finally
		{
			// end updating the graph
			graph.getModel().endUpdate();
		}

		// show the graph
		mxGraphComponent graphComponent = new mxGraphComponent(graph);
		graphComponent.setEnabled(false); 	// make graph not editable
		frame.getContentPane().add(graphComponent);


		// set the window settings
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setSize(500, 500);
		frame.setTitle("Graph");// window title
		frame.setVisible(true);
	}


	// function that draw a Minimum spanning tree
	public static void Draw_MSP(int parent[] , int numV , int key[] ){

		for(int i = 0 ; i < numV ; i++)
			System.out.println( parent[i] + " : " + key[i] + " , ");

		JFrame frame = new JFrame();

		// create object of graph, used to store all information of graph inside it
		mxGraph graph = new mxGraph();
		Object p = graph.getDefaultParent();

		// store objects of all vertices
		Object[] vertices = new Object[numV];

		// information used to specify a set of points in the circumference, each vertex in one point
		int r = numV * 20; // the radius

		// start updating the graph
		graph.getModel().beginUpdate();

		try
		{
			// for each vertex, gather the information and insert it into the graph
			for(int i = 0 ; i < numV ; i++){
				double x = r + Math.cos( 2 * Math.PI / numV * i ) * r; // calculate the x-axes
				double y = r + Math.sin( 2 * Math.PI / numV * i ) * r; // calculate the y-axes

				// insert vertex in graph:
				// vertex name: "V" + i
				// vertex point: (x , y)
				// vertex size: (40,40) height and width
				// vertex style: ellipse
				String option = (i == 0) ? "shape=ellipse;fillColor=blue": "shape=ellipse;fillColor=white";
				vertices[i] = graph.insertVertex(p, null, "v" + i , x , y, 40, 40 , option );
			}

			// in vertices matrix if there is edge, insert it into the graph
			for(int i = 1 ; i < numV ; i++){
				// edge weight: ver_matrix[i][j]
				// incident vertices: ( vertices[i], vertices[j] )
				// undirected edge: endArrow=none
				graph.insertEdge(p, null, key[i] , vertices[ i ], vertices[ parent[i] ] , "endArrow=none");
			}
		}

		finally
		{
			// end updating the graph
			graph.getModel().endUpdate();
		}

		// show the graph
		mxGraphComponent graphComponent = new mxGraphComponent(graph);
		graphComponent.setEnabled(false); 	// make graph not editable
		frame.getContentPane().add(graphComponent);

		// set the window settings
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setSize(500, 500);
		frame.setTitle("Graph");// window title
		frame.setVisible(true);

	}


	// return the average cost for each node to reach the root
	public static double getAverageCost(int[] parent , int numV , int[] key){
		double totalCost = 0;

		// for each non-root vertices, compute the cost to reach the root, and take the average
		for(int i = 1 ; i < numV ; i++){

			int currentCost   = key[i];		// get the cost to reach current parent
			int currentParent = parent[i];	// specifiy which is the current parent

			// while the current parent no the root then get the parent of current parent as parent, and add the cost
			while(currentParent != 0 ){
				currentCost += key[currentParent];
				currentParent = parent[currentParent];
			}
			// add the cost to reach the root from current node i to total cost
			totalCost += currentCost;
		}

		double averageCost = totalCost / numV; // get the average of cost

		return averageCost;
	}


	public static void main(String[] args) {

		DrawGraph MST = new DrawGraph();
		int startRange, endRange,numVertices,counterAtteps=0 ;

		System.out.print("\n\nHow many vertex do you want :");
		numVertices=MST.cin.nextInt();


		while (numVertices <= 0 ){
			if (counterAtteps==2){
				System.out.println("you were not responsive and we want to end the program\n" + "Bye Bye -_-");
				return ;
			}

			System.out.print("Sorry, the number of vertex must be grater than zero please re-enter the number of vertex : ");
			numVertices=MST.cin.nextInt();
			counterAtteps++;
		}

		counterAtteps=0;
		MST.Vc=numVertices;
		MST.Vr=numVertices;

		int parent[] = new int [MST.Vc];
		int key[] = new int [MST.Vc];
		int graph[][] = new int [MST.Vc][MST.Vc];

		System.out.print("Enter your range witch you want\n" + "Start range : ");
		startRange = MST.cin.nextInt();
		//startRange = start_costs;
		System.out.print( "End range : ");
		endRange = MST.cin.nextInt();
		//endRange = end_costs;

		while (startRange > endRange  ){
			if (counterAtteps==2){
				System.out.println("you were not responsive and we want to end the program\n" + "Bye Bye -_-");
				return ;
			}

			System.out.println("Sorry, the range should be progressive from smaller to grater: ");
			System.out.print("Enter your range which you want\n" + "start range : ");
			startRange = MST.cin.nextInt();
			System.out.print( "End range : ");
			endRange = MST.cin.nextInt();
			counterAtteps++;
		}

		System.out.println("\t  "+ "the input graph" );
		MST.writeRandomArray(graph,startRange, endRange);
		// MST.writeArray(graph, 9, 9);
		MST.readArray(graph);
		MST.primsAlgorithm(graph, parent , key);


		MST.print(parent,key);

		Draw_Graph(graph,MST.Vc);
		Draw_MSP(parent , MST.Vc , key );

	}

}