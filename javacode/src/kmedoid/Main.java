package kmedoid;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class Main {

	private Cluster[] cluster;
	final private ArrayList<Student> stu;
	private int K;
	private int subnum;

	public Main(int k) {
		this.K = k;
		// TODO Auto-generated constructor stub
		cluster = new Cluster[k];
		for (int i = 0; i < K; i++) {
			cluster[i] = new Cluster();
		}
		stu = new ArrayList<Student>();
		load();
	}

	public void load() {
		try {
			System.out.println("load students...");
			InputStreamReader in = null;
			try {
				in = new InputStreamReader(new FileInputStream("../data/grade.csv"), "UTF-8");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			BufferedReader reader = new BufferedReader(in);
			reader.readLine();
			String line = null;
			while ((line = reader.readLine()) != null) {
				String item[] = line.split(",");
				String ID = item[0];
				double[] grade = new double[item.length - 1];
				for(int i = 0; i < grade.length; i++) {
					grade[i] = Double.parseDouble(item[i + 1]);
				}
				subnum = grade.length;
				Student student = new Student(ID, grade);
				System.out.println(student);
				stu.add(student);
			}
			System.out.println("# of students: " + stu.size());
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void genMedoid() {
		int random = (int) (Math.random() * (stu.size() - K));
		System.out.println(stu.size());
		System.out.println(random);
		for (int i = 0; i < K; i++) {
			cluster[i].medoid = stu.get(random + i).getID();
		}
	}

	public void getIntoCluster() {
		for (int i = 0; i < stu.size(); i++) {
			String ID = stu.get(i).getID();
			double min = 99999999999.0;
			int which = -1;
			for (int j = 0; j < K; j++) {
				double distance = dis(ID, cluster[j].medoid);
				if (distance < min) {
					which = j;
					min = distance;
				}
			}
			cluster[which].add(stu.get(i));
//			System.out.println("add " + ID + " to cluster " + which + ", ditance = " + min);
		}
		for (int i = 0; i < K; i++) {
			cluster[i].initcost();
			System.out.println("total cost of cluster " + i + " is " + cluster[i].totalcost);
		}
	}

	public int swap() {
		int change = 0;
		for (int i = 0; i < K; i++) {
			System.out.println("cluster " + i);
			change += cluster[i].swap();
		}
		return change;
	}

	public double dis(String ID1, String ID2) { //Euclid distance
		int s1 = index(ID1);
		int s2 = index(ID2);
		double total = 0;
		for(int i = 0; i < subnum; i ++ ) {
			double dif = stu.get(s1).getGrade(i) - stu.get(s2).getGrade(i);
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
//		for (int i = 0; i < subnum; i++) {
//			double dif = stu.get(s1).getGrade(i) - stu.get(s2).getGrade(i);
//			dif = Math.abs(dif);
//			if (total < dif) {
//				total = dif;
//			}
//		}
//		return total;
//	}

	private int index(String ID) {
		for (int i = 0; i < stu.size(); i++) {
			if (stu.get(i).getID() == ID) {
				return i;
			}
		}
		return -1;
	}

	public void save() {
		try {
			File csv = new File("../data/cluster" + K + ".csv");
			BufferedWriter bw = new BufferedWriter(new FileWriter(csv, false));
			bw.write("ID,ch,math,en,phy,che,bio");
			bw.newLine();
			for (int i = 0; i < K; i++) {
				bw.write("Cluster " + i + ", medoid " + cluster[i].medoid);
				bw.newLine();
				String[] stus = cluster[i].toFile();
				for (int j = 0; j < stus.length; j++) {
					bw.write(stus[j]);
					bw.newLine();
				}
			}
			bw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
