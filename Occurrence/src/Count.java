import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.Map.Entry;

public class Count {
	public static void main(String args[]) throws IOException {
		Map<String, Integer> occurrence = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
		List<Map.Entry<String,Integer>> list;

		occurrence = Occurrence_1("input.txt", "Question 1");		

		list = SortByValue(occurrence,"Question 3");
		
		Occurrence("forestier_mayotte.txt","Question 4");
		
		list = Occurrence_Clean("forestier_mayotte.txt","Question 5");
		Select50(list, "Question 6");
		FilterProCon(list,"Question 7");
		FilterBizzares50(list,"Question 8");
		
		list = Occurrence_Clean("deontologie_police_nationale.txt","Question 9");
		FilterBizzares50(list,"Question 9");
		
		list = Occurrence_Clean("domaine_public_fluvial.txt","Question 10");
		FilterBizzares50(list,"Question 10");
		
		list = Occurrence_Clean_2("sante_publique_mini.txt","Question 11");
		FilterBizzares50(list,"Question 11");
		
		list = Occurrence_Clean_2("sante_publique.txt","Question 12");
		
//		Question 13:
		

	}
	
	/**
	 * Question 1: simple count
	 * @param fileName
	 * @param outputFile
	 * @return
	 * @throws IOException
	 */
	public static Map<String, Integer> Occurrence_1(String fileName, String outputFile) throws IOException {
		byte[] encoded = new byte[0];
		encoded = Files.readAllBytes(Paths.get(fileName));
		String str = new String(encoded);
				
		str = str.replaceAll("[^a-zA-Z0-9éêèëÉÊÈËôÔùÙàÀîÎïÏçÇ\\-]"," ");
		
		String[] words = str.split(" ");
		Map<String, Integer> occurrence = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
		for(String w : words) {
			Integer n = occurrence.get(w);
			if(n==null) n=1;
			else n++;
			occurrence.put(w, n);
		}
		
//		Write the result to file:
		File fout = new File(outputFile+".txt");
		FileOutputStream fos;
		fos = new FileOutputStream(fout);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
		
		System.out.println("----------------"+ outputFile +": TreeMap key ascending----------------");
		bw.write("----------------"+ outputFile +": TreeMap key ascending----------------");
		bw.newLine();
		for (Entry<String, Integer> entry : occurrence.entrySet()) {
			System.out.println(entry.getKey() + " " + entry.getValue());
			bw.write(entry.getKey() + " " + entry.getValue());
			bw.newLine();
		}
		bw.close();
		return occurrence;
	}
	
	/**
	 * Question 2&3: sort by value
	 * @param occurrence
	 * @param outputFile
	 * @return
	 * @throws IOException
	 */
	public static List<Map.Entry<String,Integer>> SortByValue(Map<String, Integer> occurrence, 
			String outputFile) throws IOException {
		File fout = new File(outputFile+".txt");
		FileOutputStream fos = new FileOutputStream(fout);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
//		Descending Comparator
		Comparator<Map.Entry<String, Integer>> valueComparator = new Comparator<Map.Entry<String, Integer>>() {
			@Override
			public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
				return o2.getValue() - o1.getValue();
			}
		};
//		Map to List
		List<Map.Entry<String,Integer>> listOccu = new ArrayList<Map.Entry<String, Integer>>(occurrence.entrySet());
//		Sort
		Collections.sort(listOccu, valueComparator);
//		It output not only words but also ":"
		System.out.println("----------------"+ outputFile +":----------------");
		bw.write("----------------"+ outputFile +":----------------");
		bw.newLine();
		for (Map.Entry<String, Integer> entry: listOccu) {
			System.out.println(entry.getKey() + " " + entry.getValue());
			bw.write(entry.getKey() + " " + entry.getValue());
			bw.newLine();
		}
		
		bw.close();
		return listOccu;
	}
	
	/**
	 * Question 4: without filtering
	 * @param fileName
	 * @param outputFile
	 * @throws IOException
	 */
	public static void Occurrence(String fileName, String outputFile) throws IOException {
		byte[] encoded = new byte[0];
		encoded = Files.readAllBytes(Paths.get(fileName));
		String str = new String(encoded);
		
		String[] words = str.split(" ");
		Map<String, Integer> occurrence = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
		for(String w : words) {
			Integer n = occurrence.get(w);
			if(n==null) n=1;
			else n++;
			occurrence.put(w, n);
		}
	}
	
	/**
	 * Question 5: ajoutez une étape de “nettoyage/filtrage”
	 * @param fileName
	 * @param outputFile
	 * @return
	 * @throws IOException
	 */
	public static List<Map.Entry<String,Integer>> Occurrence_Clean(String fileName, String outputFile) throws IOException {
		byte[] encoded = new byte[0];
		encoded = Files.readAllBytes(Paths.get(fileName));
		String str = new String(encoded);
				
		str = str.replaceAll("[^a-zA-Z0-9éêèëÉÊÈËôÔùÙàÀîÎïÏçÇ\\_]"," ");	

		str = str.toLowerCase();
		
		String[] words = str.split(" ");
		Map<String, Integer> occurrence = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
		for(String w : words) {
			if(w.isEmpty()) continue;
			Integer n = occurrence.get(w);
			if(n==null) n=1;
			else n++;
			occurrence.put(w, n);
		}
		List<Map.Entry<String,Integer>> list = SortByValue(occurrence, outputFile);
		return list;
	}
	
	/**
	 * Question 6: select 50 plus utilise
	 * @param list
	 * @param outputFile
	 * @throws IOException
	 */
	public static void Select50(List<Map.Entry<String,Integer>> list, String outputFile) throws IOException {
		File fout = new File(outputFile+".txt");
		FileOutputStream fos = new FileOutputStream(fout);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
		System.out.println("----------------"+ outputFile +": les 50 mots les plus utilisés----------------");
		bw.write("----------------"+ outputFile +": les 50 mots les plus utilisés----------------");
		bw.newLine();
		int i=50;
		for (Map.Entry<String, Integer> entry: list) {
			System.out.println(entry.getKey() + " " + entry.getValue());
			bw.write(entry.getKey() + " " + entry.getValue());
			i--;
			bw.newLine();
			if(i<0) break;
		}
		bw.close();
	}

	/**
	 * Question 7: On filtre les pronoms et conjonctions
	 * @param list
	 * @param outputFile
	 * @throws IOException
	 */
	public static void FilterProCon(List<Map.Entry<String,Integer>> list, String outputFile) throws IOException {
	    File fout = new File(outputFile+".txt");
		FileOutputStream fos = new FileOutputStream(fout);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
		
		String[] ProCons =  {"le","la","les","on","l","je","tu","il","elle","nous","vous","ils","elles",
				"lui","leur","eux","qui","que","quoi","dont","où","mais","ou","et","donc","or","ni","car"};
	    Set<String> mySet = new HashSet<String>(Arrays.asList(ProCons));
	    
		System.out.println("----------------"+ outputFile +": filtre les pronoms et conjonctions----------------");
		bw.write("----------------"+ outputFile +": filtre les pronoms et conjonctions----------------");
		bw.newLine();
		for (Map.Entry<String, Integer> entry: list) {
			if(!mySet.contains(entry.getKey())) {
				System.out.println(entry.getKey() + " " + entry.getValue());
				bw.write(entry.getKey() + " " + entry.getValue());
				bw.newLine();
			}
		}
		bw.close();
	}

	/**
	 * Question 8: On filtre les mots bizarres
	 * @param list
	 * @param outputFile
	 * @throws IOException
	 */
	public static void FilterBizzares50(List<Map.Entry<String,Integer>> list, String outputFile) throws IOException {
	    File fout = new File(outputFile+".txt");
		FileOutputStream fos = new FileOutputStream(fout);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
		
		String[] ProCons =  {"le","la","les","on","l","je","tu","il","elle","nous","vous","ils","elles",
				"lui","leur","eux","qui","que","quoi","dont","où","mais","ou","et","donc","or","ni",
				"car","ce","qu","d","s","tout","tous","si"};
		String[] Bizzares = {"II"};  
		String[] WORDS = new String[ProCons.length + Bizzares.length];
		System.arraycopy(ProCons, 0, WORDS, 0, ProCons.length);
		System.arraycopy(Bizzares, 0, WORDS, ProCons.length, Bizzares.length);
		Set<String> mySet = new HashSet<String>(Arrays.asList(WORDS));
		
		System.out.println("----------------"+ outputFile +": filtre les mots bizarres----------------");
		bw.write("----------------"+ outputFile +": filtre les mots bizarres----------------");
		bw.newLine();
		int i=50;
		for (Map.Entry<String, Integer> entry: list) {
			if(mySet.contains(entry.getKey())) continue;
			if(entry.getKey().matches(".*\\d+.*")) continue;
			System.out.println(entry.getKey() + " " + entry.getValue());
			bw.write(entry.getKey() + " " + entry.getValue());
			i--;
			bw.newLine();
			if(i<0) break;
		}
		bw.close();
	}

	/**
	 * Question 9: Read, nyttoyer, filtering pros/cons and bizzares, select 50
	 * @param fileName
	 * @param outputFile
	 * @return
	 * @throws IOException
	 */
	public static List<Map.Entry<String,Integer>> Occurrence_Clean_2(String fileName, String outputFile) throws IOException {
		File fout = new File(outputFile+".txt");
		FileOutputStream fos = new FileOutputStream(fout);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
		System.out.println("----------------"+ outputFile +"----------------");
		bw.write("----------------"+ outputFile +"----------------");
		
		long startTime = System.currentTimeMillis();
		long startTimeRead = System.currentTimeMillis();
		byte[] encoded = new byte[0];
		encoded = Files.readAllBytes(Paths.get(fileName));
		String str = new String(encoded);
		
		str = str.replaceAll("[^a-zA-Z0-9éêèëÉÊÈËôÔùÙàÀîÎïÏçÇ\\-\\_]"," ");
		str = str.toLowerCase();
		
		long endTimeRead  = System.currentTimeMillis();
		long totalTimeRead = endTimeRead - startTimeRead; 
		
		long startTimeSplit = System.currentTimeMillis();
		String[] ProCons =  {"le","la","les","on","l","je","tu","il","elle","nous","vous","ils","elles",
				"lui","leur","eux","qui","que","quoi","dont","où","mais","ou","et","donc","or","ni",
				"car","ce","qu","d","s","tout","tous","si","cette"};
		String[] Bizzares = {"II","de","des","à","du","en","un","une","dans","est","par","au","sont",
				"sur","ne","a","être","ses","son","pas","ii","ier","n","-","_"};
		Set<String> mySet_ProCons = new HashSet<String>(Arrays.asList(ProCons));
		Set<String> mySet_Bizzares = new HashSet<String>(Arrays.asList(Bizzares));
		Set<String> mySet = new HashSet<String>();
		mySet.addAll(mySet_ProCons);
		mySet.addAll(mySet_Bizzares);

		String[] words = str.split(" ");
		long endTimeSplit  = System.currentTimeMillis();
		long totalTimeSplit = endTimeSplit - startTimeSplit; 
		
		long startTimeAdd = System.currentTimeMillis();
		Map<String, Integer> occurrence = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
		for(String w : words) {
			if(mySet.contains(w)) continue;
			if(w.matches(".*\\d+.*")) continue;
			if(w.isEmpty()) continue;
			Integer n = occurrence.get(w);
			if(n==null) n=1;
			else n++;
			occurrence.put(w, n);
		}
		long endTimeAdd  = System.currentTimeMillis();
		long totalTimeAdd = endTimeAdd - startTimeAdd;
		
		long startTimeTri = System.currentTimeMillis();
//		Descending Comparator
		Comparator<Map.Entry<String, Integer>> valueComparator = new Comparator<Map.Entry<String, Integer>>() {
			@Override
			public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
				return o2.getValue() - o1.getValue();
			}
		};
//		Map to List
		List<Map.Entry<String,Integer>> list = new ArrayList<Map.Entry<String, Integer>>(occurrence.entrySet());
//		Sort
		Collections.sort(list, valueComparator);
		long endTimeTri   = System.currentTimeMillis();
		long totalTimeTri = endTimeTri - startTimeTri;
		
		bw.newLine();
		int i=50;
		for (Map.Entry<String, Integer> entry: list) {
			System.out.println(entry.getKey() + " " + entry.getValue());
			bw.write(entry.getKey() + " " + entry.getValue());
			i--;
			bw.newLine();
			if(i<0) break;
		}
		long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;

		System.out.println("\n----------Time Use----------");
		System.out.println("Time Lire et Nettoyer: " + totalTimeRead/1000. + " s");
		System.out.println("Time Split: " + totalTimeSplit/1000. + " s");
		System.out.println("Time filtrer and Compter: " + totalTimeAdd/1000. + " s");
		System.out.println("Time Tri: " + totalTimeTri/1000. + " s");
		System.out.println("TotalTime: "+totalTime/1000. + " s");

		bw.write("\n----------Time Use----------");
		bw.write("Time Lire et Nettoyer: " + totalTimeRead/1000. + " s");
		bw.write("Time Split: " + totalTimeSplit/1000. + " s");
		bw.write("Time filtrer and Compter: " + totalTimeAdd/1000. + " s");
		bw.write("Time Tri: " + totalTimeTri/1000. + " s");
		bw.write("TotalTime: "+totalTime/1000. + " s");

		bw.close();
		return list;
	}
	
	public static List<Map.Entry<String,Integer>> Occurrence_Clean_SansNyt(String fileName, String outputFile) throws IOException {
		File fout = new File(outputFile+".txt");
		FileOutputStream fos = new FileOutputStream(fout);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
		System.out.println("----------------"+ outputFile +"----------------");
		bw.write("----------------"+ outputFile +"----------------");
		
		long startTime = System.currentTimeMillis();
		String str = new String();
		String line = null;
		FileReader fileReader = new FileReader(fileName+".txt");
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		
		long startTimeRead = System.currentTimeMillis();
		while((line = bufferedReader.readLine()) != null) {
			if(!line.isEmpty()) {
//				line = line.replaceAll("[^a-zA-Z0-9\\-\\_]"," ");
				str += line + " ";
			}
		}
		bufferedReader.close();
		str = str.toLowerCase();
		String[] words = str.split(" ");
		long endTimeRead  = System.currentTimeMillis();
		long totalTimeRead = endTimeRead - startTimeRead;
		
		long startTimeAdd = System.currentTimeMillis();
		Map<String, Integer> occurrence = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
		for(String w : words) {
//			if(w.matches(".*\\d+.*")) continue;
			if(w.isEmpty()) continue;
			Integer n = occurrence.get(w);
			if(n==null) n=1;
			else n++;
			occurrence.put(w, n);
		}
		long endTimeAdd  = System.currentTimeMillis();
		long totalTimeAdd = endTimeAdd - startTimeAdd;
		
		long startTimeTri = System.currentTimeMillis();
//		Descending Comparator
		Comparator<Map.Entry<String, Integer>> valueComparator = new Comparator<Map.Entry<String, Integer>>() {
			@Override
			public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
				return o2.getValue() - o1.getValue();
			}
		};
//		Map to List
		List<Map.Entry<String,Integer>> list = new ArrayList<Map.Entry<String, Integer>>(occurrence.entrySet());
//		Sort
		Collections.sort(list, valueComparator);
		long endTimeTri   = System.currentTimeMillis();
		long totalTimeTri = endTimeTri - startTimeTri;
		
		int i=50;
		bw.newLine();
		for (Map.Entry<String, Integer> entry: list) {
			System.out.println(entry.getKey() + " " + entry.getValue());
			bw.write(entry.getKey() + " " + entry.getValue());
			i--;
			bw.newLine();
			if(i<0) break;
		}
		long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;

		System.out.println("\n----------Time Use----------");
		System.out.println("Time Nettoyer: " + totalTimeRead/1000. + " s");
		System.out.println("Time filtrer and Compter: " + totalTimeAdd/1000. + " s");
		System.out.println("Time Tri: " + totalTimeTri/1000. + " s");
		System.out.println("TotalTime: "+totalTime/1000. + " s");

		bw.write("\n----------Time Use----------");
		bw.write("Time Nettoyer: " + totalTimeRead/1000. + " s");
		bw.write("Time filtrer and Compter: " + totalTimeAdd/1000. + " s");
		bw.write("Time Tri: " + totalTimeTri/1000. + " s");
		bw.write("TotalTime: "+totalTime/1000. + " s");

		bw.close();
		return list;
	}
	
}


