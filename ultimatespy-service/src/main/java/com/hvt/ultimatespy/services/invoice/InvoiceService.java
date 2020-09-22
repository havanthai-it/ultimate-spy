package com.hvt.ultimatespy.services.invoice;

import com.hvt.ultimatespy.ds.Datasource;
import com.hvt.ultimatespy.models.invoice.Invoice;
import org.springframework.stereotype.Service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class InvoiceService {

    private static final Logger logger = Logger.getLogger(InvoiceService.class.getName());

    public CompletableFuture<Invoice> get(String id) {
        return CompletableFuture.supplyAsync(() -> {
            Invoice invoice = null;
            Connection conn = null;
            CallableStatement cs = null;
            ResultSet rs = null;
            String sql = "SELECT * FROM tb_invoice WHERE s_id = ?";
            try {
                conn = Datasource.getConnection();
                cs = conn.prepareCall(sql);
                cs.setString(1, id);
                rs = cs.executeQuery();
                if (rs != null && rs.next()) {
                    invoice = bindInvoice(rs);
                }
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "", e);
            } finally {
                Datasource.close(conn, cs, rs);
            }

            return invoice;
        });
    }

    public CompletableFuture<Invoice> insert(Invoice invoice) {
        return CompletableFuture.supplyAsync(() -> {
            Connection conn = null;
            CallableStatement cs = null;
            ResultSet rs = null;
            String sql = "INSERT INTO tb_invoice ( "
                    + " s_user_id, "
                    + " s_product_id, "
                    + " ) VALUES (?,?)"
                    + " WHERE s_id = ?";
            try {
                conn = Datasource.getConnection();
                cs = conn.prepareCall(sql);
                cs.setString(1, "");
                cs.setString(2, "");
                cs.execute();
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "", e);
            } finally {
                Datasource.close(conn, cs, rs);
            }

            return invoice;
        });
    }

    public CompletableFuture<Invoice> update(Invoice invoice) {
        return CompletableFuture.supplyAsync(() -> {
            Connection conn = null;
            CallableStatement cs = null;
            ResultSet rs = null;
            String sql = "INSERT INTO tb_invoice ( "
                    + " s_user_id, "
                    + " s_product_id, "
                    + " ) VALUES (?,?)"
                    + " WHERE s_id = ?";
            try {
                conn = Datasource.getConnection();
                cs = conn.prepareCall(sql);
                cs.setString(1, "");
                cs.setString(2, "");
                cs.execute();
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "", e);
            } finally {
                Datasource.close(conn, cs, rs);
            }

            return invoice;
        });
    }

    public CompletableFuture<Invoice> updateStatus(String id, String status) {
        return CompletableFuture.supplyAsync(() -> {
            Connection conn = null;
            CallableStatement cs = null;
            ResultSet rs = null;
            String sql = "INSERT INTO tb_invoice ( "
                    + " s_user_id, "
                    + " s_product_id, "
                    + " ) VALUES (?,?)"
                    + " WHERE s_id = ?";
            try {
                conn = Datasource.getConnection();
                cs = conn.prepareCall(sql);
                cs.setString(1, "");
                cs.setString(2, "");
                cs.execute();
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "", e);
            } finally {
                Datasource.close(conn, cs, rs);
            }

            return null;
        });
    }

    private static Invoice bindInvoice(ResultSet rs) {
        Invoice invoice = new Invoice();

        return invoice;
    }

}
