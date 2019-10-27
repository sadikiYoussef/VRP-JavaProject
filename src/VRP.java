import java.util.ArrayList;
import java.util.List;
import java.util.Random;




public class VRP {


    //Problem Parameters
    public static int NoOfCustomers = 9;
    public static int NoOfVehicles = 20;
    public static int VehicleCap = 20;
	private static Random ran;
	public static Node[] Nodes = new Node[NoOfCustomers + 1];
	public static double[][] distanceMatrix = new double[NoOfCustomers + 1][NoOfCustomers + 1];
	public static Solution[]population =  new Solution[10];

	public static void main(String[] args) {

        setRan(new Random());


        //Depot Coordinates
        int Depot_x = 500;
        int Depot_y = 330;


        //Initialise
        //Create Random Customers
        Node depot = new Node(Depot_x, Depot_y);

        Nodes[0] = depot;
 
        Nodes[1] = new Node(1,200,100,10);
        Nodes[2] = new Node(2,500,50,7);
        Nodes[3] = new Node(3,900,100,8);
        Nodes[4] = new Node(4,1000,330,12);
        Nodes[5] = new Node(5,900,600,5);
        Nodes[6] = new Node(6,500,660,6);
        Nodes[7] = new Node(7,100,600,9);
        Nodes[8] = new Node(8,50,330,19);
        Nodes[9] = new Node(9,100,200,3);
      /**  Nodes[10] = new Node(10,300,300,2);
        Nodes[11] = new Node(11,300,400,4);
        Nodes[12] = new Node(12,300,500,10);
        Nodes[13] = new Node(13,300,600,11);
        Nodes[14] = new Node(14,600,0,7);
        Nodes[15] = new Node(15,600,600,6);**/

        double Delta_x, Delta_y;
        for (int i = 0; i <= NoOfCustomers; i++) {
            for (int j = i + 1; j <= NoOfCustomers; j++){ //The table is summetric to the first diagonal
                                                  //Use this to compute distances in O(n/2)
            	Delta_x = (Nodes[i].Node_X - Nodes[j].Node_X);
                Delta_y = (Nodes[i].Node_Y - Nodes[j].Node_Y);

                double distance = Math.sqrt((Delta_x * Delta_x) + (Delta_y * Delta_y));

                distance = Math.round(distance);                //Distance is Casted in Integer
                //distance = Math.round(distance*100.0)/100.0; //Distance in double

                distanceMatrix[i][j] = distance;
                distanceMatrix[j][i] = distance;
            }
        }
        int printMatrix = 0; //If we want to print diastance matrix

        if (printMatrix == 1){printMatrix = 0;
            for (int i = 0; i <= NoOfCustomers; i++) {
                for (int j = 0; j <= NoOfCustomers; j++) {
                    System.out.print(distanceMatrix[i][j] + "  ");
                }
                System.out.println();
            }
        }

        //Compute the greedy Solution
        System.out.println("Attempting to resolve Vehicle Routing Problem (VRP) for "+NoOfCustomers+
                " Customers and "+NoOfVehicles+" Vehicles"+" with "+VehicleCap + " units of capacity\n");

    // générer une population d'individu de taille 10
    	// population initiale
        Fenetre fenfen;
        for (int i = 0; i < 10; i++) {
			
			Solution slt = new Solution(NoOfCustomers, NoOfVehicles, VehicleCap);
	        slt.GreedySolution(Nodes, distanceMatrix);
			population[i] = slt;
	//		 fenfen = new Fenetre(slt);
//			Fenetre fenfen = new Fenetre(slt);
			
		//	slt.SolutionPrint("Greedy Solution "+i);	
		}
		for (int i = 0; i <1; i++) {
            System.out.println("\n**************** Iteration :" + (i + 1) + "***********");
       /*    for(int j = 0; j < 10; j++) {
      //      	System.out.println(a);
        //	   population[j].SolutionPrint("Greedy Solution");
       //     	System.out.println(population[j].SolRealis);
            }*/
            testAG(population);
            showMeilleurInd(population);
        }
    }
	
	
	
	// fonction testant si l'individu est réalisable
    public static boolean testRealisable(Solution ind) {
    	if ((int)ind.SolRealis.get(0) != 0 )return false;
    	
    	int inds_loc = 1;
    	int inds_vihc = 0;
    	while(inds_vihc < ind.SolRealis.size()) {
	    	
	    	int nod_loc =(int) ind.SolRealis.get(inds_loc) ;
	    	int caps_loc = 0;
	    	while(nod_loc != 0) {
	    		caps_loc = caps_loc + Nodes[nod_loc].demand;
	    		inds_loc ++ ;
	    		nod_loc =(int) ind.SolRealis.get(inds_loc) ;
	    	}inds_loc ++ ;
	    	if(caps_loc > VehicleCap) return false;	/// depass capasss
	    	
	    	inds_vihc++;
	    }if (inds_vihc < ind.SolRealis.size())return false;
    	
	    if(ind.SolRealis.size() == NoOfCustomers + inds_vihc-2)return false; /// nomber des node qui pass
	    List<Object> NodeRepet = new ArrayList<>();
	    for (int i = 0;i<ind.SolRealis.size();i++) {
	    	if(NodeRepet.contains(ind.SolRealis.get(i))){
	    		if((int)ind.SolRealis.get(i) != 0 ) return false;
	    	}
	    }
	     
	    	return true;
    }
	
	// afficher l'individu avec la meilleur fitness qui correspond au coût de
    // pénalité le plus bas
    public static int showMeilleurInd(Solution[] population) {
      	double min = 0;    	
    	min = population[0].Cost;   
        int j = 0;
        for (int i = 1; i < population.length; i++) {
            if (population[i].Cost < min) {
                min = population[i].Cost;
                j = i;
            }
        }
        System.out.println("Meilleur Individu: fitness = " + min+"\t"+j);
        population[j].SolutionPrint("");
        Fenetre fenfen = new Fenetre(population[j]);
     //   System.out.println();
        return j;
    }
    
    // fonction qui retourne l'indice d'un individu aléatoire de la population
	public static int returnIndice(Solution[] population) {
		int x = new Random().nextInt(population.length);
		return x;
    }
	

	
    // retourner un tableau contenant les indices des individus qui subirons un
    // croisement
    public static int[] setCroisementIndices(Solution[] population, float p) {
        int a = Math.round(population.length * p);
        int[] tab = new int[a];
        for (int i = 0; i < a; i++) {
            tab[i] = i;
        }

        return tab;
    }
    
    // retourner un tableau contenant les indices des individus qui subirons une
    // mutation
    public static int[] setMutationIndices(Solution[] population, int b) {
        int a = population.length - b;
        int[] tab = new int[a];
        for (int j = 0, i = population.length - 1; i >= b; j++, i--) {
            tab[j] = i;
        }
        return tab;
    }
    
 // le croisement de deux individus passer en paramétres; et ce en deux point
    // k et l
    public static Solution[] croisement(Solution ind1, Solution ind2, int k, int l) {
    	
    	Solution[] enfants;
    	enfants = new Solution[2];
    	enfants[0] = ind1;
        enfants[1] = ind2;
       
        for (int j = k; j < l; j++) {
        	int indsvihc=0;
        	while (enfants[0].Vehicles[indsvihc].Route.contains(ind2.SolRealis.get(j))) {
        		indsvihc ++;
        	}
        	enfants[0].Vehicles[j].Route.remove(j);
        	enfants[0].SolRealis.remove(j);
        	
        	enfants[0].Vehicles[j].Route.add(j,ind2.Vehicles[j].Route.get(j)) ;
        	enfants[0].SolRealis.add(j,ind2.SolRealis.get(j)) ;
        	
        	enfants[1].Vehicles[j].Route.remove(j);
        	enfants[1].SolRealis.remove(j);
        	
        	enfants[1].SolRealis.add(j,ind1.SolRealis.get(j)) ;
        	enfants[1].SolRealis.add(j,ind1.SolRealis.get(j)) ;
        }
        
        return enfants;
    }
    
    // la mutaion de l'individu passé en paramétre; une mutation aléatoire d'un
    // géne aléatoirement choisi et remplacer par une valeur alétoire respectent
    // l'interval donné
    public static Solution mutation(Solution ind1) {
    	int b=0;int a=0; int inds1 = 0;int inds2 = 0;
    	while((a!=0)&&(b!=0)) {
	        inds1 = new Random().nextInt(ind1.SolRealis.size());
	        inds2 = new Random().nextInt(ind1.SolRealis.size());
	        a = (int) ind1.SolRealis.get(inds1);
	        b = (int) ind1.SolRealis.get(inds2);
    	}
    //	ind1.SolRealis.remove(inds1);
    	ind1.SolRealis.set(inds1, b);
    //	ind1.SolRealis.remove(inds2);
    	ind1.SolRealis.set(inds2, a);
        return ind1;
    }
    
     // fonction qui génére la nouvelle population
    private static void testAG(Solution[] population) {
    	Solution[] enfants = new Solution[2];
        int[] tableCroisement = setCroisementIndices(population, 0.7f);
        int[] tableMutation = setMutationIndices(population, tableCroisement.length);

        // si le nombre des individus à croiser est paire
        if (tableCroisement.length % 2 == 0) {
            for (int j = 0; j < tableCroisement.length; j = j + 2) {
                enfants = croisement(population[tableCroisement[j]], population[tableCroisement[j + 1]], 3, 7);
                if (testRealisable(enfants[0])) {
                    population[tableCroisement[j]] = enfants[0]; // si l'enfant
                                                                    // 1 est
                                                                    // réalisable
                                                                    // il
                                                                    // remplace
                                                                    // le parent
                                                                    // 1
                }
                if (testRealisable(enfants[1])) {
                    population[tableCroisement[j + 1]] = enfants[1]; // si
                                                                        // l'enfant
                                                                        // 2 est
                                                                        // réalisable
                                                                        // il
                                                                        // remplace
                                                                        // le
                                                                        // parent
                                                                        // 2
                }

            }
        } else { // le cas où le nombre àcroiser est impaire on mute le dernier
                    // individu et on croise les autres
            for (int j = 0; j < tableCroisement.length - 1; j = j + 2) {
            //    enfants = croisement(population[tableCroisement[j]], population[tableCroisement[j + 1]], 1, 4);
            //    if (testRealisable(enfants[0])) {
                  //  population[tableCroisement[j]] = enfants[0];
            //    }
        /*        if (testRealisable(enfants[1])) {
                 //   population[tableCroisement[j + 1]] = enfants[1];
                }*/
      //          population[tableCroisement[j]] = enfants[0];
      //          population[tableCroisement[j + 1]] = enfants[1];

            }
         /*   if (testRealisable(mutation(population[tableCroisement[tableCroisement.length - 1]])))
                mutation(population[tableCroisement[tableCroisement.length - 1]]);*/
        }
        // muter les individu sélectionner pour la mutation
        for (int i = 0; i < tableMutation.length; i++) {
         //   if (testRealisable(mutation(population[tableMutation[i]])))
         //       mutation(population[tableMutation[i]]);
        }

    }
	
    public static Random getRan() {
		return ran;
	}

	public static void setRan(Random ran) {
		VRP.ran = ran;
	}	
}