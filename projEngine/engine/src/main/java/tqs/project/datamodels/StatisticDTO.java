package tqs.project.datamodels;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StatisticDTO {

    private int numOrders;

    private int numRiders;

    private int completedOrders;

    private Map<String, Integer> orderByStore;

    private Map<String, Integer> compOrderByStore;

    private Map<String, Double> reviewPerRider;
}