package Tests;

import DataAccessLayer.StockDAL.ProductDAO;
import DataAccessLayer.StockDAL.ProductUnitDAO;
import BusinessLayer.StockBusinessLayer.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductControllerTest {

    ProductController pd;
    Product TnuvaMilk9p;
    Product TaraMilk3p;
    Product testAmount;
    ProductDAO pDAO;
    ProductUnitDAO puDAO;

    @BeforeEach
    void setUp() throws Exception {
        pDAO = new ProductDAO();
        puDAO = new ProductUnitDAO();
        pd = new ProductController();
        TnuvaMilk9p = pd.addProduct("TnuvaMilk9p", "Tnuva", 50, 2, 9, 2);
        TaraMilk3p = pd.addProduct("TaraMilk3p", "Tara", 30, 2, 6, 2);
    }

    @AfterEach
    void tearDown() {
        try {
            for (ProductUnit unit : puDAO.getProductUnits(TaraMilk3p.getId())) {
                puDAO.delete(unit.getId());
            }
            for (ProductUnit unit : puDAO.getProductUnits(TnuvaMilk9p.getId())) {
                puDAO.delete(unit.getId());
            }
            TnuvaMilk9p.setAmount(0);
            TaraMilk3p.setAmount(0);
            pd.removeProduct(TnuvaMilk9p.getId());
            pd.removeProduct(TaraMilk3p.getId());
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void getProduct() {
        assertEquals(pd.getProduct(TaraMilk3p.getId()),TaraMilk3p);
        assertNotEquals(pd.getProducts(TaraMilk3p.getId()), TnuvaMilk9p);
    }

    @Test
    void addProduct() throws Exception {
        Product test = pd.addProduct("test", "Tnuva", 40, 2, 7, 2);
        assertEquals(pd.getProduct(test.getId()).getId(), test.getId());
        pd.removeProduct(test.getId());
    }

    @Test
    void changeProductCategory() {
        assertEquals(TnuvaMilk9p.getCategoryId(), 2);
        pd.changeProductCategory(TnuvaMilk9p.getId(), 3);
        assertNotEquals(TnuvaMilk9p.getCategoryId(),2);
        assertEquals(TnuvaMilk9p.getCategoryId(), 3);
    }

    @Test
    void addAmount() throws Exception {
        testAmount = pd.addProduct("testAmount", "test", 50, 5, 12, 2);
        assertEquals(testAmount.getAmount(), 0);
        pd.addAmount(testAmount.getId(), 50);
        assertEquals(testAmount.getAmount(), 50);
        for (ProductUnit unit : puDAO.getProductUnits(testAmount.getId())) {
            puDAO.delete(unit.getId());
        }
        testAmount.setAmount(0);
        pd.removeProduct(testAmount.getId());
    }

    @Test
    void reduceAmount() throws Exception {
        testAmount = pd.addProduct("testAmount", "test", 50, 5, 12, 2);
        assertEquals(testAmount.getAmount(), 0);
        pd.addAmount(testAmount.getId(), 50);
        pd.reduceAmount(testAmount.getId());
        assertEquals(testAmount.getAmount(), 49);
        for (ProductUnit unit : puDAO.getProductUnits(testAmount.getId())) {
            puDAO.delete(unit.getId());
        }
        testAmount.setAmount(0);
        pd.removeProduct(testAmount.getId());
    }

    @Test
    void underMinimumQuantities() throws Exception {
        testAmount = pd.addProduct("testAmount", "test", 50, 5, 12, 2);
        List<Product> minQuanProd = pd.underMinimumQuantities();
        assertTrue(minQuanProd.contains(testAmount));
        pd.addAmount(testAmount.getId(), 60);
        minQuanProd = pd.underMinimumQuantities();
        assertFalse(minQuanProd.contains(testAmount));
        for (ProductUnit unit : puDAO.getProductUnits(testAmount.getId())) {
            puDAO.delete(unit.getId());
        }
        testAmount.setAmount(0);
        pd.removeProduct(testAmount.getId());
    }

    @Test
    void getProducts() {
        List<Product> listProd = pd.getProducts(2);
        int size = listProd.size();
        pd.changeProductCategory(TnuvaMilk9p.getId(), 1);
        listProd = pd.getProducts(2);
        assertNotEquals(size, listProd.size());
        assertEquals(size-1, listProd.size());
    }

    @Test
    void setSaleDiscount() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("d-M-y");
        int originalSaleID = pd.getProduct(TnuvaMilk9p.getId()).getSaleDiscountId();
        SaleDiscount sd = new SaleDiscount(formatter.parse("26-01-2022"), formatter.parse("26-10-2022"), 25);
        DiscountController dc = new DiscountController();
        dc.addSaleDiscount(sd);
        pd.setSaleDiscount(TnuvaMilk9p.getId(), sd.getId());
        assertNotEquals(pd.getProduct(TnuvaMilk9p.getId()).getSaleDiscountId(), originalSaleID);
    }
}