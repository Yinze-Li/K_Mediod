package kmedoid;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Main m = new Main(6);
		m.genMedoid();
		int loop = -1;
		while(true) {
			m.getIntoCluster();
			loop ++;
			if(m.swap() == 0) {
				m.getIntoCluster();
				break;
			}
		}
		System.out.println("total number of swap is " + loop);
		m.save();
	}

}
