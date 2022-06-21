package tqs.project.controller;

import java.util.HashMap;
import java.util.Map;

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
import tqs.project.model.Rider;
import tqs.project.model.Store;
import tqs.project.service.OrderService;
import tqs.project.service.RiderService;
import tqs.project.service.StoreService;

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

    @Autowired
    private StoreService storeservice;

    @GetMapping(value = {"/{storeId}", ""})
    public ResponseEntity<StatisticDTO> getStatistics(@PathVariable(required = false) Long storeId) {
        log.info("GET Request -> Statistic data");

        StatisticDTO stats = new StatisticDTO();

        if (storeId == null){
            stats.setNumOrders(orderservice.getAllOrders().size());
            stats.setCompletedOrders(orderservice.getAllOrdersByStatus(2).size());
            stats.setNumRiders(riderservice.getAllRiders().size());

            Map<String, Integer> store_orders = new HashMap<>();
            Map<String, Integer> store_comp_orders = new HashMap<>();
            Map<String, Double> rider_reviews = new HashMap<>();
            
            for (Store store: storeservice.getAllStores()) {
                store_orders.put(store.getName(), orderservice.getAllOrdersByStoreId(store.getStoreId()).size());
                store_comp_orders.put(store.getName(), orderservice.getAllOrdersByStoreIdAndStatus(store.getStoreId(), 2).size());
            }
            for (Rider rider: riderservice.getAllRiders()) {
                double sum = rider.getTotalReviews() == 0 ? 0 : (double) rider.getReviewSum() /  rider.getTotalReviews();
                rider_reviews.put(rider.getUser().getUsername(), sum);
            }

            stats.setCompOrderByStore(store_comp_orders);
            stats.setOrderByStore(store_orders);
            stats.setReviewPerRider(rider_reviews);

        }else{
            stats.setNumOrders(orderservice.getAllOrdersByStoreId(storeId).size());
            stats.setCompletedOrders(orderservice.getAllOrdersByStoreIdAndStatus(storeId, 2).size());
        }


        return new ResponseEntity<>(stats, HttpStatus.OK);
    }
}