# Prims-algorithm
In computer science, Prim's algorithm is a greedy algorithm that finds a minimum spanning tree 
for a weighted undirected graph. This means it finds a subset of the edges that forms a tree that includes every vertex, 
where the total weight of all the edges in the tree is minimized. 
The algorithm operates by building this tree one vertex at a time, from an arbitrary starting vertex, 
at each step adding the cheapest possible connection from the tree to another vertex. [wikipedia](https://en.wikipedia.org/wiki/Prim%27s_algorithm)

## Prerequisites
```java
Java
```

## Table of Contents :
  - [How this program works](#how-this-program-works?)
  - [How methods work](#how-methods-work)
  - [Example](#example)

#
## How this program works?
At first, the program will ask the user how many vertices he want. 
Then, the user adds the number vertices and the program will check the number is correct or not we mean it is not correct if it is zero or negative number, 
after that, the program asks the user to add a range (start range – end range) and the program should also check the rage. 
It should be correct. 
End range should be greater than start range. 
Next, the program generates random numbers and adds them to two-dimensional array which is matrix vertices by writeRnadomArray method. 
After that, the program will apply PrimsAlgorithm to create a minimum spanning tree on the vertices matrix.
Finally, the program will show two windows the first one for input graph and the second on for output graph. 
These graphs are drawn by two methods Draw_Graph and Draw_MSP.

#
### How methods work:

#### 1- `writeRandomArray` :
```java
void writeRandomArray (int graph[][],int startRange, int endRange)
```
This method has three parameters which are `matrix vertices` , `start rang` and
`end rang` .

It adds `0`s for the main diagonal and random numbers for remaining indices. 
I used a formula to generate random numbers between two numbers, whitch is:
```java
long range = (long) EndRange - (long) StartRange + 1
long fraction = (long)(range * random.nextDouble());
int randomNumber = (int)(fraction + aStart)
```



#### 2- `primsAlgorithm` :
```java
void primsAlgorithm (int graph [][],int parent [] , int key [] )
```
This method uses four arrays, 
three of them are one dimensional array they are called `parent`, 
`key` and `mstArr`. Last array is called `graph`, 
it is two-dimensional array and I need it to draw the graph. 
The parent array represents a parent of every vertex. 
The index of parent array is number of vertex and the value of index is a vertex’s parent for example, 
I have three vertices so, the parent array will be numbered from 0 to 2 first index is vertex 0, 
second index is vertex 1, and last index is vertex 2. 
The first index should contain any negative number let’s say -1 because it will be the root of the minimum spanning tree, 
second index will contain either 0 or 2 let’s say 0 that means that vertex 1 links with vertex 0, 
last index will contain either 0 or 1 let’s say 1 that means that vertex 2 links with vertex 1. 
So, the spanning tree will be:


![alt text](https://i.imgur.com/kIY1yfz.png)


The benefit of the parent array is just explaining for us how the spanning will be. 
Now we can move to another array which is key array. 
This array contains weight. The first index should contain 0 because it is a start point, 
the second index let’s say contain 5 and the last index contains 2. 
Now we can draw our spanning by the parent array and the key array:
![alt text](https://i.imgur.com/cWIFwEp.png)


Now, we can move to the last array which is called `mstArr`, This array is Boolean array. 
The benefit of this array is telling us if the vertex has been chosen or not. 
For example, we take the first vertex and we knew where it can go we should change his index’s value 
to true to tell us we have checked where it can move then we will choose the second index it will 
get the first index is true that tell us we have checked, and we know it links with.


Last thing I should explain is the choosing conditional which is:
```java
if (graph[min][ver] != 0 && mstArr[ver]== false && graph [min][ver] < key[ver] )
{
 parent[ver]  = min;
 key[ver] = graph[min][ver];
}
```

Where:
`ver`: current vertex.
`min`: the minimum value in the kay array.

Let’s say `ver = 2` and `min = 5` and we are at vertex `1` and we want where we should go. 
the first condition means the vertex which is now vertex `1` have a link with vertex `2` if the value of `graph[min][ver]` 
is `0` that means there is no link if any positive number that means there is a link between vertex `1` and vertex `2`. 
Now the second condition `mstArr[ver] == false` this means did we choose vertex `0` or not because if there 
is a link between vertex `0` and vertex `2` but the value of vertex `2` in `mstArr` is true that mean we know its 
parent we do not need to change it. Now the last condition
`[min][ver] < key[ver]` 	
If the first and second condition are true we should check its weight.


#### 3- `Draw_Graph` :
This method has two parameters they are matrix and number of vertices. It is a responsible for drawing input graph. 
I use `JGraph` to draw a graph and I store vertices in insertVertex then I add edges in insertEdge.

#### 4- `Draw_MST` :
This method has three parameters. 
they are the key array, the parent, the number of vertices. 
I need the key and the parent array to draw a minimum spanning tree because the key array 
contains weight and the parent array contains a parent every vertex. I also use `JGraph` to 
draw a graph and I store vertices in insertVertex then I add edges in insertEdge.

#### 5- `minKey` :
This method has three parameters. 
They are the key array, the msrArr array and the number of vertices. 
The benefit of this method is to determine what vertex which is its turn to determine 
its children or we can say where the vertex can move to.

#
### Example:
We have `6` vertices and the range is `10-60` :

![alt text](https://i.imgur.com/Xowth9g.png)

![alt text](https://i.imgur.com/LGKorMK.png)


#

### Another example :

#### Number of vertices: 20, random generated edges weight: [5 - 20]:
Generated vertices matrix: 

![alt text](https://i.imgur.com/ZjMSCsv.png)


#### Input graph:

![alt text](https://i.imgur.com/WgxPzDR.png)


#### Result: Minimum spanning tree:

![alt text](https://i.imgur.com/IIVGzA5.png)


#
## Author

Abdullah Alhaider, 
<br />
cs.alhaider@gmail.com
<br />
[X](https://x.com/cs4alhaider)





