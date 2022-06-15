package tqs.project.controllerTests;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import tqs.project.controller.OrderController;
import tqs.project.model.Order;
import tqs.project.service.OrderService;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {
    
    @Autowired
    private MockMvc mvc;

    @MockBean
    private OrderService orderService;

    Order order1, order2, order3;
    List<Order> orders;

    @BeforeEach
    void setUp(){
        RestAssuredMockMvc.mockMvc( mvc );
        order1 = new Order(1, "Client 1", new Date(), 1, 5);
        order1.setStatus(0);
        order2 = new Order(2, "Client 2", new Date(), 2, 3);
        order2.setStatus(2);
        order3 = new Order(3, "Client 3", new Date(), 3, 4);
        order3.setStatus(2);

        orders = new ArrayList<>(Arrays.asList(order1, order2, order3));
    }

    @Test
    void test_GetAllOrders_ReturnsCorrectOrders(){
        when(orderService.getAllOrders()).thenReturn(orders);

        given().get("/api/order")
               .then().log().body().assertThat()
               .contentType(ContentType.JSON).and()
               .status(HttpStatus.OK).and()
               .body("size()", is(3))
               .body("[0].externalOrderId", is( (int) order1.getExternalOrderId())).and()
               .body("[0].clientName", is(order1.getClientName())).and()
               .body("[1].externalOrderId", is( (int) order2.getExternalOrderId())).and()
               .body("[1].clientName", is(order2.getClientName())).and()
               .body("[2].externalOrderId", is( (int) order3.getExternalOrderId())).and()
               .body("[2].clientName", is(order3.getClientName()));
    }
}
