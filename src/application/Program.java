package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

import entities.Product;

public class Program {
	public static void main(String[] args) {
		
		Locale.setDefault(Locale.US);
		Scanner sc =  new Scanner(System.in);
		
		List<Product> list = new ArrayList<>();
		
		System.out.print("Enter file path:");
		String path = sc.next();
		
		try(BufferedReader br = new BufferedReader(new FileReader(path))){
			String line = br.readLine();
			while(line != null) {
				String[] fields = line.split(",");
				list.add(new Product(fields[0],Double.parseDouble(fields[1])));
				line=br.readLine();
			}	
			double avg = list.stream().map(p -> p.getPrice()).reduce(0.0, (x,y) -> x+y)/list.size();
			System.out.println("Average Price : " + String.format("%.2f",avg));
			
			Comparator <String> comp = (s1,s2) -> s1.toUpperCase().compareTo(s2.toUpperCase());
			
			List<String> names = list.stream().filter(p -> p.getPrice() < avg).map(p -> p.getName())
					.sorted(comp.reversed()).collect(Collectors.toList());
			
			names.forEach(System.out::println);
			
		}catch(IOException e) {
			System.out.println("Error : " + e.getMessage());
		}
		sc.close();
	}
}
