package pap.proj.papdemo;
import databaseservice.DatabaseController;

import databaseservice.models.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@SpringBootApplication
@Configuration
@ComponentScan(basePackages = "databaseservice")
public class PapDemoApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(PapDemoApplication.class, args);
		DatabaseController database = context.getBean(DatabaseController.class);

		/* PRZYKŁAD UŻYCIA BAZY DANYCH

		Student student1 = new Student("123456", "Maciej", "Scheffer", "ms@wp.pl");
		Student student2 = new Student("654321", "Roman", "Kowalski", "rk@wp.pl");
		Student student3 = new Student("424242", "Marian", "Męczypała", mmk@wp.pl");
		Student student3 = new Student("757575", "Robety", "Grzyb", "rg@wp.pl");
		Sesja sesja1 = new Sesja("123456","abcabc","cbacba");
		Sesja sesja2 = new Sesja("654321","aabbcc","ccbbaa");
		Logowanie logowanie1 = new Logowanie("yaya", "hoho");
		Logowanie logowanie2 = new Logowanie("ayay", "ohoh");
		Wymiana wymiana1 = new Wymiana("123456", "SOI", "pn", "14:30" ,"pn" , "17:15");
		Wymiana wymiana2 = new Wymiana("123456", "WSI", "pt", "11:00" ,"wt" , "13:00");
		Wymiana wymiana3 = new Wymiana("123456", "PAP", "wt", "10:30" ,"śr" , "16:30");
		Wymiana wymiana4 = new Wymiana("654321", "PROB", "śr", "8:15" ,"nd" , "8:30");
		Wymiana wymiana5 = new Wymiana("654321", "SOI", "cw", "13:45" ,"pt" , "12:40");
		Wymiana wymiana6 = new Wymiana("654321", "BD1", "pn", "16:00" ,"wt" , "13:30");
		Wymiana wymiana7 = new Wymiana("424242", "WSI", "pn", "15:20" ,"śr" , "17:50");
		Wymiana wymiana8 = new Wymiana("424242", "AISDI", "wt", "17:10" ,"cw" , "20:30");
		Wymiana wymiana9 = new Wymiana("424242", "ARKO", "pt", "7:10" ,"pn" , "7:30");
		Wymiana wymiana10 = new Wymiana("757575", "SKM", "pt", "8:15" ,"śr" , "8:10");
		Wymiana wymiana11 = new Wymiana("757575", "WAET", "cw", "20:00" ,"pt" , "10:20");
		Wymiana wymiana12 = new Wymiana("757575", "PIPR", "pn", "15:15" ,"wt" , "15:10");

		database.AddStudent(student1);
		database.AddStudent(student2);
		database.AddStudent(student3);
		database.AddSesja(sesja1);
		database.AddSesja(sesja2);
		database.AddLogowanie(logowanie1);
		database.AddLogowanie(logowanie2);
		database.AddWymiana(wymiana1);
		database.AddWymiana(wymiana2);
		database.AddWymiana(wymiana3);
		database.AddWymiana(wymiana4);
		database.AddWymiana(wymiana5);
		database.AddWymiana(wymiana6);
		database.AddWymiana(wymiana7);
		database.AddWymiana(wymiana8);
		database.AddWymiana(wymiana9);
		database.AddWymiana(wymiana10);
		database.AddWymiana(wymiana11);
		database.AddWymiana(wymiana12);

		System.out.println(database.FindWymianyIdsByToken("654321"));
		*/
	}
}
