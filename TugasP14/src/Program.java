import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.Date;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.mysql.cj.protocol.Resultset;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;

public class Program {

    static Connection conn;

    public static void main(String[] args) throws Exception {

        Scanner terimaInput = new Scanner (System.in);
        String pilihanUser;
        boolean isLanjutkan = true;

        String url = "jdbc:mysql://localhost:3306/db_pegawai";
        try{

        Date tanggal = new Date();
        DateFormat formatTanggal = new SimpleDateFormat("dd MMMM yyyy");
        DateFormat formatJam = new SimpleDateFormat("hh:mm:ss");

        String salamSapa = "Halo! Semoga Harimu Menyenangkan!";
        String sapa = salamSapa.replace("Halo", "Hai"); //method replace()
        System.out.println(sapa);

        System.out.println("Tanggal \t: " + formatTanggal.format(tanggal));
		System.out.println("Waktu \t\t: " + formatJam.format(tanggal));
		Class.forName("com.mysql.cj.jdbc.Driver");
		conn = DriverManager.getConnection(url,"root","");
		System.out.println("Class Driver ditemukan!!!");

		Gaji gaji = new Gaji();

        while (isLanjutkan) {
            System.out.println("------------------------");
            System.out.println("Data Gaji Pegawai PT ABC");
            System.out.println("------------------------");
            System.out.println("1. Lihat Data Pegawai");
            System.out.println("2. Tambah Data Pegawai");
            System.out.println("3. Ubah Data Pegawai");
            System.out.println("4. Hapus Data Pegawai");
            System.out.println("5. Cari Data Pegawai");
            
            System.out.print("\nPilihan anda (1/2/3/4/5): ");
            pilihanUser = terimaInput.next();
            
            switch (pilihanUser) {
            case "1":
                gaji.lihatData();
                break;
            case "2":
                gaji.tambahData();
                break;
            case "3":
                gaji.ubahData();
                break;
            case "4":
                gaji.delete();
                break;
            case "5":
                gaji.cariData();
                break;
            default:
                System.err.println("\nInput anda tidak ditemukan\nSilakan pilih [1-5]");
            }
            
            System.out.print("\nApakah Anda ingin melanjutkan [y/n]? ");
            pilihanUser = terimaInput.next();
            isLanjutkan = pilihanUser.equalsIgnoreCase("y");

        }
        System.out.println("Program selesai:)");
        
    }
    catch(ClassNotFoundException ex) {
        System.err.println("Driver Error");
        System.exit(0);
    }
    catch(SQLException e){
        System.err.println("Tidak berhasil koneksi");
    }
}
}