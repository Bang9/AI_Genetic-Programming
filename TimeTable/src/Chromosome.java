import java.util.Arrays;

public class Chromosome implements Cloneable, Comparable<Chromosome> {
	public Gene[] gene;
	public int	  geneSize;

	/* 
	 * @Chromosome
	 * 과목 개수만큼 유전자를 가지는 염색체
	 */
	public Chromosome(int classSize) {
		this.gene = new Gene[classSize];
		this.geneSize = 0;
	}

	/* 
	 * @addGene
	 * 염색체 추가
	 */	
	public void addGene(Gene newGene) {
		this.gene[geneSize++]=newGene;
	}

	/*
	 * @initialGene
	 * 부모가 될 유전자들의 초기값을 설정한다.
	 */
	public void initialGene() {
		for(Gene gene : this.gene) {
			gene.setRandom();
		}
	}

	/*
	 * @mutationGene
	 * 유전자중 두개를 랜덤으로 골라서 randomize시킨다.
	 */
	public void mutationGene() {
		int first = (int)(Math.random()*this.gene.length);
		int second = (int)(Math.random()*this.gene.length);
		this.gene[first].setRandom();
		this.gene[second].setRandom();
	}

	/*
	 * @crossOver
	 * 한 지점을 무작위로 선정하여 교차변이한다.
	 */
	public void crossOver(Chromosome otherChromosome) {
		int mid = (int)(Math.random()*this.gene.length);

		for(int i=mid; i<this.gene.length; i++) {
			this.gene[i] = otherChromosome.gene[i];
		}
	}

	/*
	 * @fitness
	 * 제한사항에 따른 적합도를 검사하고 패널티 value를 return한다.
	 */
	public int fitness() {
		int result = 0;

		//유전자 자체의 패널티 검사
		for(Gene g : this.gene) {
			result += g.getPenalty();
		}

		//염색체의 패널티 검사

		//강의실이 겹치는 수업이 존재하는가? +20

		//강의실 하나에 하루 배정가능한 시간을 초과하였는가? +20

		return result;
	}

	/*
	 * @clone
	 * 현재 염색체를 복사하여 반환한다.
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
		int[][] classTable = new int[5][9]; //0~5이론, 6~8 실습
		for(int i=0; i<5; i++) {
			switch(i) {
			case 0 : System.out.println("\n=======월요일========"); break;
			case 1 : System.out.println("\n=======화요일========"); break;
			case 2 : System.out.println("\n=======수요일========"); break;
			case 3 : System.out.println("\n=======목요일========"); break;
			case 4 : System.out.println("\n=======금요일========"); break;
			}
			for(Gene g : this.gene) {
				if(g.dayVector[i]==1) {
					System.out.print("\n"+g.professor+" - "+g.className+"("+g.theory+"/"+g.practice+") - ");
					for(int l=0; l<9; l++) {
						if(g.classTable[i][l]==1) {
							switch(l) {
							case 0 : System.out.println("411호"); break;
							case 1 : System.out.println("511호"); break;
							case 2 : System.out.println("605호"); break;
							case 3 : System.out.println("606호"); break;
							case 4 : System.out.println("609호"); break;
							case 5 : System.out.println("610호"); break;
							case 6 : System.out.println("418호(실습)"); break;
							case 7 : System.out.println("517호(실습)"); break;
							case 8 : System.out.println("608호(실습)"); break;
							}
						}
					}
					for(int j=0; j<18; j++) {
						if(g.timeTable[i][j]==1) {
							System.out.println(9+j/2.0+"시 ");
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
