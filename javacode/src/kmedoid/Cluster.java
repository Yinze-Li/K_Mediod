package kmedoid;

import java.util.ArrayList;

public class Cluster {

	private ArrayList<Student> list;
	public String medoid;
	public double totalcost;

	public Cluster() {
		// TODO Auto-generated constructor stub
		list = new ArrayList<Student>();
	}

	private int index(String ID) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getID() == ID) {
				return i;
			}
		}
		return -1;
	}

	public void remove(String ID) {
		int i = index(ID);
		list.remove(i);
	}

	public void add(Student stu) {
		list.add(stu);
	}

	public int swap() {
		double mincost = totalcost;
		String bestID = medoid;
		for (int i = 0; i < list.size(); i++) {
			String ID = list.get(i).getID();
			double thiscost = calcost(ID);
//			System.out.println("this cost = " + thiscost + "mincost = "+mincost);
			if (thiscost < mincost) {
				bestID = ID;
				mincost = thiscost;
			}
		}
		if (bestID != medoid) {
			medoid = bestID;
			System.out.println("medoid changes to " + medoid);
			totalcost = mincost;
			list = new ArrayList<Student>();
			return 1;
		}
		System.out.println("no change at all");
		list = new ArrayList<Student>();
		return 0;
	}

	public void initcost() {
		totalcost = calcost(medoid);
	}

	public double calcost(String ID) {
		double total = 0;
		for (int i = 0; i < list.size(); i++) {
			total += dis(ID, list.get(i).getID());
		}
//		System.out.println("total = " + totalcost);
		return total;
	}

	public double dis(String ID1, String ID2) { //Euclid distance
		int s1 = index(ID1);
		int s2 = index(ID2);
		double total = 0;
		for (int i = 0; i < list.get(s1).subnum; i++) {
			double dif = list.get(s1).getGrade(i) - list.get(s2).getGrade(i);
			dif *= dif;
			total += dif;
		}
		total = Math.sqrt(total);
		return total;
	}

//	public double dis(String ID1, String ID2) { // Chebyshev distance
//		int s1 = index(ID1);
//		int s2 = index(ID2);
//		double total = 0;
//		for (int i = 0; i < list.get(s1).subnum; i++) {
//			double dif = list.get(s1).getGrade(i) - list.get(s2).getGrade(i);
//			dif = Math.abs(dif);
//			if (total < dif) {
//				total = dif;
//			}
//		}
//		return total;
//	}

	public String[] toFile() {
		String[] stus = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			stus[i] = list.get(i).toString();
		}
		return stus;
	}
}
