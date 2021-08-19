package sr.unasat.shortestpath;

import java.util.Scanner;

class DistPar{                     // distance and parent
                                       // items stored in sPath array
        public int distance;           // distance from start to this vertex
        public int parentVert;        // current parent of this vertex

        public DistPar(int pv, int d){       // constructor
            distance = d;
            parentVert = pv;
           
        }
    }                                   // end class DistPar

class Graph {
        private final int MAX_VERTS = 20;
        private final int INFINITY = 1000000;
        private Vertex vertexList[];          // list of vertices
        private int adjMat[][];                 // adjacency matrix
        private int nVerts;                     // current number of vertices
        private int nTree;                        // number of verts in tree
        private DistPar sPath[];               // array for shortest-path data
        private int currentVert;                // current vertex
        private int startToCurrent;            // distance to currentVert


        public Graph(){                        // constructor
            vertexList = new Vertex[MAX_VERTS];    // adjacency matrix
            adjMat = new int[MAX_VERTS][MAX_VERTS];
            nVerts = 0;
            nTree = 0;

            for(int j=0; j<MAX_VERTS; j++)        // set adjacency
                for(int k=0; k<MAX_VERTS; k++)     // matrix
            adjMat[j][k] = INFINITY;              //to infinity
            sPath = new DistPar[MAX_VERTS];        // shortest paths

        }                                     // end constructor

        public void addVertex(String city) {
            vertexList[nVerts++] = new Vertex(city);
        }

        public void addEdge(int start, int end, int weight) {
            adjMat[start][end] = weight;     // (directed)
            
        }

        public void path(){                               // find all shortest paths
            Scanner scan =new Scanner(System.in);
            System.out.println("Enter current location");
            int startTree=scan.nextInt();
                                                                   // start at vertex 0
            vertexList[startTree].isInTree = true;
            nTree = 1;                                 // put it in tree

                                                        // transfer row of distances from adjMat to sPath
            for(int j=0; j<nVerts; j++) {
                int tempDist = adjMat[startTree][j];
                sPath[j] = new DistPar(startTree, tempDist);
                scan.close(); 
                }
                                                            // until all vertices are in the tree
            while(nTree < nVerts) {
                int indexMax = getMax();                  // get minimum from sPath
                int maxDist = sPath[indexMax].distance;
                if(maxDist == INFINITY)                   // if all infinite
                    {                                     // or in tree,
                        System.out.println("There are unreachable vertices");
                    break;                                    // sPath is complete
                    }
//                else
//
////                {                                              // reset currentVert
//                    currentVert = indexMax;                  // to closest vert
//                    startToCurrent = sPath[indexMax].distance;
//                                                                     // minimum distance from startTree is
//                                                                     // to currentVert, and is startToCurrent
//                }
//                                                                       // put current vertex in tree
//                vertexList[currentVert].isInTree = true;
//                nTree++;
//                adjust_sPath();                             // update sPath[] array
            }                                               // end while(nTree<nVerts)

            displayPaths();                                // display sPath[] contents
            nTree = 0;                                     // clear tree
            for(int j=0; j<nVerts; j++)
                vertexList[j].isInTree = false;
           
        }                                                 // end path()

        public int getMax() {                                // get entry from sPath with minimum distance
            int maxDist = INFINITY;                        // assume minimum
            int indexMax = 0;

//            for(int j=1; j<nVerts; j++){                      // for each vertex, if its in tree and smaller than old one
//
//                if( !vertexList[j].isInTree && sPath[j].distance <maxDist ){
//                    maxDist = sPath[j].distance;
//                    indexMax = j;                        // update minimum
//                }
//            }                                             // end for
            return indexMax;                            // return index of minimum

        }                                           // end getMin()

        public void adjust_sPath() {              // adjust values in shortest-path array sPath
            int column = 1;                           // skip starting vertex
            while(column < nVerts)                     // go across columns

            {
                                                       // if this column’s vertex already in tree, skip it
                if( vertexList[column].isInTree )
                {
                    column++;
                    continue;
                }
                                                                     // calculate distance for one sPath entry
                                                                        // get edge from currentVert to column
                int currentToFringe = adjMat[currentVert][column];
                                                                      // add distance from start
                int startToFringe = startToCurrent + currentToFringe;
                                                                      // get distance of current sPath entry
//                int sPathDist = sPath[column].distance;
                                                                       // compare distance from start with sPath entry
//                if(startToFringe < sPathDist)                         // if shorter,
                	
                {
                                                                       // update sPath
                    sPath[column].parentVert = currentVert;
                    sPath[column].distance = startToFringe;
                }
                column++;
            }                                                    // end while(column < nVerts)
        }                                                        // end adjust_sPath()

        public void displayPaths() {
            for(int j=0; j<nVerts; j++)                                 // display contents of sPath[]
            {
                System.out.print(vertexList[j].stad + "=");           // B=
                if(sPath[j].distance == INFINITY)
                    System.out.print("inf");                                    // inf

                else
                    System.out.print(sPath[j].distance);                     // 50

                String parent = vertexList[ sPath[j].parentVert ].stad;
                System.out.print(" (" + parent + "), ");                           // (A)

            }
            System.out.println("");
        }

    }          // end class Graph

