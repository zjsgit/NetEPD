package cn.edu.bioinformatics.netepd.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.junit.Test;

public class EpFinderTestData {
	
	@Test
	public void testTime() throws ParseException {
        
        Date date = new Date();
        System.out.println(date.getTime());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = sdf.format(date);
		System.out.println(str);
        
        long time = new SimpleDateFormat("HH:mm:ss yyyy/MM/dd").parse(str).getTime();
        System.out.println(time);
	}
	
	@Test
	public void testJava() {
		String[] abc = {"aaa","bbb","ccc"};
		System.out.println(Arrays.asList(abc));
	}
	
	@Test
	public void readFile() throws IOException {
		String pathname = "D:\\Program Files\\Apache Software Foundation\\"
				+ "Tomcat 8.0\\wtpwebapps\\EssentialProteinFinder\\upload"
				+ "/test.txt";
		File file = new File(pathname);
		InputStreamReader isr = new InputStreamReader(new FileInputStream(file),"UTF-8");
		BufferedReader br = new BufferedReader(isr);
		String rowStr = null;
		int countRow = 0;
		while((rowStr=br.readLine())!=null) {
			String[] arr = rowStr.split("\\t");
			System.out.println(arr[0]+";"+arr[1]+";"+arr[2]);
			countRow++;
		}
		System.out.println(countRow);
		br.close();
	}

}
