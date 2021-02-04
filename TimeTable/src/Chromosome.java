import java.util.Arrays;

public class Chromosome implements Cloneable, Comparable<Chromosome> {
	public Gene[] gene;
	public int	  geneSize;

	/* 
	 * @Chromosome
	 * ���� ������ŭ �����ڸ� ������ ����ü
	 */
	public Chromosome(int classSize) {
		this.gene = new Gene[classSize];
		this.geneSize = 0;
	}

	/* 
	 * @addGene
	 * ����ü �߰�
	 */	
	public void addGene(Gene newGene) {
		this.gene[geneSize++]=newGene;
	}

	/*
	 * @initialGene
	 * �θ� �� �����ڵ��� �ʱⰪ�� �����Ѵ�.
	 */
	public void initialGene() {
		for(Gene gene : this.gene) {
			gene.setRandom();
		}
	}

	/*
	 * @mutationGene
	 * �������� �ΰ��� �������� ��� randomize��Ų��.
	 */
	public void mutationGene() {
		int first = (int)(Math.random()*this.gene.length);
		int second = (int)(Math.random()*this.gene.length);
		this.gene[first].setRandom();
		this.gene[second].setRandom();
	}

	/*
	 * @crossOver
	 * �� ������ �������� �����Ͽ� ���������Ѵ�.
	 */
	public void crossOver(Chromosome otherChromosome) {
		int mid = (int)(Math.random()*this.gene.length);

		for(int i=mid; i<this.gene.length; i++) {
			this.gene[i] = otherChromosome.gene[i];
		}
	}

	/*
	 * @fitness
	 * ���ѻ��׿� ���� ���յ��� �˻��ϰ� �г�Ƽ value�� return�Ѵ�.
	 */
	public int fitness() {
		int result = 0;

		//������ ��ü�� �г�Ƽ �˻�
		for(Gene g : this.gene) {
			result += g.getPenalty();
		}

		//����ü�� �г�Ƽ �˻�

		//���ǽ��� ��ġ�� ������ �����ϴ°�? +20

		//���ǽ� �ϳ��� �Ϸ� ���������� �ð��� �ʰ��Ͽ��°�? +20

		return result;
	}

	/*
	 * @clone
	 * ���� ����ü�� �����Ͽ� ��ȯ�Ѵ�.
	 */
	public Chromosome clone(){
		Chromosome clone = null;
		try{
			clone = (Chromosome) super.clone();
			clone.gene = new Gene[this.gene.length];
			for(int i=0; i<clone.gene.length; i++) {
				clone.gene[i] = this.gene[i].clone();
			}
			clone.geneSize = this.geneSize;
		}catch(Exception e){

		}
		return clone; 
	} 

	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(Arrays.toString(gene));
		return str.toString();
	}

	@Override
	public int compareTo(Chromosome c) {
		// TODO Auto-generated method stub
		int a = fitness();
		int b = c.fitness();
		return a-b;
	}

	public void printTable() {
		int[][] timeTable = new int[5][18];
		int[][] classTable = new int[5][9]; //0~5�̷�, 6~8 �ǽ�
		for(int i=0; i<5; i++) {
			switch(i) {
			case 0 : System.out.println("\n=======������========"); break;
			case 1 : System.out.println("\n=======ȭ����========"); break;
			case 2 : System.out.println("\n=======������========"); break;
			case 3 : System.out.println("\n=======�����========"); break;
			case 4 : System.out.println("\n=======�ݿ���========"); break;
			}
			for(Gene g : this.gene) {
				if(g.dayVector[i]==1) {
					System.out.print("\n"+g.professor+" - "+g.className+"("+g.theory+"/"+g.practice+") - ");
					for(int l=0; l<9; l++) {
						if(g.classTable[i][l]==1) {
							switch(l) {
							case 0 : System.out.println("411ȣ"); break;
							case 1 : System.out.println("511ȣ"); break;
							case 2 : System.out.println("605ȣ"); break;
							case 3 : System.out.println("606ȣ"); break;
							case 4 : System.out.println("609ȣ"); break;
							case 5 : System.out.println("610ȣ"); break;
							case 6 : System.out.println("418ȣ(�ǽ�)"); break;
							case 7 : System.out.println("517ȣ(�ǽ�)"); break;
							case 8 : System.out.println("608ȣ(�ǽ�)"); break;
							}
						}
					}
					for(int j=0; j<18; j++) {
						if(g.timeTable[i][j]==1) {
							System.out.println(9+j/2.0+"�� ");
						}
					}
					for(int k=0; k<9; k++) {
						classTable[i][k] += g.classTable[i][k];
					}
				}
			}
		}
	}
}
