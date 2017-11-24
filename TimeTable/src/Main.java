import java.io.*;
import java.util.*;

public class Main {
	public static void main(String[] args) throws IOException {
		File inFile = new File("input.txt");
		FileReader fr = new FileReader(inFile);
		BufferedReader reader = new BufferedReader(fr);

		Chromosome Mother = new Chromosome(40);
		Chromosome[] generation = new Chromosome[500];

		for(int i=0;i<generation.length;i++)
			generation[i] = new Chromosome(40);

		String line=null;
		while((line=reader.readLine())!=null) {
			String professorName = line.split("\t")[0];
			String[] classList = line.split("\t")[1].split(",");

			for(String classInfo : classList) {
				for(Chromosome x : generation) {
					Gene newGene = new Gene(professorName,classInfo);
					x.addGene(newGene);
				}
			}
		}

		//이니시에이팅
		for(int i=0;i<generation.length;i++){
			generation[i].initialGene();
		}

		//정렬한다.
		Arrays.sort(generation);


		Chromosome[] nextGeneration = new Chromosome[125];

		//125개를 룰렛으로 추출
		int[] vector = roulette();
		for(int i=0; i<125; i++) {
			nextGeneration[i] = generation[vector[i]];
		}


		int nth = 0;
		while(!(generation[249].fitness()==0)) {
			//돌연변이
			if(nth%1000==0)
				nextGeneration[50].mutationGene();
			
			nth++;
			
			//교차변이			
			for(int i=0;i<500;i++){

				int a,b = 999;
				a = (int)(Math.random()*125);
				while(a==b || b==999)
					b = (int)(Math.random()*125);
				
				generation[i] = crossOver(nextGeneration[a],nextGeneration[b]);
			}

			//탄생한 자식들을 적합도순으로 정렬
			Arrays.sort(generation);
			double sum=0;
			for(int i=0;i<generation.length;i++){
				sum+=generation[i].fitness();
			}
			System.out.println(nth+"세대 적합도 평균:"+sum/generation.length);
			System.out.println(nth+"세대 최고 성능:"+generation[0].fitness());

			//125개를 룰렛으로 추출
			vector = roulette();
			for(int i=0; i<125; i++) {
				nextGeneration[i] = generation[vector[i]];
			}
		}
		System.out.println(nth+"세대 패널티");
		for(int i=0;i<10;i++){
			System.out.println(generation[i].fitness());
		}
		
		Chromosome result = generation[0];
		result.printTable();
	}

	public static int[] roulette() {
		int[] ret=new int[125];
		//500개중 125개 선택
		for(int i=0;i<55;i++){
			ret[i]=(int)(Math.random()*100);
		}
		for(int i=55;i<95;i++){
			ret[i]=(int)(Math.random()*100+100);
		}
		for(int i=95;i<120;i++){
			ret[i]=(int)(Math.random()*100+200);
		}
		for(int i=120;i<125;i++){
			ret[i]=(int)(Math.random()*100+300);
		}

		return ret;
	}

	/*
	 * @crossOver
	 * 한 지점을 무작위로 선정하여 교차변이한다.
	 */
	public static Chromosome crossOver(Chromosome aC, Chromosome bC) {
		Chromosome a = aC.clone();
		Chromosome b = bC.clone();

		int mid = (int)(Math.random()*a.gene.length);

		for(int i=mid; i<a.gene.length; i++) {
			a.gene[i] = b.gene[i];
		}
		return a;
	}
}
