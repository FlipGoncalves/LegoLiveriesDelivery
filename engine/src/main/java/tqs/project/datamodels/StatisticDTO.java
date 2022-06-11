package tqs.project.datamodels;

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
}