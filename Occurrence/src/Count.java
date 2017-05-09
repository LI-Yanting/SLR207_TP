import java.io.*;
import java.util.*;
import java.util.Map.Entry;

public class Count {
	public static void main(String args[]) throws IOException {
		Map<String, Integer> occurrence = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
		List<Map.Entry<String,Integer>> list;
//		Question 1:
		occurrence = Occurrence_1("input", "Question 1");		
//		Question 2,3: 
		list = SortByValue(occurrence,"input","Question 3");
		
//		Question 4:
		Occurrence("forestier_mayotte","Question 4");
		
//		Question 5:
		list = Occurrence_Clean("forestier_mayotte","Question 5");
//		Question 6:
		Select50(list, "Question 6");
//		Question 7:
		FilterProCon(list,"Question 7");
//		Question 8:
		FilterBizzares(list,"Question 8");
	}
	
	public static Map<String, Integer> Occurrence_1(String fileName, String outputFile) throws IOException {
		String str = new String();
		String line = null;
		FileReader fileReader = new FileReader(fileName+".txt");
		BufferedReader bufferedReader = new BufferedReader(fileReader);
				
		while((line = bufferedReader.readLine()) != null) {
			str = str + line + " ";
			str.replaceAll("\\pP\\pS\\d+", " ");
		}
		
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
		
		System.out.println("--------"+ outputFile +": TreeMap key ascending--------");
		bw.write("--------"+ outputFile +": TreeMap key ascending--------");
		bw.newLine();
		for (Entry<String, Integer> entry : occurrence.entrySet()) {
			System.out.println(entry.getKey() + " " + entry.getValue());
			bw.write(entry.getKey() + " " + entry.getValue());
			bw.newLine();
		}
		bufferedReader.close();
		bw.close();
		return occurrence;
	}
	
	public static List<Map.Entry<String,Integer>> SortByValue(Map<String, Integer> occurrence, 
			String fileName, String outputFile) throws IOException {
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
		System.out.println("--------"+ outputFile +":--------");
		bw.write("--------"+ outputFile +":--------");
		bw.newLine();
		for (Map.Entry<String, Integer> entry: listOccu) {
			System.out.println(entry.getKey() + " " + entry.getValue());
			bw.write(entry.getKey() + " " + entry.getValue());
			bw.newLine();
		}
		
		bw.close();
		return listOccu;
	}
	
	public static void Occurrence(String fileName, String outputFile) throws IOException {
		String str = new String();
		String line = null;
		FileReader fileReader = new FileReader(fileName+".txt");
		BufferedReader bufferedReader = new BufferedReader(fileReader);
				
		while((line = bufferedReader.readLine()) != null) {
			str = str + line + " ";
		}
		
		String[] words = str.split(" ");
		Map<String, Integer> occurrence = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
		for(String w : words) {
			Integer n = occurrence.get(w);
			if(n==null) n=1;
			else n++;
			occurrence.put(w, n);
		}
		List<Map.Entry<String,Integer>> list = SortByValue(occurrence, fileName, outputFile);
		bufferedReader.close();
	}
	
	public static List<Map.Entry<String,Integer>> Occurrence_Clean(String fileName, String outputFile) throws IOException {
		String str = new String();
		String line = null;
		FileReader fileReader = new FileReader(fileName+".txt");
		BufferedReader bufferedReader = new BufferedReader(fileReader);
				
		while((line = bufferedReader.readLine()) != null) {
//			System.out.println(line);
			str = str + line + " ";
			str = str.replaceAll("[^a-zA-Z0-9éêèëÉÊÈËôÔùÙàÀîÎïÏçÇ]"," ");
		}
		
		String[] words = str.split(" ");
		Map<String, Integer> occurrence = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
		for(String w : words) {
			Integer n = occurrence.get(w);
			if(n==null) n=1;
			else n++;
			occurrence.put(w, n);
		}
		List<Map.Entry<String,Integer>> list = SortByValue(occurrence, fileName, outputFile);
		bufferedReader.close();
		return list;
	}
	
	public static void Select50(List<Map.Entry<String,Integer>> list, String outputFile) throws IOException {
		File fout = new File(outputFile+".txt");
		FileOutputStream fos = new FileOutputStream(fout);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
		System.out.println("--------"+ outputFile +": les 50 mots les plus utilisés--------");
		bw.write("--------"+ outputFile +": les 50 mots les plus utilisés--------");
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

	public static void FilterProCon(List<Map.Entry<String,Integer>> list, String outputFile) throws IOException {
	    File fout = new File(outputFile+".txt");
		FileOutputStream fos = new FileOutputStream(fout);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
		
		String[] ProCons =  {"le","la","les","on","l","je","tu","il","elle","nous","vous","ils","elles",
				"lui","leur","eux","qui","que","quoi","dont","où","mais","ou","et","donc","or","ni","car"};
	    Set<String> mySet = new HashSet<String>(Arrays.asList(ProCons));
	    
		System.out.println("--------"+ outputFile +": filtre les pronoms et conjonctions--------");
		bw.write("--------"+ outputFile +": filtre les pronoms et conjonctions--------");
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

	public static void FilterBizzares(List<Map.Entry<String,Integer>> list, String outputFile) throws IOException {
	    File fout = new File(outputFile+".txt");
		FileOutputStream fos = new FileOutputStream(fout);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
		
		String[] ProCons =  {"le","la","les","on","l","je","tu","il","elle","nous","vous","ils","elles",
				"lui","leur","eux","qui","que","quoi","dont","où","mais","ou","et","donc","or","ni","car"};
		String[] Bizzares = {"II"};  
		String[] WORDS = new String[ProCons.length + Bizzares.length];
		System.arraycopy(ProCons, 0, WORDS, 0, ProCons.length);
		System.arraycopy(Bizzares, 0, WORDS, ProCons.length, Bizzares.length);
		Set<String> mySet = new HashSet<String>(Arrays.asList(WORDS));
		
		System.out.println("--------"+ outputFile +": filtre les mots bizarres--------");
		bw.write("--------"+ outputFile +": filtre les mots bizarres--------");
		bw.newLine();
		for (Map.Entry<String, Integer> entry: list) {
			if(mySet.contains(entry.getKey())) continue;
			if(entry.getKey().matches(".*\\d+.*")) continue;
			System.out.println(entry.getKey() + " " + entry.getValue());
			bw.write(entry.getKey() + " " + entry.getValue());
			bw.newLine();
		}
		bw.close();
	}
}


