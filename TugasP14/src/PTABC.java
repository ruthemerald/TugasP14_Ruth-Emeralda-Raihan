import java.sql.*;

    public interface PTABC {
        void lihatData() throws SQLException;
        void tambahData() throws SQLException;
        void ubahData() throws SQLException;
        void delete();
        void cariData() throws SQLException;
    }