package tqs.project.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tqs.project.datamodels.StatisticDTO;
import tqs.project.service.OrderService;
import tqs.project.service.RiderService;

@RestController
@RequestMapping("/api")
@Validated
@CrossOrigin
public class StatisticController {
    private static final Logger log = LoggerFactory.getLogger(StatisticController.class);

    @Autowired
    private RiderService riderservice;

    @Autowired
    private OrderService orderservice;

    @GetMapping("/statistics")
    public ResponseEntity<Object> getStatistics() {
        log.info("GET Request -> Statistic data");

        StatisticDTO stats = new StatisticDTO();

        stats.setNumorders(orderservice.getAllOrders().size());
        stats.setNumriders(riderservice.getAllData().size());
        stats.setCompletedorders(2);
        stats.setSales(50);

        return new ResponseEntity<>(stats, HttpStatus.OK);
    }
}