import java.io.*;
import java.util.*;
import java.util.Map.Entry;

public class Count {
	public static void main(String args[]) throws IOException {
		String str = new String();
		String line = null;
		FileReader fileReader = new FileReader("forestier_mayotte.txt");
		BufferedReader bufferedReader = new BufferedReader(fileReader);
				
		while((line = bufferedReader.readLine()) != null) {
//			line = line.trim();
			str = str + line + " ";
			str.replaceAll("\\pP\\pS\\d+", " ");
//			System.out.println(str);
		}
		
		String[] words = str.split(" ");
		Map<String, Integer> occurence = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
		for(String w : words) {
			Integer n = occurence.get(w);
			if(n==null) n=1;
			else n++;
			occurence.put(w, n);
		}
		
//		Write the result to file:
		File fout = new File("Q4.txt");
		FileOutputStream fos;
		fos = new FileOutputStream(fout);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
		
		System.out.println("--------Question 1: TreeMap key ascending--------");
		bw.write("--------Question 1: TreeMap key ascending--------");
		bw.newLine();
		for (Entry<String, Integer> entry : occurence.entrySet()) {
			System.out.println(entry.getKey() + " " + entry.getValue());
			bw.write(entry.getKey() + " " + entry.getValue());
			bw.newLine();
		}
		
//		Question 3: 
//		Descending Comparator
		Comparator<Map.Entry<String, Integer>> valueComparator = new Comparator<Map.Entry<String, Integer>>() {
			@Override
			public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
				return o2.getValue() - o1.getValue();
			}
		};
//		Map to List
		List<Map.Entry<String,Integer>> list = new ArrayList<Map.Entry<String, Integer>>(occurence.entrySet());
//		Sort
		Collections.sort(list, valueComparator);
		
//		Question 4:
//		It output not only words but also ":"
		
		System.out.println("--------Question 4:--------");
		bw.write("--------Question 4:--------");
		bw.newLine();
		for (Map.Entry<String, Integer> entry: list) {
			System.out.println(entry.getKey() + " " + entry.getValue());
			bw.write(entry.getKey() + " " + entry.getValue());
			bw.newLine();
		}
		
		bufferedReader.close();
		bw.close();
	}
}
