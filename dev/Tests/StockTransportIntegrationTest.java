package Tests;

import BusinessLayer.StockBusinessLayer.Product;
import BusinessLayer.StockBusinessLayer.ProductUnit;
import BusinessLayer.SupplierBusinessLayer.OrderController;
import DataAccessLayer.StockDAL.ProductDAO;
import DataAccessLayer.StockDAL.ProductUnitDAO;
import DataAccessLayer.SuppliersDAL.*;
import ServiceLayer.TransportServiceLayer.TransportService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StockTransportIntegrationTest {

    OrderController oc = OrderController.getInstance();
    TransportService transService =TransportService.getInstance();
    ProductUnitDAO unitDAO = new ProductUnitDAO();
    ProductDAO prodDAO = new ProductDAO();
    Product product;
    ProductUnit unit;
    SupplierDAO supplierDAO = new SupplierDAO();
    SupplierContractDAO scDAO = new SupplierContractDAO();
    QDDiscountDAO qddDAO = new QDDiscountDAO();
    SupplierProductDAO spDAO = new SupplierProductDAO();
    OrderLineDAO olDAO = new OrderLineDAO();
    ContactDAO cDAO = new ContactDAO();

    @BeforeEach
    void setUp() throws Exception {

    }

    @AfterEach
    void cleanData() {

    }

    @Test
    void makeShortageOrderAuto() {

    }
}
