package jeu_echecs;

import java.util.*;
/** Programme permettant de simuler une partie d'echec dans le cadre de l'examen du module NFA031 du CNAM
 * @author LASSALLE Adeline
 * @version 2.0*/
public class Jeu_Echecs_v3 {
	/** La methode main initialise l'echiquier et lance le menu pour l'utilisateur
	 * @param arguments initialisation de l'echiquier et du menu*/
	public static void main (String [] arguments) {

		// INITIALISER LA PARTIE
				//Création de l'echiquier
				String [][] echiquier;
				echiquier = new String [8][8];
				
				//Création d'un echiquier de coordonnées de pièce
				String [][] echiquier2 = new String [8][8];
				for(int a=0; a<echiquier2.length; a++) {
					echiquier2[a][7]= "H"+ (8-a);//8
					echiquier2[a][6]= "G"+ (8-a);//7
					echiquier2[a][5]= "F"+ (8-a);//6
					echiquier2[a][4]= "E"+ (8-a);//5
					echiquier2[a][3]= "D"+ (8-a);//4
					echiquier2[a][2]= "C"+ (8-a);//3
					echiquier2[a][1]= "B"+ (8-a);//2
					echiquier2[a][0]= "A"+ (8-a);//1
				}
									
				//Disposition des pièces noires en début de partie
				echiquier[0][0] = "TN";
				echiquier[0][1] = "CN";
				echiquier[0][2] = "FN";
				echiquier[0][3] = "QN";
				echiquier[0][4] = "KN";
				echiquier[0][5] = "FN";
				echiquier[0][6] = "CN";
				echiquier[0][7] = "TN";
				
				for(int i = 0; i<8; i++) {
					echiquier[1][i] = "PN";
				}
				
				//Disposition des cases vides en debut de partie
				for(int q=2; q<6; q++) {
					for(int r=0; r<8; r++) {
						echiquier[q][r] = "00";
					}
				}
				
				//Disposition des pièces blanches en début de partie
				
				echiquier[7][0] = "TB";
				echiquier[7][1] = "CB";
				echiquier[7][2] = "FB";
				echiquier[7][3] = "QB";
				echiquier[7][4] = "KB";
				echiquier[7][5] = "FB";
				echiquier[7][6] = "CB";
				echiquier[7][7] = "TB";
				
				for(int j = 0; j<8; j++) {
					echiquier[6][j] = "PB";
				}
//LANCEMENT DU MENU				
				do menuDeJeu(echiquier, echiquier2);
				while (menuDeJeu(echiquier, echiquier2) != 4);
				
//FIN DE MAIN	
	}

//METHODES UTILISEES	
	/**Menu du jeu - selection a choix multiples pour l'utilisateur
	 * @param echiquier
	 * 		Plateau de jeu d'echec en cours
	 * @param echiquier2
	 * 		Plateau de jeu d'echec contenant les coordonnées des cases du jeu, permet ici d'utiliser les methodes choixDuJoueur et demandeDeDeplacement
	 * @return int choixMenu
	 * 		Selection du chiffre correspondant à l'action choisie par l'utilisateur afin de cloturer le jeu notamment*/
	public static int menuDeJeu(String echiquier[][], String echiquier2[][]) {
		System.out.println("");
		System.out.println("Quelle action souhaitez vous faire ?");
		System.out.println("1: Afficher l'echiquier en cours");
		System.out.println("2: Evaluer le nombre de points de chaque joueur");
		System.out.println("3: Effectuer un deplacement");
		System.out.println("4: Quitter la partie");
		
		Scanner saisie = new Scanner(System.in);
		int choixMenu  = saisie.nextInt();
						
		switch (choixMenu) {
			case 1 -> affichagePlateau(echiquier);
			case 2 -> choixDuJoueur(echiquier, echiquier2);
			case 3 -> demandeDeDeplacement(echiquier, echiquier2);
			case 4 -> choixMenu = 4;
			default -> menuDeJeu(echiquier, echiquier2);
			};
		return choixMenu;	
		}
	
	/**Affichage du plateau du jeu a tout moment de la partie
	 * @param echiquier
	 * 		Plateau de jeu d'echec en cours*/
	public static void affichagePlateau(String echiquier[][]) {
			for(int i=0; i<8; i++) {
				for(int j=0; j<8; j++) {
					System.out.print(echiquier[i][j]);
					System.out.print(" ");
				}
				System.out.println(" ");
			}		
		}
	
	/**Score et nombre de pieces restantes des joueurs - choix initial entre les 2 joueurs
	 * @param echiquier
	 * 		Plateau de jeu d'echec en cours
	 * @param echiquier2
	 * 		Plateau de jeu d'echec contenant les coordonnees des cases du jeu, permet ici d'utiliser la methode menuDeJeu
	 * @return Selon le choix de l'utilisateur : 
	 * 		Soit la methode pointsDesBlancs qui calcule le score du joueur blanc; 
	 * 		soit la methode pointsDesNoirs qui calcule le score du joueur noir; 
	 * 		soit qui renvoie au menu du jeu en cas de saisie inadequate*/
	public static int choixDuJoueur(String echiquier[][], String echiquier2[][]) {
		System.out.println("1: Score du joueur BLANC");
		System.out.println("2: Score du joueur NOIR");
		
		Scanner saisie = new Scanner(System.in);
		int couleurJoueur = saisie.nextInt();
		
		if (couleurJoueur == 1) {
			System.out.println("Il y a " + piecesBlanches(echiquier) + " pièces blanches sur l'échiquier.");
			return pointsDesBlancs(echiquier);
					
			
		} else if(couleurJoueur == 2) {
			System.out.println("Il y a " + piecesNoires(echiquier) + " pièces noires sur l'echiquier.");
			return pointsDesNoirs(echiquier);
			
		} else {
			System.out.println("Choix non pris en charge. Retour au menu");
			return menuDeJeu(echiquier, echiquier2);
		}
	}
	
	/**Decompte du nombre de pieces sur l'echiquier pour les blancs
	 * @param echiquier
	 * 		Tableau 8x8 correspondant au Plateau d'echecs en cours
	 * @return nbrPiecesB
	 * 		Variable de type int qui cumule le nombre de pieces du joueur blanc*/
	public static int piecesBlanches(String echiquier[][]) {
		int nbrPiecesB = 0;
			for (int k=0; k<echiquier.length; k++) {
				for (int l=0; l<echiquier.length; l++) {
					if(echiquier[k][l] == "PB" || echiquier[k][l] == "TB"||echiquier[k][l] == "CB"||echiquier[k][l] == "FB"|| echiquier[k][l] == "QB"||echiquier[k][l] == "KB") {
						nbrPiecesB ++;
					}
				}
			}
		return nbrPiecesB;
	}

	/**Decompte du nombre de pieces sur l'echiquier pour les noirs
	 * @param echiquier
	 * 		Tableau 8x8 correspondant au Plateau d'echecs en cours
	 * @return nbrPiecesN
	 * 		Variable de type int qui cumule le nombre de pieces du joueur noir*/
	public static int piecesNoires(String echiquier[][]) {
		int nbrPiecesN = 0;
			for (int k=0; k<echiquier.length; k++) {
				for (int l=0; l<echiquier.length; l++) {
					if(echiquier[k][l] == "PN" || echiquier[k][l] == "TN"||echiquier[k][l] == "CN"||echiquier[k][l] == "FN"|| echiquier[k][l] == "QN"||echiquier[k][l] == "KN") {
						nbrPiecesN ++;
					}
				}
			}
		return nbrPiecesN;
	}
	
	/**Calcul du score pour le joueur blanc
	 *  @param echiquier
	 * 		Tableau 8x8 de type String correspondant au Plateau d'echecs en cours
	 * @return totalPointsBlancs
	 * 		Variable de type int qui cumule le nombre de points du joueur blanc*/
	public static int pointsDesBlancs(String echiquier[][]) {
		int totalPointsBlancs = 0;
		for (int m=0; m<echiquier.length; m++) {
			for (int n=0; n<echiquier.length; n++) {
				switch(echiquier[m][n]) {
				case "PN" -> totalPointsBlancs += 1;
				case "TN" -> totalPointsBlancs += 5;
				case "CN" -> totalPointsBlancs += 3;
				case "FN" -> totalPointsBlancs += 3;
				case "QN" -> totalPointsBlancs += 7;
				case "KN" -> totalPointsBlancs += 5;
				case "00" -> totalPointsBlancs += 0;
				default -> totalPointsBlancs += 0;
				}
			}
		}
		System.out.println("Score du joueur blanc : " + totalPointsBlancs);
		return totalPointsBlancs;
	}
		
	/**Calcul du score pour le joueur noir
	 *  @param echiquier
	 * 		Tableau 8x8 de type String correspondant au Plateau d'echecs en cours
	 * @return totalPointsNoirs
	 * 		Variable de type int qui cumule le nombre de points du joueur noir*/
	public static int pointsDesNoirs(String echiquier[][]) {
		int totalPointsNoirs = 0;
		for (int o=0; o<echiquier.length; o++) {
			for (int p=0; p<echiquier.length; p++) {
				switch(echiquier[o][p]) {
				case "PN" -> totalPointsNoirs += 1;
				case "TN" -> totalPointsNoirs += 5;
				case "CN" -> totalPointsNoirs += 3;
				case "FN" -> totalPointsNoirs += 3;
				case "QN" -> totalPointsNoirs += 7;
				case "KN" -> totalPointsNoirs += 5;
				case "00" -> totalPointsNoirs += 0;
				default -> totalPointsNoirs += 0;
				}
			}
		}
		System.out.println("Score du joueur noir : " + totalPointsNoirs);
		return totalPointsNoirs;
	}	
	
/**METHODES POUR GERER LE DEPLACEMENT D'UNE PIECE*/
		/**Transposition alphanumerique des valeurs saisies par l'utilisateur en valeur numerique + mise à la casse tout en minuscule
		 * @param coordAlphaInit
		 * 			Partie alphabetique des coordonnaes entres par l'utilisateur
		 * @return coordAlphaInit2
		 * 			Variable de type int qui associe chaque lettre a un chiffre adapte au tableau echiquier[][]*/
		public static int transposition(String coordAlphaInit) {
				int coordAlphaInit2;		
				
				switch (coordAlphaInit.toLowerCase()) {
				case "a" -> coordAlphaInit2 = 0;
				case "b" -> coordAlphaInit2 = 1;
				case "c" -> coordAlphaInit2 = 2;
				case "d" -> coordAlphaInit2 = 3;
				case "e" -> coordAlphaInit2 = 4;
				case "f" -> coordAlphaInit2 = 5;
				case "g" -> coordAlphaInit2 = 6;
				case "h" -> coordAlphaInit2 = 7;
				default -> coordAlphaInit2 = 8;
				}
			//Test du switch	
			//System.out.println("la lettre " + coordAlphaInit + " correspond au chiffre " + coordAlphaInit2);
				return coordAlphaInit2;			
		}
		
		/**Verification de l'existence des coordonnées sur l'echiquier (compris entre 0 et 7)
		 * @param coordAlphaInit3
		 * 			Valeur numerique de type int correspondant a la première variable d'enumeration du tableau echiquier[][]
		 * @param coordNumInit3
		 * 			Valeur numerique de type int correspondant a la deuxième variable d'enumeration du tableau echiquier[][]
		 * @return verification
		 * 			Variable booleenne qui associe true si les valeurs entrees par l'utilisateur sont comprise entre 0 et 7 inclus et false autrement*/		
		public static boolean verification(int coordAlphaInit3, int coordNumInit3) {
			boolean verification= true;
			for (int a=0; a<8; a++) {
				if(coordAlphaInit3 <= a && coordNumInit3<= a) {
					verification = true;
				} else {
					verification = false;
				}
			}
			return verification;				
		}
		
		/**Reconnaissance de la piece sur la case selectionnee (ou non)
		 * @param echiquier
		 * 			Tableau 8x8 correspondant au plateau de jeu d'echec
		 * @param coordAlphaInit3
		 * 			Valeur numerique de type int correspondant a la premiere variable d'enumeration du tableau echiquier[][]
		 * @param coordNumInit3
		 * 			Valeur numerique de type int correspondant a la deuxieme variable d'enumeration du tableau echiquier[][] 
		 * @return reconnaissance
		 * 			Variable qui renvoie les donnees entrées dans le tableau echiquier[][] pour determiner quelle piece est selectionnee*/
		public static String reconnaissance(String echiquier[][], int coordAlphaInit3, int coordNumInit3) {
			String reconnaissance = echiquier[coordNumInit3][coordAlphaInit3];
			return reconnaissance;
		}
		
		/**Verification du Mouvement effectif de la piece (=piece non statique)
		 * @param coordAlphaInit3
		 * 			Valeur numerique de type int correspondant a la premiere variable d'enumeration du tableau echiquier[][] pour l'origine du deplacement
		 * @param coordNumInit3
		 * 			Valeur numerique de type int correspondant a la deuxieme variable d'enumeration du tableau echiquier[][] pour l'origine du deplacement
		 * @param coordAlphaFinal3
		 * 			Valeur numerique de type int correspondant a la premiere variable d'enumeration du tableau echiquier[][] pour la destination du deplacement
		 * @param coordNumFinal3
		 * 			Valeur numerique de type int correspondant a la deuxieme variable d'enumeration du tableau echiquier[][] pour la destination du deplacement
		 * @return mouvement
		 * 			Variable booleenne qui affiche true si les ccoordonnes de depart et d'arrivee sont differents et false dans le cas contraire
		 * 			*/
		public static boolean mouvement(int coordNumInit3, int coordAlphaInit3, int coordNumFinal3, int coordAlphaFinal3) {
			boolean mouvement = true;
			if (coordNumInit3 == coordNumFinal3 && coordAlphaInit3 == coordAlphaFinal3) {
				mouvement = false;
			}
			return mouvement;
		}
		
		/**Deplacement de la piece selectionnee et effacement de sa case d'origine
		 * @param echiquier
		 * 			Tableau 8x8 correspondant au plateau de jeu d'echec en cours
		 * @param echiquier2
		 * 			Tableau 8x8 correspondant à un Plateau de jeu d'echec contenant les coordonnées des cases du jeu
		 * @param coordAlphaInit3
		 * 			Valeur numerique de type int correspondant a la premiere variable d'enumeration du tableau echiquier[][] pour l'origine du deplacement
		 * @param coordNumInit3
		 * 			Valeur numerique de type int correspondant a la deuxieme variable d'enumeration du tableau echiquier[][] pour l'origine du deplacement
		 * @param coordAlphaFinal3
		 * 			Valeur numerique de type int correspondant a la premiere variable d'enumeration du tableau echiquier[][] pour la destination du deplacement
		 * @param coordNumFinal3
		 * 			Valeur numerique de type int correspondant a la deuxieme variable d'enumeration du tableau echiquier[][] pour la destination du deplacement*/
		public static void deplacement(String echiquier[][], String echiquier2[][], int coordNumInit3, int coordAlphaInit3, int coordNumFinal3, int coordAlphaFinal3) {
			//Donner la valeur de la piece de depart à la case d'arrivée
			echiquier[coordNumFinal3][coordAlphaFinal3] = echiquier[coordNumInit3][coordAlphaInit3];
			//Effacer le contenu de la case de départ
			echiquier[coordNumInit3][coordAlphaInit3]= "00";
		}
		
		/**Autorisation de deplacement du pion noir
		 *Pour le pion : Avance d'une seule case (sauf possibilité de 2 cases si est sur la ligne de depart), ne peut pas reculer.
		 * 
		 * @param coordAlphaInit3
		 * 			Valeur numerique de type int correspondant a la première variable d'enumeration du tableau echiquier[][] pour l'origine du deplacement
		 * @param coordNumInit3
		 * 			Valeur numerique de type int correspondant a la deuxième variable d'enumeration du tableau echiquier[][] pour l'origine du deplacement
		 * @param coordAlphaFinal3
		 * 			Valeur numerique de type int correspondant a la première variable d'enumeration du tableau echiquier[][] pour la destination du deplacement
		 * @param coordNumFinal3
		 * 			Valeur numerique de type int correspondant a la deuxième variable d'enumeration du tableau echiquier[][] pour la destination du deplacement
		 * @return deplacementPionNoir
		 * 			Valiable booleenne qui affiche true si le deplacement du pion correspond aux regles, et false autrement*/
		public static boolean autorisationDeplacementPionNoir(int coordNumInit3, int coordAlphaInit3, int coordAlphaFinal3, int coordNumFinal3) {
			boolean deplacementPionNoir;
			//SI sur ligne de depart, peut avancer verticalement de 1 ou 2 cases mais pas reculer
				if(coordNumInit3 == 1) {
					System.out.println("Le pion noir est sur sa ligne de départ.");
					if(coordNumFinal3 == coordNumInit3 + 1 || coordNumFinal3 == coordNumInit3 + 2){			
						deplacementPionNoir = true;
						System.out.println("Déplacement valide");
					} else {
						System.out.println("Le deplacement n'est pas dans les regles. Le pion peut avancer verticalement d'une a deux cases et ne peut pas reculer");
						deplacementPionNoir = false;
					}
				} else {
					System.out.println("Le pion n'est pas sur sa ligne de départ");
					if(coordNumFinal3 == coordNumInit3 + 1){			
						deplacementPionNoir = true;
						System.out.println("Deplacement valide");
					} else {
						System.out.println("Le deplacement n'est pas dans les regles. Le pion peut avancer verticalement d'une cases et ne peut pas reculer");
						deplacementPionNoir = false;
					}
				}
			return deplacementPionNoir;
		}
		
		/**Autorisation de deplacement du pion blanc
		 *Pour le pion : Avance d'une seule case (sauf possibilité de 2 cases si est sur la ligne de depart), ne peut pas reculer.
		 * 
		 * @param coordAlphaInit3
		 * 			Valeur numerique de type int correspondant a la premiere variable d'enumeration du tableau echiquier[][] pour l'origine du deplacement
		 * @param coordNumInit3
		 * 			Valeur numerique de type int correspondant a la deuxieme variable d'enumeration du tableau echiquier[][] pour l'origine du deplacement
		 * @param coordAlphaFinal3
		 * 			Valeur numerique de type int correspondant a la premiere variable d'enumeration du tableau echiquier[][] pour la destination du deplacement
		 * @param coordNumFinal3
		 * 			Valeur numerique de type int correspondant a la deuxieme variable d'enumeration du tableau echiquier[][] pour la destination du deplacement
		 * @return deplacementPionBlanc
		 * 			Valiable booleenne qui affiche true si le deplacement du pion correspond aux regles, et false autrement*/
		public static boolean autorisationDeplacementPionBlanc(int coordNumInit3, int coordAlphaInit3, int coordAlphaFinal3, int coordNumFinal3) {
			boolean deplacementPionBlanc;
			//SI sur ligne de depart, peut avancer verticalement de 1 ou 2 cases mais pas reculer
				if(coordNumInit3 == 6) {
					System.out.println("Le pion blanc est sur sa ligne de départ.");
					if(coordNumFinal3 == coordNumInit3 - 1 || coordNumFinal3 == coordNumInit3 - 2){			
						deplacementPionBlanc = true;
						System.out.println("Déplacement valide");
					} else {
						System.out.println("Le deplacement n'est pas dans les regles. Le pion peut avancer verticalement d'une a deux cases et ne peut pas reculer");
						deplacementPionBlanc = false;
					}
				} else {
					System.out.println("Le pion n'est pas sur sa ligne de départ");
					if(coordNumFinal3 == coordNumInit3 - 1){			
						deplacementPionBlanc = true;
						System.out.println("Deplacement valide");
					} else {
						System.out.println("Le deplacement n'est pas dans les regles. Le pion peut avancer verticalement d'une cases et ne peut pas reculer");
						deplacementPionBlanc = false;
					}
				}
			return deplacementPionBlanc;
		}
		
		/**Signalement de la consequence du deplacement au joueur : Position finale et/ou Prise de piece
		 * @param echiquier
		 * 			Tableau 8x8 correspondant au plateau de jeu d'echec en cours
		 * @param echiquier2
		 * 			Tableau 8x8 correspondant à un Plateau de jeu d'echec contenant les coordonnées des cases du jeu
		 * @param coordAlphaInit3
		 * 			Valeur numerique de type int correspondant a la première variable d'enumeration du tableau echiquier[][] pour l'origine du deplacement
		 * @param coordNumInit3
		 * 			Valeur numerique de type int correspondant a la deuxième variable d'enumeration du tableau echiquier[][] pour l'origine du deplacement
		 * @param coordAlphaFinal3
		 * 			Valeur numerique de type int correspondant a la première variable d'enumeration du tableau echiquier[][] pour la destination du deplacement
		 * @param coordNumFinal3
		 * 			Valeur numerique de type int correspondant a la deuxième variable d'enumeration du tableau echiquier[][] pour la destination du deplacement
		 * @return piecePrise
		 * 			Variable booleenne qui affiche true s'il y a prise de piece et false si c'est un simple deplacement*/
		public static boolean priseDePiece(String echiquier[][], String echiquier2[][], int coordAlphaFinal3, int coordNumFinal3, int coordAlphaInit3, int coordNumInit3) {
			boolean piecePrise = true;
			if (reconnaissance(echiquier, coordAlphaFinal3, coordNumFinal3) == "00") {
				piecePrise = false;
				System.out.println("La pièce est deplacée en : " + echiquier2[coordNumFinal3][coordAlphaFinal3]);
			} else {
				piecePrise = true;
				System.out.println("La pièce " + reconnaissance(echiquier, coordAlphaFinal3, coordNumFinal3) + " en " + echiquier2[coordNumFinal3][coordAlphaFinal3] + " a été prise par : " + reconnaissance(echiquier, coordAlphaInit3, coordNumInit3));
			}			
			return piecePrise;
		}
				
		/**Deplacement de pieces et controles
		 * Modification des coordonnées entrees en variables correspondantes au tableau pour l'origine du deplacement ainsi que pour son arrivee
		 * Test de donnees saisies correctes et confirmation au joueur
		 * Si probleme a chaque test : retour au debut de l'action.
		 * Si controles et tests valides : deplacement de la piece demandee
		 * Si deplacement effectue, retour au menu du jeu
		 * @param echiquier
		 * 			Tableau 8x8 correspondant au plateau de jeu d'echec en cours
		 * @param echiquier2
		 * 			Tableau 8x8 correspondant à un Plateau de jeu d'echec contenant les coordonnées des cases du jeu*/ 
		public static void demandeDeDeplacement(String echiquier[][], String echiquier2[][]) {
			System.out.println("Quelle pièce souhaitez vous deplacer ? (Notez son emplacement actuel, exemple : a1 )");
			Scanner saisie = new Scanner(System.in);
			String coordInit = saisie.next();
			
			//Découpage des coordonnées saisies en 2 valeurs distinctes
			String coordAlphaInit = coordInit.substring(0,1);
			String coordNumInit = coordInit.substring(1);
						
			//Appel de la methode de transformation de string en int
			int coordNumInit2 = Integer.parseInt(coordNumInit);
			int coordNumInit3 = 8 - coordNumInit2;
						
			//Appel de la méthode de transposition alphanumerique des données				
			int coordAlphaInit3 = transposition(coordAlphaInit);
						
			if (verification(coordAlphaInit3, coordNumInit3)==true) {
				System.out.println("Les coordonnées existent sur le plateau");
			}else{
				System.out.println("Coordonnées n'existent pas sur le plateau. Veuillez recommencer");
				demandeDeDeplacement(echiquier, echiquier2);
			}
			
			//Test de reconnaissance de pièce en place ou non
			System.out.println("La pièce à deplacer est : " + reconnaissance(echiquier, coordAlphaInit3, coordNumInit3));
			
			//Verification d'un déplacement possible
			if (reconnaissance(echiquier, coordAlphaInit3, coordNumInit3) == "00") {
				System.out.println("Il n'y a pas de pièce à deplacer à ces coordonnées. Veuillez recommencer.");
				demandeDeDeplacement(echiquier, echiquier2);
			}
			
			//Demander à l'utilisateur le deplacement de la pièce
			System.out.println("Où souhaitez vous deplacer la pièce ? (Notez son emplacement final, exemple : b6 )");
			String coordFinal = saisie.next();
			
			//Découpage des coordonnées saisies en 2 valeurs distinctes
			String coordAlphaFinal = coordFinal.substring(0,1);
			String coordNumFinal = coordFinal.substring(1);
								
			//Appel de la methode de transformation de string en int
			int coordNumFinal2 = Integer.parseInt(coordNumFinal);
			int coordNumFinal3 = 8 - coordNumFinal2;
								
			//Appel de la méthode de transposition alphanumerique des données				
			int coordAlphaFinal3 = transposition(coordAlphaFinal);
							
			//Verification de l'existence des coordonnées saisis (appel de la fonction de verification)
			if (verification(coordAlphaFinal3, coordNumFinal3)==true) {
				System.out.println("Les coordonnées existent sur le plateau");
			}else{
				System.out.println("Coordonnées n'existent pas sur le plateau. Veuillez recommencer");
				demandeDeDeplacement(echiquier, echiquier2);
			}
			
			//Verification du mouvement effectif de la pièce
			if(mouvement(coordNumInit3, coordAlphaInit3, coordNumFinal3, coordAlphaFinal3) == false) {
				System.out.println("Les coordonnés de départ et de fin sont identiques. Vous n'avez pas déplacé la pièce. Veuillez recommencer.");
				demandeDeDeplacement(echiquier, echiquier2);
			} else {
				System.out.println("Les coordonnés de départ et de fin sont differents. La pièce peut etre déplacée.");
			}
			
			//Test de reconnaissance de pièce en place ou non à la destination
			System.out.println("La destination finale est occupée par : " + reconnaissance(echiquier, coordAlphaFinal3, coordNumFinal3));
			
			//Controler que le deplacement de la piece est autorisé + DEPLACEMENT DE PIECE
			//Determiner si c'est un pion qui est deplacé
					if (reconnaissance(echiquier, coordAlphaInit3, coordNumInit3) == "PN") {
						System.out.println("C'est un pion noir. Verification du deplacement");
							//Test de l'autorisation de deplacement
							if (autorisationDeplacementPionNoir(coordNumInit3, coordAlphaInit3, coordAlphaFinal3, coordNumFinal3) == true) {
								System.out.println("Ce deplacement est autorisé pour le pion noir, va être déplacée.");									
								priseDePiece(echiquier, echiquier2, coordAlphaFinal3, coordNumFinal3, coordAlphaInit3, coordNumInit3);
								deplacement(echiquier, echiquier2, coordNumInit3, coordAlphaInit3, coordNumFinal3, coordAlphaFinal3);
								menuDeJeu(echiquier, echiquier2);
							}else{
								System.out.println("Ce deplacement n'est pas autorisé pour le pion noir, la pièce ne peut pas etre déplacée.");
								demandeDeDeplacement(echiquier, echiquier2);
							}	
					} else if (reconnaissance(echiquier, coordAlphaInit3, coordNumInit3) == "PB") {		
						System.out.println("C'est un pion blanc. Verification du deplacement");
						if (autorisationDeplacementPionBlanc(coordNumInit3, coordAlphaInit3, coordAlphaFinal3, coordNumFinal3) == true) {
							System.out.println("Ce deplacement est autorisé pour le pion blanc, la pièce va être déplacée.");
							priseDePiece(echiquier, echiquier2, coordAlphaFinal3, coordNumFinal3, coordAlphaInit3, coordNumInit3);
							deplacement(echiquier, echiquier2, coordNumInit3, coordAlphaInit3, coordNumFinal3, coordAlphaFinal3);
							menuDeJeu(echiquier, echiquier2);
						}else {
							System.out.println("Ce deplacement n'est pas autorisé pour le pion blanc, la pièce ne peut pas etre déplacée. Veuillez recommencer");
							demandeDeDeplacement(echiquier, echiquier2);
						}	
					}else {
						System.out.println("Ce n'est pas un pion.");
						priseDePiece(echiquier, echiquier2, coordAlphaFinal3, coordNumFinal3, coordAlphaInit3, coordNumInit3);
						deplacement(echiquier, echiquier2, coordNumInit3, coordAlphaInit3, coordNumFinal3, coordAlphaFinal3);
						menuDeJeu(echiquier, echiquier2);
					}				
		}
		
//FIN DE CLASS	
}

