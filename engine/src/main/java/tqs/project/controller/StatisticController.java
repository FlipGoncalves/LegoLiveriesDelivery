package tqs.project.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tqs.project.datamodels.StatisticDTO;
import tqs.project.model.Order;
import tqs.project.service.OrderService;
import tqs.project.service.RiderService;

@RestController
@RequestMapping("/api/statistics")
@Validated
@CrossOrigin
public class StatisticController {
    private static final Logger log = LoggerFactory.getLogger(StatisticController.class);

    @Autowired
    private RiderService riderservice;

    @Autowired
    private OrderService orderservice;

    @GetMapping(value = {"/{storeId}", ""})
    public ResponseEntity<StatisticDTO> getStatistics(@PathVariable(required = false) Long storeId) {
        log.info("GET Request -> Statistic data");

        StatisticDTO stats = new StatisticDTO();

        if (storeId == null){
            stats.setNumOrders(orderservice.getAllOrders().size());
            stats.setNumRiders(riderservice.getAllData().size());
            stats.setCompletedOrders(orderservice.getAllOrdersByStatus(2).size());
        }else{
            stats.setNumOrders(orderservice.getAllOrdersByStoreId(storeId).size());
            stats.setCompletedOrders(orderservice.getAllOrdersByStoreIdAndStatus(storeId, 2).size());
        }
        return new ResponseEntity<>(stats, HttpStatus.OK);
    }
}