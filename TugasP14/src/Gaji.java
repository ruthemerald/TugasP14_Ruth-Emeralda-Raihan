import java.util.Scanner;
import java.util.InputMismatchException;

import com.mysql.cj.protocol.Resultset;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;

public class Gaji implements PTABC {

    static Connection conn;
	String url = "jdbc:mysql://localhost:3306/db_pegawai";

    String namaPegawai, jabatan;
    Integer noPegawai, potongan, gajiPokok, jumlahHadir, totalGaji, jumlahAbsen;

    Scanner scan = new Scanner (System.in);

    public void lihatData() throws SQLException {
		String teks1 = "\nDaftar Data Pegawai\n";
		System.out.println(teks1.toUpperCase());
						
		String sql ="SELECT * FROM pegawai";
		conn = DriverManager.getConnection(url,"root","");
		Statement statement = conn.createStatement();
		ResultSet result = statement.executeQuery(sql);
		
		while(result.next()){
			System.out.print("\nNomor pegawai\t  : ");
            System.out.print(result.getInt("no_pegawai"));
            System.out.print("\nNama pegawai\t  : ");
            System.out.print(result.getString("nama_pegawai"));
            System.out.print("\nJabatan\t\t  : ");
            System.out.print(result.getString("jabatan"));
            System.out.print("\nJumlah hari masuk : ");
            System.out.print(result.getInt("hari_masuk"));
            System.out.print("\nPotongan gaji\t  : ");
            System.out.print(result.getInt("potongan"));
            System.out.print("\nTotal gaji\t  : ");
            System.out.print(result.getInt("total_gaji"));
            System.out.print("\n");
		}
	}

    public void tambahData() throws SQLException {
    	String teks2 = "\nTambah Data Pegawai\n";
		System.out.println(teks2.toUpperCase());
		
    	try {
            //Nomor Pegawai
            System.out.print("Nomor pegawai\t\t : ");
            noPegawai = scan.nextInt();
            
            //Nama Pegawai
            System.out.print("Nama pegawai\t\t : ");
            namaPegawai = scan.next();

            //Jabatan
            Integer pilihJabatan;
            System.out.println("List Jabatan : \n1.Direktur Utama\n2.Direktur\n3.Manajer\n4.Karyawan\n5.Magang");
            System.out.print("Pilih jabatan (1-5)\t : ");
            pilihJabatan = scan.nextInt();

                if (pilihJabatan == 1)
                {
                    jabatan = "Direktur Utama";
                }
                else if (pilihJabatan == 2) 
                {
                    jabatan = "Direktur";
                }
                else if (pilihJabatan == 3) 
                {
                    jabatan = "Manajer";
                }
                else if (pilihJabatan == 4) 
                {
                    jabatan = "Karyawan";
                }
                else if (pilihJabatan == 5) 
                {
                    jabatan = "Magang";
                }
                else
                {
                    System.out.println("Jabatan tidak tersedia");
                }
    
            //Gaji Pokok
            if (jabatan == "Direktur Utama")
            {
                gajiPokok = 75000000;
            }
            else if (jabatan == "Direktur")
            {
                gajiPokok = 50000000;
            }
            else if (jabatan == "Manajer")
            {
                gajiPokok = 35000000;
            }
            else if (jabatan == "Karyawan")
            {
                gajiPokok = 20000000;
            }
            else if (jabatan == "Magang")
            {
                gajiPokok = 10000000;
            }
            else{
	            System.out.println("\nGaji pokok tidak tersedia");
	        }

            //Jumlah Hari Masuk
            System.out.print("Jumlah Absen\t\t : ");
            jumlahAbsen = scan.nextInt();
            if (jumlahAbsen >30 || jumlahAbsen < 0)
            {
                throw new ArithmeticException("Data yang diinputkan salah! (hitungan dari 0-30 hari)");
            }
            else
            {
                jumlahHadir = 30 - jumlahAbsen;
            }
                
            //Potongan
            potongan = jumlahAbsen*150000;
            
            //Total Gaji        
            totalGaji = gajiPokok - potongan;

            String sql = "INSERT INTO pegawai (no_pegawai, nama_pegawai, jabatan, hari_masuk, potongan, total_gaji) VALUES ('"+noPegawai+"','"+namaPegawai+"','"+jabatan+"','"+jumlahHadir+"','"+potongan+"','"+totalGaji+"')";
	        conn = DriverManager.getConnection(url,"root","");
	        Statement statement = conn.createStatement();
	        statement.execute(sql);
	        System.out.println("Input Data Berhasil!");
    	}
    	catch (SQLException e) {
	        System.err.println("Terjadi kesalahan pada input data!");
	    } 
    	catch (InputMismatchException e) {
	    	System.err.println("Inputan harus berupa angka!");
	   	}
	} 
    
	public void ubahData() throws SQLException{
		String teks3 = "\nUbah Data Pegawai\n";
		System.out.println(teks3.toUpperCase());
		
		try {
            lihatData();
            System.out.print("\nMasukkan nomor pegawai yang akan di ubah : ");
            Integer noPegawai = Integer.parseInt(scan.nextLine());
            
            String sql = "SELECT * FROM pegawai WHERE no_pegawai = " +noPegawai;
            conn = DriverManager.getConnection(url,"root","");
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);
            
            if(result.next()){
                
                System.out.print("Nama baru ["+result.getString("nama_pegawai")+"]\t: ");
                String namaPegawai = scan.nextLine();
                   
                sql = "UPDATE pegawai SET nama_pegawai='"+namaPegawai+"' WHERE no_pegawai='"+noPegawai+"'";
                //System.out.println(sql);

                if(statement.executeUpdate(sql) > 0){
                    System.out.println("Berhasil memperbaharui data pegawai (Nomor "+noPegawai+")");
                }
            }
            statement.close();        
        } 
		catch (SQLException e) {
        	System.err.println("Terjadi kesalahan dalam mengedit data!");
            System.err.println(e.getMessage());
        }
	}
	
	public void delete() {
		String teks4 = "\nHapus Data Pegawai\n";
		System.out.println(teks4.toUpperCase());
		
		try{
	        lihatData();
	        System.out.print("\nMasukkan Nomor Pegawai yang akan Anda Hapus : ");
	        Integer noPegawai= Integer.parseInt(scan.nextLine());
	        
	        String sql = "DELETE FROM pegawai WHERE no_pegawai = "+ noPegawai;
	        conn = DriverManager.getConnection(url,"root","");
	        Statement statement = conn.createStatement();
	        //ResultSet result = statement.executeQuery(sql);
	        
	        if(statement.executeUpdate(sql) > 0){
	            System.out.println("Berhasil menghapus data pegawai (Nomor "+noPegawai+")");
	        }
	   }
		catch(SQLException e){
	        System.out.println("Terjadi kesalahan dalam menghapus data!");
	    }
        catch(Exception e){
            System.out.println("Masukkan data yang benar!");
        }
	}
	
	public void cariData() throws SQLException {
		String teks5 = "\nCari Data Pegawai\n";
		System.out.println(teks5.toUpperCase());
				
		System.out.print("Masukkan Nama Pegawai yang ingin dicari : ");    
		String keyword = scan.nextLine();
		
		String sql = "SELECT * FROM pegawai WHERE nama_pegawai LIKE '%"+keyword+"%'";
		conn = DriverManager.getConnection(url,"root","");
        Statement statement = conn.createStatement();
        ResultSet result = statement.executeQuery(sql); 
                
        while(result.next()){
        	System.out.print("\nNomor pegawai\t  : ");
            System.out.print(result.getInt("no_pegawai"));
            System.out.print("\nNama pegawai\t  : ");
            System.out.print(result.getString("nama_pegawai"));
            System.out.print("\nJabatan\t\t  : ");
            System.out.print(result.getString("jabatan"));
            System.out.print("\nJumlah hari masuk : ");
            System.out.print(result.getInt("hari_masuk"));
            System.out.print("\nPotongan gaji\t  : ");
            System.out.print(result.getInt("potongan"));
            System.out.print("\nTotal gaji\t  : ");
            System.out.print(result.getInt("total_gaji"));
            System.out.print("\n");
        }
	}   
}
            
